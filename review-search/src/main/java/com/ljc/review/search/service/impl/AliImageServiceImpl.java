package com.ljc.review.search.service.impl;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.imagesearch.model.v20190325.AddImageRequest;
import com.aliyuncs.imagesearch.model.v20190325.AddImageResponse;
import com.ljc.review.search.entity.common.BaseResult;
import com.ljc.review.search.service.spec.AliImageService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Service
@Transactional
public class AliImageServiceImpl implements AliImageService {

    @Value("${image.instance.name}")
    private String instanceName;
    private IAcsClient client;

    @Autowired
    public AliImageServiceImpl(IAcsClient client) {
        this.client = client;
    }

    @Override
    public BaseResult pushToRepository(HttpServletRequest request, HttpServletResponse response) {
        //获取所有图片

        //推送图片

        return null;
    }

    public void pushOne(String filePath,String productId,String picName) {
        AddImageRequest imageRequest = new AddImageRequest();
        // 必填，图像搜索实例名称。
        imageRequest.setInstanceName(instanceName);
        // 必填，商品id，最多支持 512个字符。
        // 一个商品可有多张图片。
        imageRequest.setProductId(productId);
        // 必填，图片名称，最多支持 512个字符。
        // 1. ProductId + PicName唯一确定一张图片。
        // 2. 如果多次添加图片具有相同的ProductId + PicName，以最后一次添加为准，前面添加的的图片将被覆盖。
        imageRequest.setPicName(picName);
        // 选填，图片类目。
        // 1. 对于商品搜索：若设置类目，则以设置的为准；若不设置类目，将由系统进行类目预测，预测的类目结果可在Response中获取 。
        // 2. 对于通用搜索：不论是否设置类目，系统会将类目设置为88888888。
        imageRequest.setCategoryId(1);
        byte[] bytes2 = getBytes(filePath);
        Base64 base64 = new Base64();
        String encodePicContent = base64.encodeToString(bytes2);
        // 必填，图片内容，Base64编码。
        // 最多支持 2MB大小图片以及5s的传输等待时间。当前仅支持jpg和png格式图片；图片
        // 长和宽的像素必须都大于等于200，并且小于等于1024；图像中不能带有旋转信息。
        imageRequest.setPicContent(encodePicContent);
        // 选填，是否需要进行主体识别，默认为true。
        // 1.为true时，由系统进行主体识别，以识别的主体进行搜索，主体识别结果可在Response中获取。
        // 2. 为false时，则不进行主体识别，以整张图进行搜索。
        imageRequest.setCrop(true);
        // 选填，图片的主体区域，格式为 x1,x2,y1,y2, 其中 x1,y1 是左上角的点，x2，y2是右下角的点。
        // 若用户设置了Region，则不论Crop参数为何值，都将以用户输入Region进行搜索。
        // imageRequest.setRegion("280,486,232,351");
        // 选填，整数类型属性，可用于查询时过滤，查询时会返回该字段。
        //  例如不同的站点的图片/不同用户的图片，可以设置不同的IntAttr，查询时通过过滤来达到隔离的目的
        imageRequest.setIntAttr(0);
        // 选填，字符串类型属性，最多支持 128个字符。可用于查询时过滤，查询时会返回该字段。
        imageRequest.setStrAttr("demo");
        // 选填，用户自定义的内容，最多支持 4096个字符。
        // 查询时会返回该字段。例如可添加图片的描述等文本。
        imageRequest.setCustomContent("1");
        try {
            AddImageResponse response = client.getAcsResponse(imageRequest);
        } catch (ClientException e) {
            // 抛出异常，例如参数无效，或者实例不可用等情况
            e.printStackTrace();
        }
    }

    private static byte[] getBytes(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            // picture max size is 2MB
            ByteArrayOutputStream bos = new ByteArrayOutputStream(2000 * 1024);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

}
