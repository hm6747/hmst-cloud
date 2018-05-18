package com.syscloud.provider.model.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/16 0016.
 */
@Getter
@Setter
@ToString
public class SearchLogParam implements Serializable{


    private Integer type; // LogType

    private String beforeSeg;

    private String afterSeg;

    private String operator;

    private String fromTime;//yyyy-MM-dd HH:mm:ss

    private String toTime;

}
