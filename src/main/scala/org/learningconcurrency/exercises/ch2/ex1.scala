package org.learningconcurrency.exercises.ch2

/**
  * Implement	a	 parallel 	method,	which	takes	two	computation	blocks	 a 	and	 b ,	and
  * starts	each	of	them	in	a	new	thread.	The	method	must	return	a	tuple	with	the	result
  * values	of	both	the	computations.
  */
object ex1 {

  def	parallel1[A,	B](a:	=> A,	b: => B):	(A,	B) = {

    var result1 = null.asInstanceOf[A]
    var result2 = null.asInstanceOf[B]

    val thread1 = new Thread{
      override def run() = {
        result1 = a
      }
    }

    val thread2 = new Thread{
      override def run() = {
        result2 = b
      }
    }

    thread1.start()
    thread2.start()
    thread1.join()
    thread2.join()
    (result1, result2)

  }

  def	parallel2[A,	B](a:	=> A,	b: => B):	(A,	B) = {

    var result1 = Option.empty[A]
    var result2 = Option.empty[B]

    val thread1 = thread {result1 = Option(a)}
    val thread2 = thread {result2 = Option(b)}

    thread1.join()
    thread2.join()
    (result1.get, result2.get)

  }


}
