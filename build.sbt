name := "WebServiceClients"

version := "1.0"

lazy val `webserviceclients` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq( jdbc , cache , ws   , specs2 % Test,
  "org.webjars.npm" % "jquery" % "2.1.4",
  "org.webjars" % "bootswatch-superhero" % "3.3.5+4",
  "org.webjars" %% "webjars-play" % "2.4.0-1"
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"  