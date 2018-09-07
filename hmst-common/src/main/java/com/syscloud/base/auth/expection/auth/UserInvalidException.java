package com.syscloud.base.auth.expection.auth;


import com.syscloud.base.auth.constant.CommonConstants;
import com.syscloud.base.auth.expection.BaseException;

/**
 * Created by hm on 2017/9/8.
 */
public class UserInvalidException extends BaseException {
    public UserInvalidException(String message) {
        super(message, CommonConstants.EX_USER_PASS_INVALID_CODE);
    }
}
