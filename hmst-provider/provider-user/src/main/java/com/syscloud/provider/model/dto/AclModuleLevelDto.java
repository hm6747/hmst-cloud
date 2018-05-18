package com.syscloud.provider.model.dto;

import com.google.common.collect.Lists;
import com.syscloud.provider.model.vo.SysAclModule;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
public class AclModuleLevelDto extends SysAclModule implements Serializable{

    private List<AclModuleLevelDto> aclModuleList = Lists.newArrayList();

      private List<AclDto> aclList = Lists.newArrayList();

    public static AclModuleLevelDto adapt(SysAclModule aclModule) {
        AclModuleLevelDto dto = new AclModuleLevelDto();
        BeanUtils.copyProperties(aclModule, dto);
        return dto;
    }
}
