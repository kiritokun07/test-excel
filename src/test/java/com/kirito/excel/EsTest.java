package com.kirito.excel;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class EsTest {

    private RestHighLevelClient client;

    @BeforeEach
    void setup() {
        this.client = new RestHighLevelClient(
                RestClient.builder(HttpHost.create("http://localhost:9200"))
        );
    }

    @AfterEach
    void tearDown() throws IOException {
        this.client.close();
    }

    public static final String MAPPING_TEMPLATE= """
            {
                "mappings": {
                    "properties": {
                        "id": {
                            "type": "keyword"
                        },
                        "name": {
                            "type": "text",
                            "analyzer": "ik_max_word",
                            "copy_to": "all"
                        },
                        "city": {
                            "type": "keyword",
                            "copy_to": "all"
                        },
                        "address": {
                            "type": "keyword",
                            "index": false
                        },
                        "price": {
                            "type": "integer"
                        },
                        "score": {
                            "type": "integer"
                        },
                        "location": {
                            "type": "geo_point"
                        },
                        "all": {
                            "type": "text",
                            "analyzer": "ik_max_word"
                        }
                    }
                }
            }
            """;

    @Test
    public void testCreateHotelIndex() throws IOException {
        //1.创建Request对象
        CreateIndexRequest request = new CreateIndexRequest("hotel");
        //2.准备请求的参数：DSL语句
        request.source(MAPPING_TEMPLATE, XContentType.JSON);
        //3.发送请求
        client.indices().create(request, RequestOptions.DEFAULT);
    }

    @Test
    public void testDeleteHotelIndex() throws IOException {
        //1.创建Request对象
        DeleteIndexRequest request = new DeleteIndexRequest("hotel");
        //2.发送请求
        client.indices().delete(request, RequestOptions.DEFAULT);
    }

}
