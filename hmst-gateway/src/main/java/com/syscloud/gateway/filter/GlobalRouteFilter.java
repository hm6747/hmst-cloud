package com.syscloud.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.syscloud.base.auth.context.BaseContextHandler;
import com.syscloud.base.auth.msg.NoPermissionResponse;
import com.syscloud.base.auth.msg.TokenForbiddenResponse;
import com.syscloud.pojo.JsonData;
import com.syscloud.provider.auth.config.ServiceAuthConfig;
import com.syscloud.provider.auth.feign.ServiceAuthFeign;
import com.syscloud.provider.auth.jwt.ServiceAuthUtil;
import com.syscloud.provider.auth.jwt.UserAuthUtil;
import com.syscloud.utils.jwt.IJWTInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by hm on 2018/7/20 0020.
 */
@Configuration
@Slf4j
public class GlobalRouteFilter implements GlobalFilter {

    @Value("${gate.ignoreUrl}")
    private String startWith;
    @Autowired
    private UserAuthUtil userAuthUtil;
    @Autowired
    private ServiceAuthFeign serviceAuthFeign;
    @Autowired
    private ServiceAuthUtil serviceAuthUtil;
    @Autowired
    private ServiceAuthConfig serviceAuthConfig;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest.Builder builder = exchange.getRequest().mutate();
        ServerHttpRequest request = exchange.getRequest();
        String requestUri = request.getPath().pathWithinApplication().value();
        log.info("网关接收请求地址为:{}", requestUri);
        LinkedHashSet requiredAttribute = exchange.getRequiredAttribute(ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR);
        final String method = request.getMethod().toString();
        ServerHttpRequest.Builder mutate = request.mutate();
        List<String> token = request.getHeaders().get("token");
        if (isStartWith(requestUri)) {
            ServerHttpRequest build = mutate.build();
            return chain.filter(exchange.mutate().request(build).build());
        }
        //校验登录token
        IJWTInfo user = null;
        try {
            user = getJWTUser(request, mutate);
        } catch (Exception e) {
            log.error("用户Token过期异常", e);
            return getVoidMono(exchange, new TokenForbiddenResponse("用户登录过期"));
        }
        JsonData jsonData = serviceAuthFeign.hasPermission(requestUri,Integer.parseInt(user.getId()));
        if(! Boolean.parseBoolean(jsonData.getData().toString())){
            return getVoidMono(exchange, new NoPermissionResponse("无权限访问"));
        }
        // 申请客户端密钥头
        mutate.header(serviceAuthConfig.getTokenHeader(), serviceAuthUtil.getClientToken());
        ServerHttpRequest build = mutate.build();
        return chain.filter(exchange.mutate().request(build).build());
    }

    /**
     * URI是否以什么打头
     *
     * @param requestUri
     * @return
     */
    private boolean isStartWith(String requestUri) {
        boolean flag = false;
        for (String s : startWith.split(",")) {
            if (requestUri.startsWith(s)) {
                return true;
            }
        }
        return flag;
    }

    /**
     * 网关抛异常
     *
     * @param body
     * @param code
     */
    private Mono<Void> setFailedRequest(ServerWebExchange serverWebExchange, String body, int code) {
        serverWebExchange.getResponse().setStatusCode(HttpStatus.OK);
        return serverWebExchange.getResponse().setComplete();
    }

    /**
     * 网关抛异常
     *
     * @param body
     */
    private Mono<Void> getVoidMono(ServerWebExchange serverWebExchange, Object body) {
        serverWebExchange.getResponse().setStatusCode(HttpStatus.OK);
        byte[] bytes = JSONObject.toJSONString(body).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = serverWebExchange.getResponse().bufferFactory().wrap(bytes);
        return serverWebExchange.getResponse().writeWith(Flux.just(buffer));
    }


    /**
     * 返回session中的用户信息
     *
     * @param request
     * @param ctx
     * @return
     */
    private IJWTInfo getJWTUser(ServerHttpRequest request, ServerHttpRequest.Builder ctx) throws Exception {
        List<String> strings = request.getHeaders().get("token");
        String authToken = null;
        if (strings != null) {
            authToken = strings.get(0);
        }
        if (StringUtils.isBlank(authToken)) {
            strings = request.getQueryParams().get("token");
            if (strings != null) {
                authToken = strings.get(0);
            }
        }
        ctx.header("token", authToken);
        BaseContextHandler.setToken(authToken);
        return userAuthUtil.getInfoFromToken(authToken);
    }

}
