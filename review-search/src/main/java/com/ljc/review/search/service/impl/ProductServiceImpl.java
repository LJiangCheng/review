package com.ljc.review.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.ljc.review.search.entity.vo.SkuToElasticSearchVO;
import com.ljc.review.search.repository.PcProductSkuMapper;
import com.ljc.review.search.service.spec.ProductService;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
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
        /*int count = skuMapper.countAllProduct();
        int page = count / 1000 == 0 ? count / 1000 : count / 1000 + 1;
        for (int i = 0; i < page; i++) {
            List<SkuToElasticSearchVO> productByPage = skuMapper.getProductByPage(i * 1000, 1000);
            LOGGER.info("第" + (i + 1) + "页数据获取成功，共" + productByPage.size() + "条！");
            productList.addAll(productByPage);
        }*/
        productList.addAll(skuMapper.getProductByPage(0, 100));
        LOGGER.info("商品数据获取成功，共" + productList.size() + "条！");
        IndexRequest request;
        for (SkuToElasticSearchVO vo : productList) {
            request = new IndexRequest("data", "product", vo.getUnicode());
            request.source(JSON.toJSONString(vo), XContentType.JSON);
            try {
                client.index(request);
            } catch (IOException e) {
                LOGGER.error("=======>商品" + vo.getUnicode() + "索引异常！");
            }
        }
        LOGGER.info("索引成功！");
    }
}
