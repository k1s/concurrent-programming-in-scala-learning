package org.learningconcurrency.exercises.ch1

import org.scalatest.FunSuite

class ex3$Test extends FunSuite {

  val wrongs = Seq(1,5,4,-3)
  val rights = Seq(1,5,4)

  test("testCheck2") {
    assert(ex3.check(rights)(_ > 0))
    assert(!ex3.check(wrongs)(_ > 0))
  }

}
