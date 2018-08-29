package com.scala.syscloud

/**
  * Created by Administrator on 2018/8/28 0028.
  */
object Context {
  implicit val a = "laozhao"
}

object implic {

  def sayHi()(implicit name:String = "laomao") = {
    println(s"hi${name}")
  }
  def main(args: Array[String]) {
    import Context.a
    sayHi()
  }
}