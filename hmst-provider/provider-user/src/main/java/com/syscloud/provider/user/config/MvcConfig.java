package com.syscloud.provider.user.config;

import com.syscloud.base.config.SwaggerConfiguration;
import com.syscloud.provider.user.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;


/**
 * Created by Administrator on 2018/5/17 0017.
 */

@Configuration
@Import(SwaggerConfiguration.class)
public class MvcConfig extends WebMvcConfigurerAdapter {
   @Resource
   LoginInterceptor loginInterceptor;
  @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
       registry.addResourceHandler("*//**")
               .addResourceLocations("classpath:/META-INF/resources/", "classpath:/resources/", "classpath:/static/");
    }

/*  @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("*//**")
                .excludePathPatterns("/login.json")
                .excludePathPatterns("/sign.json");
        super.addInterceptors(registry);
    }*/

}
