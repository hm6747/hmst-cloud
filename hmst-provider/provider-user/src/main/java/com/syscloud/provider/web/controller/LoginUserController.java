package com.syscloud.provider.web.controller;

import com.syscloud.pojo.JsonData;
import com.syscloud.provider.model.dto.LoginUserDto;
import com.syscloud.provider.model.vo.LoginUser;
import com.syscloud.provider.model.vo.SysUser;
import com.syscloud.provider.service.LoginService;
import com.syscloud.provider.service.SysUserService;
import com.syscloud.utils.Base64Util;
import com.syscloud.utils.RSAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;


/**
 * Created by hm on 2017/12/25.
 */
@Controller
public class LoginUserController {
    private static final String PROVITE_KEY = "PROVITE_KEY";
    @Autowired
    private LoginService loginService;
    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("/login.page")
    public String loginPage() {
        return "redirect:/pages/login.html";
    }

    @RequestMapping("/logout.page")
    public String logout(HttpServletRequest request) throws IOException, ServletException {
        request.getSession().removeAttribute("user");
        return "redirect:/pages/login.html";
    }

    @RequestMapping("/sign.json")
    @ResponseBody
    public JsonData Sign(HttpServletRequest request) throws Exception {
        Map<String, Object> keyMap  = RSAUtil.initKey();
        String publicKey = RSAUtil.getPublicKey(keyMap);
        String privateKey = RSAUtil.getPrivateKey(keyMap);
        request.getSession().setAttribute(PROVITE_KEY, privateKey);
        return JsonData.success(publicKey);
    }

    @RequestMapping("/login.json")
    @ResponseBody
    public JsonData Login(String username, String password, Integer type, String key, HttpServletRequest request) throws Exception {
        String privateKey = (String) request.getSession().getAttribute(PROVITE_KEY);
        password = new String(RSAUtil.decrypt(Base64Util.decryptBASE64(password), privateKey, false));
        LoginUser loginUser = loginService.LoginByUserNameAndPassword(username, password, type);
        SysUser user = sysUserService.findById(loginUser.getUserId());
        LoginUserDto loginUserDto = loginService.Login(user, loginUser);
        request.getSession().setAttribute("user", loginUserDto);
        return JsonData.success(loginUserDto);
    }

}
