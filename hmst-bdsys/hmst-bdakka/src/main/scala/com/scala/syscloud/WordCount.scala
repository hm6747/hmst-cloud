package com.scala.syscloud

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Administrator on 2018/8/31 0031.
  */
object WordCount {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("WC")
    val sc = new SparkContext(conf)
    sc.textFile(args(0)).flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_).sortBy(_._2,false).saveAsTextFile(args(1))
    sc.stop()
  }

}
