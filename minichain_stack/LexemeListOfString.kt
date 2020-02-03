package my.lexeme.list.string

import java.io.File
import java.util.Stack
import java.lang.Character.MIN_VALUE as nullChar

import my.library.*
import my.lexeme.*

fun lexemeListOfAuthorLine (lin: String, caller: String) : MutableList<Lexeme> {
// # $Author: michel$'
    val here = functionName()
    entering(here, caller)

    println("$here: input lin '$lin'")
    
    val lexemeList = mutableListOf<Lexeme>()

    var Done = false
    var position = 0
    
    while (!Done) {
    	  try {
    	      var currentCharacter = lin.get(position)
	      val lexeme: Lexeme = when (currentCharacter){
	      '$' -> {
	      	  tokenOfCharOfPositionOfString(currentCharacter, position, lin, here)
		  }
	      'A' -> {
	      	  val str = lin.substring(position)
    	      	  val word = nextWordOfEndCharOfString(':', str, here)
	  	  position = position + word.length
    	  	  lexemeOfKeyword (word, here)
		  }
	      ' ' -> {
	      	  tokenOfCharOfPositionOfString(currentCharacter, position, lin, here)
		  position = position + 1
	      	  val str = lin.substring(position)
		  val word = nextWordOfEndCharOfString('$', str, here)
		  if (isAuthorNameOfString(word, here)) {
		     position = position + word.length 
    		     AuthorName (word)
		  }
		  else {
		       val message = "$here: Error word '$word' is not a valid Author Name"
		       throw Exception(message)
		  }
	          }
	       else -> {
		     val message = "$here: Error unknown current Character '$currentCharacter' at position $position"
		     throw Exception(message)
	          }
		}
	        position = position + 1
		lexemeList.add (lexeme)
   		}
	  catch (e: java.lang.StringIndexOutOfBoundsException) {
	  	val previousCharacter = lin.get(position-1)
	  	if (previousCharacter.equals('$')) {
	     	   var lexeme = TokenEndOfLine
		   lexemeList.add (lexeme)	
	     	   if (debug) println("$here: setting End Of Line")
	     	   Done = true			
	     	}
		else {
		     val message = "$here: Error previous character '$previousCharacter' should be '$'"
	    	     throw Exception(message)
		}
	  }
   }

   println("$here: output lexemeList "+lexemeList)
   exiting(here)
   return lexemeList
}


fun lexemeListOfDateLine (lin: String, caller: String) : MutableList<Lexeme> {
// # $Date: now$
    val here = functionName()
    entering(here, caller)

    println("$here: input lin '$lin'")
    
    val lexemeList = mutableListOf<Lexeme>()

    var Done = false
    var position = 0
    
    while (!Done) {
    	  try {
    	      var currentCharacter = lin.get(position)
	      val lexeme = when (currentCharacter) {
	      ' ' -> {
	      	  tokenOfCharOfPositionOfString(currentCharacter, position, lin, here)
		  }
	      'D' -> {
	      	  val str = lin
    	      	  val word = nextWordOfEndCharOfString(':', str, here)
	  	  position = position + word.length 
    	  	  lexemeOfKeyword (word, here)
		  }
	      'n' -> {
	      	  val str = lin.substring(position)
		  val word = nextWordOfEndCharOfString('$', str, here)
		  if (isDateOfString(word, here)) {
		     position = position + word.length
    		     DateValue (word)
		  }
		  else {
		       val message = "$here: Error word '$word' is not a valid date"
		       throw Exception(message)
		  }
	          }
	       else -> {
		     val message = "$here: Error unknown Character '$currentCharacter' at position $position"
		     throw Exception(message)
	          }
		}
		position = position + 1
		lexemeList.add (lexeme)
   		}
	  catch (e: java.lang.StringIndexOutOfBoundsException) {
	  	val previousCharacter = lin.get(position-1)
	  	if (previousCharacter.equals('$')) {
	     	   var lexeme = TokenEndOfLine
		   lexemeList.add (lexeme)
	     	   if (debug) println("$here: setting End Of Line")
	     	   Done = true			
	     	}
		else {
		     val message = "$here: Error previous character '$previousCharacter' should be '$'"
	    	     throw Exception(message)
		}
	  }
   }

   println("$here: output lexemeList "+lexemeList)
   exiting(here)
   return lexemeList
}

