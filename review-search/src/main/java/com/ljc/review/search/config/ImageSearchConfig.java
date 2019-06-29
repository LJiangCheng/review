package com.ljc.review.search.config;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImageSearchConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageSearchConfig.class);

    @Value("${access.key}")
    private String accessKey;
    @Value("${secret.key}")
    private String secretKey;

    @Bean
    public IAcsClient getImageSearchClient() {
        try {
            DefaultProfile.addEndpoint("cn-shanghai", "ImageSearch", "imagesearch.cn-shanghai.aliyuncs.com");
            IClientProfile profile = DefaultProfile.getProfile("cn-shanghai", accessKey, secretKey);
            return new DefaultAcsClient(profile);
        } catch (Exception e) {
            LOGGER.error("创建图像搜索实例失败，返回默认实例！");
            return new DefaultAcsClient(DefaultProfile.getProfile("", "", ""));
        }
    }

}

