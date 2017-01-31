package org.learningconcurrency.exercises.ch8

import akka.actor.Actor

/**
  * Implement	the	timer	actor	with	the	 TimerActor 	class.	After	receiving	a	 Register
  * message	containing	the	 t 	timeout	in	milliseconds,	the	timer	actor	sends	a	 Timeout
  * message	back	after	 t 	milliseconds.	The	timer	must	accept	multiple	 Register
  * messages.
  *
  */
class ex1Timer extends Actor {

  def receive = {
    case Timer.Register(timeout) =>
      Thread.sleep(timeout)
      sender() ! Timer.Timeout(timeout)
  }

}

object Timer {
  case class Timeout(timeout: Long)
  case class Register(timeout: Long)
}