fun lexemeListOfMutableLine (lin: String, caller: String) : MutableList<Lexeme> {
// mutable: /.brings/system/bin/kwextract.pl$
    val here = functionName()
    entering(here, caller)

    println("$here: input lin '$lin'")
    
    val lexemeList = mutableListOf<Lexeme>()

    var Done = false
    var position = 0
    
    while (!Done) {
    	  try {
    	      var currentCharacter = lin.get(position)
	      val lexeme: Lexeme = when (currentCharacter){
	      ' ' -> {
	      	  tokenOfCharOfPositionOfString(currentCharacter, position, lin, here)
		  }
	      '$' -> {
	      	  tokenOfCharOfPositionOfString(currentCharacter, position, lin, here)
		  }
	      'm' -> {
	      	  val str = lin.substring(position)
    	      	  val word = nextWordOfEndCharOfString(':', str, here)
	  	  position = position + word.length
    	  	  lexemeOfKeyword (word, here)
		  }
	      '/' -> {
	      	  val str = lin.substring(position)
		  val word = nextWordOfEndCharOfString('$', str, here)
		  if (isFilePathOfString(word, here)) {
		     position = position + word.length 
    		     FilePath (word)
		  }
		  else {
		       val message = "$here: Error word '$word' is not a valid File Path"
		       throw Exception(message)
		  }
	          }
	       else -> {
		     val message = "$here: Error unknown current Character '$currentCharacter' at position $position"
		     throw Exception(message)
	          }
		}
	        position = position + 1
		lexemeList.add (lexeme)
   		}
	  catch (e: java.lang.StringIndexOutOfBoundsException) {
	  	val previousCharacter = lin.get(position-1)
	  	if (previousCharacter.equals('$')) {
	     	   var lexeme = TokenEndOfLine
		   lexemeList.add (lexeme)
	     	   if (debug) println("$here: setting End Of Line")
	     	   Done = true			
	     	}
		else {
		     val message = "$here: Error previous character '$previousCharacter' should be '$'"
	    	     throw Exception(message)
		}
	  }
   }

   println("$here: output lexemeList "+lexemeList)
   exiting(here)
   return lexemeList
}

fun lexemeListOfNextLine (lin: String, caller: String) : MutableList<Lexeme> {
// # $next: unknown$'
    val here = functionName()
    entering(here, caller)

    println("$here: input lin '$lin'")
    
    val lexemeList = mutableListOf<Lexeme>()

    var Done = false
    var position = 0
    
    while (!Done) {
    	  try {
    	      var currentCharacter = lin.get(position)
	      val lexeme: Lexeme = when (currentCharacter){
	      '$' -> {
	      	  tokenOfCharOfPositionOfString(currentCharacter, position, lin, here)
		  }
	      'n' -> {
	      	  val str = lin.substring(position)
    	      	  val word = nextWordOfEndCharOfString(':', str, here)
	  	  position = position + word.length
    	  	  lexemeOfKeyword (word, here)
		  }
	      ' ' -> {
	      	  tokenOfCharOfPositionOfString(currentCharacter, position, lin, here)
		  position = position + 1
	      	  val str = lin.substring(position)
		  val word = nextWordOfEndCharOfString('$', str, here)
		  if (isNextNameOfString(word, here)) {
		     position = position + word.length 
    		     NextName (word)
		  }
		  else {
		       val message = "$here: Error word '$word' is not a valid Next Name"
		       throw Exception(message)
		  }
	          }
	       else -> {
		     val message = "$here: Error unknown current Character '$currentCharacter' at position $position"
		     throw Exception(message)
	          }
		}
	        position = position + 1
		lexemeList.add (lexeme)
   		}
	  catch (e: java.lang.StringIndexOutOfBoundsException) {
	  	val previousCharacter = lin.get(position-1)
	  	if (previousCharacter.equals('$')) {
	     	   var lexeme = TokenEndOfLine
		   lexemeList.add (lexeme)		
	     	   if (debug) println("$here: setting End Of Line")
	     	   Done = true			
	     	}
		else {
		     val message = "$here: Error previous character '$previousCharacter' should be '$'"
	    	     throw Exception(message)
		}
	  }
   }

   println("$here: output lexemeList "+lexemeList)
   exiting(here)
   return lexemeList
}

