name := """test"""
organization := "test"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.6"

libraryDependencies += guice



libraryDependencies ++= Seq(
    javaWs
)
libraryDependencies += "org.mockito" % "mockito-core" % "3.6.0" % Test
