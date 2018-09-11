package com.syscloud.provider.user;

import com.ctrip.framework.apollo.spring.annotation.EnableHmstConfig;
import com.syscloud.base.properties.SwaggerConfiguration;
import com.syscloud.provider.auth.EnableHmstAuthClient;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import javax.sql.DataSource;

@EnableEurekaClient
@SpringBootApplication
@Import(SwaggerConfiguration.class)
@ComponentScan({"com.syscloud.provider.user","com.syscloud.provider.auth"})
@EnableHmstAuthClient
@EnableFeignClients({"com.syscloud.provider.auth.feign"})
@EnableHmstConfig
public class HmstCloudUserApplication {
	public static void main(String[] args) {
		SpringApplication.run(HmstCloudUserApplication.class, args);
	}

	@Bean
	public SpringLiquibase springLiquibase(DataSource dataSource) {

		SpringLiquibase springLiquibase = new SpringLiquibase();

		springLiquibase.setDataSource(dataSource);
		springLiquibase.setChangeLog("classpath:/liquibase/index.xml");
		springLiquibase.setShouldRun(true);
		return springLiquibase;
	}

	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:org/springframework/security/messages_zh_CN");
		return messageSource;
	}


}
