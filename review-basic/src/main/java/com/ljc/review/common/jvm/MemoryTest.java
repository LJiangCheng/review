package com.ljc.review.common.jvm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MemoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MemoryTest.class);

    /**
     * 18-19 巨星科技有限公司 自建电商平台 土猫网企业购
     * 1.商品服务接口、搜索引擎和智能报价模块、内容管理模块，登陆下单也有参与
     * 2.负责网站内部管理系统的开发和日常运行 登录授权、定时任务、图片处理
     * 商品服务：维护网站的所有商品数据以及对外提供接口。维护方面使用Dubbo和内部平台交互，同时为网站提供restful接口
     * 搜索引擎：基于OpenSearch，提供网站首页的搜索功能。自建的ES也有研究过，不过还没有切换过去
     * 智能报价：为报价定制的商品搜索，根据用户输入的名称、品牌、型号、规格等信息给出最接近的商品。可以从不同的维度调整商品优先级，比如品牌、价格/销量、用户历史搜索习惯、用户行业标签等
     * 登录：使用Oauth2独立的登录授权服务。token + 本地缓存 + oauth2缓存
     *
     * 技术：SpringBoot/SpringOauth2 Dubbo 内部系统用的Shiro 中间件redis/rocketMq/zookeeper
     * 多线程：
     * 数据结构：栈、队列、链表、散列表、二叉树
     * 设计模式：工厂、单例、Reactor(NIO)
     *
     * java基础会重点问
     * 这两年做过的电商业务可以重点讲，尤其是有亮点的内容，以及你解决过的一些难题。你做过的一些创新和突破对业务增长的促进可以重点讲，这块不需要多大，有一个点的突破就可以
     * 还有重点说你在面对问题和困难的思考，可以举些例子
     *
     * vm参数：
     * -server -XX:MetaspaceSize=256m -XX:MaxMetaspaceSize=256m -Xms2816m -Xmx2816m -Xmn1024m -Xss256k -XX:SurvivorRatio=8 -XX:+UseConcMarkSweepGC -Duser.timezone=GMT+8
     * -XX:+PrintClassHistogram -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintHeapAtGC -Xloggc:gc.log
     * 最近需要频繁处理图片，导致程序内存溢出甚至宕机。
     * 1.优化图片处理程序，将图片处理的内存占用量降到了原来的1/4左右
     * 2.新生代内存区域过小，大量对象进入老年代，GC效率较低，手动设置Xmn为Xmx的3/8
     * 3.增加30%的内存资源
     *
     * 智能报价：切词优化(京东、搜狗词库)、个性化推荐
     *
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    try {
                        BufferedImage image = ImageIO.read(new File("C:\\Users\\toolmall\\Desktop\\watermaker\\webp\\2.jpg"));
                        printMemory();
                        Thread.sleep((j + 1) * 400);
                        System.out.println(image.getHeight());
                    } catch (Exception ignore) {}
                }
            }).start();
        }
        Thread.sleep(1000000);
    }

    private static void printMemory() {
        Runtime runtime = Runtime.getRuntime();
        long max = runtime.maxMemory() / 1024 / 1024;
        long total = runtime.totalMemory() / 1024 / 1024;
        long free = runtime.freeMemory() / 1024 / 1024;
        long used = total - free;
        LOGGER.info("total:{}m used:{}m free:{}m max:{}m", total, used, free, max);
    }

}





























