package org.learningconcurrency.exercises.ch1

/**
  * Implement	a	 check 	method,	which	takes	a	set	of	values	of	the	type	 T 	and	a	function
  * of	the	type	 T	=>	Boolean :
  * def	check[T](xs:	Seq[T])(pred:	T	=>	Boolean):	Boolean	=	???
  * The	method	must	return	 true 	if	and	only	if	the	 pred 	function	returns	 true 	for	all	the
  * values	in	 xs 	without	throwing	an	exception.	Use	the	 check 	method	as	follows:
  * check(0	until	10)(40	/	_	>	0)
  */
object ex3 {

  def	check[T](xs:	Seq[T])(pred:	T	=>	Boolean):	Boolean = xs match {
    case Nil => true
    case y::ys => pred(y) && check(ys)(pred)
  }

}
