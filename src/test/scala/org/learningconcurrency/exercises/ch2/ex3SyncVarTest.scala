package org.learningconcurrency.exercises.ch2

import org.scalatest.FunSuite

import scala.util.Random

/**
  * Running testVolatileError after testAllSync shows that syncing variables only with @volatile is not enough
  *
  * Java volatile keyword doesn't mean atomic, its common misconception that after declaring volatile ++ will be atomic,
  * to make the operation atomic you still need to ensure exclusive access using synchronized method or block in Java.
  *
  * In fact, multiple threads could even be writing to a shared volatile variable,
  * and still have the correct value stored in main memory, if the new value written to the variable does not depend on its previous value.
  *
  * As soon as a thread needs to first read the value of a volatile variable, and based on that value generate a new value
  * for the shared volatile variable, a volatile variable is no longer enough to guarantee correct visibility.
  * The short time gap in between the reading of the volatile variable and the writing of its new value, creates a race condition
  * where multiple threads might read the same value of the volatile variable, generate a new value for the variable,
  * and when writing the value back to main memory - overwrite each other's values.
  *
  * The situation where multiple threads are incrementing the same counter is exactly such a situation where a volatile variable is not enough.
  *
  */
class ex3SyncVarTest extends FunSuite {

  test("test1") {
    val s = new ex3SyncVar[Int]
    assertThrows[IllegalStateException](s.get())
    s.put(4)
    assertThrows[IllegalStateException](s.put(4))
    assert(4 == s.get())
    assertThrows[IllegalStateException](s.get())
  }

  test("testAllSync") {
    for (j <- 0 until 5) {
      var sum: Int = 0
      var errorGet: Int = 0
      val lock = new AnyRef
      val s = new ex3SyncVar[Int]
      val end = Random.nextInt(100) + 50
      val t11 = thread {
        for (i <- 0 until end) {
          try {
            Thread.sleep(22)
            s.put(1)
          } catch {
            case e: IllegalStateException => //for this task it's ok to just try once more
          }
        }
      }
      val t12 = thread {
        for (i <- 0 until end) {
          try {
            Thread.sleep(22)
            s.put(1)
          } catch {
            case e: IllegalStateException => //for this task it's ok to just try once more
          }
        }
      }
      val t21 = thread {
        for (i <- 0 until end) {
          try {
            Thread.sleep(22)
            lock.synchronized(sum += s.get())
          } catch {
            case e: IllegalStateException => lock.synchronized(errorGet += 1)
          }
        }
      }
      val t22 = thread {
        for (i <- 0 until end) {
          try {
            Thread.sleep(22)
            lock.synchronized(sum += s.get())
          } catch {
            case e: IllegalStateException => lock.synchronized(errorGet += 1)
          }
        }
      }
      t11.join(); t12.join(); t21.join(); t22.join()
      assert(sum + errorGet == end * 2)
    }
  }

  test("testVolatileError") {
    for (j <- 0 until 15) {
      @volatile var sum: Int = 0
      @volatile var errorGet: Int = 0
      val lock = new AnyRef
      val s = new ex3SyncVar[Int]
      val end = Random.nextInt(100) + 50

      val t11 = thread {
        for (i <- 0 until end) {
          try {
            Thread.sleep(22)
            s.put(1)
          } catch {
            case e: IllegalStateException => //for this task it's ok to just try once more
          }
        }
      }

      val t12 = thread {
        for (i <- 0 until end) {
          try {
            Thread.sleep(22)
            s.put(1)
          } catch {
            case e: IllegalStateException => //for this task it's ok to just try once more
          }
        }
      }
      val t21 = thread {
        for (i <- 0 until end) {
          try {
            Thread.sleep(22)
            sum = sum + s.get()
          } catch {
            case e: IllegalStateException => errorGet = errorGet + 1
          }
        }
      }
      val t22 = thread {
        for (i <- 0 until end) {
          try {
            Thread.sleep(22)
            sum = sum + s.get()
          } catch {
            case e: IllegalStateException => errorGet = errorGet + 1
          }
        }
      }
      t11.join(); t12.join(); t21.join(); t22.join()
      assert(sum + errorGet == end * 2)
    }
  }

}
