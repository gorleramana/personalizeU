package com.rg.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import lombok.extern.slf4j.Slf4j;

@EnableCaching
@EnableDiscoveryClient
@SpringBootApplication
@Slf4j
public class WebApplication {

	public static void main(String[] args) {
		log.info("Starting PersonalizeU Web Application...");
		SpringApplication.run(WebApplication.class, args);
		log.info("PersonalizeU Web Application started successfully");
	}

}
