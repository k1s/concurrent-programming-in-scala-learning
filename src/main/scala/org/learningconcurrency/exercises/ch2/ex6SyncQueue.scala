package org.learningconcurrency.exercises.ch2

import scala.collection.mutable

/**
  * A	 SyncVar 	object	can	hold	at	most	one	value	at	a	time.	Implement	a	 SyncQueue 	class,
  * which	has	the	same	interface	as	the	 SyncVar 	class,	but	can	hold	at	most	 n 	values.	The
  * parameter	 n 	is	specified	in	the	constructor	of	the	 SyncQueue 	class
  */
class ex6SyncQueue[T](n: Int) {

  private var queue = mutable.Queue[T]()
  private val lock = new AnyRef

  def getWait: T = lock.synchronized {
    while (queue.isEmpty)
      lock.wait()
    val tmp = queue.dequeue()
    lock.notify()
    tmp
  }

  def putWait(t: T): Unit = lock.synchronized {
    while (queue.nonEmpty)
      lock.wait()
    queue.enqueue(t)
    lock.notify()
  }

}
