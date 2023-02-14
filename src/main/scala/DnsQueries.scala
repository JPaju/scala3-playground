import java.net.*
import scala.util.Try

def resolve(domain: String) =
  Try { InetAddress.getByName(domain) }

@main
def dnsQueries =
  val yle    = resolve("yle.fi")
  val google = resolve("google.com")
  println(s"yle.fi: $yle")
  println(s"google.com: $google")
