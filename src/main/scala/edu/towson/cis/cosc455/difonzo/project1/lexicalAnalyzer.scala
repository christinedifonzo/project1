package edu.towson.cis.cosc455.difonzo.project1


trait lexicalAnalyzer {

    def addChar() : Unit
    def getChar() : Unit
    def getNextToken() : String
    def lookup(candidateToken: String) : Boolean
  }

