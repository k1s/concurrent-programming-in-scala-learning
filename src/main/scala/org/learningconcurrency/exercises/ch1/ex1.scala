package org.learningconcurrency.exercises.ch1

/**
 * Implement	a	 compose	 method	with	the	following	signature:
 * def	compose[A,	B,	C](g:	B	=>	C,	f:	A	=>	B):	A	=>	C	=	???
 * This	method	must	return	a	function	 h ,	which	is	the	composition	of	the	functions	 f 	and
 * g .
 */
object ex1 {

  def	compose1[A,	B,	C](g:	B	=>	C,	f:	A	=>	B):	A	=>	C	=	f andThen g

  def	compose2[A,	B,	C](g:	B	=>	C,	f:	A	=>	B):	A	=>	C	=	g compose f

  def	compose3[A,	B,	C](g:	B	=>	C,	f:	A	=>	B):	A	=>	C	=	x => g(f(x))

}


