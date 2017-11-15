package edu.towson.cis.cosc455.difonzo.project1

import scala.collection.mutable.Stack


class MySyntaxAnalyzer extends SyntaxAnalyzer {
  var tree = Stack[String]()
  var currentToken: String = ""
  var errorFound: Boolean = false

  def reset() = errorFound = false


  override def gittex(): Unit = {
    reset()
    docb()
    while (currentToken.equalsIgnoreCase(CONSTANTS.DEFB)) {
      variableDefine()
    }
    title()
    body()
    doce()
  }

  override def link(): Unit = {
    linkb()
    reqtext()
    closebrackete()
    addressb()
    reqtext()
    addresse()
  }

  override def image(): Unit = {
    imageb()
    reqtext()
    closebrackete()
    addressb()
    reqtext()
    addresse()
  }

  override def newline(): Unit = {
    reset()
    if (currentToken.equalsIgnoreCase(CONSTANTS.NEWLINE)) {
      tree.push(currentToken)
      if (!currentToken.equalsIgnoreCase(CONSTANTS.DOCE)) {
        currentToken = Compiler.Scanner.getNextToken()
      }
    }
    else
     setError(CONSTANTS.NEWLINE)
  }


  def setError(expect: String): Unit = {
    errorFound = true
    println("Syntax error at " + currentToken + ". Expected: " + expect)
    System.exit(1)
  }

  override def title(): Unit = {
    titleb()
    reqtext()
    closebrackete()
  }

  override def paragraph(): Unit = {
    parab()
    while(currentToken.equalsIgnoreCase(CONSTANTS.DEFB)){
      variableDefine()}
reqtext()
    inneritem()
    parae()
  }

  override def heading(): Unit = {
    headb()
    reqtext()
  }

  override def variableUse(): Unit = {
    useb()
    reqtext()
    closebrackete()
  }

  override def bold(): Unit = {
    bold()
    reqtext()
    bold()
  }

  override def listItem(): Unit = {
    listitemb()
    inneritem()
  }

  override def body(): Unit = {

    if (currentToken.equalsIgnoreCase(CONSTANTS.DEFB)) {
      variableDefine()
      body()
    }
    else if (currentToken.equalsIgnoreCase(CONSTANTS.PARAB)) {
      paragraph()
      body()
    }
    else if (currentToken.equalsIgnoreCase(CONSTANTS.NEWLINE)) {
      newline()
      body()
    }
    else if (currentToken.equalsIgnoreCase(CONSTANTS.PARAE)){}
    else if (currentToken.equalsIgnoreCase(CONSTANTS.DOCE)){}
    else {
      innerText()
      body()
    }
  }

  def innerText(): Unit = {
    if (currentToken.equalsIgnoreCase(CONSTANTS.PARAB)) {
      paragraph()
    }
    else if (currentToken.equalsIgnoreCase(CONSTANTS.USEB)) {
      variableUse()
      innerText()
    }
    else if (currentToken.equalsIgnoreCase(CONSTANTS.HEADING)) {
      heading()
      innerText()
    }
    else if (currentToken.equalsIgnoreCase(CONSTANTS.BOLD)) {
      bold()
      innerText()
    }
    else if (currentToken.equalsIgnoreCase(CONSTANTS.LISTITEM)) {
      listItem()
      innerText()
    }
    else if (Compiler.currentToken.equalsIgnoreCase(CONSTANTS.IMAGEB)) {
      image()
      innerText()
    }
    else if (Compiler.currentToken.equalsIgnoreCase(CONSTANTS.LINKB)) {
      link()
      innerText()
    }

    else if (Compiler.currentToken.equalsIgnoreCase(CONSTANTS.NEWLINE)) {
      newline()
      innerText()
    }
    else if (Compiler.currentToken.equalsIgnoreCase(CONSTANTS.DOCE)) {
    }
    else
      reqtext()
  }


  override def variableDefine(): Unit = {
    defb()
    reqtext()
    eqsign()
    reqtext()
    closebrackete()
  }


  def reqtext(): Unit = {
    reset()
    if (!CONSTANTS.gittexLexemes.contains(currentToken)) {
      tree.push(currentToken)

      if (!currentToken.equalsIgnoreCase(CONSTANTS.DOCE)) {
        currentToken = Compiler.Scanner.getNextToken()
      }
    }
  }


  def eqsign(): Unit = {

    reset()

    if (currentToken.equalsIgnoreCase(CONSTANTS.EQSIGN)) {
      tree.push(currentToken)
      if (!currentToken.equalsIgnoreCase(CONSTANTS.DOCE)) {
        currentToken = Compiler.Scanner.getNextToken()
      }
      else
        setError(CONSTANTS.EQSIGN)
    }
  }

  def closebrackete(): Unit = {
    reset()

    if (currentToken.equalsIgnoreCase(CONSTANTS.BRACKETE)) {
      tree.push(currentToken)
      if (!currentToken.equalsIgnoreCase(CONSTANTS.DOCE)) {
        currentToken = Compiler.Scanner.getNextToken()
      }
      else
        setError(CONSTANTS.BRACKETE)
    }
  }

