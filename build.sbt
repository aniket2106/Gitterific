name := """test"""
organization := "test"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.6"

libraryDependencies += guice

libraryDependencies += ehcache

libraryDependencies ++= Seq(
    javaWs
)

lazy val akkaVersion = "2.6.14"

libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
    "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion,
    "ch.qos.logback" % "logback-classic" % "1.2.3",
    "junit" % "junit" % "4.13.1" % Test,
    "com.novocode" % "junit-interface" % "0.11" % Test)


libraryDependencies += "org.mockito" % "mockito-core" % "4.0.0" % Test

libraryDependencies += "org.powermock" % "powermock-module-junit4" % "2.0.9" % Test

libraryDependencies += "org.powermock" % "powermock-api-mockito2" % "2.0.9" % Test

