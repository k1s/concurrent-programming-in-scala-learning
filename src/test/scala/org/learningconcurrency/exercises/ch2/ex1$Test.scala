package org.learningconcurrency.exercises.ch2

import org.scalatest.FunSuite

/**
  *
  *
  */
class ex1$Test extends FunSuite {

  test("testParallel") {

    val expected = (3, 4)
    val expectedWithNull = (3, 0)

    val f1 = (x: Int) => {
      Thread.sleep(3000)
      x
    }

    val f2 = (x: Int) => {
      Thread.sleep(6000)
      x
    }

    assert(expected == ex1.parallel1(f1(3), f1(4)))
    assert(expected == ex1.parallel1(f1(3), f2(4)))
    assert(expectedWithNull == ex1.parallel1(f1(3), f1(null.asInstanceOf[Int])))

    assert(expected == ex1.parallel2(f1(3), f1(4)))
    assert(expectedWithNull == ex1.parallel2(f1(3), f1(null.asInstanceOf[Int])))

  }

}
