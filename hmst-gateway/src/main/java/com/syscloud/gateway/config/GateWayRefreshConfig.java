//package com.syscloud.gateway.config;
//
//import com.ctrip.framework.apollo.model.ConfigChangeEvent;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.context.scope.refresh.RefreshScope;
//import org.springframework.stereotype.Component;
//
///**
// * Created by Administrator on 2018/8/1 0001.
// */
//@Component
//public class GateWayRefreshConfig {
//    @Autowired
//    private GateWayConfig gateWayConfig;
//
//    @Autowired
//    private RefreshScope refreshScope ;
//
//    public void onChange(ConfigChangeEvent changEvent){
//        refreshScope.refresh("gateway.CONFIGURATION_PROPERTIES") ;
//    }
//}
