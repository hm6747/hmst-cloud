package com.scala.syscloud

import java.util.UUID

import akka.actor.{Actor, ActorSelection, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

/**
  * Created by Administrator on 2018/8/23 0023.
  */
class Worker(val masterHost:String,val masterPort:Int,memory:Int,cores:Int) extends Actor{
  var master : ActorSelection = _
  val workerId = UUID.randomUUID().toString
  override def receive: Receive = {
    case "reply" => {
      println("a reply from master")
    }
  }

  override def preStart(): Unit = {
    val master = context.actorSelection(s"akka.tcp://MasterSystem@${masterHost}:${masterPort}/user/Master")
    master ! RegisterWorker(workerId,memory,cores)
  }
}

object Worker {
  def main(args: Array[String]) {
    val host = args(0)
    val port = args(1).toInt
    val masterHost = args(2)
    val masterPort = args(3).toInt
    val memory = args(4).toInt
    val cores = args(5).toInt
    val configStr =
      s"""
         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname = "$host"
         |akka.remote.netty.tcp.port = "$port"
         """.stripMargin
    val actorSystem = ActorSystem("MasterSystem",ConfigFactory.parseString(configStr))
    actorSystem.actorOf(Props(new Worker(masterHost,masterPort,memory,cores)),"Worker")
    actorSystem.awaitTermination()
  }
}