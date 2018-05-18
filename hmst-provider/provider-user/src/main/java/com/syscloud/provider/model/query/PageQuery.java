package com.syscloud.provider.model.query;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * Created by hm on 2017/12/26.
 */
public class PageQuery implements Serializable{
    @Getter
    @Setter
    @Min(value = 1,message = "当前页码不合法")
    private int pageNo = 1;

    @Getter
    @Setter
    @Min(value = 1,message = "每页展示的数量不合法")
    private int pageSize = 10;

    @Setter
    private int offset;

    public int getOffset(){
        return (pageNo-1)*pageSize;
    }
}
