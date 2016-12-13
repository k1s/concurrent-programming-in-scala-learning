package org.learningconcurrency.exercises.ch1

import org.scalatest.FunSuite

/**
  *
  *
  */
class ex5$Test extends FunSuite {

  test("testPermutations") {
    val s = "1234567890"
    assert(ex5.permutations(s).toSet.equals(s.permutations.toSet))
  }

}
