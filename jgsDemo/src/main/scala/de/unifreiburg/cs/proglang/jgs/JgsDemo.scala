package de.unifreiburg.cs.proglang.jgs

import sys.process._

object JgsDemo {

  def printUsage(): Unit = {
    println("usage: jgs-demo [--secdomain project] [--demo project] [--only-dynamic] [--create-jimple] class-to-run")
  }

  def nextOption(args : List[String]) : Map[Symbol, String] = {
    args match {
      case "--secdomain" :: path :: rest => nextOption(rest) + ('secdomain -> path)
      case "--demo" :: path :: rest => nextOption(rest) + ('demo -> path)
      case "--only-dynamic" :: rest => nextOption(rest) + ('only_dynamic -> "-onlydynamic")
      case "--create-jimple" :: rest => nextOption(rest) + ('create_jimple -> "-j")
      case "--force-monomorphic-methods" :: rest => nextOption(rest) + ('force_monomorphic_methods -> "-forcemonomorphic")
      case s :: rest if !s.startsWith("-") => nextOption(rest) + ('class -> s)
      case s :: rest => println("Unkown option" + s); printUsage; sys.exit(-1)
      case Nil => Map()
    }
  }

  def run(p: ProcessBuilder) : Unit = {
    if (p.! != 0) {
      println("A command failed. Exiting")
      sys.exit(-1)
    }
  }

  def main(args : Array[String]) : Unit = {
    val options = nextOption(args.toList)
    val secdomainProject = options.getOrElse('secdomain, "LMHSecurityDomain")
    val secdomainCP = secdomainProject + "/target/scala-2.11/classes"
    val secdomainJar = secdomainProject + "/target/scala-2.11/lmhsecuritydomain_2.11-0.1.jar"
    val demoProject = options.getOrElse('demo, "DemoTestclasses")
    val classpathCompile = List(
      "JGSTestclasses/Demo/target/scala-2.11/classes",
      "JGSTestclasses/Scratch/target/scala-2.11/classes",
      "JGSSupport/target/scala-2.11/classes",
      secdomainCP,
      "DynamicAnalyzer/target/scala-2.11/classes"
    ).mkString(":")
    val classpathRun = List(
      secdomainCP,
      "DynamicAnalyzer/target/scala-2.11/classes",
      "GradualConstraints/InstrumentationSupport/target/scala-2.11/classes",
      "lib/commons-collections4-4.0.jar",
      "JGSSupport/target/scala-2.11/classes"
    ).mkString(":")
    val outputDir = "out-instrumented"
    val outputJimple = "out-original"
    val classToRun = options.getOrElse('class, {printUsage() ; throw new IllegalArgumentException("No class-to-run specified ") })

    val sbtJgsRunCommand : String = (List(
      "runMain", "de.unifreiburg.cs.proglang.jgs.Main", "-m", classToRun, "-cp", classpathCompile, "-o", outputDir
    ) ++
      options.get('only_dynamic).toList ++
      options.get('create_jimple).toList ++
      options.get('force_monomorphic_methods)
      ).mkString(" ")

    println(s"Compiling: ${secdomainProject}, ${demoProject}")
    val sbtCompileProcess =
      Process(Seq("sbt", s"; ${secdomainProject}/package ; ${demoProject}/compile"))
    run(sbtCompileProcess)

    println("Running sbt with: " + sbtJgsRunCommand)
    val sbtRunProcess = Process(
      (Seq("sbt", s"${sbtJgsRunCommand}")),
      None,
      "JGS_SECDOMAIN_JARS" -> secdomainJar
    )
    run(sbtRunProcess)


    val runCommand = Seq("java", "-cp", s"${outputDir}:${classpathRun}", classToRun)
    println()
    println(runCommand.map(s => "\"" + s + "\"").mkString(" "))
    println()
    runCommand.run(true)
  }
}
