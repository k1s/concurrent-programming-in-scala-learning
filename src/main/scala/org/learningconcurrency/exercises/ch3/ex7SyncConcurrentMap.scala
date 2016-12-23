package org.learningconcurrency.exercises.ch3

import scala.collection.concurrent._
import scala.collection.mutable

/**
  * Implement	a	 SyncConcurrentMap 	class	that	extends	the	 Map 	interface	from	the
  * scala.collection.concurrent package.	Use	the	 synchronized 	statement	to	protect
  * the	state	of	the	concurrent	map.
  *
  */
class ex7SyncConcurrentMap[K, V] extends Map[K, V] {

  private val map = mutable.Map[K, V]()

  override def putIfAbsent(k: K, v: V): Option[V] = map.synchronized {
    require(k != null)
    require(v != null)
    map.get(k) match  {
      case None => map.put(k, v)
      case Some(oldValue) => Some(oldValue)
    }
  }

  override def remove(k: K, v: V): Boolean = map.synchronized {
    map.get(k) match {
      case Some(oldValue) => if (oldValue.equals(k)) map.remove(k); true
      case _ => false
    }
  }

  override def replace(k: K, oldvalue: V, newvalue: V): Boolean = map.synchronized {
    require(newvalue != null)
    map.get(k) match {
      case Some(value) => if (value.equals(oldvalue)) map.put(k, newvalue); true
      case _ => false
    }
  }

  override def replace(k: K, v: V): Option[V] = map.synchronized {
    map.get(k) match {
      case Some(oldValue) => map.put(k, v); Some(oldValue)
      case None => None
    }
  }

  override def +=(kv: (K, V)): ex7SyncConcurrentMap.this.type = map.synchronized {
    require(kv._1 != null)
    map.put(kv._1, kv._2)
    this
  }

  override def -=(key: K): ex7SyncConcurrentMap.this.type = map.synchronized {
    map.remove(key)
    this
  }

  override def get(key: K): Option[V] = map.synchronized(map.get(key))

  override def iterator: Iterator[(K, V)] = map.synchronized(map.iterator)

}
