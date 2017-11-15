package edu.towson.cis.cosc455.difonzo.project1
import scala.collection.mutable.Queue
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.ArrayBuffer

class MyLexicalAnalyzer extends lexicalAnalyzer{

  var file : String= ""
  var lexeme: List[Char] = List()
  //var found: List[String] = List()
  private var nextChar:Char = 0
 var charPosition = 0
  private var validText : List[String] = List()
  private var lexemesList : List[String] = List()

  def start(f: String): Unit = {
    initializeLexemes() // sets list from constants
    file = f
    charPosition = 0
  }

  override def getNextToken(): String = {
    getChar()
    lexeme = List() //
    while(isSpace(nextChar)){ // While there is blank space, keep getting the next char in the file
      getChar()
    }
    //If potential start of token, process token
    if(isKeyWordSymbols()){
      nextChar match {
        case '=' => addChar()
        case '(' => addChar()
        case '+' => addChar()
        case '*' => addChar()
        case '#' => addChar()
        case ')' => addChar()
        case ']' => addChar()
        case '[' => addChar()
        case '!' => processToken() // these are special cases - must check for the link, title, new block type
        case '\\' => processToken()
        case  _ =>  System.exit(1)
      }
      check() // is this valid???
    }
    // If the char is not a token start, process if it valid text
    else if (validText.contains(nextChar.toString())){
      processText()
    } else{
      println("Lexical Error at " + nextChar + ". This is not valid text")
      System.exit(1)
    }
    //found :+= lexeme.mkString  // debugging purposes
    lexeme.mkString // returns the string in compiler
  }

  def check(): Unit ={
    if(!lookup(lexeme.mkString)){
      println("Lexical Error at " + lexeme.mkString + ". This is an invalid token")
      System.exit(1)
    }
    else {
      lexeme = lexeme.map(_.toTitleCase)
    }

  }

  def processText(): Unit ={
    var stop: Boolean = false

    while(!stop){
      if(isKeyWordSymbols()){
        stop = true
      }
      else if (!CONSTANTS.whiteEscapes.contains(file.charAt(charPosition)) && !CONSTANTS.keyWordSymbols.contains(file.charAt(charPosition))) { //if its not white space or key world, add and get
        addChar()
        getChar()
      }
      else{
        addChar() // if it is both, add Char and stop the process
        stop = true
      }
    }
  }

  def processToken(): Unit = {
    var stop: Boolean = false
    if (nextChar.equals('!')) { //links
      addChar()
      getChar()
      if (nextChar.equals('[')) {
        addChar()
      }
    }
    else if (nextChar.equals('\\')) {
      addChar()
      getChar()
      if (nextChar.equals('\\')) {
        addChar() // if another one, add the next slash to differenitiate newline
      }
      else {
        while (!stop) {
          if (nextChar.equals('[')) {
            addChar()
            stop = true // time to process text
          }
          else if(isSpace(nextChar) || lookup(lexeme.mkString)){ // if its a space OR the token exists stop
            stop = true
          }
          else {
            addChar() //if not keep going
            getChar()
          }
        }
      }
    }
  }
// if it's is one of the main starting symbols is continues
  def isKeyWordSymbols(): Boolean ={
    CONSTANTS.keyWordSymbols.contains(nextChar)
  }
//does gittexlemes in constants contain this
  override def lookup(candidateToken: String): Boolean = {
    lexemesList.contains(candidateToken.toUpperCase)
  }

  override def getChar(): Unit = {
    if (charPosition < file.length()) {
      nextChar = file.charAt(charPosition)
      charPosition = charPosition + 1
      //println("getting char: " + nextChar)
    }
  }

  override def addChar(): Unit = {
    lexeme :+= nextChar
      //println("adding char: " + nextChar)
  }

  private def isSpace(c: Char):Boolean = {
    CONSTANTS.whiteSpace.contains(c.toString())
  }

  private def initializeLexemes() = {
    validText = CONSTANTS.letters ::: CONSTANTS.numbersEtc
    lexemesList = CONSTANTS.gittexLexemes

  }
  def isText(): Boolean ={
    if (CONSTANTS.REQTEXT.contains(nextChar.toString()))
    {
      true
    }
    else false
  }
// method is used in syntax to determine req text - can have the spaces

}