package com.kirito.excel;

import com.fasterxml.jackson.core.type.TypeReference;
import com.kirito.excel.domain.Hotel;
import com.kirito.excel.domain.HotelDoc;
import com.kirito.excel.service.IHotelService;
import com.kirito.excel.utils.JacksonUtil;
import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * es文档测试
 */
@SpringBootTest
public class EsDocTest {

    private IHotelService iHotelService;
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

    /**
     * POST /hotel/_doc/1
     * {
     *     "xxx": "xxx"
     * }
     */
    @Test
    public void testAddDocument() throws IOException {
        //1.根据id查询酒店数据
        Hotel hotel = iHotelService.getById(1L);
        //2.转换为文档类型
        HotelDoc hotelDoc = new HotelDoc(hotel);
        //3.将HotelDoc转为json
        String json = JacksonUtil.toJSONString(hotelDoc);

        //1.准备Request
        IndexRequest request = new IndexRequest("hotel").id(hotelDoc.getId().toString());
        //2.准备json
        request.source(json, XContentType.JSON);
        //3.发送请求
        client.index(request, RequestOptions.DEFAULT);
    }

    /**
     * GET /hotel/_doc/{id}
     */
    @Test
    public void testGetDocumentById() throws IOException {
        //1.准备Request
        GetRequest request = new GetRequest("hotel", "1");
        //2.发送请求，得到响应
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        //3.解析响应结果
        String json = response.getSourceAsString();
        HotelDoc hotelDoc = JacksonUtil.parse(json, new TypeReference<HotelDoc>() {
        });
        System.out.println(hotelDoc);
    }

    /**
     * DELETE /hotel/_doc/{id}
     */
}
