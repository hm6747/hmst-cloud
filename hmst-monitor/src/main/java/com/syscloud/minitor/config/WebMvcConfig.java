package com.syscloud.minitor.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * 配置静态资源映射
 * 
 * @author admin
 * @since 2018/5/5
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 将所有/static/** 访问都映射到classpath:/static/ 目录下
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/assets/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/js/");
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/css/");
        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/img/");
        registry.addResourceHandler("/**/login**").addResourceLocations("classpath:/login.html");
    }

}
