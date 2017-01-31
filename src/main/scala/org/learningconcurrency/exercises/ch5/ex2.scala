package org.learningconcurrency.exercises.ch5

import scala.util.Random
import org.learningconcurrency.ch5._

/**
  *
  *
  */
object ex2 extends App {

  def randomString(p: Double): String = {
    require(p > 0.0 && p < 1.0)
    val random = new Random()
    val stringWithoutSpaces = random.alphanumeric.take(100500000).toString()
    stringWithoutSpaces.map(if (random.nextDouble() > p) '_' else _)
  }

  def countWithCount(s: String): Int = s.par.count(_ == ' ')

  def countWithForEach(s: String): Int = {
    var count = 0
    s.par.foreach(c => if (c == ' ') synchronized(count += 1))
    count
  }

  def countTimeFromP(p: Double)(f: String => Int) = {
    val s = randomString(p)
    (p, warmedTimed()(f(s)))
  }

  def countTimeWithCount = (p: Double) => countTimeFromP(p)(countWithCount)

  def countTimeWithForEach = (p: Double) => countTimeFromP(p)(countWithForEach)

  def times(f: Double => (Double, Double)) = (1 to 99).map(_.toDouble / 100).map(f)

  val timesCount = times(countTimeWithCount)
  val timesForEach = times(countTimeWithForEach)

  import com.quantifind.charts.Highcharts._
  hold
  line(timesCount)
  line(timesForEach)
  xAxis("Probability")
  yAxis("Times")
  legend(Seq("Count", "ForEach"))

}
