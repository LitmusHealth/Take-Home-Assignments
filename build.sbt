name := "DatastoreEntities"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "com.google.appengine" % "appengine-testing" % "1.9.27",
  "com.google.appengine" % "appengine-api-stubs" % "1.9.27",
  "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test",
  "com.google.appengine" % "appengine-api-1.0-sdk" % "1.9.27"

)