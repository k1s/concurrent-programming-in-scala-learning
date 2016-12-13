package org.learningconcurrency.exercises.ch2

/**
  * Implement	a	 periodically 	method,	which	takes	a	time	interval	 duration 	specified
  * in	milliseconds,	and	a	computation	block	 b .	The	method	starts	a	thread	that	executes
  * the	computation	block	 b 	every	 duration 	milliseconds
  *
  */
object ex2 {

  def periodically(duration: Long)(b: => Unit): Unit = {
    val t = thread {
      while (true) {
        b
        Thread.sleep(duration)
      }
    }
  }

}
