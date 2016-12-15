package org.learningconcurrency.exercises.ch2

import org.learningconcurrency.exercises.ch2.ex7.Account
import org.learningconcurrency.log
import org.scalatest.FunSuite

/**
  *
  *
  */
class ex7$Test extends FunSuite {

  test("testSendAll") {
    val accounts1 = (1 to 1000000).map((i) => new Account(s"Account: $i",i*10)).permutations.next().toSet
    val accounts2 = (1 to 1000000).map((i) => new Account(s"Account: $i",i*10)).permutations.next().toSet
    val accounts3 = (1 to 1000000).map((i) => new Account(s"Account: $i",i*10)).permutations.next().toSet
    val target = new Account("Target account", 0)

    val sum1 = accounts1.map(_.money).sum
    val sum2 = accounts2.map(_.money).sum
    val sum3 = accounts3.map(_.money).sum

    val t1 = thread(ex7.sendAll(accounts1,target))
    val t2 = thread(ex7.sendAll(accounts2,target))
    val t3 = thread(ex7.sendAll(accounts3,target))

    t1.join()
    t2.join()
    t3.join()

    assert(target.money == sum1 + sum2 + sum3)

  }

}
