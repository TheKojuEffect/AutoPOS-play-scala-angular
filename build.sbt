name := "AutoPOS"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion in ThisBuild := "2.11.7"

resolvers += "Scalaz Bintray Repo" at "https://dl.bintray.com/scalaz/releases"

resolvers += "Madoushi sbt-plugins" at "https://dl.bintray.com/madoushi/sbt-plugins/"

libraryDependencies ++= Seq(
  filters,
  cache,

  "org.postgresql" % "postgresql" % "9.4-1206-jdbc42",
  "com.github.tminglei" %% "slick-pg" % "0.10.2",
  "com.github.tminglei" %% "slick-pg_date2" % "0.10.2",

  "com.typesafe.play" %% "play-slick" % "1.1.1",

  "org.flywaydb" %% "flyway-play" % "2.2.1",

  "org.webjars" % "requirejs" % "2.1.20",
  "org.webjars" % "angularjs" % "1.4.7" exclude("org.webjars", "jquery"),
  "org.webjars" % "angular-ui-router" % "0.2.15",
  "org.webjars" % "angular-ui-bootstrap" % "0.14.3",
  "org.webjars" % "bootstrap" % "3.3.5" exclude("org.webjars", "jquery"),
  "org.webjars" % "lodash" % "3.10.1",
  "org.webjars" % "underscorejs" % "1.6.0-3",
  "org.webjars" % "jquery" % "1.11.1",

  "org.webjars" % "modernizr" % "2.8.3",
  "org.webjars" % "json3" % "3.3.2-1",
  "org.webjars" % "angular-local-storage" % "0.2.1",
  "org.webjars.bower" % "angular-cache-buster" % "0.4.3",
  "org.webjars.npm" % "ng-infinite-scroll" % "1.2.0",
  "org.webjars.bower" % "angular-ui-router-tabs" % "1.7.0",

  specs2 % Test,
  "org.scalacheck" %% "scalacheck" % "1.12.5" % Test
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

// Configure the steps of the asset pipeline (used in stage and dist tasks)
// rjs = RequireJS, uglifies, shrinks to one file, replaces WebJars with CDN
// digest = Adds hash to filename
// gzip = Zips all assets, Asset controller serves them automatically when client accepts them
pipelineStages := Seq(rjs, digest, gzip)

// RequireJS with sbt-rjs (https://github.com/sbt/sbt-rjs#sbt-rjs)
// ~~~
RjsKeys.paths += ("jsRoutes" -> ("/jsroutes" -> "empty:"))

//RjsKeys.mainModule := "main"

fork in run := true

// All work and no play...
emojiLogs
