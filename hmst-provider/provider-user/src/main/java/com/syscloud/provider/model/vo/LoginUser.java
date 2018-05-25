package com.syscloud.provider.model.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/26 0026.
 */
@Getter
@Setter
public class LoginUser implements Serializable{
    private Integer id;
    private Integer userId;
    private Integer identityType;
    private Integer status;
    private String identifier;
    private String credential;
    private String expand1;
    private String expand2;
    private String expand3;
    private String expand4;
}