fun lexemeListOfParentsLine (lin: String, caller: String) : MutableList<Lexeme> {
// parents: QmU1RDLsAGNPVuwDjKD3RQx7R6aEuQfcmSiubviDZ2XRVC$
    val here = functionName()
    entering(here, caller)

    println("$here: input lin '$lin'")
    
    val lexemeList = mutableListOf<Lexeme>()

    var Done = false
    var position = 0
    
    while (!Done) {
    	  try {
    	      var currentCharacter = lin.get(position)
	      val lexeme: Lexeme = when (currentCharacter){
	      ' ' -> {
	      	  tokenOfCharOfPositionOfString(currentCharacter, position, lin, here)
		  }
	      '$' -> {
	      	  tokenOfCharOfPositionOfString(currentCharacter, position, lin, here)
		  }
	      'p' -> {
	      	  val str = lin.substring(position)
    	      	  val word = nextWordOfEndCharOfString(':', str, here)
	  	  position = position + word.length
    	  	  lexemeOfKeyword (word, here)
		  }
	      'Q' -> {
	      	  val str = lin.substring(position)
		  val word = nextWordOfEndCharOfString('$', str, here)
		  if (isQmHashOfString(word, here)) {
		     position = position + word.length 
    		     QmHash (word)
		  }
		  else {
		       val message = "$here: Error word '$word' is not a valid Qm Hash"
		       throw Exception(message)
		  }
	          }
	       else -> {
		     val message = "$here: Error unknown current Character '$currentCharacter' at position $position"
		     throw Exception(message)
	          }
		}
	        position = position + 1
		lexemeList.add (lexeme)
   		}
	  catch (e: java.lang.StringIndexOutOfBoundsException) {
	  	val previousCharacter = lin.get(position-1)
	  	if (previousCharacter.equals('$')) {
	     	   var lexeme = TokenEndOfLine
		   lexemeList.add (lexeme)
	     	   if (debug) println("$here: setting End Of Line")
	     	   Done = true			
	     	}
		else {
		     val message = "$here: Error previous character '$previousCharacter' should be '$'"
	    	     throw Exception(message)
		}
	  }
   }

   println("$here: output lexemeList "+lexemeList)
   exiting(here)
   return lexemeList
}

fun lexemeListOfPreviousLine (lin: String, caller: String) : MutableList<Lexeme> {
// previous: QmU1RDLsAGNPVuwDjKD3RQx7R6aEuQfcmSiubviDZ2XRVC$
    val here = functionName()
    entering(here, caller)

    println("$here: input lin '$lin'")
    
    val lexemeList = mutableListOf<Lexeme>()

    var Done = false
    var position = 0
    
    while (!Done) {
    	  try {
    	      var currentCharacter = lin.get(position)
	      val lexeme: Lexeme = when (currentCharacter){
	      ' ' -> {
	      	  tokenOfCharOfPositionOfString(currentCharacter, position, lin, here)
		  }
	      '$' -> {
	      	  tokenOfCharOfPositionOfString(currentCharacter, position, lin, here)
		  }
	      'p' -> {
	      	  val str = lin.substring(position)
    	      	  val word = nextWordOfEndCharOfString(':', str, here)
	  	  position = position + word.length
    	  	  lexemeOfKeyword (word, here)
		  }
	      'Q' -> {
	      	  val str = lin.substring(position)
		  val word = nextWordOfEndCharOfString('$', str, here)
		  if (isQmHashOfString(word, here)) {
		     position = position + word.length 
    		     QmHash (word)
		  }
		  else {
		       val message = "$here: Error word '$word' is not a valid Qm Hash"
		       throw Exception(message)
		  }
	          }
	       else -> {
		     val message = "$here: Error unknown current Character '$currentCharacter' at position $position"
		     throw Exception(message)
	          }
		}
	        position = position + 1
		lexemeList.add (lexeme)
   		}
	  catch (e: java.lang.StringIndexOutOfBoundsException) {
	  	val previousCharacter = lin.get(position-1)
	  	if (previousCharacter.equals('$')) {
	     	   var lexeme = TokenEndOfLine
		   lexemeList.add (lexeme)
	     	   if (debug) println("$here: setting End Of Line")
	     	   Done = true			
	     	}
		else {
		     val message = "$here: Error previous character '$previousCharacter' should be '$'"
	    	     throw Exception(message)
		}
	  }
   }

   println("$here: output lexemeList "+lexemeList)
   exiting(here)
   return lexemeList
}

