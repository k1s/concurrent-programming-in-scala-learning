package org.learningconcurrency.exercises

/**
  *
  *
  */
package object ch2 {

  /**
   * Create and start thread with <b>body</b> function
   */
  def thread(body: => Unit): Thread = {
    val t = new Thread{
      override def run() = body
    }
    t.start()
    t
  }

}
