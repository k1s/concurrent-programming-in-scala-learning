package org.learningconcurrency.exercises.ch4

import org.scalatest.FunSuite
import org.learningconcurrency.ch2.thread

/**
  *
  *
  */
class IVarTest extends FunSuite {

  test("testApply") {
    val v = new IVar[String]
    (0 until 4).map(i => thread {
      try {
        v := "some"
      } catch {
        case e: Throwable => //
      }
    }).foreach(t => t.join())
    assert(v() == "some")
  }

}
