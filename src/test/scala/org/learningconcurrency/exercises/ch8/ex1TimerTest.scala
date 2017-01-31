package org.learningconcurrency.exercises.ch8

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.WordSpecLike

import scala.concurrent.duration.FiniteDuration

/**
  */
class ex1TimerTest extends TestKit(ActorSystem("testSystem"))
  with WordSpecLike
  with ImplicitSender
  with StopSystemAfterAll {

  "Timer Actor" must {

    "responds with Timeout" in {
      import Timer._
      val timer = system.actorOf(Props[ex1Timer])
      timer ! Register(4)
      expectMsg(FiniteDuration(10, "millis"), Timeout(4))
    }

  }

}