fun lexemeListOfQmHashLine (lin: String, caller: String) : MutableList<Lexeme> {
// qm: z2U1RDLsAGNPVuwDjKD3RQx7R6aEuQfcmSiubviDZ2XRVC$
    val here = functionName()
    entering(here, caller)

    println("$here: input lin '$lin'")
    
    val lexemeList = mutableListOf<Lexeme>()

    var Done = false
    var position = 0
    
    while (!Done) {
    	  try {
    	      var currentCharacter = lin.get(position)
	      val lexeme: Lexeme = when (currentCharacter){
	      ' ' -> {
	      	  tokenOfCharOfPositionOfString(currentCharacter, position, lin, here)
		  }
	      '$' -> {
	      	  tokenOfCharOfPositionOfString(currentCharacter, position, lin, here)
		  }
	      'q' -> {
	      	  val str = lin.substring(position)
    	      	  val word = nextWordOfEndCharOfString(':', str, here)
	  	  position = position + word.length
    	  	  lexemeOfKeyword (word, here)
		  }
	      'z' -> {
	      	  val str = lin.substring(position)
		  val word = nextWordOfEndCharOfString('$', str, here)
		  if (isZ2HashOfString(word, here)) {
		     position = position + word.length 
    		     Z2Hash (word)
		  }
		  else {
		       val message = "$here: Error word '$word' is not a valid z2 Hash"
		       throw Exception(message)
		  }
	          }
	       else -> {
		     val message = "$here: Error unknown current Character '$currentCharacter' at position $position"
		     throw Exception(message)
	          }
		}
	        position = position + 1
		lexemeList.add (lexeme)
   		}
	  catch (e: java.lang.StringIndexOutOfBoundsException) {
	  	val previousCharacter = lin.get(position-1)
	  	if (previousCharacter.equals('$')) {
	     	   var lexeme = TokenEndOfLine
             	   lexemeList.add (lexeme)
	     	   if (debug) println("$here: setting End Of Line")
	     	   Done = true			
	     	}
		else {
		     val message = "$here: Error previous character '$previousCharacter' should be '$'"
	    	     throw Exception(message)
		}
	  }
   }

   println("$here: output lexemeList "+lexemeList)
   exiting(here)
   return lexemeList
}

