package org.learningconcurrency.exercises.ch8

import akka.actor.{Actor, ActorRef}

/**
  * Recall	the	bank	account	example	from	Chapter	2,	Concurrency	on	the	JVM	and	the
  * Java	Memory	Model.	Implement	different	bank	accounts	as	separate	actors,
  * represented	with	the	 AccountActor 	class.	When	an	 AccountActor 	class	receives	a
  * Send 	message,	it	must	transfer	the	specified	amount	of	money	to	the	target	actor.
  * What	will	happen	if	either	of	the	actors	receives	a	 Kill 	message	at	any	point	during
  * the	money	transaction?
  *
  */
class ex2AccountActor() extends Actor {

  import Account._

  var money: Double = _

  def receive = {
    case Put(quantity) =>
      money += quantity
    case Send(quantity, actor) =>
      money -= quantity
      actor ! Put(quantity)
  }

}

object Account {
  case class Put(quantity: Double)
  case class Send(quantity: Double, actor: ActorRef)
}
