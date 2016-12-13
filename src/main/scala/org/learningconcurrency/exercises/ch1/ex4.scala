package org.learningconcurrency.exercises.ch1

/**
  * Modify	the	 Pair 	class	from	this	chapter	so	that	it	can	be	used	in	a	pattern	match.
  * class	Pair[P,	Q](val	first:	P,	val	second:	Q)
  */
object ex4 {

  abstract class	Pair
  case class First[P](first:	P) extends Pair
  case class Second[Q](second:	Q) extends Pair

}
