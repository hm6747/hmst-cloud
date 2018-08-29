package com.syscloud.provider.user.web.controller;

import com.syscloud.pojo.JsonData;
import com.syscloud.provider.user.model.dto.LoginUserDto;
import com.syscloud.provider.user.model.vo.LoginUser;
import com.syscloud.provider.user.model.vo.SysUser;
import com.syscloud.provider.user.service.LoginService;
import com.syscloud.provider.user.service.SysUserService;
import com.syscloud.utils.Base64Util;
import com.syscloud.utils.RSAUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * Created by hm on 2017/12/25.
 */
@Controller
@Slf4j
@Api(value = "签名与登录", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class LoginUserController {
    private static final String PROVITE_KEY = "PROVITE_KEY";
    @Autowired
    private LoginService loginService;
    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("/sign.json")
    @ResponseBody
    @ApiOperation(httpMethod = "GET", value = "签名接口")
    public JsonData Sign(HttpServletRequest request) throws Exception {
        Map<String, Object> keyMap  = RSAUtil.initKey();
        String publicKey = RSAUtil.getPublicKey(keyMap);
        String privateKey = RSAUtil.getPrivateKey(keyMap);
        request.getSession().setAttribute(PROVITE_KEY, privateKey);
        return JsonData.success(publicKey);
    }

    @RequestMapping("/login.json")
    @ResponseBody
    @ApiOperation(httpMethod = "GET", value = "登录接口")
    public JsonData Login(String username, String password, Integer type, String key, HttpServletRequest request) throws Exception {
        String privateKey = (String) request.getSession().getAttribute(PROVITE_KEY);
        log.info(PROVITE_KEY,privateKey);
        password = new String(RSAUtil.decrypt(Base64Util.decryptBASE64(password), privateKey, false));
        LoginUser loginUser = loginService.LoginByUserNameAndPassword(username, password, type);
        SysUser user = sysUserService.findById(loginUser.getUserId());
        LoginUserDto loginUserDto = loginService.Login(user, loginUser);
        request.getSession().setAttribute("user", loginUserDto);
        return JsonData.success(loginUserDto);
    }

}
