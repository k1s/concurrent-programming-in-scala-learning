package org.learningconcurrency.exercises.ch2

import org.scalatest.FunSuite

/**
  *
  *
  */
class ex2$Test extends FunSuite {

  test("testPeriodically") {
    var r = 0
    def inc() =  {
      r += 1
    }
    ex2.periodically(2000)(inc())
    Thread.sleep(11000)
    assert(r > 5)
  }

}
