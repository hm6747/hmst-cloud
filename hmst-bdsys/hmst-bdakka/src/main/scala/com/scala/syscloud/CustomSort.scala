package com.scala.syscloud

import org.apache.spark.{SparkConf, SparkContext}


object OrderContext {
  implicit val girlOrdering = new Ordering[Girls2]{
    override def compare(x: Girls2, y: Girls2): Int = {
      if(x.faceVal > y.faceVal) 1
      else if (x.faceVal == y.faceVal){
        if(x.age>y.age) -1 else 1
      }else -1
    }
  }
}
/**
  * Created by Administrator on 2018/9/4 0004.
  */
object CustomSort {
  def main(args: Array[String]) {
    System.setProperty("hadoop.home.dir","G:\\ha-2.6.5\\hadoop-2.6.5\\");
    val conf = new SparkConf().setAppName("UrlCount").setMaster("local[2]")
    val sc = new SparkContext(conf)
    val rdd1 = sc.parallelize(List(("yuihatano1",55,28,1),("yuihatano2",90,33,1),("yuihatano3",86,22,1),("yuihatano4",90,27,1)))
    import OrderContext._
    val rdd2 = rdd1.sortBy(x => Girls2(x._2,x._3),false)
    println(rdd2.collect().toBuffer)
  }
}

case class Girls ( faceVal:Int, age:Int) extends Ordered[Girls] with Serializable{
  override def compare(that: Girls): Int = {
    if(this.faceVal == that.faceVal){
      that.age-this.age
    }else{
      this.faceVal - that.faceVal
    }
  }
}

case class Girls2 (faceVal:Int,age:Int) extends Serializable