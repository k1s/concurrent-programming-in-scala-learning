package org.learningconcurrency.exercises.ch2

/**
  * The	 SyncVar 	object	from	the	previous	exercise	can	be	cumbersome	to	use,	due	to
  * exceptions	when	the	 SyncVar 	object	is	in	an	invalid	state.	Implement	a	pair	of
  * methods	 isEmpty 	and	 nonEmpty 	on	the	 SyncVar 	object.	Then,	implement	a	producer
  * thread	that	transfers	a	range	of	numbers	 0	until	15 	to	the	consumer	thread	that
  * prints	them.
  */
class ex4SyncVar[T] {

  private var x: Option[T] = None
  private val lock = new AnyRef

  def isEmpty: Boolean = lock.synchronized(x.isEmpty)

  def nonEmpty: Boolean = lock.synchronized(x.nonEmpty)

  def get(): T = lock.synchronized {
    if (isEmpty)
      throw new IllegalStateException("Var is empty!")
    else {
      val tmp = x.get
      x = None
      tmp
    }
  }

  def put(t: T): Unit = lock.synchronized {
    if (isEmpty)
      x = Some(t)
    else
      throw new IllegalStateException("Var is not empty!")
  }

}
