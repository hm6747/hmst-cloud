package com.syscloud.utils.jwt;

import java.util.List;
import java.util.Map;

public interface IJWTInfo {
    /**
     * 获取用户名
     * @return
     */
    String getUniqueName();

    /**
     * 获取用户ID
     * @return
     */
    String getId();

    /**
     * 获取名称
     * @return
     */
    String getName();

    /**
     * 获取菜单信息
     */
    List<Map<String,String>> getSource();

    /**
     * 获取权限信息
     */
    List<Map<String,String>> getAcls();
}
