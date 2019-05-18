package com.ljc.review.search.service.impl;

import com.ljc.review.search.entity.common.BaseResult;
import com.ljc.review.search.entity.param.QueryEntity;
import com.ljc.review.search.service.spec.SimpleSearchService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SimpleSearchServiceImpl implements SimpleSearchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleSearchServiceImpl.class);

    @Autowired
    private RestHighLevelClient client;

    @Override
    public BaseResult intelliQuote(QueryEntity queryEntity) {
        try {
            //创建查询请求对象
            SearchRequest searchRequest = new SearchRequest("products");
            //设置请求条件
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.from(0);
            sourceBuilder.size(5);
            sourceBuilder.timeout(TimeValue.MINUS_ONE);
            MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("productName", queryEntity.getProductName())
                    .fuzziness(Fuzziness.AUTO).prefixLength(3).maxExpansions(5);
            sourceBuilder.query(queryBuilder);
            searchRequest.source(sourceBuilder);
            //发起请求
            SearchResponse result = client.search(searchRequest, RequestOptions.DEFAULT);
            //解析结果
            List<Map<String, Object>> resultList = new ArrayList<>();
            SearchHit[] hits = result.getHits().getHits();
            for (SearchHit hit : hits) {
                resultList.add(hit.getSourceAsMap());
            }
            return BaseResult.success(resultList);
        } catch (IOException e) {
            LOGGER.error("查询失败", e);
            return BaseResult.error(e.getMessage());
        }
    }
}




























