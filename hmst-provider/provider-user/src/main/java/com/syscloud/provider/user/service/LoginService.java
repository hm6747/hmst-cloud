package com.syscloud.provider.user.service;


/**
 * Created by Administrator on 2018/3/21 0021.
 */
public interface LoginService {
     String LoginByUserNameAndPassword(String UserName, String password, Integer type) throws Exception;
     String refresh(String oldToken) throws Exception;
     void validate(String token) throws Exception;
}
