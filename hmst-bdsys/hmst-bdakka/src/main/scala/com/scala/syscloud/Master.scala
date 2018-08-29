package com.scala.syscloud

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

import scala.collection.mutable
import scala.concurrent.duration._
class Master(val host:String,val port:Int) extends Actor {
  val idToWorker = new mutable.HashMap[String,WorkInfo]()
  val workers = new mutable.HashSet[WorkInfo]()
  val CHECK_INTERVAL = 15000
  override def receive: Receive = {
    case RegisterWorker(id,memory,cores) => {
      if(idToWorker.contains(id)){
        println(s"${id}已经注册过")

      }else{
        val workInfo = new WorkInfo(id,memory,cores)
        idToWorker(id)=workInfo
        workers += workInfo
        sender ! RegisteredWorker(s"akka.tcp://MasterSystem@${host}:${port}/user/Master")
      }
    }

    case Heartbeat(id) => {
      if(idToWorker.contains(id)){
        val  workInfo = idToWorker(id)
        val currentTime = System.currentTimeMillis()
        workInfo.lastHeartbeatTimes=currentTime
      }
    }

    case CheckTimeOutWorker => {
      val currentTime = System.currentTimeMillis()
      val toRemove = workers.filter(x => currentTime-x.lastHeartbeatTimes > CHECK_INTERVAL)
      for(w <- toRemove){
        workers -= w
        idToWorker -= w.id
      }
      println(s"还有${workers.size}个服务")
    }
  }

  override def preStart(): Unit = {
    println("preStart invoked")
    import context.dispatcher
    context.system.scheduler.
      schedule(0 millis,CHECK_INTERVAL millis,self,CheckTimeOutWorker)
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
    val master = actorSystem.actorOf(Props(new Master(host,port)),"Master")
    actorSystem.awaitTermination()
  }
}