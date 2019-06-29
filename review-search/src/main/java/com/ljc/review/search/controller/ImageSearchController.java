package com.ljc.review.search.controller;

import com.ljc.review.search.service.spec.AliImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/search/image")
public class ImageSearchController {

    private AliImageService aliImageService;

    @Autowired
    public ImageSearchController(AliImageService aliImageService) {
        this.aliImageService = aliImageService;
    }

    @GetMapping("/ali/deleteOne")
    public void deleteOne() {
        String[] arr = new String[]{"日立13MM手电钻800W超大功率电钻D13VG/1台", "日立13MM 手电钻800W超大功率电钻 D13VG/1台", "博世 手电钻 220V", "手电钻2号 万克宝", "东成 手电钻550W", "手电钻2号",
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "11", "12", "13", "14", "15", "16", "17", "111", "1111", "21", "22", "23", "24", "25", "26",
                "19", "29", "39", "49", "59", "69", "79", "89", "99", "119", "129", "139", "149", "159", "169", "179", "1119", "11119", "219", "229", "239", "249", "259", "269"
        };
        for (String proId : arr) {
            aliImageService.delete(proId);
        }
        System.out.println("删除完成！");
    }

}
