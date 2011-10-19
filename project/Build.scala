import sbt._
import Keys._

object SocializeNBuild extends Build {
  val commonSettings = Defaults.defaultSettings ++ Seq(
    organization     := "com.threerings.socializen",
    version          := "1.0-SNAPSHOT",
    crossPaths       := false,
    javacOptions     ++= Seq("-Xlint", "-Xlint:-serial", "-source", "1.6", "-target", "1.6"),
    fork in Compile  := true,
    autoScalaLibrary := false // no scala-library dependency
  )

  val playnVer = "1.0-SNAPSHOT"
  val gwtVer = "2.4.0"
  val gwtDeps = Seq(
    "com.google.gwt" % "gwt-user" % gwtVer,
    "com.google.gwt" % "gwt-dev" % gwtVer
  )

  val testDeps = Seq(
    "junit" % "junit" % "4.+" % "test",
 	  "com.novocode" % "junit-interface" % "0.7" % "test->default"
  )

  val coreLocals = com.samskivert.condep.Depends(
    ("playn", "core", "com.googlecode.playn" % "playn-core" % playnVer)
  )
  lazy val core = coreLocals.addDeps(Project(
    "core", file("core"), settings = commonSettings ++ Seq(
      name := "socializen-core",
      // adds source files to our jar file (needed by GWT)
      unmanagedResourceDirectories in Compile <+= baseDirectory / "src/main/java",
      libraryDependencies ++= coreLocals.libDeps ++ testDeps ++ Seq(
        // nada for now
      )
    )
  ))

  val androidLocals = com.samskivert.condep.Depends(
    ("playn", "android", "com.googlecode.playn" % "playn-android" % playnVer)
  )
  lazy val facebookAndroid = androidLocals.addDeps(Project(
    "facebook-android", file("facebook-android"), settings = commonSettings ++ Seq(
      name := "socializen-facebook-android",
      resolvers += "egoclean" at "http://mvn.egoclean.com",
      libraryDependencies ++= androidLocals.libDeps ++ testDeps ++ Seq(
        "com.egoclean" % "android-facebook" % "1.4"
      )
    )
  )) dependsOn(core)

  lazy val socializen = Project("socializen", file(".")) aggregate(
    core, facebookAndroid)
}
