package org.learningconcurrency.exercises.ch3

import org.scalatest.FunSuite

import scala.util.{Failure, Try}

/**
  *
  *
  */
class ex1PiggybackContextTest extends FunSuite {

  test("testExecute") {

    @volatile var flag = false

    val executor = new ex1PiggybackContext

    val task = new Runnable {
      override def run(): Unit = flag = true
    }

    executor.execute(task)

    assert(flag)

    val taskError = new Runnable {
      override def run(): Unit = throw new RuntimeException("error")
    }

    Try(executor.execute(taskError)) match {
      case Failure(e) => assert(e.getCause.getMessage == "error")
      case _ =>
    }

  }

}
