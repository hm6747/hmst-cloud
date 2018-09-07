package com.syscloud.base.auth.expection.auth;


import com.syscloud.base.auth.expection.BaseException;
import com.syscloud.utils.jwt.CommonConstants;

/**
 * Created by hm on 2017/9/12.
 */
public class ClientForbiddenException extends BaseException {
    public ClientForbiddenException(String message) {
        super(message, CommonConstants.EX_CLIENT_FORBIDDEN_CODE);
    }

}
