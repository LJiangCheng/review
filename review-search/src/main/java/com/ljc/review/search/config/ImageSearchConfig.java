package com.ljc.review.search.config;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImageSearchConfig {

    @Value("${access.key}")
    private String accessKey;
    @Value("${secret.key}")
    private String secretKey;

    @Bean
    public IAcsClient getImageSearchClient() {
        DefaultProfile.addEndpoint( "shanghai", "ImageSearch", "imagesearch.shanghai.aliyuncs.com");
        IClientProfile profile = DefaultProfile.getProfile("shanghai", accessKey, secretKey);
        return new DefaultAcsClient(profile);
    }

}

