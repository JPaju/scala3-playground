import pureconfig.*
import pureconfig.generic.derivation.default.*

final case class AppConfiguration(
    username: String,
    password: String,
    properties: Map[String, String]
) derives ConfigReader

@main
def pureconfigMain =
  val configSource = ConfigSource.defaultApplication
  val appConf      = configSource.load[AppConfiguration]

  println(s"App configuration: $appConf")
