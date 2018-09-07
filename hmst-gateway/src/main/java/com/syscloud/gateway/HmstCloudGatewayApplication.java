package com.syscloud.gateway;


import com.syscloud.provider.auth.EnableHmstAuthClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan(value = {"com.syscloud.gateway","com.syscloud.provider.auth"})
@EnableHmstAuthClient
@EnableFeignClients({"com.syscloud.provider.auth.feign"})
public class HmstCloudGatewayApplication {
	public static void main(String[] args) {
		SpringApplication.run(HmstCloudGatewayApplication.class, args);
	}
}
