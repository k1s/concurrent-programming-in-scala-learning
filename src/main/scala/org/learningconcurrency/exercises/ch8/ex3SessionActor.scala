package org.learningconcurrency.exercises.ch8

import akka.actor.{Actor, ActorRef}
import org.learningconcurrency.exercises.ch8.SessionActor.{EndSession, StartSession}

/**
  * Implement	the	 SessionActor 	class,	for	actors	that	control	access	to	other	actors:
  * class	SessionActor(password:	String,	r:	ActorRef)	extends	Actor	{
  * 		def	receive	=	???
  * }
  * After	the	 SessionActor 	instance	receives	the	 StartSession 	message	with	the	correct
  * password,	it	forwards	all	the	messages	to	the	actor	reference	 r ,	until	it	receives	the
  * EndSession 	message.	Use	behaviors	to	model	this	actor.
  *
  */
class ex3SessionActor(password: String, ref: ActorRef) extends Actor {

  def receive = {
    case StartSession(pass) =>
      if (pass == this.password)
        context.become(forward)
  }

  def forward: PartialFunction[Any, Unit] = {
    case EndSession() => context.become(receive)
    case x: Any => ref forward x
  }

}

object SessionActor {
  case class StartSession(password: String)
  case class EndSession()
}
