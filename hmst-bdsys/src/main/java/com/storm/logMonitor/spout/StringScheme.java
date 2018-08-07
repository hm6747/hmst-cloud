package com.storm.logMonitor.spout;

import backtype.storm.spout.Scheme;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import java.util.List;

/**
 * Created by Administrator on 2018/8/6 0006.
 */
public class StringScheme implements Scheme {
    @Override
    public List<Object> deserialize(byte[] bytes) {
        try {
            return new Values(new String(bytes));
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("反序列化异常");
            throw  new RuntimeException();
        }

    }

    @Override
    public Fields getOutputFields() {
        return null;
    }
}
