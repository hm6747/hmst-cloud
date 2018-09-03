package com.storm.wordconunt;

import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

/**
 * Created by Administrator on 2018/7/30 0030.
 */
public class WordCount {
    public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException {
        TopologyBuilder topologyBuilder = new TopologyBuilder();
        topologyBuilder.setSpout("MySpout",new MySpout(),1);
        topologyBuilder.setBolt("MyBount",new MyBount(),10).shuffleGrouping("MySpout");
        topologyBuilder.setBolt("MyBount2",new MyCountBolt(),2).fieldsGrouping("MyBount",new Fields("word"));

        Config config = new Config();
        config.setNumWorkers(2);
        StormSubmitter.submitTopology("mywordcount",config,topologyBuilder.createTopology());
/*        LocalCluster localCluster = new LocalCluster();
        localCluster.submitTopology("mywordcount",config,topologyBuilder.createTopology());*/
    }
}
