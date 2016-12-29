package org.learningconcurrency.exercises.ch4

import org.scalatest.FunSuite

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Promise}

/**
  *
  *
  */
class PromiseOpsTest extends FunSuite {

  test("testCompose") {

    import ex8.PromiseOps

    val p1 = Promise[String]()

    val p2: Promise[Int] = p1.compose(x => "123")

    p2.success(1)

    val res = Await.result(p1.future, Duration.Inf)

    assert("123" == res)

  }

}
