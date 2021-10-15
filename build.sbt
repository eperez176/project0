import Dependencies._

ThisBuild / scalaVersion     := "2.13.6"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "p0",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % Test,
    libraryDependencies += "org.mongodb.scala" %% "mongo-scala-driver" % "2.8.0",
    libraryDependencies += "io.spray" %%  "spray-json" % "1.3.6",
    libraryDependencies += "com.lihaoyi" %% "upickle" % "0.9.5",
    libraryDependencies += "com.lihaoyi" %% "os-lib" % "0.7.8"

  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
