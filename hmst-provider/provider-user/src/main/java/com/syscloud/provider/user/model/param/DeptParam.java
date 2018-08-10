package com.syscloud.provider.user.model.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by hm on 2017/12/23.
 */
@Getter
@Setter
@ToString
public class DeptParam implements Serializable{
    private  Integer id;
    @NotBlank(message = "部门名称不可以为空")
    @Length(max = 15,min = 2,message = "部门名称必须在2到15之间")
    private  String name;
    private  Integer parentId = 0;
    @NotNull(message = "展示顺序不可以为空")
    private  Integer seq;
    @Length(max = 150,message = "备注的长度不能超过150个字")
    private  String remark;
}
