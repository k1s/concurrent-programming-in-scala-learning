package org.learningconcurrency.exercises.ch1

/**
  * Implement	a	 fuse	 method	with	the	following	signature:
  * def	fuse[A,	B](a:	Option[A],	b:	Option[B]):	Option[(A,	B)]	=	???
  * The	resulting	 Option 	object	should	contain	a	tuple	of	values	from	the	 Option 	objects
  * a 	and	 b ,	given	that	both	 a 	and	 b 	are	non-empty.	Use	for-comprehensions.
  */
object ex2 {

  def fuse[A, B](a: Option[A], b: Option[B]): Option[(A, B)] =
    for {
      aVal <- a
      bVal <- b
    }
      yield (aVal, bVal)

}