fun lexemeListOfSourceLine (lin: String, caller: String) : MutableList<Lexeme> {
// Source: /my/perl/script/kwextract.pl,v$
    val here = functionName()
    entering(here, caller)

    println("$here: input lin '$lin'")
    
    val lexemeList = mutableListOf<Lexeme>()

    var Done = false
    var position = 0
    
    while (!Done) {
    	  try {
    	      var currentCharacter = lin.get(position)
	      val lexeme: Lexeme = when (currentCharacter){
	      ' ' -> {
	      	  tokenOfCharOfPositionOfString(currentCharacter, position, lin, here)
		  }
	      'v' -> {
	      	  tokenOfCharOfPositionOfString(currentCharacter, position, lin, here)
		  }
	      '$' -> {
	      	  tokenOfCharOfPositionOfString(currentCharacter, position, lin, here)
		  }
	      'S' -> {
	      	  val str = lin.substring(position)
    	      	  val word = nextWordOfEndCharOfString(':', str, here)
	  	  position = position + word.length
    	  	  lexemeOfKeyword (word, here)
		  }
	      '/' -> {
	      	  val str = lin.substring(position)
		  val word = nextWordOfEndCharOfString(',', str, here)
		  if (isFilePathOfString(word, here)) {
		     position = position + word.length 
    		     FilePath (word)
		  }
		  else {
		       val message = "$here: Error word '$word' is not a valid File Path"
		       throw Exception(message)
		  }
	          }
	       else -> {
		     val message = "$here: Error unknown current Character '$currentCharacter' at position $position"
		     throw Exception(message)
	          }
		}
	        position = position + 1
		lexemeList.add (lexeme)
   		}
	  catch (e: java.lang.StringIndexOutOfBoundsException) {
	  	val previousCharacter = lin.get(position-1)
	  	if (previousCharacter.equals('$')) {
	     	   var lexeme = TokenEndOfLine
             	   lexemeList.add (lexeme)
	     	   if (debug) println("$here: setting End Of Line")
	     	   Done = true			
	     	}
		else {
		     val message = "$here: Error previous character '$previousCharacter' should be '$'"
	    	     throw Exception(message)
		}
	  }
   }

   println("$here: output lexemeList "+lexemeList)
   exiting(here)
   return lexemeList
}

fun lexemeListOfSignatureLine (lin: String, caller: String) : MutableList<Lexeme> {
// # $spot: 1579373044$'
    val here = functionName()
    entering(here, caller)

    println("$here: input lin '$lin'")
    
    val lexemeList = mutableListOf<Lexeme>()

    var Done = false
    var position = 0
    
    while (!Done) {
    	  try {
    	      var currentCharacter = lin.get(position)
	      val lexeme: Lexeme = when (currentCharacter){
	      '$' -> {
	      	  tokenOfCharOfPositionOfString(currentCharacter, position, lin, here)
		  }
	      'S' -> {
	      	  val str = lin.substring(position)
    	      	  val word = nextWordOfEndCharOfString(':', str, here)
	  	  position = position + word.length
    	  	  lexemeOfKeyword (word, here)
		  }
	      ' ' -> {
	      	  tokenOfCharOfPositionOfString(currentCharacter, position, lin, here)
		  position = position + 1
	      	  val str = lin.substring(position)
		  val word = nextWordOfEndCharOfString('$', str, here)
		  if (isSignatureOfString(word, here)) {
		     position = position + word.length 
    		     Signature (word)
		  }
		  else {
		       val message = "$here: Error word '$word' is not a valid Spot field"
		       throw Exception(message)
		  }
	          }
	       else -> {
		     val message = "$here: Error unknown current Character '$currentCharacter' at position $position"
		     throw Exception(message)
	          }
		}
	        position = position + 1
		lexemeList.add (lexeme)
   		}
	  catch (e: java.lang.StringIndexOutOfBoundsException) {
	  	val previousCharacter = lin.get(position-1)
	  	if (previousCharacter.equals('$')) {
	     	   var lexeme = TokenEndOfLine
             	   lexemeList.add (lexeme)
	     	   if (debug) println("$here: setting End Of Line")
	     	   Done = true			
	     	}
		else {
		     val message = "$here: Error previous character '$previousCharacter' should be '$'"
	    	     throw Exception(message)
		}
	  }
   }

   println("$here: output lexemeList "+lexemeList)
   exiting(here)
   return lexemeList
}

