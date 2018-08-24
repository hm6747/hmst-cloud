package com.syscloud.scala


import scala.actors.{Actor, Future}
import scala.collection.mutable.{HashSet, ListBuffer}
import scala.io.Source

/**
  * Created by Administrator on 2018/8/22 0022.
  */
class Task extends Actor {
  override def act(): Unit = {
    loop {
      react {
        case SubmitTask(filename) => {
         val result =  Source.fromFile(filename).getLines().flatMap(_.split(" "))
            .map((_,1)).toList.groupBy(_._1).mapValues(_.size)
            sender ! ResultTask(result)
        }
        case StopTask => {
          exit()
        }
      }
    }
  }
}

case class SubmitTask(filename: String)
case class ResultTask(reslut : Map[String, Int])
case object StopTask

object Word {
  def main(args: Array[String]) {
    val replySet = new HashSet[Future[Any]]()
    var resultList = new ListBuffer[ResultTask]()
    val  files = Array[String]("c://words1.txt","c://words2.txt")
    for(f <- files){
      val actor = new Task
      val reply = actor.start() !! SubmitTask(f)
      replySet += reply
    }
    while (replySet.size >0){
      val toCompute = replySet.filter(_.isSet)
      for(r <- toCompute){
        val result = r().asInstanceOf[ResultTask]
        resultList += result
        replySet -= r
      }
      Thread.sleep(100)
    }
    //汇总
//    val fr = resultList.flatMap(_.reslut).groupBy(_._1).mapValues(_.foldLeft(0)(_+_._2))
  }
}
