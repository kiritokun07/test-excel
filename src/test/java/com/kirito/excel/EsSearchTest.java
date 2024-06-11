package com.kirito.excel;

import com.kirito.excel.utils.JacksonUtil;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Objects;

/**
 * es查询测试
 */
public class EsSearchTest {

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

    //查询
    //查询所有 match_all
    //全文检索 match_query multi_match_query
    //精确查询 ids range term
    //地理查询 geo_distance geo_bounding_box
    //复合查询 bool function_score

    @Test
    public void testMatchAll() throws IOException {
        //1.准备Request
        SearchRequest request = new SearchRequest("hotel");
        //2.组装DSL参数
        request.source().query(QueryBuilders.matchAllQuery());
        //3.发送请求，得到响应结果
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        System.out.println("response = " + JacksonUtil.toJSONString(response));
        //4.解析结果
        SearchHits searchHits = response.getHits();
        if (Objects.isNull(searchHits.getTotalHits())) {
            System.out.println("没有查到数据");
            return;
        }
        //总条数
        long total = searchHits.getTotalHits().value;
        System.out.println("总条数 " + total);
        for (SearchHit searchHit : searchHits) {
            String json = searchHit.getSourceAsString();
            System.out.println(json);
        }
    }

}
