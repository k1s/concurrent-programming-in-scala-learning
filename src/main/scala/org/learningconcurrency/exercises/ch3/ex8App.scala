package org.learningconcurrency.exercises.ch3

import java.io._
import java.nio.file.{Files, Paths}
import java.security.Permission

import scala.io.Source

/**
  *
  *
  */
object ex8App extends App {
  val path = args(0)
  val in = new ObjectInputStream(new FileInputStream(path))
  try {
    val f = in.readObject().asInstanceOf[Function0[Any]]
    in.close()
    val out = new ObjectOutputStream(new FileOutputStream(path))
    try {
      out.writeObject(f())
    } catch {
      case e: Throwable => out.writeObject(e)
    } finally {
      out.close()
    }
  } finally {
    in.close()
  }
}
