package com.syscloud.scala

import scala.actors.Actor

class Person{
  val id = "214dsa"
  var name = "华安"
}
object Person {
  def main(args: Array[String]): Unit = {
//    var arr = ArrayBuffer(0);
//    for(a <- 1 to 1000000) arr.append(a)T
//
//
//    val starttime=System.nanoTime
//    println("第一个"+arr.reduce(_+_))
//    val endtime=(System.nanoTime-starttime)/100000
//    println("第一个"+endtime)
//
//    val starttime2=System.nanoTime
//    println("第二个"+arr.par.reduce(_+_))
//    val endtime2=(System.nanoTime-starttime2)/100000
//    println("第二个"+endtime2)

//    val p = new Person
//    println(p.id)
//    println(p.name)
      Actor1.start()
    Actor2.start()
  }

}

object Actor1 extends Actor{
  def act (): Unit ={
    for(a <- 1 to 20){
      println(s"thread1,${new Person().name}")
      Thread.sleep(1000)
    }
  }
}

object Actor2 extends Actor{
  def act (): Unit ={
    for(a <- 1 to 20) {
      println(s"thread2,${new Person().id}")
      Thread.sleep(1000)
    }
  }
}
