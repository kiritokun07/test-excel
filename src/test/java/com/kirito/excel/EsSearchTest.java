package com.kirito.excel;

import com.fasterxml.jackson.core.type.TypeReference;
import com.kirito.excel.domain.HotelDoc;
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
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
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

    /**
     * GET /hotel/_search
     * {
     * "query": {
     * "match_all": {}
     * }
     * }
     */
    @Test
    public void testMatchAll() throws IOException {
        //1.准备Request
        SearchRequest request = new SearchRequest("hotel");
        //2.组装DSL参数
        request.source().query(QueryBuilders.matchAllQuery());
        //3.发送请求，得到响应结果
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        //System.out.println("response = " + JacksonUtil.toJSONString(response));
        //4.解析结果
        handleHotelResponse(response);
    }

    private void handleHotelResponse(SearchResponse response) {
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
            HotelDoc hotelDoc = JacksonUtil.parse(json, new TypeReference<HotelDoc>() {
            });
            System.out.println(hotelDoc);
        }
    }

    /**
     * GET /hotel/_search
     * {
     * "query": {
     * "match": {
     * "all": "希尔顿"
     * }
     * }
     * }
     * GET /hotel/_search
     * {
     * "query": {
     * "multi_match": {
     * "query": "希尔顿",
     * "fields": ["brand", "name"]
     * }
     * }
     * }
     */
    @Test
    public void testMatch() throws IOException {
        //1.准备Request
        SearchRequest request = new SearchRequest("hotel");
        //2.组装DSL参数
        request.source().query(QueryBuilders.matchQuery("all", "宣城"));
        //3.发送请求，得到响应结果
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        //System.out.println("response = " + JacksonUtil.toJSONString(response));
        //4.解析结果
        handleHotelResponse(response);
    }

    @Test
    public void testTerm() throws IOException {
        //1.准备Request
        SearchRequest request = new SearchRequest("hotel");
        //2.组装DSL参数
        request.source().query(QueryBuilders.termQuery("city", "希尔顿"));
        //3.发送请求，得到响应结果
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        //System.out.println("response = " + JacksonUtil.toJSONString(response));
        //4.解析结果
        handleHotelResponse(response);
    }

    @Test
    public void testRange() throws IOException {
        //1.准备Request
        SearchRequest request = new SearchRequest("hotel");
        //2.组装DSL参数
        request.source().query(QueryBuilders.rangeQuery("price").gte(480).lte(500));
        //3.发送请求，得到响应结果
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        //System.out.println("response = " + JacksonUtil.toJSONString(response));
        //4.解析结果
        handleHotelResponse(response);
    }

    @Test
    public void testBool() throws IOException {
        //1.准备Request
        SearchRequest request = new SearchRequest("hotel");
        //2.组装DSL参数
        request.source().query(
                QueryBuilders.boolQuery()
                        .must(QueryBuilders.termQuery("city", "上海市"))
                        .filter(QueryBuilders.rangeQuery("price").gte(200).lte(252))
        );
        //3.发送请求，得到响应结果
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        //System.out.println("response = " + JacksonUtil.toJSONString(response));
        //4.解析结果
        handleHotelResponse(response);
    }

    @Test
    public void testPageAndSort() throws IOException {
        int page = 2, size = 2;
        //1.准备Request
        SearchRequest request = new SearchRequest("hotel");
        //2.组装DSL参数
        request.source().query(QueryBuilders.matchAllQuery());
        request.source().sort("price", SortOrder.ASC);
        request.source().from((page - 1) * size).size(size);
        //3.发送请求
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        //4.解析结果
        handleHotelResponse(response);
    }

    @Test
    public void testHighlight() throws IOException {
        //1.准备Request
        SearchRequest request = new SearchRequest("hotel");
        //2.组装DSL参数
        request.source().query(QueryBuilders.matchQuery("all", "希尔顿"));
        //高亮
        request.source().highlighter(
                new HighlightBuilder()
                        .field("name").requireFieldMatch(false)
        );
        //3.发送请求
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        System.out.println("response = " + JacksonUtil.toJSONString(response));
        //4.解析结果
        handleHotelResponse(response);
    }
}
