package org.learningconcurrency.exercises.ch2

import org.scalatest.FunSuite

/**
  *
  *
  */
class ex10PriorityTaskPoolTest extends FunSuite {

  test("testAsynchronous1") {
    var str = new StringBuffer
    val pool = new ex10PriorityTaskPool(2, 10)

    pool.shutdown()
    pool.asynchronous(1100)(str.append("4"))
    pool.asynchronous(100500)(str.append("2"))
    pool.asynchronous(2)(str.append("3"))
    pool.asynchronous(4)(str.append("1"))

    Thread.sleep(500)

    assert(str.toString.sorted == "24")

  }

  test("testAsynchronous2") {
    var str = new StringBuffer
    val pool = new ex10PriorityTaskPool(2, 10)

    pool.asynchronous(1100)(str.append("4"))
    pool.asynchronous(100500)(str.append("2"))
    pool.asynchronous(2)(str.append("3"))
    pool.asynchronous(4)(str.append("1"))

    Thread.sleep(500)

    assert(str.toString.sorted == "1234")

  }

}
