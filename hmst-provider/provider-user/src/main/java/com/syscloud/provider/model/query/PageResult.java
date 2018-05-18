package com.syscloud.provider.model.query;

import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hm on 2017/12/26.
 */
@Getter
@Setter
@ToString
@Builder
public class PageResult<T> implements Serializable{

    private List<T> data = Lists.newArrayList();

    private int total;

    private int pageSize;

    private int pageNum;
}
