package org.learningconcurrency.exercises.ch3

import java.io._
import java.util.regex.Pattern

import scala.sys.process.Process
import scala.util.Try

/**
  * Implement	a	method	spawn	that,	given	a	block	of	Scala	code,	starts	a	new	JVM
  * process	and	runs	the	specified	block	in	the	new	process:
  * def	spawn[T](block:	=>T):	T	=	???
  * Once	the	block	returns	a	value,	the	 spawn 	method	should	return	the	value	from	the
  * child	process.	If	the	block	throws	an	exception,	the	 spawn 	method	should	throw	the
  * same	exception.
  * Tip
  * Use	Java	serialization	to	transfer	the	block	of	code,	its	return	value,	and	the	potential
  * exceptions	between	the	parent	and	the	child	JVM	processes.
  *
  */
object ex8 extends App {

  def	spawn[T](block:	=>T):	T	=	{
    val tmpFile = File.createTempFile("tmp", "")
    val outputStream = new ObjectOutputStream(new FileOutputStream(tmpFile))
    try {
        outputStream.writeObject(() => block)
    } finally {
      outputStream.close()
    }

    val className = ex8App.getClass.getName.split(Pattern.quote("$"))(0)
    val result = Process(s"java -cp ${System.getProperty("java.class.path")} $className ${tmpFile.getCanonicalPath}").!
    val in = new ObjectInputStream(new FileInputStream(tmpFile))
    try {
      in.readObject() match {
        case e: Throwable => throw e
        case x => x.asInstanceOf[T]
      }
    } finally {
      in.close()
      tmpFile.delete()
    }

  }

}
