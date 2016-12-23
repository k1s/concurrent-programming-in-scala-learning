package org.learningconcurrency.exercises.ch3

import java.util.concurrent.atomic.AtomicReference

/**
  * Implement	a	 LazyCell 	class	with	the	following	interface:
  * class	LazyCell[T](initialization:	=>T)	{
  * 		def	apply():	T	=	???
  * }
  * Creating	a	 LazyCell 	object	and	calling	the	 apply 	method	must	have	the	same
  * semantics	as	declaring	a	lazy	value	and	reading	it,	respectively.
  * You	are	not	allowed	to	use	lazy	values	in	your	implementation.
  *
  * Implement	a	 PureLazyCell 	class	with	the	same	interface	and	semantics	as	the
  * LazyCell 	class	from	the	previous	exercise.	The	 PureLazyCell 	class	assumes	that	the
  * initialization	parameter	does	not	cause	side	effects,	so	it	can	be	evaluated	more	than
  * once.
  * The	 apply 	method	must	be	lock-free	and	should	call	the	initialization	as	little	as
  * possible
  *
  */
class ex56PureLazyCell[T](initialization:	=>T) {

  val v = new AtomicReference[Option[T]](None)

  def	apply():	T	=	v.get() match {
    case None => if (!v.compareAndSet(None, Some(initialization))) apply() else v.get().get
    case Some(x) => x
  }

}
