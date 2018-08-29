package com.syscloud.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableEurekaServer
@SpringBootApplication
public class HmstCloudEurekaApplication {

	/**
	 * CSRF保护默认是开启的，可以禁用掉即可
	 */
	@EnableWebSecurity
	static class WebSecurityConfig extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			super.configure(http);//加这句是为了访问eureka控制台和/actuator时能做安全控制
			http.csrf().disable();
			http.headers().frameOptions().disable();
		}
	}


	public static void main(String[] args) {
		SpringApplication.run(HmstCloudEurekaApplication.class, args);
	}
}
