Global / onChangedBuildSource := ReloadOnSourceChanges
watchBeforeCommand            := Watch.clearScreen

name         := "scala3-playground"
scalaVersion := "3.2.2"

fork         := true
connectInput := true

scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-explain",
  "-Ycheck-all-patmat",
  "-Ykind-projector",
  "-Ysafe-init"
) ++ Seq("-source", "future")

javacOptions ++= Seq("-source", "17")

val zioVersion        = "2.0.6"
val zioKafkaVersion   = "2.0.0"
val zioSchemaVersion  = "0.4.1"
val pureconfigVersion = "0.17.2"

libraryDependencies ++= Seq(
  "dev.zio"               %% "zio"                   % zioVersion,
  "dev.zio"               %% "zio-streams"           % zioVersion,
  "dev.zio"               %% "zio-kafka"             % zioKafkaVersion,
  "dev.zio"               %% "zio-schema"            % zioSchemaVersion,
  "dev.zio"               %% "zio-schema-avro"       % zioSchemaVersion,
  "dev.zio"               %% "zio-schema-derivation" % zioSchemaVersion,
  "com.github.pureconfig" %% "pureconfig-core"       % pureconfigVersion,
  "com.sksamuel.avro4s"   %% "avro4s-core"           % "5.0.3",
  ("com.sksamuel.avro4s"  %% "avro4s-kafka"          % "4.1.0")
    .cross(CrossVersion.for3Use2_13)
    .excludeAll(ExclusionRule(organization = "com.sksamuel.avro4s"))
)

libraryDependencies ++= Seq(
  "dev.zio" %% "zio-test"     % zioVersion,
  "dev.zio" %% "zio-test-sbt" % zioVersion
).map(_ % Test)
