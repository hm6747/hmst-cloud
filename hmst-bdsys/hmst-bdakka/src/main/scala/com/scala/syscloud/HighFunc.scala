package com.scala.syscloud

/**
  * Created by Administrator on 2018/8/28 0028.
  */
object HighFunc {
  val func:Int => Int={x => x*x}
  def multiply(x:Int) : Int = x*x
  def multi() = (x:Int) => {
    x*x
  }
  def main(args: Array[String]) {
    val arr = Array(1,2,3,4,5)
    val a1 =arr.map(func)
    println(arr.toBuffer)

  }
}
