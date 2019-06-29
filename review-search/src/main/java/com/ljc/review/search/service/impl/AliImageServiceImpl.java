package com.ljc.review.search.service.impl;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.imagesearch.model.v20190325.DeleteImageRequest;
import com.aliyuncs.imagesearch.model.v20190325.DeleteImageResponse;
import com.ljc.review.search.service.spec.AliImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AliImageServiceImpl implements AliImageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AliImageServiceImpl.class);

    @Value("${image.instance.name}")
    private String instanceName;
    @Autowired
    private IAcsClient client;

    @Override
    public void delete(String productId) {
        deletedOne(productId);
    }

    private void deletedOne(String productId) {
        DeleteImageRequest request = new DeleteImageRequest();
        // 必填，图像搜索实例名称。
        request.setInstanceName(instanceName);
        // 必填，商品id。
        request.setProductId(productId);
        // 选填，图片名称。若不指定本参数，则删除ProductId下所有图片；若指定本参数，则删除ProductId+PicName指定的图片。
        //request.setPicName(imageName);
        try {
            DeleteImageResponse response = client.getAcsResponse(request);
            if (response.getSuccess()) {
                System.out.println("success!");
            } else {
            }
        } catch (ClientException e) {
        }
    }

}
