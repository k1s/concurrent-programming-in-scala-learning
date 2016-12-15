package org.learningconcurrency.exercises.ch2

import org.scalatest.FunSuite

import scala.util.Random

/**
  *
  *
  */
class ex4SyncVarTest extends FunSuite {

  test("consumerProducer") {
    val sync = new ex4SyncVar[Int]
    val producer = thread {
      var i = 0
      while (i < 15) {
        if (sync.isEmpty) {
          sync.put(i)
          i += 1
        }
      }
    }
    val consumer = thread {
      var i = 0
      while (i < 15) {
        if (sync.nonEmpty) {
          println(sync.get())
          i += 1
        }
      }
    }
    producer.join(); consumer.join()
  }

  test("testAllSync") {
    for (j <- 0 until 5) {
      var sum: Int = 0
      var error: Int = 0
      val lock = new AnyRef
      val s = new ex4SyncVar[Int]
      val end = Random.nextInt(100) + 50
      val x = Random.nextInt(10)
      val t11 = thread {
        for (i <- 0 until end) {
          try {
            Thread.sleep(22)
            lock.synchronized {
              if (s.isEmpty)
                s.put(x)
            }
          } catch {
            case e: IllegalStateException => fail()
          }
        }
      }
      val t12 = thread {
        for (i <- 0 until end) {
          try {
            lock.synchronized {
              if (s.isEmpty)
                s.put(x)
            }
          } catch {
            case e: IllegalStateException => fail()
          }
        }
      }
      val t21 = thread {
        for (i <- 0 until end) {
          try {
            Thread.sleep(22)
            lock.synchronized {
              if (s.nonEmpty)
                sum += s.get()
              else
                error += 1
            }
          } catch {
            case e: IllegalStateException => fail()
          }
        }
      }
      val t22 = thread {
        for (i <- 0 until end) {
          try {
            Thread.sleep(22)
            lock.synchronized {
              if (s.nonEmpty)
                sum += s.get()
              else
                error += 1
            }
          } catch {
            case e: IllegalStateException => fail()
          }
        }
      }
      t11.join(); t12.join(); t21.join(); t22.join()
      assert(sum + error * x == 2 * end * x)
    }
  }

  test("testVolatileError") {
    for (j <- 0 until 5) {
      @volatile var sum: Int = 0
      @volatile var error: Int = 0
      val s = new ex4SyncVar[Int]
      val end = Random.nextInt(100) + 50
      val x = Random.nextInt(10)
      val t11 = thread {
        for (i <- 0 until end) {
          try {
            Thread.sleep(22)
            if (s.isEmpty)
              s.put(x)
          } catch {
            case e: IllegalStateException => fail()
          }
        }
      }
      val t12 = thread {
        for (i <- 0 until end) {
          try {
            if (s.isEmpty)
              s.put(x)
          } catch {
            case e: IllegalStateException => fail()
          }
        }
      }
      val t21 = thread {
        for (i <- 0 until end) {
          try {
            Thread.sleep(22)
            if (s.nonEmpty)
              sum += s.get()
            else
              error += 1
          } catch {
            case e: IllegalStateException => fail()
          }
        }
      }
      val t22 = thread {
        for (i <- 0 until end) {
          try {
            Thread.sleep(22)
            if (s.nonEmpty)
              sum += s.get()
            else
              error += 1
          } catch {
            case e: IllegalStateException => fail()
          }
        }
      }
      t11.join(); t12.join(); t21.join(); t22.join()
      assert(sum + error * x == 2 * end * x)
    }
  }

}
