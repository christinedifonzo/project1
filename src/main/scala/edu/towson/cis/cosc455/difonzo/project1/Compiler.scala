package edu.towson.cis.cosc455.difonzo.project1

import scala.io.Source
import java.io.{BufferedWriter, File, FileWriter, IOException}
import java.awt.Desktop


object Compiler {

  val Scanner = new MyLexicalAnalyzer
  val Parser = new MySyntaxAnalyzer
  val SemanticAnalyzer = new MySemanticAnalyzer
  var currentToken: String = ""
  var convertedFile: String = ""
  var endCase: Boolean = false
  val filename: String = ""
  var fileContents: String = ""



  def main(args: Array[String]) = {

    val filename = args(0)

    checkFile(args)
    readFile(args(0))


    Scanner.start(fileContents) // trigger method

    while (Scanner.charPosition < Scanner.file.length() - 1 && !endCase) {
      currentToken = Scanner.getNextToken() // sets current token to be checked
      Parser.gittex()
      if (currentToken.equalsIgnoreCase(CONSTANTS.DOCE)) {
        endCase = true
      }

    }
    SemanticAnalyzer.makeStack() //makes the new html stack!!
    //testOutputs()
  }

  def readFile(file: String) = {
    val source = scala.io.Source.fromFile(file)
    fileContents = try source.mkString finally source.close()
  }

  //checks file type and args
  def checkFile(args: Array[String]) = {
    if (args.length != 1) {
      println("USAGE ERROR: Wrong number of args")
      System.exit(1)
    }
    else if (!args(0).endsWith(".gtx")) {
      println("USAGE ERROR: Wrong Extension Type")
      System.exit(1)
    }

  }





}
