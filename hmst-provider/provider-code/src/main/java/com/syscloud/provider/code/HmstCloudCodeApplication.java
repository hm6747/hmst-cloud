package com.syscloud.provider.code;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * The class Paas cloud uac application.
 *
 * @author paascloud.net@gmail.com
 */
@EnableEurekaClient
@SpringBootApplication
public class HmstCloudCodeApplication {
	public static void main(String[] args) {
		SpringApplication.run(HmstCloudCodeApplication.class, args);
	}

	@Bean
	public SpringLiquibase springLiquibase(DataSource dataSource) {
		SpringLiquibase springLiquibase = new SpringLiquibase();
		springLiquibase.setDataSource(dataSource);
		springLiquibase.setChangeLog("classpath:/liquibase/index.xml");
		return springLiquibase;
	}
}
