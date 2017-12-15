import com.typesafe.sbt.packager.docker.Cmd
import play.sbt.PlayJava

name := """Omnie ImageService"""

version := "1.0"

lazy val root = (project in file( "." )).enablePlugins( PlayJava, PlayAkkaHttpServer, DebianPlugin, DockerPlugin )

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

javaOptions ++= Seq("-Xmx2G")

fork in run := false

fork in stage := false

/* Docker Configuration */


dockerBaseImage := "openjdk:8-jre-alpine"
packageName in Docker := "image-service-old"
dockerCommands := dockerCommands.value.flatMap {
	case cmd@Cmd("FROM", _) => List(cmd, Cmd("RUN", "apk update && apk add bash"), Cmd("RUN", "mkdir -p /opt/docker/logs"))
	case other => List(other)
}

dockerEntrypoint := Seq("bin/omnie-imageservice", "-Dplay.crypto.secret=asdasdasdAvj&fvn8Tf", "-Dconfig.file=conf/application-docker.conf", "-DmongoHost=172.17.0.1")
maintainer := "Alexander Balyshyn"
dockerExposedPorts in Docker := Seq(9000, 9443)
dockerExposedVolumes := Seq("/opt/docker/logs")



