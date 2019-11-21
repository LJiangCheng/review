package com.ljc.review.common.image.webp;

import org.apache.commons.io.FilenameUtils;
import org.junit.Test;

import java.io.File;

public class UnitTest {

    @Test
    public void test() {
        File srcFile = new File("C:\\Users\\toolmall\\Desktop\\watermaker\\webp\\1.jpg");
        File dstFile = new File("C:\\Users\\toolmall\\Desktop\\watermaker\\webp\\1.webp");
        Util util = new Util();
        ImageData imageData = util.readImage(srcFile);
        if(FilenameUtils.getExtension(dstFile.getName()).equals("webp")){
            util.writeImage(imageData, dstFile, 100);
        }

    }

}
