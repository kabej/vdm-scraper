organization := "com.iadvize"

version := "0.1"

scalaVersion  := "2.11.6"

scalacOptions := Seq("-encoding", "utf8")

libraryDependencies ++= {
  val akkaV = "2.3.9"
  val sprayV = "1.3.4"
  Seq(
    "io.spray" %% "spray-can" % sprayV,
    "io.spray" %% "spray-routing" % sprayV,
    "io.spray" %% "spray-json" % "1.3.2",
    "com.typesafe.akka" %% "akka-actor" % akkaV,
    "org.reactivemongo" %% "reactivemongo" % "0.11.2",
    "org.mongodb" %% "casbah" % "3.1.1",
    "joda-time" % "joda-time" % "2.9.9",
    "com.github.salat" %% "salat" % "1.11.2",
    "net.ruippeixotog" %% "scala-scraper" % "2.0.0",
    "org.scalatest" %% "scalatest" % "3.0.4" % "test"
  )
}

assemblyJarName in assembly := "scraper-vdm.jar"

mainClass in assembly := Some("com.iadvize.vdm.Boot")

Revolver.settings: Seq[sbt.Def.Setting[_]]
