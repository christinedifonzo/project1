package edu.towson.cis.cosc455.difonzo.project1;
import java.awt.Desktop
import java.io.{File, IOException}
import java.io._
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.Stack


class MySemanticAnalyzer{
var htmlStack= new mutable.Stack[String]
  var inputStack=new mutable.Stack[String]
  var nextBlock: String =""
  var varName = new Array[String](10)//  arrays for the var def
  var varMean = new Array[String](10)
  var variables = List()
  var currentToken: String = ""
  var tree = new MySyntaxAnalyzer

  var scopeNames = new ArrayBuffer[String](100)
  var varCount: Int = 0
  var hasPrinted : Boolean = false

  def makeStack(): Unit = {
    inputStack=Compiler.Parser.tree.reverse // gets the parse tree from syntax analyzer and reverses the order
    nextBlock=inputStack.pop()
        convert()


  }

def convert(): Unit = {

  while (!inputStack.isEmpty) {
    nextBlock = inputStack.pop()
    nextBlock match {
      case CONSTANTS.DOCB => htmlStack.push("<html>")
      case CONSTANTS.DEFB => {
        var name = inputStack.pop()
        inputStack.pop()
        val mean = inputStack.pop()
        inputStack.pop()
        name = name.filter(!" ".contains(_)) // filters out the name that was set from top of stack so what is left is var
        val defed = varName.indexOf(name)
        if (defed != -1) { //update size of array
          varName(defed) = name
          varMean(defed) = mean
        }
        else {
          varName(varCount) = name // where in array
          varMean(varCount) = mean
          varCount += 1
        }
//given to an array for use later
        //nextBlock = inputStack.pop()
      }

      case CONSTANTS.DOCE => htmlStack.push("</html>")
      case CONSTANTS.PARAB => htmlStack.push("<p>")
      case CONSTANTS.PARAE => htmlStack.push("</p>")
      case CONSTANTS.NEWLINE => htmlStack.push("<br>")
      case CONSTANTS.USEB => {
        var name: String = inputStack.pop() // gets name
        inputStack.pop()
        name = name.filter(!" ".contains(_))
        if (varName.contains(name)) // if it is the same as from defintion, push it onto html stack for usafe
          htmlStack.push(varMean(varName.indexOf(name)))
        else {
          println("Static Symantic Error: Variable " + name + "has not be defined ")
          System.exit(1)
        }
        //nextBlock = inputStack.pop()
      }
      case CONSTANTS.TITLEB => { // title implemetation  -- Something went wrong in this with the head and title I believe.
        htmlStack.push("<head>")
        htmlStack.push("<title>")
        nextBlock = inputStack.pop()
        htmlStack.push(nextBlock)
        htmlStack.push("</title>")
        htmlStack.push("</head>")
        inputStack.pop()
        nextBlock=inputStack.pop()
      }
      case CONSTANTS.HEADING => {
        htmlStack.push("<h1>")
        nextBlock = inputStack.pop()
        htmlStack.push(nextBlock)
        htmlStack.push("</h1>")
        nextBlock=inputStack.pop()
      }
      case CONSTANTS.LINKB => {
        var alt = inputStack.pop()
        inputStack.pop()
        inputStack.pop()
        htmlStack.push("<a href = \"")
        nextBlock = inputStack.pop()
        htmlStack.push(nextBlock)
        htmlStack.push("\">")
        htmlStack.push(alt)
        htmlStack.push("</a>")
        inputStack.pop()
        nextBlock=inputStack.pop()

      }
      case CONSTANTS.IMAGEB => {
        val alt = inputStack.pop() // !
        inputStack.pop() // takes off text
        inputStack.pop()
        htmlStack.push("<img src =\"") //
        nextBlock = inputStack.pop()
        htmlStack.push(nextBlock) //pushes link
        htmlStack.push("\" alt=\"") //pushes on alt 'try your best'
        htmlStack.push(alt) //val push
        htmlStack.push("\">") // final characters
        inputStack.pop()
        nextBlock=inputStack.pop()

      }
      case CONSTANTS.LISTITEM => {
        htmlStack.push("<li>")
        inputStack.pop()
        nextBlock=inputStack.pop()
        htmlStack.push(nextBlock)
        htmlStack.push("</li>")
        nextBlock=inputStack.pop()
      }
      case CONSTANTS.BOLD => {
        htmlStack.push("<b>")
        nextBlock = inputStack.pop()
        htmlStack.push(nextBlock)
        inputStack.pop()
        htmlStack.push("</b>")
        nextBlock=inputStack.pop()
      }
      case _ => htmlStack.push(nextBlock) // pushes text
    }

  }

  val output = htmlStack.reverse.mkString
  val print = new PrintWriter(new File(Compiler.filename + ".html"))
  print.write(output)
  print.close

  //calls html to open
  if (!hasPrinted) {
    openHTMLFileInBrowser(Compiler.filename + ".html")
    hasPrinted = true
  }
}

  /* * Hack Scala/Java function to take a String filename and open in default web browser. */
  def openHTMLFileInBrowser(htmlFileStr : String) = {
    val file : File = new File(htmlFileStr.trim)
    println(file.getAbsolutePath)
    if (!file.exists())
      sys.error("File " + htmlFileStr + " does not exist.")

    try {
      Desktop.getDesktop.browse(file.toURI)
    }
    catch {
      case ioe: IOException => sys.error("Failed to open file:  " + htmlFileStr)
      case e: Exception => sys.error("He's dead, Jim!")
    }
  }


}