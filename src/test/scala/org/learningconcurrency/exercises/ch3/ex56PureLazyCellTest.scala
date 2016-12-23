package org.learningconcurrency.exercises.ch3

import org.learningconcurrency.exercises.ch2
import org.scalatest.FunSuite

/**
  *
  *
  */
class ex56PureLazyCellTest extends FunSuite {

  test("testApply") {

    val f = "123"

    val lazyCell = new ex56PureLazyCell[String](f)

    assert(f == lazyCell.apply())
  }

}
