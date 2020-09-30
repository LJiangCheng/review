package com.review.spider.controller;

import com.review.spider.bean.BaseResult;
import com.review.spider.service.spec.Spider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/spider")
public class SpiderController {

    private final Spider spiderService;

    @Autowired
    public SpiderController(Spider spiderService) {
        this.spiderService = spiderService;
    }

    @PostMapping("/crawler")
    public BaseResult crawler(String url) {
        return spiderService.crawler(url);
    }

    @PostMapping("shutDown")
    public BaseResult shutDown(String url) {
        spiderService.shutDown(url);
        return BaseResult.success();
    }

}
