package com.scala.syscloud

import java.io.File

import scala.io.Source

/**
  * Created by Administrator on 2018/8/29 0029.
  */
object MyPredef{
  implicit def fileToRichFile(f:File) = new RichFile(f)
}
class RichFile(val f:File) {
   def read() = Source.fromFile(f).mkString
}


object RichFile {
  def main(args: Array[String]) {
    val f = new File("C:\\words1.txt")
    import MyPredef.fileToRichFile
    val contents = f.read()
    println(contents)
  }
}

