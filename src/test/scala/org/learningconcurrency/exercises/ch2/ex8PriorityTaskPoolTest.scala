package org.learningconcurrency.exercises.ch2

import org.scalatest.FunSuite

/**
  *
  *
  */
class ex8PriorityTaskPoolTest extends FunSuite {

  test("testAsynchronous") {
    var str = new StringBuffer
    val pool = new ex8PriorityTaskPool

    pool.asynchronous(1100)(str.append("4"))
    pool.asynchronous(100500)(str.append("2"))
    pool.asynchronous(2)(str.append("3"))
    pool.asynchronous(4)(str.append("1"))

    Thread.sleep(500)

    assert(str.toString.sorted == "1234")
  }

}
