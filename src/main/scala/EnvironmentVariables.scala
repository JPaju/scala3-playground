@main
def envVarMain =
  val envVarName  = "JAVA_HOME"
  val envVarValue = sys.env.get(envVarName)

  println(s"$envVarName: $envVarValue")
