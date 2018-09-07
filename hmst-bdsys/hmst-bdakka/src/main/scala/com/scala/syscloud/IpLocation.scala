package com.scala.syscloud

import java.sql.{Connection, Date, DriverManager, PreparedStatement}

import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Administrator on 2018/9/5 0005.
  */
object IpLocation {
  val data2MySQL = (iterator: Iterator[(String, Int)]) => {
    var conn: Connection = null
    var ps: PreparedStatement = null
    val sql = "INSERT INTO location_info (location, counts, accesse_date) VALUES (?, ?, ?)"
    try {
      conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/user", "root", "Hm123456")
      iterator.foreach(line => {
        ps = conn.prepareStatement(sql)
        ps.setString(1, line._1)
        ps.setInt(2, line._2)
        ps.setDate(3, new Date(System.currentTimeMillis()))
        ps.executeUpdate()
      })
    } catch {
      case e: Exception => println("Mysql Exception")
    } finally {
      if (ps != null)
        ps.close()
      if (conn != null)
        conn.close()
    }
  }


  def main(args: Array[String]) {
    System.setProperty("hadoop.home.dir", "G:\\ha-2.6.5\\hadoop-2.6.5\\");
    val conf = new SparkConf().setAppName("IpLocation").setMaster("local[2]")
    val sc = new SparkContext(conf)
    val ipRdd = sc.textFile("D:\\ip.txt")
      .map(line => {
        val fields = line.split("\\|")
        val startip = fields(2)
        val endip = fields(3)
        val province = fields(6)
        (startip, endip, province)
      })
    val iparr = ipRdd.collect()
    val ipbroadcast = sc.broadcast(iparr)
    val ipRdd2 = sc.textFile("D:\\20090121000132.394251.http.format")
      .map(line => {
        val fields = line.split("\\|")
        val ip = ip2Long(fields(1))
        val index = binarySearch(ipbroadcast.value, ip)
        val info = ipbroadcast.value(index)
        info
      }).map(t =>(t._3,1)).reduceByKey(_+_)
    ipRdd2.foreachPartition(data2MySQL)
  }

  def ip2Long(ip: String): Long = {

    val fragments = ip.split("[.]")
    var ipNum = 0L
    for (i <- 0 until fragments.length) {
      ipNum = fragments(i).toLong | ipNum << 8L
    }
    ipNum
  }

  def binarySearch(lines: Array[(String, String, String)], ip: Long): Int = {
    var low = 0
    var high = lines.length - 1
    while (low <= high) {
      val middle = (low + high) / 2
      if ((ip >= lines(middle)._1.toLong) && (ip <= lines(middle)._2.toLong))
        return middle
      if (ip < lines(middle)._1.toLong)
        high = middle - 1
      else {
        low = middle + 1
      }
    }
    -1
  }

}
