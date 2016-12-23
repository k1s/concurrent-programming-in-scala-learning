package org.learningconcurrency.exercises.ch3

import java.util.regex.Pattern

import org.scalatest.FunSuite

/**
  *
  *
  */
class ex8$Test extends FunSuite {

  test("testSpawn") {
    val s1 = ex8.spawn({
      1 + 1
    })
    assert(s1 == 2)

    try {
      ex8.spawn({
        "test".toInt
      })
    } catch {
      case e: NumberFormatException =>
      case _: Throwable => assert(false)
    }

  }

}
