package com.ljc.review.search.controller;

import com.ljc.review.search.entity.common.BaseResult;
import com.ljc.review.search.service.spec.AliImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/image/ali")
public class AliImageSearchController {

    private AliImageService aliImageService;

    @Autowired
    public AliImageSearchController(AliImageService aliImageService) {
        this.aliImageService = aliImageService;
    }

    @PostMapping("/push")
    public BaseResult pushToRepository(HttpServletRequest request, HttpServletResponse response) {
        return aliImageService.pushToRepository(request, response);
    }

}
