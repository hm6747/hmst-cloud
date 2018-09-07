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
public class LoginUserController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("/login.json")
    @ResponseBody
    public JsonData Login(String username, String password, Integer type, String key, HttpServletRequest request) throws Exception {
        String jwtInfo = loginService.LoginByUserNameAndPassword(username, password, type);
        return JsonData.success(jwtInfo);
    }



}
