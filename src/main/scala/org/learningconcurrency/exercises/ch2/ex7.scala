package org.learningconcurrency.exercises.ch2

/**
  * The	 send 	method	in	the	Deadlocks	section	was	used	to	transfer	money	between	the
  * two	accounts.	The	 sendAll 	method	takes	a	set	 accounts 	of	bank	accounts	and	a
  * target 	bank	account,	and	transfers	all	the	money	from	every	account	in	 accounts 	to
  * the	 target 	bank	account.	The	 sendAll 	method	has	the	following	signature:
  * def	sendAll(accounts:	Set[Account],	target:	Account):	Unit
  * Implement	the	 sendAll 	method	and	ensure	that	a	deadlock	cannot	occur.
  */
object ex7 {

  class Account(val name: String, var money: Int)

  def send(from: Account, to: Account, n: Int): Unit =
    from.synchronized {
      to.synchronized {
        from.money -= n
        to.money += n
      }
    }

  def sendAll(accounts: Set[Account], target: Account): Unit = accounts.foreach(acc => send(acc, target, acc.money))

}
