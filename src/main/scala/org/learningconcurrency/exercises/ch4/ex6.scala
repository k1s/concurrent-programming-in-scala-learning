package org.learningconcurrency.exercises.ch4

import scala.concurrent._
import scala.language.postfixOps
import scala.sys.process._


/**
  * Implement	the	 spawn 	method,	which	takes	a	command-line	string,	asynchronously
  * executes	it	as	a	child	process,	and	returns	a	future	with	the	exit	code	of	the	child
  * process:
  * def	spawn(command:	String):	Future[Int]
  * Make	sure	that	your	implementation	does	not	cause	thread	starvation.
  *
  */
object ex6 {

  import ExecutionContext.Implicits.global

  def	spawn(command:	String):	Future[Int] = Future {
    blocking {
      command !
    }
  }

}
