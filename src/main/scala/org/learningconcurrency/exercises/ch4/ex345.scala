package org.learningconcurrency.exercises.ch4

import scala.concurrent.{Future, Promise}
import scala.async.Async.async
import scala.async.Async.await

/**
  * Extend	the	 Future[T] 	type	with	the	 exists 	method,	which	takes	a	predicate	and
  * returns	a	 Future[Boolean] 	object:
  * def	exists(p:	T	=>	Boolean):	Future[Boolean]
  * The	resulting	future	is	completed	with	 true 	if	and	only	if	the	original	future	is
  * completed	and	the	predicate	returns	 true ,	and	 false 	otherwise.	You	can	use	future
  * combinators,	but	you	are	not	allowed	to	create	any	 Promise 	objects	in	the
  * implementation.
  *
  * Repeat	the	previous	exercise,	but	use	 Promise 	objects	instead	of	future	combinators.
  *
  * Repeat	the	previous	exercise,	but	use	the	Scala	Async	framework.
  *
  */
object ex345 {

  import scala.concurrent.ExecutionContext.Implicits.global

  implicit class FutureOps1[T](self: Future[T]) {

    def	exists(p:	T	=>	Boolean):	Future[Boolean] = self map p

  }

  implicit class FutureOps2[T](self: Future[T]) {

    def	exists(p:	T	=>	Boolean):	Future[Boolean] = {
      val promise = Promise[Boolean]()
      self map (t => promise.success(p(t)))
      promise.future
    }

  }

  implicit class FutureOps3[T](self: Future[T]) {

    def	exists(p:	T	=>	Boolean):	Future[Boolean] = async {
      p(await(self))
    }

  }

}
