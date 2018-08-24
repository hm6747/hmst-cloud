import akka.actor.{Actor, ActorSelection, ActorSystem, Props}
import akka.actor.Actor.Receive
import com.typesafe.config.ConfigFactory

/**
  * Created by Administrator on 2018/8/23 0023.
  */
class Worker(val masterHost:String,val masterPort:Int) extends Actor{
  val master : ActorSelection = _
  override def receive: Receive = {
    case "replay" => {
      println("a reply from master")
    }
  }

  override def preStart(): Unit = {
    val master = context.actorSelection(s"akka.tcp://MasterSystem@${masterHost}:${masterPort}/user/Master")
    master ! "connect"
  }
}
object Worker {
  def main(args: Array[String]) {
    val host = args(0)
    val port = args(1).toInt
    val masterHost = args(2)
    val masterPort = args(3).toInt
    val configStr =
      s"""
         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname = "$host"
         |akka.remote.netty.tcp.port = "$port"
         """.stripMargin
    val actorSystem = ActorSystem("MasterSystem",ConfigFactory.parseString(configStr))
    actorSystem.actorOf(Props(new Worker(masterHost,masterPort)),"Worker")
    actorSystem.awaitTermination()
  }
}