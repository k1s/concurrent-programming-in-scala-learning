package org.learningconcurrency.exercises.ch5

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

import scala.collection.parallel.immutable.ParSeq

/**
  *
  *
  */
object ex3 extends App {

  case class Complex(x: Double, y: Double) {

    def +(that: Complex): Complex = Complex(this.x + that.x, this.y + that.y)

    def *(that: Complex): Complex = Complex(this.x * that.x - this.y * that.y, this.x * that.y + that.x * this.y)

    def abs(): Double = Math.sqrt(x * x + y * y)

  }

  val size = 4444

  val level = 300

  val pixels =
    for {
      x <- 0 until size
      y <- 0 until size
    } yield (x, y)

  def computeColor(x0: Int, y0: Int): Int = {
    val x1 = -2.0 + x0 * 3.0 / size
    val y1 = -1.5 + y0 * 3.0 / size
    var z = Complex(0, 0)
    var c = Complex(x1, y1)

    for (i <- 0 until level)
      z = z * z + c

    if (z.abs() < 2)
      0
    else
      255
  }

  def write(pixels: ParSeq[(Int, Int, Int)]) = {
    val image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB)
    pixels.foreach(p => image.setRGB(p._1, p._2, p._3))
    ImageIO.write(image, "png", new File("image.png"))
  }

  def run() = write(pixels.par.map(t => (t._1, t._2, computeColor(t._1, t._2))))

  run()

}