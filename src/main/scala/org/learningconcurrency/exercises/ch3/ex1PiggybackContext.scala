package org.learningconcurrency.exercises.ch3

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success, Try}

/**
  * Implement	a	custom	 ExecutionContext 	class	called	 PiggybackContext ,	which
  * executes	 Runnable 	objects	on	the	same	thread	that	calls	 execute .	Ensure	that	a
  * Runnable 	object	executing	on	the	 PiggybackContext 	can	also	call	 execute 	and	that
  * exceptions	are	properly	reported
  */
class ex1PiggybackContext extends ExecutionContext {

  override def execute(runnable: Runnable): Unit = Try(runnable.run()) match {
    case Success(s) =>
    case Failure(f) => reportFailure(f)
  }

  override def reportFailure(cause: Throwable): Unit = throw new IllegalStateException(cause)

}
