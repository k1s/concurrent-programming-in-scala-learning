package org.learningconcurrency.exercises.ch8

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.WordSpecLike

/**
  */
class ex3SessionActorTest extends TestKit(ActorSystem("testSystem"))
  with WordSpecLike
  with ImplicitSender
  with StopSystemAfterAll {

  import SessionActor._

  "Session Actor" must {

    "forward all messages to the test actor" in {
      val sessionActor = system.actorOf(Props(new ex3SessionActor("1234", testActor)))
      sessionActor ! StartSession("")
      sessionActor ! "1234"
      expectNoMsg()
      sessionActor ! StartSession("1234")
      sessionActor ! "1234"
      expectMsg("1234")
    }

    "stops forwarding messages to the test actor" in {
      val sessionActor = system.actorOf(Props(new ex3SessionActor("1234", testActor)))
      sessionActor ! StartSession("")
      sessionActor ! "1234"
      expectNoMsg()
      sessionActor ! StartSession("1234")
      sessionActor ! "1234"
      expectMsg("1234")
      sessionActor ! EndSession()
      expectNoMsg()
    }

  }

}
