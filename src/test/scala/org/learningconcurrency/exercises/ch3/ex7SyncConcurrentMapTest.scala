package org.learningconcurrency.exercises.ch3

import java.util.concurrent.CopyOnWriteArrayList

import org.learningconcurrency.exercises.ch2
import org.scalatest.FunSuite

import scala.collection.mutable.ListBuffer

/**
  *
  *
  */
class ex7SyncConcurrentMapTest extends FunSuite {

  test("test1") {
    val map = new ex7SyncConcurrentMap[Int, Int]()

    0.until(4)
      .map(i => ch2.thread {
        for (i <- 0 until 100)
          map.putIfAbsent(i, i)
      }).foreach(t => t.join())

    val iterator = map.iterator
    val result = new ListBuffer[Int]

    while (iterator.hasNext)
      result += iterator.next()._2

    assert(100 == result.size)

  }

  test("test2") {
    val map = new ex7SyncConcurrentMap[Int, Int]()
    val result = new CopyOnWriteArrayList[Int]()
    0.until(4)
      .map(i => ch2.thread {
        for (i <- 0 until 100)
          map.putIfAbsent(i, i)
      }).foreach(t => t.join())

    0.until(4)
      .map(i => ch2.thread {
        for (i <- 0 until 100)
          result.add(map.get(i).get)
      }).foreach(t => t.join())

    assert(400 == result.size)

  }

}
