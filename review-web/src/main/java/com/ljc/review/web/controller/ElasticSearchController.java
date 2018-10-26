package com.ljc.review.web.controller;

import com.ljc.review.web.service.ElasticSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
public class ElasticSearchController {

    @Resource(name = "ESJavaBaseService")
    private ElasticSearchService searchService;



}
