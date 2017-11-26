organization := "com.iadvize"

version := "0.1"

scalaVersion := "2.11.6"

scalacOptions := Seq("-encoding", "utf8")

resolvers ++= Seq(
  "spray repo" at "http://repo.spray.io/",
  "typesafe repo" at "http://repo.typesafe.com/typesafe/releases/"
)

libraryDependencies ++= {
  val akkaV = "2.3.9"
  val sprayV = "1.3.4"
  Seq(
    "io.spray" %% "spray-can" % sprayV,
    "io.spray" %% "spray-routing" % sprayV,
    "io.spray" %% "spray-json" % "1.3.2",
    "io.spray" %% "spray-testkit" % sprayV % "test",
    "com.typesafe.akka" %% "akka-actor" % akkaV,
    "com.typesafe.akka" %% "akka-testkit" % akkaV % "test",
    "org.specs2" %% "specs2-core" % "2.3.11" % "test",
    "org.reactivemongo" %% "reactivemongo" % "0.11.2",
    "org.mongodb" %% "casbah" % "3.1.1",
    "joda-time" % "joda-time" % "2.9.9",
    "com.github.salat" %% "salat" % "1.11.2"

  )
}

Revolver.settings: Seq[sbt.Def.Setting[_]]
