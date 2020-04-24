ThisBuild / organization := "com.soares"
ThisBuild / scalaVersion := "2.13.1"
ThisBuild / version := "0.0.1-SNAPSHOT"

val scalatest = "org.scalatest" %% "scalatest" % "3.1.1" % Test
val apachepoi = "org.apache.poi" % "poi" % "4.1.2"
val apacheooxml = "org.apache.poi" % "poi-ooxml" % "4.1.2"

lazy val root = (project in file("."))
  .settings(
    name := "excel-to-xml",
    libraryDependencies ++= Seq(scalatest, apachepoi, apacheooxml)
  )
