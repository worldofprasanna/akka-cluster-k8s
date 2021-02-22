name := "akka-cluster-k8s"

version := "0.1"

scalaVersion := "2.11.11"

maintainer := "Prasanna V"

packageSummary := "Akka Cluster & Kubernetes tryout"

packageDescription := """POC to try Akka clustering with Kubernetes.
  This will be using little bit older version of Akka (2.5.1) and Scala 2.11.11"""

debianPackageDependencies := Seq("java8-runtime-headless")

enablePlugins(JavaAppPackaging)
enablePlugins(DebianPlugin)

lazy val akkaVersion = "2.5.1"

mainClass := Some("com.tryout.akkas.Boot")

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-remote" % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster" % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster-sharding" % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster-tools" % akkaVersion
)
