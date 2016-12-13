package org.learningconcurrency.exercises.ch1

import org.scalatest.FunSuite

class ex2$Test extends FunSuite {

  test("testFuse") {
    val a = 3
    val b = 4
    val res = (a,b)
    assert(res == ex2.fuse(Option(a), Option(b)).get)
  }

}