fun lexemeListOfSpotLine (lin: String, caller: String) : MutableList<Lexeme> {
// # $spot: 1579373044$'
    val here = functionName()
    entering(here, caller)

    println("$here: input lin '$lin'")
    
    val lexemeList = mutableListOf<Lexeme>()

    var Done = false
    var position = 0
    
    while (!Done) {
    	  try {
    	      var currentCharacter = lin.get(position)
	      val lexeme: Lexeme = when (currentCharacter){
	      '$' -> {
	      	  tokenOfCharOfPositionOfString(currentCharacter, position, lin, here)
		  }
	      's' -> {
	      	  val str = lin.substring(position)
    	      	  val word = nextWordOfEndCharOfString(':', str, here)
	  	  position = position + word.length
    	  	  lexemeOfKeyword (word, here)
		  }
	      ' ' -> {
	      	  tokenOfCharOfPositionOfString(currentCharacter, position, lin, here)
		  position = position + 1
	      	  val str = lin.substring(position)
		  val word = nextWordOfEndCharOfString('$', str, here)
		  if (isSpotOfString(word, here)) {
		     position = position + word.length 
    		     Spot (word)
		     }
		  else {
		       val message = "$here: Error word '$word' is not a valid Spot field"
		       throw Exception(message)
		       }
	           }    
	       else -> {
		     val message = "$here: Error unknown current Character '$currentCharacter' at position $position"
		     throw Exception(message)
	          }
		}
	        position = position + 1
		lexemeList.add (lexeme)
   		}
	  catch (e: java.lang.StringIndexOutOfBoundsException) {
	  	val previousCharacter = lin.get(position-1)
	  	if (previousCharacter.equals('$')) {
	     	   var lexeme = TokenEndOfLine
             	   lexemeList.add (lexeme)
	     	   if (debug) println("$here: setting End Of Line")
	     	   Done = true			
	     	}
		else {
		     val message = "$here: Error at end of line previous character '$previousCharacter' should be '$'"
	    	     throw Exception(message)
		}
	  }
   }

   println("$here: output lexemeList "+lexemeList)
   exiting(here)
   return lexemeList
}

fun lexemeListOfTicLine (lin: String, caller: String) : MutableList<Lexeme> {
// # $tic: 1579373044$'
    val here = functionName()
    entering(here, caller)

    println("$here: input lin '$lin'")
    
    val lexemeList = mutableListOf<Lexeme>()

    var Done = false
    var position = 0
    
    while (!Done) {
    	  try {
    	      var currentCharacter = lin.get(position)
	      val lexeme: Lexeme = when (currentCharacter){
	      '$' -> {
	      	  tokenOfCharOfPositionOfString(currentCharacter, position, lin, here)
		  }
	      't' -> {
	      	  val str = lin.substring(position)
    	      	  val word = nextWordOfEndCharOfString(':', str, here)
	  	  position = position + word.length
    	  	  lexemeOfKeyword (word, here)
		  }
	      ' ' -> {
	      	  tokenOfCharOfPositionOfString(currentCharacter, position, lin, here)
		  position = position + 1
	      	  val str = lin.substring(position)
		  val word = nextWordOfEndCharOfString('$', str, here)
		  if (isTicOfString(word, here)) {
		     position = position + word.length 
    		     Tic (word)
		  }
		  else {
		       val message = "$here: Error word '$word' is not a valid Tic field"
		       throw Exception(message)
		  }
	          }
	       else -> {
		     val message = "$here: Error unknown current Character '$currentCharacter' at position $position"
		     throw Exception(message)
	          }
		}
	        position = position + 1
		lexemeList.add (lexeme)
   		}
	  catch (e: java.lang.StringIndexOutOfBoundsException) {
	  	val previousCharacter = lin.get(position-1)
	  	if (previousCharacter.equals('$')) {
	     	   var lexeme = TokenEndOfLine
             	   lexemeList.add (lexeme)
	     	   if (debug) println("$here: setting End Of Line")
	     	   Done = true			
	     	}
		else {
		     val message = "$here: Error previous character '$previousCharacter' should be '$'"
	    	     throw Exception(message)
		}
	  }
   }

   println("$here: output lexemeList "+lexemeList)
   exiting(here)
   return lexemeList
}

