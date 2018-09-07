package com.syscloud.base.auth.msg;


import com.syscloud.base.auth.constant.RestCodeConstants;

/**
 * Created by hm on 2017/8/23.
 */
public class TokenErrorResponse extends BaseResponse {
    public TokenErrorResponse(String message) {
        super(RestCodeConstants.TOKEN_ERROR_CODE, message);
    }
}
