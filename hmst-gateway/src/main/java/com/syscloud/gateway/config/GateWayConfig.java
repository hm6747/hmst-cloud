//package com.syscloud.gateway.config;
//
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.cloud.gateway.config.GatewayProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
///**
// * Created by Administrator on 2018/8/1 0001.
// */
//@Configuration
//public class GateWayConfig {
//
//    @Bean(name="gateway.CONFIGURATION_PROPERTIES")
//    @RefreshScope
//    @ConfigurationProperties("gateway")
//    @Primary
//    public GatewayProperties gatewayProperties(){
//        return new GatewayProperties();
//    }
//}
