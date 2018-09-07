package com.syscloud.provider.user.service.impl;


import com.syscloud.exception.ParamException;
import com.syscloud.provider.user.mapper.LoginUserMapper;
import com.syscloud.provider.user.mapper.SysAclMapper;
import com.syscloud.provider.user.mapper.SysUserMapper;
import com.syscloud.provider.user.model.dto.LoginUserDto;
import com.syscloud.provider.user.model.vo.LoginUser;
import com.syscloud.provider.user.model.vo.SysUser;
import com.syscloud.provider.user.service.LoginService;
import com.syscloud.provider.user.utils.JwtTokenUtil;
import com.syscloud.utils.MD5Util;
import com.syscloud.utils.jwt.JWTInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2018/3/21 0021.
 */
@Service
public class loginServiceImpl implements LoginService {
    @Resource
    private LoginUserMapper loginUserMapper;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private SysAclMapper sysAclMapper;
    @Override
    public String LoginByUserNameAndPassword(String username, String password, Integer type) throws Exception {
        if (StringUtils.isBlank(username)) {
            throw new ParamException("用户名不可以为空");
        }
        LoginUser loginUser =  loginUserMapper.selectByIdentifierAndType(type, username);
        if (loginUser == null) {
            throw new ParamException("查询不到指定的用户");
        } else if (loginUser.getStatus() != 1) {
            throw new ParamException("用户已被禁止登陆，请联系管理员");
        } else if (StringUtils.isBlank(password)) {
            throw new ParamException("密码不可以为空");
        } else if (!loginUser.getCredential().equals(MD5Util.encrypt(password))) {
            throw new ParamException("用户名或密码错误");
        }
        SysUser user = sysUserMapper.selectByPrimaryKey(loginUser.getUserId());
        return jwtTokenUtil.generateToken(new JWTInfo(user.getUsername(),user.getId().toString(),user.getRemark()));
    }

    @Override
    public String refresh(String oldToken) throws Exception {
        return jwtTokenUtil.generateToken(jwtTokenUtil.getInfoFromToken(oldToken));
    }

    @Override
    public void validate(String token) throws Exception {
        jwtTokenUtil.getInfoFromToken(token);
    }

    public LoginUserDto LoginUserToDto(SysUser sysUser, LoginUser loginUser) throws ParamException {
        if(sysUser == null){
            throw new ParamException("用户已被删除");
        }else if(sysUser.getStatus() != 1){
            throw new ParamException("用户已被冻结");
        }
        LoginUserDto loginUserDto = LoginUserDto.adapt(sysUser);
        loginUserDto.setExpand1(loginUser.getExpand1());
        loginUserDto.setExpand2(loginUser.getExpand2());
        loginUserDto.setExpand3(loginUser.getExpand3());
        loginUserDto.setExpand4(loginUser.getExpand4());
        return  loginUserDto;
    }

}
