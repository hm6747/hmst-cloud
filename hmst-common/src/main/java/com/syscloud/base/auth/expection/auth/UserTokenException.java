package com.syscloud.base.auth.expection.auth;


import com.syscloud.base.auth.expection.BaseException;
import com.syscloud.utils.jwt.CommonConstants;

/**
 * Created by hm on 2017/9/8.
 */
public class UserTokenException extends BaseException {
    public UserTokenException(String message) {
        super(message, CommonConstants.EX_USER_INVALID_CODE);
    }
}
