package my.lexer

import java.io.File
import java.util.Stack
import java.lang.Character.MIN_VALUE as nullChar

import my.library.*
import my.lexeme.*

// kotlinc MyLibrary.kt -include-runtime -d MyLibrary.jar 
// kotlinc -classpath MyLibrary.jar Lexer.kt -include-runtime -d Lexer.jar

fun lexemeListOfYmlFile (ymlFileName: String, caller: String): List<Lexeme> {
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

fun lexemeListOfFileName(fil_nam: String, caller: String) : List<Lexeme> {
    val here = functionName()
    entering(here, caller)

    val ext = fileExtensionOfFileName(fil_nam)
    val lex_l =
      when (ext) {
      "yml" -> lexemeListOfYmlFile (fil_nam, here)
     else -> {
       val message = "$here: file extension '$ext' should be 'yml'"
       throw Exception(message)
       }
     }
    if (debug) println("$here: output lexeme List '$lex_l'")
    exiting(here)
    return lex_l
}

fun fullnameListOfLexemeList (lex_l: List<Lexeme>) : List<String> {
  val str_l = lex_l.map({l -> fullnameOfLexeme (l) })
  return str_l 
}

fun hasKeywordPreviousOfLexemeList (met_l: List<Lexeme>, caller: String): Boolean {
    val here = functionName()
    entering(here, caller)

    val lex = KeywordWithQmHash("previous")
    val result = met_l.contains(lex)

    exiting(here)
    return result
}

fun isAuthorNameOfString(str: String, caller: String): Boolean {
    val here = functionName()
    entering(here, caller)

    println("$here: input str '$str'")

    val pattern = Regex("[a-zA-Z][a-zA-Z_ ]*")
    val result = pattern.matches(str)

    exiting(here + " with result '$result'")
    return result
}

fun isDateOfString(str: String, caller: String): Boolean {
    val here = functionName()
    entering(here, caller)
    
    println("$here: input str '$str'")
    val pattern_us = Regex("""\d\d\/\d\d\/\d\d\d\d""")
    val result_us = pattern_us.matches(str)
    val pattern_eu = Regex("""\d\d-\d\d-\d\d\d\d""")
    val result_eu = pattern_eu.matches(str)
    val result = result_us || result_eu
    
    exiting(here + " with result '$result'")
    return result
}

fun isFilePathOfString(str: String, caller: String): Boolean {
    val here = functionName()
    entering(here, caller)
    
    println("$here: input str '$str'")
    val pattern = Regex("""^(/(\.)?\w[a-zA-Z_0-9]*)(/([a-zA-Z_0-9]+))*\.\w+$""")
    val result = pattern.matches(str)

    exiting(here + " with result '$result'")
    return result
}

fun isKeywordOfString(str: String, caller: String): Boolean {
    val here = functionName()
    entering(here, caller)

    println("$here: input str '$str'")
    val result = str.startsWith('$') && str.endsWith(':')

    exiting(here + " with result '$result'")
    return result
}

fun isKeywordNameOfString(str: String, caller: String): Boolean {
    val here = functionName()
    entering(here, caller)

    println("$here: input str '$str'")
    val result = str.endsWith('$')

    exiting(here + " with result '$result'")
    return result
}

fun isKeywordWithQmHashOfLexeme(lex: Lexeme, caller: String): Boolean {
    val here = functionName()
    entering(here, caller)

    println("$here: input lex '$lex'")
    val result = lex is KeywordWithQmHash
    exiting(here + " with result '$result'")
    return result
}

fun isNextNameOfString(str: String, caller: String): Boolean {
    val here = functionName()
    entering(here, caller)
    
    println("$here: input str '$str'")
    val pattern = Regex("""\w[a-zA-Z_0-9]*""")
    val result = pattern.matches(str)

    exiting(here + " with result '$result'")
    return result
}

fun isQmHashOfString(str: String, caller: String): Boolean {
    val here = functionName()
    entering(here, caller)
    
    val pattern = Regex("^Qm([a-zA-Z0-9]+)")
    println("$here: input str '$str'")
    val result = pattern.matches(str)

    exiting(here + " with result '$result'")
    return result
}

fun isSignatureOfString(str: String, caller: String): Boolean {
    val here = functionName()
    entering(here, caller)
    
    val pattern = Regex("(.*)")
    println("$here: input str '$str'")
    val result = pattern.matches(str)

    exiting(here + " with result '$result'")
    return result
}

fun isSpotOfString(str: String, caller: String): Boolean {
    val here = functionName()
    entering(here, caller)
    
    println("$here: input str '$str'")

    val pattern = Regex("""\d+""")
    val result = pattern.matches(str)

    exiting(here + " with result '$result'")
    return result
}

fun isTextVariableOfString(str: String, caller: String): Boolean {
    val here = functionName()
    entering(here, caller)
    
    val pattern = Regex("""\w+""")
    println("$here: input str '$str'")
    val result = pattern.matches(str)

    exiting(here + " with result '$result'")
    return result
}

fun isTextWordOfString(str: String, caller: String): Boolean {
    val here = functionName()
    entering(here, caller)
    
    val pattern = Regex("""\w+""")
    println("$here: input str '$str'")
    val result = pattern.matches(str)

    exiting(here + " with result '$result'")
    return result
}

fun isTicOfString(str: String, caller: String): Boolean {
    val here = functionName()
    entering(here, caller)
    
    println("$here: input str '$str'")

    val pattern = Regex("""\d+""")
    val result = pattern.matches(str)

    exiting(here + " with result '$result'")
    return result
}

fun isZ2HashOfString(str: String, caller: String): Boolean {
    val here = functionName()
    entering(here, caller)
    
    val pattern = Regex("^z2([a-zA-Z0-9]+)")
    println("$here: input str '$str'")
    val result = pattern.matches(str)

    exiting(here + " with result '$result'")
    return result
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

fun keywordNameOfString (str: String, caller: String): String {
    val here = functionName()
    entering(here, caller)

   println("$here: str '$str'")

    assert (isKeywordNameOfString(str, here))
    var result = str.trimEnd({ c -> c.equals('$')})

    exiting(here + " with result "+result)
    return result
}

fun keywordOfString(str: String, caller: String): String {
    val here = functionName()
    entering(here, caller)

   println("$here: input str '$str'")

    assert (isKeywordOfString(str, here))
    val result = (str.substring(1)).trimEnd({c -> c.equals(':')})

    exiting(here + " with result '$result'")
    return result
}

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
	      	  tokenOfChar(currentCharacter, position, lin, here)
		  }
	      'A' -> {
	      	  val str = lin.substring(position)
    	      	  val word = nextWordOfEndCharOfString(':', str, here)
	  	  position = position + word.length
    	  	  lexemeOfKeyword (word, here)
		  }
	      ' ' -> {
	      	  tokenOfChar(currentCharacter, position, lin, here)
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
    	      var cha = lin.get(position)
	      val lexeme = when (cha) {
	      ' ' -> {
	      	  tokenOfChar(cha, position, lin, here)
		  }
	      'D' -> {
	      	  val str = lin
    	      	  val word = nextWordOfEndCharOfString(':', str, here)
	  	  position = position + word.length 
    	  	  lexemeOfKeyword (word, here)
		  }
	      '0', '1', '2','3','4','5','6','7','8','9' -> {
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
		    val message = "$here: Error unknown Character '$cha' at position $position of line '$lin'"
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

fun lexemeListOfMembersKeyword (lin: String, caller: String) : MutableList<Lexeme> {
// $members: michelc@lausanne , emilea$ 
    val here = functionName()
    entering(here, caller)

    println("$here: input lin '$lin'")
    
    val lexemeList = mutableListOf<Lexeme>()

    var Done = false
    var position = 0
    
    while (!Done) {
    	  try {
    	      var cha = lin.get(position)
	      val lexeme: Lexeme = when (cha){
	      ' ' -> {
	      	  tokenOfChar(cha, position, lin, here)
		  }
	      '$' -> {
	      	  tokenOfChar(cha, position, lin, here)
		  }
	      ',' -> {
	      	  tokenOfChar(cha, position, lin, here)
		  }
	      'm' -> {
	      	  val str = lin.substring(position)
    	      	  val word = nextWordOfEndCharOfString(':', str, here)
		  if (word == "members") {
		     Done = true
		  }
		  else {
		     val message = "$here: Error expecting word '$cha' were 'members'"
		     throw Exception(message)
		  }
	  	  position = position + word.length
    	  	  lexemeOfKeyword (word, here)
		  }		  
	       else -> {
		     val message = "$here: Error unknown current Character '$cha' at position $position of line '$lin'"
		     throw Exception(message)
	          }
		}
	        position = position + 1
		lexemeList.add (lexeme)
   		}
	  catch (e: java.lang.StringIndexOutOfBoundsException) {
	    val message = "$here: Error unexpected End Of Line at position $position of line '$lin'"
	    throw Exception(message)
	  
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
    	      var cha = lin.get(position)
	      val lexeme: Lexeme = when (cha){
	      ' ' -> {
	      	  tokenOfChar(cha, position, lin, here)
		  }
	      '$' -> {
	      	  tokenOfChar(cha, position, lin, here)
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
		     val message = "$here: Error unknown current Character '$cha' at position $position"
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
    	      var cha = lin.get(position)
	      val lexeme: Lexeme = when (cha){
	      '$' -> {
	      	  tokenOfChar(cha, position, lin, here)
		  }
	      'n' -> {
	      	  val str = lin.substring(position)
    	      	  val word = nextWordOfEndCharOfString(':', str, here)
	  	  position = position + word.length
    	  	  lexemeOfKeyword (word, here)
		  }
	      ' ' -> {
	      	  tokenOfChar(cha, position, lin, here)
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
		     val message = "$here: Error unknown current Character '$cha' at position $position"
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
    	      var cha = lin.get(position)
	      val lexeme: Lexeme = when (cha){
	      ' ' -> {
	      	  tokenOfChar(cha, position, lin, here)
		  }
	      '$' -> {
	      	  tokenOfChar(cha, position, lin, here)
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
		     val message = "$here: Error unknown current Character '$cha' at position $position"
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
    	      var cha = lin.get(position)
	      val lexeme: Lexeme = when (cha){
	      ' ' -> {
	      	  tokenOfChar(cha, position, lin, here)
		  }
	      '$' -> {
	      	  tokenOfChar(cha, position, lin, here)
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
		     val message = "$here: Error unknown current Character '$cha' at position $position"
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
    	      var cha = lin.get(position)
	      val lexeme: Lexeme = when (cha){
	      ' ' -> {
	      	  tokenOfChar(cha, position, lin, here)
		  }
	      '$' -> {
	      	  tokenOfChar(cha, position, lin, here)
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
		     val message = "$here: Error unknown current Character '$cha' at position $position"
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
    	      var cha = lin.get(position)
	      val lexeme: Lexeme = when (cha){
	      ' ' -> {
	      	  tokenOfChar(cha, position, lin, here)
		  }
	      'v' -> {
	      	  tokenOfChar(cha, position, lin, here)
		  }
	      '$' -> {
	      	  tokenOfChar(cha, position, lin, here)
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
		     val message = "$here: Error unknown current Character '$cha' at position $position"
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
    	      var cha = lin.get(position)
	      val lexeme: Lexeme = when (cha){
	      '$' -> {
	      	  tokenOfChar(cha, position, lin, here)
		  }
	      'S' -> {
	      	  val str = lin.substring(position)
    	      	  val word = nextWordOfEndCharOfString(':', str, here)
	  	  position = position + word.length
    	  	  lexemeOfKeyword (word, here)
		  }
	      ' ' -> {
	      	  tokenOfChar(cha, position, lin, here)
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
		     val message = "$here: Error unknown current Character '$cha' at position $position"
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
    	      var cha = lin.get(position)
	      val lexeme: Lexeme = when (cha){
	      '$' -> {
	      	  tokenOfChar(cha, position, lin, here)
		  }
	      's' -> {
	      	  val str = lin.substring(position)
    	      	  val word = nextWordOfEndCharOfString(':', str, here)
	  	  position = position + word.length
    	  	  lexemeOfKeyword (word, here)
		  }
	      ' ' -> {
	      	  tokenOfChar(cha, position, lin, here)
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
		     val message = "$here: Error unknown current Character '$cha' at position $position"
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
    	      var cha = lin.get(position)
	      val lexeme: Lexeme = when (cha){
	      '$' -> {
	      	  tokenOfChar(cha, position, lin, here)
		  }
	      't' -> {
	      	  val str = lin.substring(position)
    	      	  val word = nextWordOfEndCharOfString(':', str, here)
	  	  position = position + word.length
    	  	  lexemeOfKeyword (word, here)
		  }
	      ' ' -> {
	      	  tokenOfChar(cha, position, lin, here)
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
		     val message = "$here: Error unknown current Character '$cha' at position $position"
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

fun nameKeywordWithOfLexeme(lex: Lexeme, caller: String): String {
    val here = functionName()
    entering(here, caller)

    println("$here: input lex '$lex'")
    val result = when (lex) {
    	is KeywordWithZ2Hash -> lex.name 
	is KeywordWithDate -> lex.name     
    	is KeywordWithFile -> lex.name 
    	is KeywordWithInteger -> lex.name 
    	is KeywordWithQmHash -> lex.name  
    	is KeywordWithString -> lex.name   
        is KeywordWithPersonName -> lex.name
	else -> "none"
    }
    
    exiting(here + " with result '$result'")
    return result
}

fun printLexemeListOfYmlFile (ymlFileName: String, caller: String) {
    val here = functionName()
    entering(here, caller)

    println("$here: input ymlFileName '$ymlFileName'")
    
    val lex_l = lexemeListOfYmlFile (ymlFileName, here)
    val str_l = fullnameListOfLexemeList (lex_l)
    val content = stringOfGlueOfStringList ("\n", str_l)

    println ("Lexemes from file '$ymlFileName'")
    println (content)

    exiting(here)
}

fun printStringList (str_l: List<String>) {
    val content = stringOfGlueOfStringList ("\n", str_l)

    println (content)
}


fun stringValueListOfLexemeList (lex_l: List<Lexeme>) : List<String> {
  val str_l = lex_l.map({l -> stringValueOfLexeme (l) })
  return str_l 
}

fun unknownCharacterOfMessage (mes: String?, caller: String): Char? {
    val here = functionName()
    entering(here, caller)

    val messageType : String?
    
    println("$here: input mes '$mes'")
    
    var unknownCharacter = try {
    	messageType = mes?.substring (0, 36)
	println("$here: messageType '$messageType'")
	mes?.get (38)
	}
    catch (e: Exception) {nullChar}

    println("$here: output character '$unknownCharacter'")
    exiting(here)

    return unknownCharacter
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
    	var cha = lin.get(position)	
 	when (cha) {
// Header 
	'#' -> {
	    if (lin.length > 1) {
	       val lexeme = tokenOfChar(cha, position, lin, here)
	       lexemeList.add(lexeme)
	       position = position + 1
	    }
	    else {
	       Done = true
	       TokenEmptySharpedLine
	    }
	}
	' ' -> {
	    val lexeme = tokenOfChar(cha, position, lin, here)
	    lexemeList.add(lexeme)
	    position = position + 1	 
	}
	'$' -> {
	    val lexeme = tokenOfChar(cha, position, lin, here)
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
            val nextCharacter = lin.get(position + 1)
	    if (nextCharacter.equals ('e')){
	       val str = lin.substring(position)
	       lexemeList.addAll (lexemeListOfMembersKeyword (str, here)) 
	    }
	    else if (nextCharacter.equals ('u')){
	       val str = lin.substring(position)
	       lexemeList.addAll (lexemeListOfMutableLine (str, here)) 
	    }
	    else {
	    	 val message = "$here: Error next character '$nextCharacter' should be 'e' or 'u'"
	    	 throw Exception(message)
	    }
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
	    	 val message = "$here: Error unexpected current character '$cha' at position $position"
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

fun writeLexemeListOfYmlFileOfOuputFile (ymlFileName: String, lexFileName: String, caller: String) {
    val here = functionName()
    entering(here, caller)

    println("$here: input ymlFileName '$ymlFileName'")
    println("$here: input lexFileName '$lexFileName'")

    val lex_l = lexemeListOfYmlFile (ymlFileName, here)
    val str_l = fullnameListOfLexemeList (lex_l)
    val content = stringOfGlueOfStringList ("\n", str_l)

    outputWrite (lexFileName, content, here)

    val siz = lex_l.size
    println("$here: $siz lexemes written to File '$lexFileName'")
}




