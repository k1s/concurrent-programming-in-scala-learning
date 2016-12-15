package org.learningconcurrency.exercises.ch2

import scala.collection.mutable

/**
  * Extend	the	 PriorityTaskPool 	class	from	the	previous	exercise	so	that	it	supports	the
  * shutdown 	method:
  * def	shutdown():	Unit
  * When	the	 shutdown 	method	is	called,	all	the	tasks	with	the	priorities	greater	than
  * important 	must	be	completed,	and	the	rest	of	the	tasks	must	be	discarded.	The
  * important 	integer	parameter	is	specified	in	the	constructor	of	the	 PriorityTaskPool
  * class.
  */
class ex10PriorityTaskPool(threadsNum: Int, important: Int) {

  private val tasks = mutable.PriorityQueue[(Int, () => Unit)]()(Ordering.by(_._1))

  @volatile private var isShutdown = false

  def nextTask(): Unit = tasks.synchronized {
    while (tasks.isEmpty)
      tasks.wait()
    tasks.dequeue() match {
      case (priority, task) => if (!isShutdown || priority > important) task()
    }
  }

  def asynchronous(priority: Int)(body: => Unit): Unit = tasks.synchronized {
    tasks.enqueue((priority, () => body))
    tasks.notify()
  }

  def shutdown(): Unit = isShutdown = true

  class Worker extends Thread {
    setDaemon(true)

    override def run(): Unit =
      while (true) {
        nextTask()
      }
  }

  for (_ <- 0 until threadsNum) {
    val worker = new Worker()
    worker.start()
  }

}
