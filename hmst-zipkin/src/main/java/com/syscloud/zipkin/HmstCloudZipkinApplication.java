package com.syscloud.zipkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class HmstCloudZipkinApplication {
	public static void main(String[] args) {
		SpringApplication.run(HmstCloudZipkinApplication.class, args);
	}

}
