package org.learningconcurrency.exercises.ch4

import org.scalatest.FunSuite

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration
import scala.util.{Success, Try}

/**
  *
  *
  */
class ex6$Test extends FunSuite {

  import scala.concurrent.ExecutionContext.Implicits.global

  test("testSpawn") {
    val futures = (1 until 17).map(i => ex6.spawn(s"echo $i"))
    val result = Future.sequence(futures).map(list => list.sum)
    Await.result(result, Duration.Inf)
    assert(0 == result.value.get.get)
  }

}
