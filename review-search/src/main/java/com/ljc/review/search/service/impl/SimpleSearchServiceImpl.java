package com.ljc.review.search.service.impl;

import com.ljc.review.search.entity.common.BaseResult;
import com.ljc.review.search.entity.param.QueryEntity;
import com.ljc.review.search.service.spec.SimpleSearchService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
            //构造boolQuery
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            boolQueryBuilder.must(QueryBuilders.matchQuery("productName", queryEntity.getProductName()).operator(Operator.AND));
            boolQueryBuilder.should(QueryBuilders.matchQuery("categoryName", queryEntity.getCategoryName()).operator(Operator.OR).boost(1));
            boolQueryBuilder.should(QueryBuilders.termQuery("brandName", queryEntity.getBrandName()));
            boolQueryBuilder.minimumShouldMatch(0).boost(1);
            //构造functionScoreQuery
            FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders
                    .functionScoreQuery(boolQueryBuilder, ScoreFunctionBuilders.scriptFunction(new Script("doc['source'].value")))
                    .boostMode(CombineFunction.SUM);
            //设置scriptFields
            sourceBuilder.query(functionScoreQueryBuilder)
                    .scriptField("_source", new Script("params._source"))
                    .scriptField("myscore", new Script("doc['price'].value"));
            //发起请求
            searchRequest.source(sourceBuilder);
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            //解析结果
            List<Map<String, Object>> resultList = new ArrayList<>();
            SearchHit[] hits = searchResponse.getHits().getHits();
            for (SearchHit hit : hits) {
                //读取自定义field并封装返回
                Map<String, Object> result = new HashMap<>();
                Map<String, DocumentField> fields = hit.getFields();
                result.put("source", fields.get("_source").getValue());
                result.put("myscore", fields.get("myscore").getValue());
                resultList.add(result);
            }
            return BaseResult.success(resultList, searchResponse.getTook().getMillis() + "ms");
        } catch (IOException e) {
            LOGGER.error("查询失败", e);
            return BaseResult.error(e.getMessage());
        }
    }
}




























