package com.ljc.review.web.config;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
public class ESBeanConfig {

    private final static Logger LOGGER = LoggerFactory.getLogger(ESBeanConfig.class);

    @Value("${elasticsearch.host}")
    private String esHost;
    @Value("${elasticsearch.port}")
    private Integer esPort;

    @Bean("esClient")
    public Client getClient() {
        try {
            TransportAddress transportAddress = new InetSocketTransportAddress(InetAddress.getByName(esHost), esPort);
            return new PreBuiltTransportClient(Settings.EMPTY).addTransportAddresses(transportAddress);
        } catch (UnknownHostException e) {
            LOGGER.error("ES Java Client 创建失败！", e);
            return null;
        }
    }

}
