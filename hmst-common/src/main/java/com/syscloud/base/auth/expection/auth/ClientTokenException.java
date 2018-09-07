package com.syscloud.base.auth.expection.auth;


import com.syscloud.base.auth.expection.BaseException;
import com.syscloud.utils.jwt.CommonConstants;

/**
 * Created by hm on 2017/9/10.
 */
public class ClientTokenException extends BaseException {
    public ClientTokenException(String message) {
        super(message, CommonConstants.EX_CLIENT_INVALID_CODE);
    }
}
