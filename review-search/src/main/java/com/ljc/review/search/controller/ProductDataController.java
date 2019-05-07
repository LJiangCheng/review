package com.ljc.review.search.controller;

import com.ljc.review.search.entity.common.BaseResult;
import com.ljc.review.search.service.spec.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductDataController {

    @Autowired
    private ProductService productService;

    @PostMapping("pushAllProductToES")
    public BaseResult pushAllProductToES() {
        productService.pushAllProductToES();
        return BaseResult.success();
    }

}
