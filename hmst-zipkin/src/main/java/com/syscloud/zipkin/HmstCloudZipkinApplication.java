package com.syscloud.zipkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.sleuth.zipkin.stream.EnableZipkinStreamServer;

/**
 * The class Paas cloud uac application.
 *
 * @author paascloud.net@gmail.com
 */
@EnableEurekaClient
@SpringBootApplication
@EnableZipkinStreamServer
public class HmstCloudZipkinApplication {
	public static void main(String[] args) {
		SpringApplication.run(HmstCloudZipkinApplication.class, args);
	}



}
