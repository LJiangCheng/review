package com.review.spider.controller;

import com.review.spider.bean.BaseResult;
import com.review.spider.service.spec.Spider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/spider")
public class SpiderController {

    private final Spider spiderService;

    @Autowired
    public SpiderController(Spider spiderService) {
        this.spiderService = spiderService;
    }

    @RequestMapping("/crawler")
    public BaseResult crawler(String url) {
        spiderService.crawler(url);
        return BaseResult.success();
    }

}
