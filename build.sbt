name := "Tweet Stream"
 
version := "0.1.0 "
 
scalaVersion := "2.9.2"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
	"org.specs2" %% "specs2" % "1.12.4.1" % "test",
	"org.twitter4j" % "twitter4j-stream" % "3.0.3",
	"com.typesafe.akka" % "akka-actor"        % "2.0.3" withSources,
  	"com.typesafe.akka" % "akka-testkit"      % "2.0.3" % "test" withSources)

resolvers ++= Seq("snapshots", "releases").map(Resolver.sonatypeRepo)