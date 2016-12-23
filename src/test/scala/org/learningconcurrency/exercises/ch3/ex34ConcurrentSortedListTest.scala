package org.learningconcurrency.exercises.ch3

import org.learningconcurrency.exercises.ch2
import org.scalatest.FunSuite

import scala.collection.mutable.ListBuffer

/**
  *
  *
  */
class ex34ConcurrentSortedListTest extends FunSuite {

  test("testIterator") {

    val list = new ex34ConcurrentSortedList[Int]()

    0.until(4)
      .map(i => ch2.thread {
        for (i <- 0 until 100)
          list.add(i)
      }).foreach(t => t.join())

    val iterator = list.iterator
    val result = new ListBuffer[Int]

    while (iterator.hasNext)
      result += iterator.next()

    assert(400 == result.size)

    assert(result.zip(result).forall(t => t._1 <= t._2))

  }

}