  def titleb(): Unit = {
    reset()
    if (currentToken.equalsIgnoreCase(CONSTANTS.TITLEB)) {
      tree.push(currentToken)
      if (!currentToken.equalsIgnoreCase(CONSTANTS.DOCE)) {
        currentToken = Compiler.Scanner.getNextToken()
      }
    }
    else
      setError(CONSTANTS.TITLEB)
  }


  def docb(): Unit = {
    if (Compiler.currentToken.equalsIgnoreCase(CONSTANTS.DOCB)) {
      tree.push(Compiler.currentToken)
      currentToken = Compiler.Scanner.getNextToken()
      }
    else
      setError(CONSTANTS.DOCB)
    }


  def doce(): Unit = {
    reset()
    if (currentToken.equalsIgnoreCase(CONSTANTS.DOCE)) {
      tree.push(currentToken)
    }
    if (!currentToken.equalsIgnoreCase(CONSTANTS.DOCE)) {
      currentToken = Compiler.Scanner.getNextToken()
    }
    else println("done!")
  }

  def parab(): Unit = {
    reset()
    if (currentToken.equalsIgnoreCase(CONSTANTS.PARAB)) {
      tree.push(CONSTANTS.PARAB)
      if (!currentToken.equalsIgnoreCase (CONSTANTS.DOCE)) {
        currentToken = Compiler.Scanner.getNextToken()
      }
    }
    else
      setError(CONSTANTS.PARAB)

  }


  def parae(): Unit = {
    reset()
    if (currentToken.equalsIgnoreCase(CONSTANTS.PARAE)) {
      tree.push(currentToken)
      if (!currentToken.equalsIgnoreCase (CONSTANTS.DOCE)) {
        currentToken = Compiler.Scanner.getNextToken()
      }
    }
    else {
      setError(CONSTANTS.PARAE)
    }
  }

  def useb(): Unit = {
    reset()
    if (currentToken.equalsIgnoreCase(CONSTANTS.USEB)) {
      tree.push(CONSTANTS.USEB)
      if (!currentToken.equalsIgnoreCase(CONSTANTS.DOCE)) {
        currentToken = Compiler.Scanner.getNextToken()
      }
      else
        setError((CONSTANTS.USEB))
    }
  }

  def headb(): Unit = {
    reset()
    if (currentToken.equalsIgnoreCase(CONSTANTS.HEADING)) {
      tree.push(currentToken)
      Compiler.Scanner.getNextToken()
    }
    else {
      println("Syntax error. \\BEGIN was needed to start the document.")
      System.exit(1)
    }
  }


  def defb(): Unit = {
  reset()
    if (currentToken.equalsIgnoreCase(CONSTANTS.DEFB) ) {
    tree.push(currentToken)
  if (!currentToken.equalsIgnoreCase (CONSTANTS.DOCE)) {
  currentToken = Compiler.Scanner.getNextToken()
}
  else
 setError(CONSTANTS.DEFB)
}
}



  def listitemb(): Unit = {
    reset()
    if (currentToken.equalsIgnoreCase(CONSTANTS.LISTITEM) ) {
      tree.push(currentToken)
      if (!currentToken.equalsIgnoreCase (CONSTANTS.DOCE)) {
        currentToken = Compiler.Scanner.getNextToken()
      }
      else
        setError(CONSTANTS.LISTITEM)
    }
  }
def inneritem(): Unit = {
  if (currentToken.equalsIgnoreCase(CONSTANTS.USEB)) {
    variableUse()
    inneritem()
  }
  else if (currentToken.equalsIgnoreCase(CONSTANTS.BOLD)) {
    bold()
    inneritem()
  }
  else if (currentToken.equalsIgnoreCase(CONSTANTS.LINKB)) {
    link()
    inneritem()
  }
  else
    reqtext()
}

  def linkb(): Unit = {
    reset()
    if (currentToken.equalsIgnoreCase(CONSTANTS.LINKB) ) {
      tree.push(currentToken)
      if (!currentToken.equalsIgnoreCase (CONSTANTS.DOCE)) {
        currentToken = Compiler.Scanner.getNextToken()
      }
      else
        setError(CONSTANTS.LINKB)
    }
  }

  def addressb(): Unit = {
    reset()
    if (currentToken.equalsIgnoreCase(CONSTANTS.ADDRESSB) ) {
      tree.push(currentToken)
      if (!currentToken.equalsIgnoreCase (CONSTANTS.DOCE)) {
        currentToken = Compiler.Scanner.getNextToken()
      }
      else
        setError(CONSTANTS.ADDRESSB)
    }
  }

  def addresse(): Unit = {
    reset()
    if (currentToken.equalsIgnoreCase(CONSTANTS.ADDRESSE) ) {
      tree.push(currentToken)
      if (!currentToken.equalsIgnoreCase (CONSTANTS.DOCE)) {
        currentToken = Compiler.Scanner.getNextToken()
      }
      else
        setError(CONSTANTS.ADDRESSE)
    }
  }
  def imageb(): Unit = {
    reset()
    if (currentToken.equalsIgnoreCase(CONSTANTS.IMAGEB) ) {
      tree.push(currentToken)
      if (!currentToken.equalsIgnoreCase (CONSTANTS.DOCE)) {
        currentToken = Compiler.Scanner.getNextToken()
      }
      else
        setError(CONSTANTS.IMAGEB)
    }
  }


}


