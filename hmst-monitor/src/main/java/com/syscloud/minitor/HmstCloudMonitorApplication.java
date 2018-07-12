package com.syscloud.minitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * The class Paas cloud uac application.
 *
 * @author paascloud.net@gmail.com
 */
@SpringBootApplication
@EnableAdminServer
@EnableEurekaClient
public class HmstCloudMonitorApplication {
	public static void main(String[] args) {
		SpringApplication.run(HmstCloudMonitorApplication.class, args);
	}



}
