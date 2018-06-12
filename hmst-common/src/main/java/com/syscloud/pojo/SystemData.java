package com.syscloud.pojo;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2018/6/11 0011.
 */
@Getter
@Setter
public class SystemData extends JsonData {
    private int code; //错误信息定义码 面向系统级异常
    private String errorMsg; //错误信息 面向系统级异常
    public SystemData() {
        super();
    }
    public static SystemData create(int code ,String errorMsg) {
        SystemData systemData = new SystemData();
        systemData.setCode(code);
        systemData.setErrorMsg(errorMsg);
        return  systemData;
    }
}
