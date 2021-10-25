name := "DBIntegrationTestWithTestContainerScala"

version := "0.1"

scalaVersion := "2.12.6"

val testContainersScalaVersion = "0.39.8"
val mockito = "1.16.37"

libraryDependencies ++= Seq(
  "com.dimafeng" %% "testcontainers-scala-scalatest" % testContainersScalaVersion % "test",
  "com.dimafeng" %% "testcontainers-scala-mongodb" % testContainersScalaVersion % "test",
  "org.mongodb.scala" %% "mongo-scala-driver" % "2.7.0",
  "joda-time" % "joda-time" % "2.10",
  "org.joda" % "joda-money" % "1.0.1",
  "org.mockito" %% "mockito-scala" % mockito,
  "org.mockito" %% "mockito-scala-scalatest" % mockito,
  "org.scalatest" %% "scalatest" % "3.0.0",
  "com.dimafeng" %% "testcontainers-scala-mysql" % testContainersScalaVersion % "test",
  //"org.apache.logging.log4j" % "log4j-to-slf4j" % "2.8.2" % Test
  "ch.qos.logback" % "logback-classic" % "1.1.3" % Runtime
)

Test / fork := true
