package com.syscloud.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class HmstCloudEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(HmstCloudEurekaApplication.class, args);
	}
}
