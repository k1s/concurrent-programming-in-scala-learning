package org.learningconcurrency.exercises.ch4

import org.scalatest.FunSuite

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

/**
  *
  *
  */
class FutureOpsTest extends FunSuite {

  test("testExists1") {
    import ex345.FutureOps1
    import scala.concurrent.ExecutionContext.Implicits.global

    val f1 = Future(100)
    val f2 = Future(0)
    val f3 = Future(-100)

    assert(Await.result(f1.exists((i: Int) => i > 0), Duration.Inf))
    assert(!Await.result(f2.exists((i: Int) => i > 0), Duration.Inf))
    assert(!Await.result(f3.exists((i: Int) => i > 0), Duration.Inf))
  }

  test("testExists2") {
    import ex345.FutureOps2
    import scala.concurrent.ExecutionContext.Implicits.global

    val f1 = Future(100)
    val f2 = Future(0)
    val f3 = Future(-100)

    assert(Await.result(f1.exists((i: Int) => i > 0), Duration.Inf))
    assert(!Await.result(f2.exists((i: Int) => i > 0), Duration.Inf))
    assert(!Await.result(f3.exists((i: Int) => i > 0), Duration.Inf))
  }

  test("testExists3") {
    import ex345.FutureOps3
    import scala.concurrent.ExecutionContext.Implicits.global

    val f1 = Future(100)
    val f2 = Future(0)
    val f3 = Future(-100)

    assert(Await.result(f1.exists((i: Int) => i > 0), Duration.Inf))
    assert(!Await.result(f2.exists((i: Int) => i > 0), Duration.Inf))
    assert(!Await.result(f3.exists((i: Int) => i > 0), Duration.Inf))
  }

}
