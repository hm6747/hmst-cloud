import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

class Master extends Actor {
  println("constructor invoked")

  override def receive: Receive = {
    case "connect" => {
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