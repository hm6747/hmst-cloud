package com.syscloud.provider.good.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Administrator on 2018/9/3 0003.
 */
@Getter
@Setter
@Entity
@Table(name = "h_good")
public class Good {
    private Long id;
    private String name;
}
