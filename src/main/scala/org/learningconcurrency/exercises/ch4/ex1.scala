package org.learningconcurrency.exercises.ch4

import scala.concurrent.Future
import scala.io.{Source, StdIn}

/**
  * Implement	a	command-line	program	that	asks	the	user	to	input	a	URL	of	some
  * website,	and	displays	the	HTML	of	that	website.	Between	the	time	that	the	user	hits
  * ENTER	and	the	time	that	the	HTML	is	retrieved,	the	program	should	repetitively
  * print	a	 . 	to	the	standard	output	every	50	milliseconds,	with	a	two	seconds	timeout.
  * Use	only	futures	and	promises,	and	avoid	the	synchronization	primitives	from	the
  * previous	chapters.	You	may	reuse	the	 timeout 	method	defined	in	this	chapter.
  */
object ex1 extends App {

  import scala.concurrent.ExecutionContext.Implicits.global

  def input(): String = StdIn.readLine("Enter your url\n")

  def getWebPage(url: String) = Future {
    val f = Source.fromURL(url)
      try f.getLines().mkString finally f.close()
  }

  def printWebPage: Future[Unit] = getWebPage(input()) map (println(_))

  def main(): Unit = {
    val future = printWebPage
    while (!future.isCompleted)
       println(".")
  }

  main()

}
