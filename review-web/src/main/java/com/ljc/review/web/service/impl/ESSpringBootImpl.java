package com.ljc.review.web.service.impl;

import com.ljc.review.web.dao.es.ElasticSearchDao;
import com.ljc.review.web.service.ElasticSearchService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("ESSpringBootService")
public class ESSpringBootImpl implements ElasticSearchService {

    @Resource(name = "ESSpringBootDao")
    private ElasticSearchDao searchDao;

}
