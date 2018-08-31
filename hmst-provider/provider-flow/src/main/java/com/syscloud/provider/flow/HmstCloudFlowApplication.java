package com.syscloud.provider.flow;

import liquibase.integration.spring.SpringLiquibase;
import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import javax.sql.DataSource;

/**
 * The class Paas cloud uac application.
 *
 * @author paascloud.net@gmail.com
 */
@EnableEurekaClient
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class HmstCloudFlowApplication {
	public static void main(String[] args) {
		SpringApplication.run(HmstCloudFlowApplication.class, args);
	}

	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:org/springframework/security/messages_zh_CN");
		return messageSource;
	}
	@Bean
	public SpringLiquibase springLiquibase(DataSource dataSource) {
		SpringLiquibase springLiquibase = new SpringLiquibase();
		springLiquibase.setDataSource(dataSource);
		springLiquibase.setChangeLog("classpath:/liquibase/index.xml");
		return springLiquibase;
	}

}
