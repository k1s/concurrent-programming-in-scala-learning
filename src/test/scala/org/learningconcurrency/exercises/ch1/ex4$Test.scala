package org.learningconcurrency.exercises.ch1

import org.learningconcurrency.exercises.ch1.ex4.{First, Second}
import org.scalatest.FunSuite

/**
  *
  *
  */
class ex4$Test extends FunSuite {

  test("ex4") {
    val l = First(1)
    val r = Second(2)

    def pairMatch(p: (ex4.Pair)): Int = p match {
      case First(first: Int) => first
      case Second(second: Int) => second
    }

    assert(1 == pairMatch(l))
    assert(2 == pairMatch(r))
  }

}
