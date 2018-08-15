package com.syscloud.scala

object Hello {
  def main(args: Array[String]): Unit = {
    val map = Map(("a",1),("b",2))
    println(map("a"))
    println(map.getOrElse("d",4))
  }
}
