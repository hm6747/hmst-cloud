package com.scala.syscloud

/**
  * Created by Administrator on 2018/8/24 0024.
  */
trait RemoveMessage extends Serializable

//Worker->Master
case class RegisterWorker(id:String,memory:Int,cores:Int)
