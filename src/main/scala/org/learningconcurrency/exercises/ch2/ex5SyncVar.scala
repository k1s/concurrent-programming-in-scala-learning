package org.learningconcurrency.exercises.ch2

/**
  * Using	the	 isEmpty 	and	 nonEmpty 	pair	of	methods	from	the	previous	exercise	requires
  * busy-waiting.	Add	the	following	methods	to	the	 SyncVar 	class:
  * def	getWait():	T
  * def	putWait(x:	T):	Unit
  * These	methods	have	similar	semantics	as	before,	but	go	into	the	waiting	state	insteadof	throwing	an
  * exception,	and	return	once	the	 SyncVar 	object	is	empty	or	non-empty, respectively.
  */
class ex5SyncVar[T] {

  private var x: Option[T] = None
  private val lock = new AnyRef

  def getWait: T = lock.synchronized {
    while (x.isEmpty)
      lock.wait()
    val tmp = x.get
    x = None
    lock.notify()
    tmp
  }

  def putWait(t: T): Unit = lock.synchronized {
    while (x.nonEmpty)
      lock.wait()
      x = Some(t)
    lock.notify()
  }

}
