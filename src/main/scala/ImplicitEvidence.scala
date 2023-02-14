import scala.util.NotGiven

object ImplicitEvidence:
  type !:![A, B] = NotGiven[A =:= B]

  def mustBeSametypes[A, B](a: A, b: B)(using A =:= B) = (a, b)
  def mustBeDifferent[A, B](a: A, b: B)(using A !:! B) = (a, b)

  mustBeSametypes(1, 2)
  mustBeDifferent(1, false)
  // mustBeSametypes(1, false)
  // mustBeDifferent(1, 2)