fun lexemeListOfYmlFileByLine (ymlFileName: String, caller: String): List<Lexeme> {
    val here = functionName()
    entering(here, caller)

    var lexemeList = mutableListOf<Lexeme>()
    println("$here: input ymlFileName '$ymlFileName'")
    val lineList = lineListOfFileName (ymlFileName, here)

    var count = 0
    for (line in lineList) {
      count = count + 1
      if (debug) println("$here: for line # $count '$line'")
      if (line.startsWith('#'))  {
      	 val lexeme_l = lexemeListOfSharpedLine (line, here)
	 lexemeList.addAll (lexeme_l)
	 }		
      else {
	 val lexeme_l = lexemeListOfTextRecord (line, here)
	 lexemeList.addAll (lexeme_l)
	 }
    }
    
    if (debug) println("$here: output lexemeList $lexemeList")
    exiting(here)

    return lexemeList
}

fun keywordAndStringOfSharpedLine (lin: String, caller: String) : pairString {
// # $Source: /my/perl/script/kwextract.pl,v$
    val here = functionName()
    entering(here, caller)

   println("$here: input lin '$lin'")
   var wordStack = wordStackOfLine (lin)

   var currentWord = ""
   var nextWord = ""

   try {
      currentWord = wordStack.pop()
      nextWord = wordStack.pop()
      }
      catch (e: java.util.EmptyStackException) {
      }

    println("$here: currentWord: '" + currentWord + "'")
    println("$here: nextWord: '" + nextWord + "'")

  exiting(here)
  return pairString (currentWord, nextWord)
}

fun lexemeListOfSharpedLine (lin: String, caller: String) : MutableList<Lexeme> {
// Source: /my/perl/script/kwextract.pl,v$
    val here = functionName()
    entering(here, caller)

    println("$here: input lin '$lin'")
    
    val lexemeList = mutableListOf<Lexeme>()

    var Done = false
    var position = 0
    
    while (!Done) {
      try {
    	var currentCharacter = lin.get(position)	
 	when (currentCharacter) {
	'#' -> {
	    if (lin.length > 1) {
	       val lexeme = tokenOfCharOfPositionOfString(currentCharacter, position, lin, here)
	       lexemeList.add(lexeme)
	       position = position + 1
	    }
	    else {
	       Done = true
	       TokenEmptySharpedLine
	    }
	}
	' ' -> {
	    val lexeme = tokenOfCharOfPositionOfString(currentCharacter, position, lin, here)
	    lexemeList.add(lexeme)
	    position = position + 1	 
	}
	'$' -> {
	    val lexeme = tokenOfCharOfPositionOfString(currentCharacter, position, lin, here)
	    lexemeList.add(lexeme)
	    position = position + 1
	}
// Remainder	
	'A' -> {
	    val str = lin.substring(position)
	    lexemeList.addAll (lexemeListOfAuthorLine (str, here))
	    Done = true
	    }
	'D' -> {
	    val str = lin.substring(position)
	    lexemeList.addAll (lexemeListOfDateLine (str, here))
	    Done = true
	    }
        'm' -> {
	    val str = lin.substring(position)
	    lexemeList.addAll (lexemeListOfMutableLine (str, here)) 
	    Done = true
	    }
        'n' -> {
	    val str = lin.substring(position)
	    lexemeList.addAll (lexemeListOfNextLine (str, here)) 
	    Done = true
	    }
	'p' -> {
	    val nextCharacter = lin.get(position + 1)
	    if (nextCharacter.equals ('a')){
	       val str = lin.substring(position)
	       lexemeList.addAll (lexemeListOfParentsLine (str, here)) 
	    }
	    else if (nextCharacter.equals ('r')){
	       val str = lin.substring(position)
	       lexemeList.addAll (lexemeListOfPreviousLine (str, here)) 
	    }
	    else {
	    	 val message = "$here: Error next character '$nextCharacter' should be 'a' or 'r'"
	    	 throw Exception(message)
	    }
	    Done = true  
	    }
        'q' -> {
	    val str = lin.substring(position)
	    lexemeList.addAll (lexemeListOfQmHashLine (str, here)) 
	    Done = true
	    }
        's' -> {
	    val str = lin.substring(position)
	    lexemeList.addAll (lexemeListOfSpotLine (str, here)) 
	    Done = true
	    }
        'S' -> {
	    val nextCharacter = lin.get(position + 1)
	    if (nextCharacter.equals ('i')){
	       val str = lin.substring(position)
	       lexemeList.addAll (lexemeListOfSignatureLine (str, here)) 
	    }
	    else if (nextCharacter.equals ('o')){
	       val str = lin.substring(position)
	       lexemeList.addAll (lexemeListOfSourceLine (str, here)) 
	    }
	    else {
	    	 val message = "$here: Error next character '$nextCharacter' should be 'i' or 'o'"
	    	 throw Exception(message)
	    }
	    Done = true  
	    }
        't' -> {
	    val str = lin.substring(position)
	    lexemeList.addAll (lexemeListOfTicLine (str, here)) 
	    Done = true
	    }
	    else -> {
	    	 val message = "$here: Error unexpected current character '$currentCharacter' at position $position"
	    	 throw Exception(message)
	    }
	    }
      }
      catch (e: java.lang.StringIndexOutOfBoundsException) {
	  	val previousCharacter = lin.get(position-1)
	  	if (previousCharacter.equals('$')) {
	     	   var lexeme = TokenEndOfLine
             	   lexemeList.add (lexeme)
	     	   if (debug) println("$here: setting End Of Line")
	     	   Done = true			
	     	}
		else {
		     val message = "$here: Error previous character '$previousCharacter' (position $position) should be '$'"
	    throw Exception(message)
		}
	  }
   }

   println("$here: output lexemeList: "+ fullnameListOfLexemeList(lexemeList))
   exiting(here)
   return lexemeList
}

