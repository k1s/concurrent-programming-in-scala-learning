package org.learningconcurrency.exercises.ch2

import org.learningconcurrency.ch2.SynchronizedProtectedUid

/**
  * The	 send 	method	in	the	Deadlocks	section	was	used	to	transfer	money	between	the
  * two	accounts.	The	 sendAll 	method	takes	a	set	 accounts 	of	bank	accounts	and	a
  * target 	bank	account,	and	transfers	all	the	money	from	every	account	in	 accounts 	to
  * the	 target 	bank	account.	The	 sendAll 	method	has	the	following	signature:
  * def	sendAll(accounts:	Set[Account],	target:	Account):	Unit
  * Implement	the	 sendAll 	method	and	ensure	that	a	deadlock	cannot	occur.
  */
object ex7 {

  class Account(val name: String, var money: Int) {
    val uid: Long = SynchronizedProtectedUid.getUniqueId()

    def getUid: Long = uid
  }

  def send(from: Account, to: Account, n: Int): Unit = {
    def transfer(): Unit = {
      from.money -= n
      to.money += n
    }

    if (from.getUid < to.getUid) {
      from.synchronized {
        to.synchronized {
          transfer()
        }
      }
    } else {
      to.synchronized {
        from.synchronized {
          transfer()
        }
      }
    }
  }

  def sendAll(accounts: Set[Account], target: Account): Unit = accounts.foreach(acc => send(acc, target, acc.money))

}
