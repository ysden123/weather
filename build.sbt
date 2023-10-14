import java.util.Calendar

ThisBuild / scalaVersion := "3.3.1"
ThisBuild / version := "0.2.0"
ThisBuild / organization := "com.stulsoft"
ThisBuild / organizationName := "stulsoft"

lazy val root = (project in file("."))
  .settings(
    name := "weather",
    libraryDependencies += "com.stulsoft" %% "common" % "latest.integration",
    libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",
    libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.4.7",

    // toolkit
    libraryDependencies += "org.scala-lang" %% "toolkit" % "0.2.0",

    // Swing
    libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "3.0.0",

    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.15" % Test,

    scalacOptions ++= Seq(
      "-feature",
      "-deprecation",
      "-unchecked",
      "-language:postfixOps",
      "-Xfatal-warnings"
    ),
    scalacOptions ++= Seq("-Xmax-inlines", "50")
  )
  .enablePlugins(JavaAppPackaging)

Compile / packageBin / packageOptions += Package.ManifestAttributes("Build-Date" -> Calendar.getInstance().getTime.toString)
Compile / mainClass := Some("com.stulsoft.weather.Main")