package org.learningconcurrency.exercises.ch4

import org.scalatest.FunSuite

import scala.collection.mutable.ListBuffer
import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  *
  *
  */
class IMapTest extends FunSuite {

  test("testApply") {
    val m = new IMap[Int, String]

    val res = m.apply(4)

    assert(!res.isCompleted)

    m.update(4, "123")

    assert(res.isCompleted)
  }

}
