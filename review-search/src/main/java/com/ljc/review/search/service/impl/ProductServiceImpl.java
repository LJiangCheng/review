package com.ljc.review.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.ljc.review.search.entity.vo.SkuToElasticSearchVO;
import com.ljc.review.search.repository.PcProductSkuMapper;
import com.ljc.review.search.service.spec.ProductService;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private PcProductSkuMapper skuMapper;
    @Autowired
    private RestHighLevelClient client;

    @Override
    public void pushAllProductToES() {
        List<SkuToElasticSearchVO> productList = new ArrayList<>();
        int count = skuMapper.countAllProduct();
        int page = count / 3000 == 0 ? count / 3000 : count / 3000 + 1;
        for (int i = 0; i < page; i++) {
            List<SkuToElasticSearchVO> productByPage = skuMapper.getProductByPage(i * 3000, 3000);
            LOGGER.info("第" + (i + 1) + "页数据获取成功，共" + productByPage.size() + "条！");
            productList.addAll(productByPage);
        }
        LOGGER.info("商品数据获取成功，共" + productList.size() + "条！");
        List<List<SkuToElasticSearchVO>> partition = Lists.partition(productList, 3000);
        for (int i = 0; i < partition.size(); i++) {
            List<SkuToElasticSearchVO> part = partition.get(i);
            BulkRequest request = new BulkRequest();
            for (SkuToElasticSearchVO vo : part) {
                if (vo.getCategoryId() != null) {
                    vo.setTags(Collections.singletonList(vo.getCategoryId().doubleValue()));
                }
                vo.setSource(0);
                request.add(new IndexRequest("products").id(vo.getUnicode()).source(JSON.toJSONString(vo), XContentType.JSON));
            }
            try {
                client.bulk(request, RequestOptions.DEFAULT);
                LOGGER.info("第" + (i + 1) + "批商品索引成功！");
            } catch (IOException e) {
                LOGGER.error("=======>第" + (i + 1) + "批商品索引异常！", e);
            }
        }
        LOGGER.info("索引成功！");
    }
}
