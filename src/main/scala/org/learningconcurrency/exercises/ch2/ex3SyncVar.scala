package org.learningconcurrency.exercises.ch2

/**
  * Implement	a	 SyncVar 	class.
  * A	 SyncVar 	object	is	used	to	exchange	values	between	two	or	more	threads.	When
  * created,	the	 SyncVar 	object	is	empty:
  * Calling	 get 	throws	an	exception
  * Calling	 put 	adds	a	value	to	the	 SyncVar 	object
  * After	a	value	is	added	to	a	 SyncVar 	object,	we	can	say	that	it	is	non-empty:
  * Calling	 get 	returns	the	current	value,	and	changes	the	state	to	empty
  * Calling	 put 	throws	an	exception
  */
class ex3SyncVar[T] {

  private var x: Option[T] = None

  def get(): T = x.synchronized {
    if (x.isEmpty)
      throw new IllegalStateException("Var is empty!")
    else {
      val tmp = x.get
      x = None
      tmp
    }
  }

  def put(t: T): Unit = x.synchronized {
    if (x.isEmpty)
      x = Some(t)
    else
      throw new IllegalStateException("Var is not empty!")
  }

}
