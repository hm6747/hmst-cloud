package com.syscloud.provider.user.web.controller;

import com.syscloud.pojo.JsonData;
import com.syscloud.provider.user.service.LoginService;
import com.syscloud.provider.user.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by hm on 2017/12/25.
 */
@Controller
@Slf4j
//@Api(value = "签名与登录", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class LoginUserController {
//    private static final String PROVITE_KEY = "PROVITE_KEY";
    @Autowired
    private LoginService loginService;
    @Autowired
    private SysUserService sysUserService;

//    @RequestMapping("/sign.json")
//    @ResponseBody
//    @ApiOperation(httpMethod = "GET", value = "签名接口")
//    public JsonData Sign(HttpServletRequest request) throws Exception {
//        Map<String, Object> keyMap  = RSAUtil.initKey();
//        String publicKey = RSAUtil.getPublicKey(keyMap);
//        String privateKey = RSAUtil.getPrivateKey(keyMap);
//        request.getSession().setAttribute(PROVITE_KEY, privateKey);
//        return JsonData.success(publicKey);
//    }

    @RequestMapping("/login.json")
    @ResponseBody
//    @ApiOperation(httpMethod = "GET", value = "登录接口")
    public JsonData Login(String username, String password, Integer type, String key, HttpServletRequest request) throws Exception {
        String jwtInfo = loginService.LoginByUserNameAndPassword(username, password, type);
        return JsonData.success(jwtInfo);
    }



}
