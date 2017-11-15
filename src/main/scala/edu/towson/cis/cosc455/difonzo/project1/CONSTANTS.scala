package edu.towson.cis.cosc455.difonzo.project1
import scala.collection.mutable.ListBuffer
object CONSTANTS {
  val DOCB : String = "\\BEGIN"
  val DOCE: String = "\\END"
  val TITLEB : String = "\\TITLE["
  val BRACKETE : String = "]"
  val HEADING : String = "#"
  val PARAB : String = "\\PARAB"
  val PARAE: String = "\\PARAE"
  val BOLD : String = "*"
  val LISTITEM : String = "+"
  val NEWLINE : String = "\\\\"
  val LINKB : String =  "["
  val ADDRESSB : String = "("
  val ADDRESSE: String = ")"
  val IMAGEB : String = "!["
  val DEFB : String = "\\DEF["
  val EQSIGN : String = "="
  val USEB : String = "\\USE["
  val EL: String = "\n"
  val letters : List[String] = List("a","b","c","d","e","f","g","h","i","j","k","l","m",
    "n","o","p","q","r","s","t","u","v","w","x","y","z","A","B","C","D","E","F","G","H","I","J","K","L","M",
    "N","O","P","Q","R","S","T","U","V","W","X","Y","Z")
  val numbersEtc : List[String] = List("1","2","3","4","5","6","7","8","9","0",
    ",",".","\"",":","?","_","/", "'", " ", "")
  val whiteSpace : List[String] = List(" ", "\t", "\n", "\b","\f","\r")
  val whiteEscapes: List[Char] = List('\t', '\n', '\b','\f','\r')
  val REQTEXT: List[Any] = whiteSpace ::: letters ::: numbersEtc // this is for the syntax method -- see isText method

  val gittexLexemes: List[String] = List(DOCB,DOCE,TITLEB,BRACKETE,HEADING,PARAB,PARAE,BOLD,LISTITEM,NEWLINE,LINKB,ADDRESSB,ADDRESSE,IMAGEB,DEFB,EQSIGN,USEB) //sets the tokens
  val keyWordSymbols: List[Char] = List ('\\','=','!','(','[','+','*','#',')',']') // is it a start symbol that triggers wheether a token or text


}