fun lexemeListOfTextRecord (rec: String, caller: String) : MutableList<Lexeme> {
    val here = functionName()
    entering(here, caller)

    println("$here: input rec '$rec'")
    var stack = characterStackOfString (rec)

    var lexemeList = mutableListOf<Lexeme>()
    
    while (! stack.isEmpty()) {
      var cha = stack.pop()
      println("$here: for cha '$cha'")

      when (cha){
      	' ' -> {
	  var lexeme = TokenSpace
	  lexemeList.add (lexeme)
	  println("$here: cha '$cha' lexeme '$lexeme'")
	  }
	'$' -> {
	    lexemeList.add (TokenDollar)
	    
// $variable$ to be substituted
		  var (w, s) = nextWordAndStackOfEndCharOfCharacterStack('$', stack, here)
		  stack = s
		  var word = w
		  cha = stack.pop()
		  if (! cha.equals('$')) {
		     fatalErrorPrint ("current character were a '$'",
  		                       cha.toString(),
				      "Check that current record '$rec' is actual Text",
				       here)
		  }

		  println("$here: for after word '$word'")
		  if (isTextVariableOfString(word, here)) {
		     val lexeme = TextVariableSubstituable(word)
		     lexemeList.add (lexeme)
		     lexemeList.add (TokenDollar)	
		  }
		  else {
		     fatalErrorPrint ("word below followed Regexp \"\"\"\\w+\"\"\"",
		                      word,
				      "Check the Regular Expression",
				      here)
		  }
		  }
	  else -> {
	       stack.push(cha)
	       var (w, s) = nextWordAndStackOfEndCharOfCharacterStack(' ', stack, here)
		  stack = s
		  var word = w
		  println("$here: for else word '$word'")
		  
		  val lexeme = TextWordConstant(word)

		  println("$here: for lexeme '$lexeme'")
		  lexemeList.add (lexeme)
	          }
        }
	println("$here: end for lexemeList '$lexemeList'")
   } 

   lexemeList.add (TokenEndOfLine)
   println("$here: output lexemeList "+lexemeList)
   exiting(here)
   return lexemeList
}

