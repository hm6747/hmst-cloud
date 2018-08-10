package com.syscloud.provider.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Created by hm on 2018/6/21 0021.
 * 服务编排系统 核心服务系统
 */
@EnableEurekaClient
@SpringBootApplication
public class HmstCloudCoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(HmstCloudCoreApplication.class, args);
    }
}
