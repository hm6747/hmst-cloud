package com.syscloud.provider.config;

import com.syscloud.provider.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * Created by Administrator on 2018/5/17 0017.
 */

@Configuration
public class MvcConfig  extends WebMvcConfigurerAdapter {
    @Autowired
    LoginInterceptor loginInterceptor;
 /*   @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static*//*").addResourceLocations("classpath:/static/");
    }*/

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/login.json")
                .excludePathPatterns("/sign.json");
        super.addInterceptors(registry);
    }

}
