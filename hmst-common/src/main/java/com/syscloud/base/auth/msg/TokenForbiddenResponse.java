package com.syscloud.base.auth.msg;


import com.syscloud.base.auth.constant.RestCodeConstants;

/**
 * Created by hm on 2017/8/25.
 */
public class TokenForbiddenResponse  extends BaseResponse {
    public TokenForbiddenResponse(String message) {
        super(RestCodeConstants.TOKEN_FORBIDDEN_CODE, message);
    }
}
