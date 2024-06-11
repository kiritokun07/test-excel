package com.kirito.excel;

import com.fasterxml.jackson.core.type.TypeReference;
import com.kirito.excel.domain.Hotel;
import com.kirito.excel.domain.HotelDoc;
import com.kirito.excel.service.IHotelService;
import com.kirito.excel.utils.JacksonUtil;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

/**
 * es文档测试
 */
@SpringBootTest
public class EsDocTest {

    @Autowired
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
     * "xxx": "xxx"
     * }
     */
    @Test
    public void testAddDocument() throws IOException {
        //1.根据id查询酒店数据
        Hotel hotel = iHotelService.getById(2L);
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
        GetRequest request = new GetRequest("hotel", "2");
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
    @Test
    public void testDeleteDocumentById() throws IOException {
        //1.准备Request
        DeleteRequest request = new DeleteRequest("hotel", "1");
        //2.发送请求，得到响应
        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
    }

    /**
     * POST /hotel/_update/1
     * {
     * "doc":{
     * "xxx": "xxx"
     * }
     * }
     * 全量修改：本质是先根据id删除再新增
     * 增量修改：修改文档中的指定字段值
     * 在RestClient的API中，全量修改与新增的API完全一致，判断依据是ID：
     * 如果新增时ID已存在，则修改
     * 如果新增时ID不存在，则新增
     */
    @Test
    public void testUpdateDocumentById() throws IOException {
        //1.准备Request
        UpdateRequest request = new UpdateRequest("hotel", "1");
        //2.准备请求参数
        request.doc("price", "888",
                "city", "宣州区"
        );
        //3.发送请求，得到响应
        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
    }

    @Test
    public void testBulkRequest() throws IOException {
        List<Hotel> hotelList = iHotelService.list();

        //1.准备Request
        BulkRequest request = new BulkRequest();
        //2.准备参数，添加多个新增的Request
        for (Hotel hotel : hotelList) {
            //2.1转换为文档类型
            HotelDoc hotelDoc = new HotelDoc(hotel);
            //2.2创建新增文档的Request对象
            request.add(new IndexRequest("hotel")
                    .id(hotelDoc.getId().toString())
                    .source(JacksonUtil.toJSONString(hotelDoc), XContentType.JSON)
            );
        }
        //3.发送请求
        client.bulk(request, RequestOptions.DEFAULT);
    }

}
