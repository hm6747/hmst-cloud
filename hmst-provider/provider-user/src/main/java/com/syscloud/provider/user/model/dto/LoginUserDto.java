package com.syscloud.provider.user.model.dto;

import com.syscloud.provider.user.model.vo.SysUser;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

/**
 * Created by Administrator on 2018/3/26 0026.
 */
@Getter
@Setter
@ToString
public class LoginUserDto extends SysUser {
    private String expand1;
    private String expand2;
    private String expand3;
    private String expand4;
    public static LoginUserDto adapt(SysUser user) {
        LoginUserDto loginUserDto = new LoginUserDto();
        BeanUtils.copyProperties(user, loginUserDto);
        return loginUserDto;
    }
}
