package com.scala.syscloud

import java.util.UUID

import akka.actor.{Actor, ActorSelection, ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import scala.concurrent.duration._

/**
  * Created by Administrator on 2018/8/23 0023.
  */
class Worker(val host:String , val port:Int,val masterHost:String,val masterPort:Int,memory:Int,cores:Int) extends Actor{
  var master : ActorSelection = _
  val workerId = UUID.randomUUID().toString
  val HEATBEAT_INTERVAL = 10000
  override def receive: Receive = {
    case RegisteredWorker(masterUrl) => {
      println(masterUrl)
      import context.dispatcher
      context.system.scheduler.schedule(0 millis,HEATBEAT_INTERVAL millis,self,SendHeartbeat)
    }

    case SendHeartbeat => {
      println("send heartbeat to master")
      master ! Heartbeat (workerId)
    }
  }

  override def preStart(): Unit = {
    master = context.actorSelection(s"akka.tcp://MasterSystem@${masterHost}:${masterPort}/user/Master")
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
    actorSystem.actorOf(Props(new Worker(host,port,masterHost,masterPort,memory,cores)),"Worker")
    actorSystem.awaitTermination()
  }
}