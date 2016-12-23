package org.learningconcurrency.exercises.ch3

import org.learningconcurrency.exercises.ch2._
import org.scalatest.FunSuite

import scala.collection.mutable.{ArrayBuffer, ListBuffer}

/**
  *
  *
  */
class ex2TreiberStackTest extends FunSuite {

  test("testPush") {

    val stack = new ex2TreiberStack[Int]()

    val t1 = thread {
      for (i <- 0 until 100)
        stack.push(i)
    }

    val t2 = thread {
      for (i <- 100 until 200)
        stack.push(i)
    }

    t1.join()
    t2.join()

    val result = new ArrayBuffer[Int]()
    val expected = 0.until(200).toList

    while (stack.nonEmpty)
      result.+=(stack.pop())

    assert(expected == result.toList.sorted)

  }

  test("testPush1") {

    val stack = new ex2TreiberStack[Int]()

    val t1 = thread {
      for (i <- 0 until 100)
        stack.push(i)
    }

    val t2 = thread {
      for (i <- 0 until 100)
        stack.push(i)
    }

    t1.join()
    t2.join()

    val result = new ArrayBuffer[Int]()
    val expected = 0.until(100).toSet

    while (stack.nonEmpty)
      result.+=(stack.pop())

    assert(expected == result.toSet)

  }

}
