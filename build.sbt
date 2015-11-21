name := "AutoPOS"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

resolvers += "Scalaz Bintray Repo" at "https://dl.bintray.com/scalaz/releases"

libraryDependencies ++= Seq(

  "mysql" % "mysql-connector-java" % "5.1.34",

  "com.typesafe.play" %% "play-slick" % "1.1.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "1.1.0",

  "org.webjars" % "requirejs" % "2.1.20",
  "org.webjars" % "angularjs" % "1.4.7",
  "org.webjars" % "angular-ui-bootstrap" % "0.14.3",
  "org.webjars" % "bootstrap" % "3.3.5",
  "org.webjars" % "lodash" % "3.10.1",

  specs2 % Test
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

pipelineStages := Seq(rjs, digest, gzip)

fork in run := true
