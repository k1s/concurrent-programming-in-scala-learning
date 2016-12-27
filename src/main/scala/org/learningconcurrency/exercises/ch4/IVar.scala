package org.learningconcurrency.exercises.ch4

import scala.concurrent.Promise
import scala.util.Try

/**
  * Implement	an	abstraction	called	a	single-assignment	variable,	represented	by	the
  * IVar 	class:
  * class	IVar[T]	{
  * 		def	apply():	T	=	???
  * 		def	:=(x:	T):	Unit	=	???
  * }
  * When	created,	the	 IVar 	class	does	not	contain	a	value,	and	calling	 apply 	results	in	an
  * exception.	After	a	value	is	assigned	using	the	 := 	method,	subsequent	calls	to	 :=
  * throw	an	exception,	and	the	 apply 	method	returns	the	previously	assigned	value.	Use
  * only	futures	and	pro
  *
  */
class IVar[T] {

  val p: Promise[T] = Promise[T]()

  def	apply():	T	=	if (p.isCompleted) p.future.value.get.get else throw new Exception("Not contain a value!")

  def	:=(x:	T):	Unit	= if (!p.tryComplete(Try(x))) throw new Exception("Already contain a value!")

}
