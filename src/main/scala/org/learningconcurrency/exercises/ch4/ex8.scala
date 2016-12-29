package org.learningconcurrency.exercises.ch4

import scala.concurrent.Promise
import scala.util.{Failure, Success}

/**
  * Extend	the	 Promise[T] 	type	with	the	 compose 	method,	which	takes	a	function	of	the
  * S	=>	T 	type,	and	returns	a	 Promise[S] 	object:
  * def	compose[S](f:	S	=>	T):	Promise[S]
  * Whenever	the	resulting	promise	is	completed	with	some	value	 x 	of	the	type	 S 	(or
  * failed),	the	original	promise	must	be	completed	with	the	value	 f(x) 	asynchronously
  * (or	failed),	unless	the	original	promise	is	already	completed.
  *
  */
object ex8 {

  implicit class PromiseOps[T](self: Promise[T]) {

    import scala.concurrent.ExecutionContext.Implicits.global

    def	compose[S](f:	S	=>	T):	Promise[S] = {

      val that = Promise[S]()

      that.future.onComplete {
        case Success(s) => self.success(f(s))
        case Failure(e) => self.failure(e)
      }

      that

    }

  }

}
