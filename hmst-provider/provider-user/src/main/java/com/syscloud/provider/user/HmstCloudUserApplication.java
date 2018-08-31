package com.syscloud.provider.user;

import com.syscloud.base.config.SwaggerConfiguration;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import javax.sql.DataSource;

/**
 * The class Paas cloud uac application.
 *
 * @author paascloud.net@gmail.com
 */
@EnableEurekaClient
@SpringBootApplication
@Import(SwaggerConfiguration.class)
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
