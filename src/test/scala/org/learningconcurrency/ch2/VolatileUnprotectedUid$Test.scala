package org.learningconcurrency.ch2

import org.scalatest.FunSuite

/**
  * Volatile can holds really unique ids
  *
  */
class VolatileUnprotectedUid$Test extends FunSuite {

  test("testGetUniqueId") {

    @volatile var xs: Seq[Long] = Seq()

    for (i <- 0 until 100) {
      val t = thread {
        xs = xs ++ VolatileUnprotectedUid.uniqueIds(42)
      }
      xs = xs ++ VolatileUnprotectedUid.uniqueIds(42)
      t.join()
      assert(xs.size == xs.distinct.size)
    }

  }

}
