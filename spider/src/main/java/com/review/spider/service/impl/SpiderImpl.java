package com.review.spider.service.impl;

import com.review.spider.service.spec.Spider;
import com.review.spider.service.spec.WebSpider;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class SpiderImpl implements Spider {

    public static void main(String[] args) {
        new SpiderImpl().crawler("https://www.zhihu.com/");
    }

    @Override
    public void crawler(String url) {
        try {
            CrawlConfig config = new CrawlConfig();
            config.setCrawlStorageFolder("/tmp/crawler4j/");
            config.setPolitenessDelay(1000); //请求间隔时间毫秒数
            config.setMaxDepthOfCrawling(2);
            config.setMaxPagesToFetch(1000);
            config.setIncludeBinaryContentInCrawling(false);
            config.setResumableCrawling(false);
            PageFetcher pageFetcher = new PageFetcher(config);
            //controller配置
            RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
            RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
            //不遵循Robots规则：网站根目录下有一个Robots.txt文件表名本页是否愿意被收录，约定非限制
            robotstxtConfig.setEnabled(false);
            CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
            //添加url，可重复添加多个
            controller.addSeed(url);
            //执行爬取的线程数量
            int numberOfCrawlers = 1;
            //线程间共享数据传递
            AtomicInteger urlNums = new AtomicInteger();
            //工厂将会创建爬虫实例
            CrawlController.WebCrawlerFactory<WebSpider> factory = () -> new WebSpider(urlNums);
            //启动。这是一个阻塞操作，意味着之后爬取结束之后方法才会终止
            controller.start(factory, numberOfCrawlers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
