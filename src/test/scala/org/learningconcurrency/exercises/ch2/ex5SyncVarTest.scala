package org.learningconcurrency.exercises.ch2

import org.scalatest.FunSuite

import scala.util.Random

/**
  *
  *
  */
class ex5SyncVarTest extends FunSuite {

  test("consumerProducer") {
    val sync = new ex5SyncVar[Int]
    val producer = thread {
      for (i <- 0 until 15) {
        sync.putWait(i)
      }
    }
    val consumer = thread {
      var i = 0
      while (i < 15) {
        println(sync.getWait)
        i += 1
      }
    }
    producer.join()
    consumer.join()
  }

  test("testSyncDeadLock") {
    for (j <- 0 until 5) {
      var sum: Int = 0
      val lock = new AnyRef
      val s = new ex5SyncVar[Int]
      val end = Random.nextInt(100) + 50
      val x = Random.nextInt(10)
      val t11 = thread {
        for (i <- 0 until end) {
          Thread.sleep(22)
          s.putWait(x)
        }
      }
      val t12 = thread {
        for (i <- 0 until end) {
          s.putWait(x)
        }
      }
      val t21 = thread {
        for (i <- 0 until end) {
          Thread.sleep(22)
          lock.synchronized {
            sum += s.getWait
          }
        }
      }
      val t22 = thread {
        for (i <- 0 until end) {
          Thread.sleep(22)
          lock.synchronized {
            sum += s.getWait
          }
        }
      }

      t11.join(5000)
      t12.join(5000)
      t21.join(5000)
      t22.join(5000)
      assert(sum == 2 * end * x)
    }
  }

}
