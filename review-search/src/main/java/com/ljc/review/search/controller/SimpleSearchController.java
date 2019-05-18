package com.ljc.review.search.controller;

import com.ljc.review.search.entity.common.BaseResult;
import com.ljc.review.search.entity.param.QueryEntity;
import com.ljc.review.search.service.spec.SimpleSearchService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pc/simpleSearch")
public class SimpleSearchController {

    @Autowired
    private SimpleSearchService searchService;

    @ApiOperation(value = "智能搜索推荐", notes = "@Author ljc")
    @PostMapping("intelliQuote")
    public BaseResult intelliQuote(@RequestBody QueryEntity queryEntity) {
        return searchService.intelliQuote(queryEntity);
    }

}
