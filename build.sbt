name := "AutoPOS"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion in ThisBuild := "2.11.7"

libraryDependencies ++= Seq(
  filters,
  cache,

  "org.postgresql" % "postgresql" % "9.4-1206-jdbc42",
  "com.github.tminglei" %% "slick-pg" % "0.10.2",
  "com.github.tminglei" %% "slick-pg_date2" % "0.10.2",

  "com.typesafe.play" %% "play-slick" % "1.1.1",

  "org.flywaydb" %% "flyway-play" % "2.2.1",

  specs2 % Test
)

routesGenerator := InjectedRoutesGenerator

fork in run := false

// All work and no play...
emojiLogs
