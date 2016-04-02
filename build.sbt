name := "scalgebra"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.8"

resolvers += Resolver.sonatypeRepo("releases")

val scalaTestVersion = "3.0.0-M15"

libraryDependencies ++= Seq(
  "org.spire-math" %% "spire" % "0.11.0",
  "org.typelevel" %% "cats" % "0.4.1",
  "org.scalactic" %% "scalactic" % scalaTestVersion,
  "org.scalatest" %% "scalatest" % scalaTestVersion % "test"
)