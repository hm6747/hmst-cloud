package com.scala.syscloud

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

import scala.collection.mutable

class Master extends Actor {
  val idToWorker = new mutable.HashMap[String,WorkInfo]()

  override def receive: Receive = {
    case RegisterWorker(id,memory,cores) => {
      if(idToWorker.contains(id)){
        println(s"${id}已经注册过")
      }else{
        val workInfo = new WorkInfo(id,memory,cores)
        idToWorker(id)=workInfo
      }
      println("a client connected")
      sender ! "reply"
    }

    case "hello" => {
      println("hello")
    }
  }

  override def preStart(): Unit = {
    println("preStart invoked")
  }
}

object Master {
  def main(args: Array[String]) {
    val host = args(0)
    val port = args(1).toInt
    val configStr =
      s"""
         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname = "$host"
         |akka.remote.netty.tcp.port = "$port"
         """.stripMargin
    val actorSystem = ActorSystem("MasterSystem",ConfigFactory.parseString(configStr))
    val master = actorSystem.actorOf(Props[Master],"Master")
    master ! "hello"
    actorSystem.awaitTermination()
  }
}