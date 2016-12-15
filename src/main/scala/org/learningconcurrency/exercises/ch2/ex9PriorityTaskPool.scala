package org.learningconcurrency.exercises.ch2

import scala.annotation.tailrec
import scala.collection.mutable

/**
  * Extend	the	 PriorityTaskPool 	class	from	the	previous	exercise	so	that	it	supports	any
  * number	of	worker	threads	 p .	The	parameter	 p 	is	specified	in	the	constructor	of	the
  * PriorityTaskPool 	class.
  */
class ex9PriorityTaskPool(p: Int) {

  private val tasks = mutable.PriorityQueue[(Int, () => Unit)]()(Ordering.by(_._1))

  def nextTask: () => Unit = tasks.synchronized {
    while (tasks.isEmpty)
      tasks.wait()
    tasks.dequeue()._2
  }

  def asynchronous(priority: Int)(body: => Unit): Unit = tasks.synchronized {
    tasks.enqueue((priority, () => body))
    tasks.notify()
  }

  class Worker extends Thread {
    setDaemon(true)

    override def run(): Unit =
      while (true) {
        nextTask()
      }
  }

  for (_ <- 0 until p) {
    val worker = new Worker()
    worker.start()
  }

}
