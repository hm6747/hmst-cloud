package com.storm.logMonitor;

import backtype.storm.topology.TopologyBuilder;
import com.storm.logMonitor.spout.RandomSpout;
import logAnalyze.storm.spout.StringScheme;
import org.apache.log4j.Logger;

/**
 * Created by Administrator on 2018/8/6 0006.
 */
public class LogMonitorApp {
    private static Logger logger = Logger.getLogger(LogMonitorApp.class);

    public static void main(String[] args) {
        // 使用TopologyBuilder进行构建驱动类
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("k-spout",new RandomSpout(new StringScheme()),2);
    }
}
