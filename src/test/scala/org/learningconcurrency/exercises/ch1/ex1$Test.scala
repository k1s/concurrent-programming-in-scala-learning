package org.learningconcurrency.exercises.ch1

import org.scalatest.FunSuite

class ex1$Test extends FunSuite {

  test("compose") {
    val f1 = (x: Int) => x + x
    val f2 = (x: Int) => x * x
    val sumAndSquare1  = ex1.compose1(f1, f2)
    val sumAndSquare2  = ex1.compose2(f1, f2)
    val sumAndSquare3  = ex1.compose3(f1, f2)
    assert(32 == sumAndSquare1(4))
    assert(32 == sumAndSquare2(4))
    assert(32 == sumAndSquare3(4))
  }

}
