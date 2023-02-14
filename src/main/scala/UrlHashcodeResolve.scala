import java.net.URL
import scala.util.Random

def randomAlphaString(length: Int): String =
  Random.alphanumeric
    .filter(_.isLetter)
    .map(_.toLower)
    .take(length)
    .mkString

/** Measure how long it takes to execute the block of code and print the result Return the result of the block.
  */
def time[A](tag: String)(block: => A): A =
  val start         = System.nanoTime()
  val result        = block // Evaluate the block of code
  val end           = System.nanoTime()
  val elapsedMillis = (end - start) / 1_000_000

  println(s"$tag took: $elapsedMillis ms")
  result

@main
def urlHashCodeResolve: Unit =
  // Create random domain to make sure it is not cached and DNS lookup is performed
  val domain = randomAlphaString(5)
  println(s"Domain: $domain")

  val urlList = time("Creating List") {
    List
      .range(1, 50)
      .map(i => s"http://$domain$i.com")
      .map(URL(_))
  }

  // Adding URLs to a Set calls hashCode on each URL.
  // URL.hashCode calls performs a DNS lookup, which takes a long time.
  time("Creating Set") {
    Set.from(urlList)
  }
