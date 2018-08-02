package com.syscloud.zipkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import zipkin.server.EnableZipkinServer;

@EnableEurekaClient
@SpringBootApplication
@EnableZipkinServer
public class HmstCloudZipkinApplication {
	public static void main(String[] args) {
		SpringApplication.run(HmstCloudZipkinApplication.class, args);
	}

}
