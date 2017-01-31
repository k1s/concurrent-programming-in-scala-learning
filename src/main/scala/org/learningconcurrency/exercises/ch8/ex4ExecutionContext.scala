package org.learningconcurrency.exercises.ch8

import akka.actor.{Actor, ActorSystem, Props}

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success, Try}

/**
  * Use	actors	to	implement	the	 ExecutionContext 	interface
  *
  */
class ex4ExecutionContext extends ExecutionContext {

  import ExecutionContext._

  class Executor extends Actor {

    def receive = {
      case Execute(runnable) =>
        val runner = system.actorOf(Props(new Runner()))
        runner ! Execute(runnable)
      case ReportFailure(throwable) =>
        throwable.printStackTrace()
    }

  }

  class Runner extends Actor {
    import ExecutionContext._

    def receive = {
      case Execute(runnable) =>
        Try(runnable.run()) match {
          case Failure(f) => sender() ! ReportFailure(f)
          case Success(s) =>
        }
        context.stop(self)
    }

  }

  val system = ActorSystem("executorSystem")
  val executor = system.actorOf(Props(new Executor()))

  override def execute(runnable: Runnable): Unit = executor ! Execute(runnable)

  override def reportFailure(cause: Throwable): Unit = executor ! ReportFailure(cause)

  def shutdown() = system.shutdown()

}

object ExecutionContext {
  case class Execute(runnable: Runnable)
  case class ReportFailure(throwable: Throwable)
}

object Test extends App {

  import scala.language.implicitConversions

  implicit def toRunnable(f: () => Unit): Runnable = new Runnable {
    override def run() = f()
  }

  val executionContext = new ex4ExecutionContext
  executionContext.execute(() => {println("345"); throw new RuntimeException("aaa")})
  executionContext.shutdown()

}
