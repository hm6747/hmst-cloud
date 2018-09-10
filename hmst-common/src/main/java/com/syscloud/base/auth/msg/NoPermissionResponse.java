package com.syscloud.base.auth.msg;

import com.syscloud.base.auth.constant.RestCodeConstants;

/**
 * Created by Administrator on 2018/9/10 0010.
 */
public class NoPermissionResponse extends BaseResponse {
    public NoPermissionResponse(String message) {
        super(RestCodeConstants.PERMISSION_FORBIDDEN_CODE, message);
    }
}
