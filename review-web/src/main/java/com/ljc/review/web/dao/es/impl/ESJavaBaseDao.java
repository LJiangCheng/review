package com.ljc.review.web.dao.es.impl;

import com.ljc.review.web.dao.es.ElasticSearchDao;
import org.elasticsearch.client.Client;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository("ESJavaBaseDao")
public class ESJavaBaseDao implements ElasticSearchDao {

    @Resource(name = "esClient")
    private Client client;



}
