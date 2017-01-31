package org.learningconcurrency.exercises.ch8

import akka.actor.ActorSystem
import akka.testkit.{TestActorRef, TestKit}
import org.scalatest.WordSpecLike

/**
  *
  *
  */
class ex2AccountActorTest extends TestKit(ActorSystem("testSystem"))
  with WordSpecLike
  with StopSystemAfterAll {

  import Account._

  "Account Actor" must {

    "receive money that send to him" in {
      val account = TestActorRef[ex2AccountActor]
      account ! Put(100)
      assert(100 == account.underlyingActor.money)
    }

    "transfer money to another account" in {
      val account1 = TestActorRef[ex2AccountActor]
      val account2 = TestActorRef[ex2AccountActor]
      account1 ! Put(100)
      account1 ! Send(42, account2)
      assert(58 == account1.underlyingActor.money)
      assert(42 == account2.underlyingActor.money)
    }

  }

}
