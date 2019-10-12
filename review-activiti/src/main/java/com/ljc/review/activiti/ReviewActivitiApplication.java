package com.ljc.review.activiti;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class ReviewActivitiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReviewActivitiApplication.class, args);
	}

}
