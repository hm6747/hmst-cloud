package com.syscloud.provider.user.mapper;

import com.syscloud.provider.user.model.vo.LoginUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface LoginUserMapper {
    LoginUser selectByIdentifierAndType(@Param("identityType") Integer identityType, @Param("identifier") String identifier);

}