package fi.jpaju

import zio.*

trait Amplifier

trait Yeller:
  def tell(msg: String): UIO[Unit]
  def yell(msg: String): IO[ThroatBrokeError, Unit]
  def scream(msg: String): ZIO[Amplifier, ThroatBrokeError, Unit]

case class ThroatBrokeError(severity: Int)

// How to use/implement polymorphic function types in Scala

// Scala 3 only solution
object PolymorphicFunctions:
  def serviceWith[Service: Tag] =
    [A] => (f: Service => A) => ZIO.service[Service].map(f)

  def serviceWithZIO[Service: Tag] =
    [R, E, A] => (f: Service => ZIO[R, E, A]) => ZIO.service[Service].flatMap(f)

  val uioProgram = serviceWithZIO[Yeller](_.tell("Hello"))
  val ioProgram  = serviceWithZIO[Yeller](_.yell("Hello"))
  val zioProgram = serviceWithZIO[Yeller](_.scream("Hello"))

// Scala 2 solution
object PartiallyApplied:
  final class ServiceWithZIOPartiallyApplied[Service](using Tag[Service]) extends AnyVal:
    def apply[R, E, A](f: Service => ZIO[R, E, A]): ZIO[Service & R, E, A] =
      ZIO.service[Service].flatMap(f)

  def serviceWithZIO[Service: Tag] =
    new ServiceWithZIOPartiallyApplied[Service]

  val uioProgram = serviceWithZIO[Yeller](_.tell("Hello"))
  val ioProgram  = serviceWithZIO[Yeller](_.yell("Hello"))
  val zioProgram = serviceWithZIO[Yeller](_.scream("Hello"))
