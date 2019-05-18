package com.ljc.review.search.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ElasticSearchConfig {

    @Value("${elasticsearch.address}")
    private String hostName;

    @Bean
    @Primary
    public RestHighLevelClient getRestHighLevelClient() {
        return new RestHighLevelClient(RestClient.builder(new HttpHost(hostName, 9200, "http")));
    }


}
