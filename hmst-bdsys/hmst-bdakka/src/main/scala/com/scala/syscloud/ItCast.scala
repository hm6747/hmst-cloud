package com.scala.syscloud

import java.net.URL

import org.apache.spark.{SparkConf, SparkContext}

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
      (host,url,t._2)
    })

    val rdd4 = rdd3.groupBy(_._1).mapValues(it => {
      it.toList.sortBy(_._3).reverse.take(3)
    })
    println(rdd4.collect().toBuffer)
    sc.stop()
  }
}