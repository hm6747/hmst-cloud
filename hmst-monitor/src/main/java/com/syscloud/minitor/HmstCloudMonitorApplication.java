package com.syscloud.minitor;

import de.codecentric.boot.admin.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * The class Paas cloud uac application.
 *
 * @author paascloud.net@gmail.com
 */
@SpringBootApplication
@EnableEurekaClient
@EnableTurbine
@EnableHystrixDashboard
@EnableAdminServer
public class HmstCloudMonitorApplication {
	public static void main(String[] args) {
		SpringApplication.run(HmstCloudMonitorApplication.class, args);
	}



}
