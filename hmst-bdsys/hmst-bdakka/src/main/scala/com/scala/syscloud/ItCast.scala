package com.scala.syscloud

import java.net.URL

import org.apache.spark.{HashPartitioner, Partitioner, SparkConf, SparkContext}

import scala.collection.mutable

/**
  * Created by Administrator on 2018/9/3 0003.
  */
object ItCast {
  def main(args: Array[String]) {
    System.setProperty("hadoop.home.dir","G:\\ha-2.6.5\\hadoop-2.6.5\\");
    val conf = new SparkConf().setAppName("UrlCount").setMaster("local[2]")
    val sc = new SparkContext(conf)
    val rdd1 = sc.textFile("D:\\itcast.log").map(line =>{
      val f = line.split("\t")
      (f(1),1)
    })
    val rdd2 = rdd1.reduceByKey(_+_)

    val rdd3 = rdd2.map(t => {
      val url = t._1
      val host = new URL(url).getHost
      (host, (url,t._2))
    })
    var ins = rdd3.map(_._1).distinct().collect()
    println(ins.toBuffer)
//    rdd3.partitionBy(new HashPartitioner(ins.length)).saveAsTextFile("d:\\out3")

//    val hostParitioner = new HostParitioner(ins)
//    rdd3.partitionBy(hostParitioner).mapPartitions(it => {
//      it.toList.sortBy(_._2._2).reverse.take(2).iterator
//    }).saveAsTextFile("d:\\out2")
//    val rdd4 = rdd3.groupBy(_._1).mapValues(it => {
//      it.toList.sortBy(_._3).reverse.take(3)
//    })
//    rdd3.repartition(3).saveAsTextFile("d:\\out")
//    println(rdd4.collect().toBuffer)
    sc.stop()
  }

  class HostParitioner(ins:Array[String]) extends Partitioner {
    val parMap = new mutable.HashMap[String,Int]()
    var  count = 0;
    for (i <- ins){
      parMap += (i ->count)
      count += 1
    }

    override def numPartitions: Int = ins.length
    override def getPartition(key: Any): Int = {
      parMap.getOrElse(key.toString,0)
    }
  }
}
