package org.learningconcurrency.exercises.ch8

import akka.actor.Actor
import akka.actor.Actor.Receive

/**
  * Implement	the	 FailureDetector 	actor,	which	sends	 Identify 	messages	to	the
  * specified	actors	every	 interval 	seconds.	If	an	actor	does	not	reply	with	any
  * ActorIdentity 	messages	within	 threshold 	seconds,	the	 FailureDetector 	actor
  * sends	a	 Failed 	message	to	its	parent	actor,	which	contains	the	actor	reference	of	the
  * failed	actor.
  *
  */
class ex5FailureDetector extends Actor {

  def receive: Receive = ???

}
