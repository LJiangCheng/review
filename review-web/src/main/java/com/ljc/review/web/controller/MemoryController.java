package com.ljc.review.web.controller;

import com.ljc.review.web.utils.BaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("memory")
public class MemoryController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MemoryController.class);

    @RequestMapping("test")
    public BaseResult test() {
        List<byte[]> list = new ArrayList<>();
        for(int i=0;i<10;i++) {
            byte[] bytearr = new byte[1024 * 1024 * 50];
            list.add(bytearr);
            printMemory();
        }
        LOGGER.info("======================");
        return BaseResult.success();
    }

    @RequestMapping("image")
    public BaseResult image() throws IOException {
        for(int i=0;i<10;i++) {
            BufferedImage image = ImageIO.read(new File("C:\\Users\\toolmall\\Desktop\\watermaker\\webp\\2.jpg"));
            String filePath = "C:\\Users\\toolmall\\Desktop\\watermaker\\webp\\memory\\" + i + ".jpg";
            drawImage(image, filePath, 400, 400, 0, 0, null);
            printMemory();
        }
        LOGGER.info("======================");
        return BaseResult.success();
    }

    private static File drawImage(Image image, String filePath, int w, int h, int x, int y, ImageObserver observer) throws IOException {
        BufferedImage bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.getGraphics();
        graphics.drawImage(image.getScaledInstance(w, h, Image.SCALE_SMOOTH), x, y, observer);
        File file = new File(filePath);
        // 获取文件扩展名
        String ext = filePath.substring(filePath.lastIndexOf(".") + 1);
        ImageIO.write(bufferedImage, ext, file);
        //释放资源
        image.getGraphics().dispose();
        image.flush();
        graphics.dispose();
        bufferedImage.flush();
        return file;
    }

    private void printMemory() {
        Runtime runtime = Runtime.getRuntime();
        //long max = runtime.maxMemory() / 1024 / 1024;
        long total = runtime.totalMemory() / 1024 / 1024;
        long free = runtime.freeMemory() / 1024 / 1024;
        long used = total - free;
        LOGGER.info("used: {}M   free: {}M ", used, free);
    }



}
