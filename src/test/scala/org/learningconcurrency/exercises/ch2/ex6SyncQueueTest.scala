package org.learningconcurrency.exercises.ch2

import org.scalatest.FunSuite

/**
  *
  *
  */
class ex6SyncQueueTest extends FunSuite {

  test("consumerProducer") {
    val sync = new ex6SyncQueue[Int](20)
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

}
