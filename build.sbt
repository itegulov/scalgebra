name := "scalgebra"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.8"

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)

scalastyleConfig := file("project/scalastyle-config.xml")

val scalaTestVersion = "3.0.0-M15"
val algebraVersion = "0.3.1"
val scalazVersion = "7.2.2"

libraryDependencies ++= Seq(
  "org.spire-math" %% "algebra" % algebraVersion,
  "org.spire-math" %% "algebra-std" % algebraVersion,
  "org.scalaz" %% "scalaz-core" % scalazVersion,
  "com.chuusai" %% "shapeless" % "2.3.0",
  "org.scalactic" %% "scalactic" % scalaTestVersion,
  "org.scalatest" %% "scalatest" % scalaTestVersion % "test",
  "org.scalacheck" %% "scalacheck" % "1.12.5" % "test"
)
