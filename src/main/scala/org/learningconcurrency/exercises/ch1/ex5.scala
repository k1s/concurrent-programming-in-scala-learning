package org.learningconcurrency.exercises.ch1

/**
  * Implement	a	 permutations 	function,	which,	given	a	string,	returns	a	sequence	of
  * strings	that	are	lexicographic	permutations	of	the	input	string:
  *
  */
object ex5 {

  def	permutations(x:	String):	Seq[String] = {
    if (x.isEmpty)
      Seq("")
    else
      for {
        i <- 0 until x.length
        newString <- permutations(x.take(i) + x.drop(i+1))
      } yield x(i) + newString
  }

}
