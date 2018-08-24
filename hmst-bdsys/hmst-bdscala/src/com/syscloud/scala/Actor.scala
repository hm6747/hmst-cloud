package com.syscloud.scala

import scala.actors.Actor

/**
  * Created by Administrator on 2018/8/22 0022.
  */
class Actor1 extends Actor {
  override def act(): Unit = {
    while (true) {
      receive {
        case "start" => {
          println("starting...")
          Thread.sleep(5000)
          println(Thread.currentThread().getName)
          println("started...")
        }
        case "end" => {
          println("ending...")
          Thread.sleep(5000)
          println(Thread.currentThread().getName)
          println("ended...")
        }
        case  "exit" => {
          exit()
        }
      }
    }
  }
}

object Actor1 {
  def main(args: Array[String]) {
    var as = new Actor1
    as.start()
    as ! "start"
    as ! "end"
    as ! "exit"
  }
}
