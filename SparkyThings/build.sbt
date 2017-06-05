name := "SparkyThings"

version := "1.0"

scalaVersion := "2.11.8"


libraryDependencies += "org.apache.hadoop" % "hadoop-client" % "2.7.1"
libraryDependencies += "org.apache.spark" % "spark-core_2.11" % "2.0.2"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.0.2"
libraryDependencies += "org.apache.spark" %% "spark-graphx" % "2.1.0"
libraryDependencies += "org.apache.spark" %% "spark-mllib" % "2.1.0"
libraryDependencies += "com.typesafe.slick" %% "slick" % "3.2.0"
libraryDependencies += "com.typesafe.slick" %% "slick-codegen" % "3.2.0"
libraryDependencies += "io.spray" %%  "spray-json" % "1.3.3"
libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.12"