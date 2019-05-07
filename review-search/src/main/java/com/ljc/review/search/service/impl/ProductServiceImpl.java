package com.ljc.review.search.service.impl;

import com.ljc.review.search.entity.vo.SkuToElasticSearchVO;
import com.ljc.review.search.repository.PcProductSkuMapper;
import com.ljc.review.search.service.spec.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private PcProductSkuMapper skuMapper;

    @Override
    public void pushAllProductToES() {
        List<SkuToElasticSearchVO> productList = skuMapper.getAllProduct();


    }
}
