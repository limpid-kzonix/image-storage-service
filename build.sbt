import play.sbt.PlayJava

name := """Omnie ImageService"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file( "." )).enablePlugins( PlayJava, DebianPlugin )

scalaVersion := "2.11.8"

resolvers ++= Seq(
	Resolver.url( "Edulify Repository", url( "https://edulify.github.io/modules/releases/" ) )(
		Resolver.ivyStylePatterns )
)
resolvers += Resolver.url( "Typesafe Ivy releases", url( "https://repo.typesafe.com/typesafe/ivy-releases" ) )(
	Resolver.ivyStylePatterns )

libraryDependencies ++= Seq(
	filters,
	javaCore,
	cache,
	"com.impetus.kundera.client" % "kundera-mongo" % "3.8",
	"com.google.code.gson" % "gson" % "2.8.0",
	"com.fasterxml.jackson.core" % "jackson-core" % "2.8.8",
	"com.fasterxml.jackson.core" % "jackson-databind" % "2.8.8",
	"com.fasterxml.jackson.core" % "jackson-annotations" % "2.8.8",
	"org.projectlombok" % "lombok" % "1.16.16"
)

updateOptions := updateOptions.value.withCachedResolution( true )
offline := true
aggregate in update := false

routesGenerator := InjectedRoutesGenerator

publishMavenStyle := false


fork in run := true

fork in stage := true



