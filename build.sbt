name := "AutoPOS"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion in ThisBuild := "2.11.8"

libraryDependencies ++= Seq(
  filters,
  cache,

  "org.postgresql" % "postgresql" % "9.4-1206-jdbc42",

  "com.typesafe.play" %% "play-slick" % "2.0.0",

  "org.flywaydb" %% "flyway-play" % "3.0.0",

  "org.scalactic" %% "scalactic" % "3.0.0-M15",
  "org.scalatest" %% "scalatest" % "3.0.0-M15" % "test",
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.0" % "test"
)

fork in run := false

// All work and no play...
emojiLogs