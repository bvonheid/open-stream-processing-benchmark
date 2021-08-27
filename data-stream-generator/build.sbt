import sbt.Keys.javaOptions

name := "ospbench-data-stream-generator"

version := "3.0"

scalaVersion := "2.11.8"
dockerBaseImage := "openjdk:8-jdk"
val extJvmOpts = Seq(
	"-J-Xmx5g",
	"-J-Xms5g"
)

libraryDependencies ++= Dependencies.rootDependencies

assemblyMergeStrategy in assembly := {
	//For load local file with spark; https://stackoverflow.com/a/48860990
	case PathList("META-INF", "services", "org.apache.hadoop.fs.FileSystem") => MergeStrategy.filterDistinctLines
	case PathList("META-INF", xs @ _*) => MergeStrategy.discard
	case x => MergeStrategy.first
}

mainClass in assembly := Some("ingest.StreamProducer")
mainClass in(Compile, run) := Some("ingest.StreamProducer")

// JVM options
javaOptions in Universal ++= extJvmOpts
javaOptions in Test ++= extJvmOpts
// Docker configs
javaOptions in Docker ++= extJvmOpts
enablePlugins(JavaAppPackaging)


