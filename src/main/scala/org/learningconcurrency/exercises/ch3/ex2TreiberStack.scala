package org.learningconcurrency.exercises.ch3

import java.util.concurrent.atomic.AtomicReference

import scala.annotation.tailrec

/**
  * Implement	a	 TreiberStack 	class,	which	implements	a	concurrent	stack	abstraction:
  * class	TreiberStack[T]	{
  * 		def	push(x:	T):	Unit	=	???
  * 		def	pop():	T	=	???
  * }
  * Use	an	atomic	reference	variable	that	points	to	a	linked	list	of	nodes	that	were
  * previously	pushed	to	the	stack.	Make	sure	that	your	implementation	is	lock-free	and
  * not	susceptible	to	the	ABA	problem.
  *
  */
class ex2TreiberStack[T] {

  private val list = new AtomicReference[List[T]](List())

  def nonEmpty: Boolean = list.get().nonEmpty

  @tailrec
  final def	push(x:	T):	Unit	=	{
    val oldList = list.get()
    val newList = x :: oldList
    if (!list.compareAndSet(oldList, newList))
      push(x)
  }

  @tailrec
  final def	pop():	T	=	{
    val oldList = list.get()
    val newList = oldList.tail
    if (list.compareAndSet(oldList, newList))
      oldList.head
    else
      pop()
  }


}
