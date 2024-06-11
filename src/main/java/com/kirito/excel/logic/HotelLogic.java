package com.kirito.excel.logic;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.kirito.excel.base.R;
import com.kirito.excel.domain.HotelDoc;
import com.kirito.excel.dto.HotelListReq;
import com.kirito.excel.dto.PageVO;
import com.kirito.excel.service.IHotelService;
import com.kirito.excel.utils.JacksonUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelLogic {

    private final IHotelService iHotelService;
    private final RestHighLevelClient esClient;
    private final RestHighLevelClient client;

    @SneakyThrows
    public R<PageVO<HotelDoc>> hotelList(HotelListReq req) {
        //1.准备Request
        SearchRequest request = new SearchRequest("hotel");
        //2.组装DSL参数
        buildBasicQuery(req, request);
        //分页
        request.source().from((req.getPage() - 1) * req.getSize()).size(req.getSize());
        //3.发送请求
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        PageVO<HotelDoc> result = handleHotelResponse(response);
        return R.success(result);
    }

    private PageVO<HotelDoc> handleHotelResponse(SearchResponse response) {
        System.out.println("response = " + JacksonUtil.toJSONString(response));
        SearchHits searchHits = response.getHits();
        if (Objects.isNull(searchHits.getTotalHits())) {
            System.out.println("没有查到数据");
            return PageVO.empty();
        }
        //总条数
        long total = searchHits.getTotalHits().value;
        System.out.println("总条数 " + total);
        List<HotelDoc> hotelDocList = new ArrayList<>(searchHits.getHits().length);
        for (SearchHit searchHit : searchHits) {
            String json = searchHit.getSourceAsString();
            HotelDoc hotelDoc = JacksonUtil.parse(json, new TypeReference<HotelDoc>() {
            });
            if (searchHit.getSortValues().length > 0) {
                hotelDoc.setDistance(searchHit.getSortValues()[0]);
            }
            hotelDocList.add(hotelDoc);
            System.out.println(hotelDoc);
        }
        return PageVO.ok(total, hotelDocList);
    }

    private void buildBasicQuery(HotelListReq req, SearchRequest request) {
        //1.构造bool查询
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        if (StrUtil.isEmpty(req.getKey())) {
            //must参与算分
            boolQuery.must(QueryBuilders.matchAllQuery());
        } else {
            boolQuery.must(QueryBuilders.matchQuery("all", req.getKey().trim()));
        }
        //2.term条件
        if (StrUtil.isNotEmpty(req.getCity())) {
            //filter不参与算分
            boolQuery.filter(QueryBuilders.termQuery("city", req.getCity().trim()));
        }
        //3.range条件
        if (Objects.nonNull(req.getMinPrice()) && Objects.nonNull(req.getMaxPrice())) {
            boolQuery.filter(
                    QueryBuilders.rangeQuery("price")
                            .gte(req.getMinPrice())
                            .lte(req.getMaxPrice())
            );
        }
        if (StrUtil.isNotEmpty(req.getLocation())) {
            request.source().sort(
                    SortBuilders.geoDistanceSort("location", new GeoPoint(req.getLocation()))
                            .order(SortOrder.ASC)
                            .unit(DistanceUnit.KILOMETERS)
            );
        }
        request.source().query(boolQuery);
        //4.算分控制
        //FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(
        //        //原始查询，相关性算分的查询
        //        boolQuery,
        //        //function score的数组
        //        new FunctionScoreQueryBuilder.FilterFunctionBuilder[]{
        //                //其中的一个function score元素
        //                new FunctionScoreQueryBuilder.FilterFunctionBuilder(
        //                        //过滤条件
        //                        QueryBuilders.termQuery("isAD", true),
        //                        //算分函数
        //                        ScoreFunctionBuilders.weightFactorFunction(30)
        //                )
        //        }
        //);
        //
        //request.source().query(functionScoreQueryBuilder);
    }

}
