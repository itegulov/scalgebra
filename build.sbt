name := "scalgebra"

version := "0.1-M2"

licenses += ("MIT", url("http://opensource.org/licenses/MIT"))

organization := "org.scalgebra"

scalaVersion := "2.11.8"

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases")
)

scalastyleConfig := file("project/scalastyle-config.xml")

val scalaTestVersion = "3.0.1"
val algebraVersion = "0.6.0"

libraryDependencies ++= Seq(
  "org.typelevel" %% "algebra" % algebraVersion,
//  "org.typelevel" %% "algebra-std" % algebraVersion,
  "com.chuusai" %% "shapeless" % "2.3.2",
  "org.scalactic" %% "scalactic" % scalaTestVersion,
  "org.scalatest" %% "scalatest" % scalaTestVersion % "test",
  "org.scalacheck" %% "scalacheck" % "1.13.4" % "test",
  // Require explicit scala-xml version because there are multiple dependencies requiring different versions of it
  "org.scala-lang.modules" %% "scala-xml" % "1.0.5"
)
