Global / onChangedBuildSource         := ReloadOnSourceChanges
Global / watchBeforeCommand           := Watch.clearScreen
Global / watchForceTriggerOnAnyChange := true

name         := "scala3-playground"
scalaVersion := "3.2.2"

fork         := true
connectInput := true

scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-unchecked",
  "-Xfatal-warnings",
  "-Ykind-projector",
  "-Ysafe-init"
) ++ Seq("-source", "future")

javacOptions ++= Seq("-source", "17")

val zioVersion        = "2.0.8"
val pureconfigVersion = "0.17.2"

libraryDependencies ++= Seq(
  "dev.zio"               %% "zio"             % zioVersion,
  "dev.zio"               %% "zio-streams"     % zioVersion,
  "com.github.pureconfig" %% "pureconfig-core" % pureconfigVersion,
  "com.sksamuel.avro4s"   %% "avro4s-core"     % "5.0.3"
)
