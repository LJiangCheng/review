package com.ljc.review.search.controller;

import com.ljc.review.search.entity.common.BaseResult;
import com.ljc.review.search.service.spec.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pc/product/data")
public class ProductDataController {

    @Autowired
    private ProductService productService;

    @ApiOperation(value = "重建商品索引", notes = "@Author ljc")
    @PostMapping("pushAllProductToES")
    public BaseResult pushAllProductToES() {
        productService.pushAllProductToES();
        return BaseResult.success();
    }

}
