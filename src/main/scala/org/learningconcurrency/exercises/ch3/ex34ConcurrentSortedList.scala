package org.learningconcurrency.exercises.ch3

import java.util.concurrent.atomic.AtomicReference

import scala.annotation.tailrec

/**
  * Implement	a	 ConcurrentSortedList 	class,	which	implements	a	concurrent	sorted	list
  * abstraction:
  * class	ConcurrentSortedList[T](implicit	val	ord:	Ordering[T])	{
  * def	addRef(x:	T):	Unit	=	???
  * def	iterator:	Iterator[T]	=	???
  * }
  * Under	the	hood,	the	 ConcurrentSortedList 	class	should	use	a	linked	list	of	atomic
  * references.	Ensure	that	your	implementation	is	lock-free	and	avoids	ABA	problems.
  * The	 Iterator 	object	returned	by	the	 iterator 	method	must	correctly	traverse	the
  * elements	of	the	list	in	the	ascending	order	under	the	assumption	that	there	are	no
  * concurrent	invocations	of	the	 addRef 	method.
  *
  * If	required,	modify	the	 ConcurrentSortedList 	class	from	the	previous	example	so
  * that	calling	the	 addRef 	method	has	the	running	time	linear	to	the	length	of	the	list	and
  * creates	a	constant	number	of	new	objects	when	there	are	no	retries	due	to	concurrent
  * addRef 	invocations
  *
  */
class ex34ConcurrentSortedList[T](implicit val ord: Ordering[T]) {

  case class Cons(head: T, tail: AtomicReference[Option[Cons]])

  val root = new AtomicReference[Option[Cons]](None)

  def add(x: T): Unit = {
    @tailrec
    def addRef(r: AtomicReference[Option[Cons]], x: T): Unit  = r.get match {
      case None =>
        if (!r.compareAndSet(None, Some(Cons(x, new AtomicReference[Option[Cons]](None)))))
          addRef(r, x)
      case Some(Cons(head, tail)) =>
        if (ord.compare(x, head) <= 0) {
          if (!r.compareAndSet(r.get(), Some(Cons(x, new AtomicReference[Option[Cons]](r.get())))))
            addRef(r, x)
        } else {
          addRef(tail, x)
        }

    }
    addRef(root, x)
  }

  def iterator: Iterator[T] = new Iterator[T] {

    private var nextRef = root

    override def hasNext: Boolean = nextRef.get().nonEmpty

    override def next(): T = nextRef.get() match {
      case None => throw new NoSuchElementException("Next on empty iterator!")
      case Some(Cons(head, tail)) =>
        nextRef = tail
        head
    }

  }

}
