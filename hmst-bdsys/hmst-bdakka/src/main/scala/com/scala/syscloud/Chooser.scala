package com.scala.syscloud

/**
  * Created by Administrator on 2018/8/29 0029.
  */
object Mypredf2 {
  implicit def girl120(g:Girl) = new Ordered[Girl] {
    override def compare(that: Girl): Int = {
      g.age - that.age
    }
  }
}
class Chooser[T <% Ordered[T]] {
  def choose (first:T,second:T):T = {
    val ord = implicitly[Ordering[T]]
    if(ord.gt(first,second))first else second
  }
}
object Chooser {
  def main(args: Array[String]) {
    import Mypredf2.girl120
    val c = new Chooser[Girl]
    val g1 = new Girl("bt",90)
    val g2 = new Girl("at",90)
    val g = c.choose(g1,g2)
    println(g.name)
  }
}