package com.review.spider.service.spec;

import com.review.spider.bean.BaseResult;

public interface Spider {
    BaseResult crawler(String url);

    void shutDown(String url);
}
