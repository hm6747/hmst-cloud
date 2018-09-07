package com.syscloud.provider.user.service.impl;

import com.syscloud.provider.user.service.AuthService;
import com.syscloud.utils.jwt.JwtAuthenticationRequest;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Override
    public String login(JwtAuthenticationRequest authenticationRequest) throws Exception {
        return null;
    }

    @Override
    public String refresh(String oldToken) throws Exception {
        return null;
    }

    @Override
    public void validate(String token) throws Exception {

    }

//    private JwtTokenUtil jwtTokenUtil;
//    private IUserService userService;
//
//    @Autowired
//    public AuthServiceImpl(
//            JwtTokenUtil jwtTokenUtil,
//            IUserService userService) {
//        this.jwtTokenUtil = jwtTokenUtil;
//        this.userService = userService;
//    }

//    @Override
//    public String login(JwtAuthenticationRequest authenticationRequest) throws Exception {
//        UserInfo info = userService.validate(authenticationRequest);
//        if (!StringUtils.isEmpty(info.getId())) {
//            return jwtTokenUtil.generateToken(new JWTInfo(info.getUsername(), info.getId() + "", info.getName()));
//        }
//        throw new UserInvalidException("用户不存在或账户密码错误!");
//    }
//
//    @Override
//    public void validate(String token) throws Exception {
//        jwtTokenUtil.getInfoFromToken(token);
//    }
//
//    @Override
//    public String refresh(String oldToken) throws Exception {
//        return jwtTokenUtil.generateToken(jwtTokenUtil.getInfoFromToken(oldToken));
//    }
}
