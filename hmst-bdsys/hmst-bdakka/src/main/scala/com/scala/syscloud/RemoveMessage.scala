package com.scala.syscloud

/**
  * Created by Administrator on 2018/8/24 0024.
  */
trait RemoveMessage extends Serializable

//Worker->Master
case class RegisterWorker(id:String,memory:Int,cores:Int) extends RemoveMessage
//Master->Worker
case class RegisteredWorker(masterUrl:String) extends RemoveMessage
//self -> self
case object SendHeartbeat
case class Heartbeat(id:String) extends RemoveMessage
case object CheckTimeOutWorker

