package com.syscloud.pojo;

import java.util.List;

/**
 * Created by Administrator on 2018/2/5 0005.
 */
public class TreeBase {
    private long id;
    private String text;
    private  String state;
    private List<TreeBase> nodes;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<TreeBase> getNodes() {
        return nodes;
    }

    public void setNodes(List<TreeBase> nodes) {
        this.nodes = nodes;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
