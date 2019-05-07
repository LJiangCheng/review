package com.ljc.review.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class ReviewSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReviewSearchApplication.class, args);
	}
}
