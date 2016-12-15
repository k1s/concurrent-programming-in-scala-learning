package org.learningconcurrency.exercises.ch2

import scala.collection.mutable

/**
  * Recall	the	 asynchronous 	method	from	the	Guarded	blocks	section.	This	method
  * stores	the	tasks	in	a	First	In	First	Out	(FIFO)	queue;	before	a	submitted	task	is
  * executed,	all	the	previously	submitted	tasks	need	to	be	executed.	In	some	cases,	we
  * want	to	assign	priorities	to	tasks	so	that	a	high-priority	task	can	execute	as	soon	as	it
  * is	submitted	to	the	task	pool.	Implement	a	 PriorityTaskPool 	class	that	has	the
  * asynchronous 	method	with	the	following	signature:
  * def	asynchronous(priority:	Int)(task:	=>Unit):	Unit
  * A	single	worker	thread	picks	tasks	submitted	to	the	pool	and	executes	them.
  * Whenever	the	worker	thread	picks	a	new	task
  */
class ex8PriorityTaskPool {

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

  val worker = new Thread {
    setDaemon(true)

    override def run(): Unit =
      while (true) {
        nextTask()
      }
  }

  worker.start()

}
