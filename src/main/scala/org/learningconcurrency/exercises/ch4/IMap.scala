package org.learningconcurrency.exercises.ch4

import scala.collection.concurrent.TrieMap
import scala.concurrent.{Future, Promise}

/**
  * Implement	the	 IMap 	class,	which	represents	a	single-assignment	map:
  * class	IMap[K,	V]	{
  * def	update(k:	K,	v:	V):	Unit		def	apply(k:	K):	Future[V]
  * }
  * Pairs	of	keys	and	values	can	be	added	to	the	 IMap 	object,	but	they	can	never	be
  * removed	or	modified.	A	specific	key	can	be	assigned	only	once,	and	subsequent	calls
  * to	 update 	with	that	key	results	in	an	exception.	Calling	 apply 	with	a	specific	key
  * returns	a	future,	which	is	completed	after	that	key	is	inserted	into	the	map.	In	addition
  * to	futures	and	promises,	you	may	use	the	 scala.collection.concurrent.Map 	class
  *
  */
class IMap[K, V] {

  var m = TrieMap[K, Promise[V]]()

  def update(k: K, v: V): Unit = {
    m.putIfAbsent(k, Promise().success(v)) match {
      case Some(p) => p.success(v)
      case None =>
    }
  }

  def apply(k: K): Future[V] = {
    def createPromise: Future[V] = {
      val p = Promise()
      m.putIfAbsent(k, Promise()) match {
        case Some(old) => old.future
        case None => p.future
      }
    }
    m.get(k) match {
      case Some(p) => p.future
      case None => createPromise
    }
  }

}
