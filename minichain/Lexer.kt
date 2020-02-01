package my.lexer

import java.io.File
import java.util.Stack
import java.lang.Character.MIN_VALUE as nullChar

import my.library.*

// kotlinc MyLibrary.kt -include-runtime -d MyLibrary.jar 
// kotlinc -classpath MyLibrary.jar Lexer.kt -include-runtime -d Lexer.jar

sealed class Lexeme ()
  data class KeywordWithPersonName (val name: String) : Lexeme ()
  data class KeywordWithDate (val name: String) : Lexeme ()
  data class KeywordWithQmHash (val name: String) : Lexeme ()
  data class KeywordWithZ2Hash (val name: String) : Lexeme () 
  data class KeywordWithString (val name: String) : Lexeme ()
  data class KeywordWithFile (val name: String) : Lexeme ()
  data class KeywordWithInteger (val name: String) : Lexeme ()

  data class Comment (val name: String) : Lexeme ()
  data class DateValue (val value: String) : Lexeme ()
  data class AuthorName (val name: String) : Lexeme ()
  data class NextName (val name: String) : Lexeme ()
  data class FilePath (val name: String) : Lexeme ()
  data class QmHash (val hash: String) : Lexeme ()
  data class Z2Hash (val hash: String) : Lexeme ()
  data class Signature (val value: String) : Lexeme ()
  data class Spot (val value: String) : Lexeme ()
  data class Tic (val value: String) : Lexeme ()	  

  data class TextRecordConstant (val record: String) : Lexeme ()
  data class TextStringConstant (val string: String) : Lexeme ()
  data class TextWordConstant (val word: String) : Lexeme ()
  data class TextVariableSubstituable (val variable: String) : Lexeme ()
	  
  object TokenUnknown : Lexeme ()
  object TokenSkipped : Lexeme ()

  object TokenEmptySharpedLine : Lexeme ()
  object TokenEmptyLine : Lexeme ()

  object TokenDollar : Lexeme ()
  object TokenSpace : Lexeme ()
  object TokenEndOfLine : Lexeme ()
  object TokenVee : Lexeme ()
  object TokenComma : Lexeme ()
  object TokenColon : Lexeme ()
  object TokenSemicolon : Lexeme ()
  object TokenSharp : Lexeme ()
  object TokenDot : Lexeme ()
  object TokenHyphen : Lexeme ()

data class pairString (val first: String, val second: String)

object lexemeListRegister {
     var list = mutableListOf<Lexeme>()

     fun isEmpty () : Boolean {
     	 return list.isEmpty()
     }
     
     fun store (l:List<Lexeme>) {
     	 l.forEach {e -> list.add(e)}
     }
     
     fun retrieve (caller: String) : List<Lexeme> {
         val here = functionName()
    	 entering(here, caller)
     	 var l = mutableListOf<Lexeme>()
     	 list.forEach {e -> l.add(e)}

	 exiting(here)
	 return l
     }
}

fun fullnameListOfLexemeList (lex_l: List<Lexeme>) : List<String> {
  val str_l = lex_l.map({l -> fullnameOfLexeme (l) })
  return str_l 
}

fun fullnameOfLexeme (lexeme: Lexeme): String {
    val string = when (lexeme) {

	is AuthorName -> "AuthorName("+lexeme.name+")"
	is Comment -> "Comment("+lexeme.name+")"
	is DateValue -> "DateValue("+lexeme.value+")"
	is FilePath -> "FilePath("+lexeme.name+")"
	is KeywordWithZ2Hash -> "KeywordWithZ2Hash("+lexeme.name+")"
	is NextName -> "NextName("+lexeme.name+")"
	is QmHash -> "QmHash("+lexeme.hash+")"
	is Signature -> "Signature("+lexeme.value+")"	
	is Spot -> "Spot("+lexeme.value+")"
	is TextRecordConstant -> "TextRecordConstant("+lexeme.record+")"
	is TextStringConstant -> "TextStringConstant("+lexeme.string+")"
	is TextVariableSubstituable -> "TextVariableSubstituable("+lexeme.variable+")"	
	is TextWordConstant -> "TextWordConstant("+lexeme.word+")"
	is Tic -> "Tic("+lexeme.value+")"	
	is Z2Hash -> "Z2Hash("+lexeme.hash+")"

	is KeywordWithDate    -> "KeywordWithDate("+lexeme.name+")"
    	is KeywordWithFile    -> "KeywordWithFile("+lexeme.name+")"
    	is KeywordWithInteger -> "KeywordWithInteger("+lexeme.name+")"
    	is KeywordWithQmHash  -> "KeywordWithQmHash("+lexeme.name+")"
    	is KeywordWithString  -> "KeywordWithString("+lexeme.name+")"
        is KeywordWithPersonName -> "KeywordWithPersonName("+lexeme.name+")"

	TokenColon	-> "TokenColon"
	TokenComma	-> "TokenComma"
	TokenDollar	-> "TokenDollar"
	TokenDot	-> "TokenDot"
	TokenEmptyLine  -> "TokenEmptyLine"
	TokenEmptySharpedLine -> "TokenEmptySharpedLine"
	TokenEndOfLine	-> "TokenEndOfLine"
	TokenHyphen	-> "TokenHyphen"
	TokenSemicolon	-> "TokenSemicolon"
	TokenSharp	-> "TokenSharp"
	TokenSkipped    -> "skipped "
	TokenSpace	-> "TokenSpace"
	TokenUnknown    -> "unknown "
	TokenVee	-> "TokenVee"
	}
    return string
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
    val pattern = Regex("[a-zA-Z_]+")
    val result = pattern.matches(str)

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

fun isInMetaOfLexeme(lex: Lexeme, caller: String): Boolean {
    val here = functionName()
    entering(here, caller)

    println("$here: input lex '$lex'")
    val result = when (lex) {
	is TextRecordConstant  -> false
	is TextStringConstant  -> false
	is TextVariableSubstituable  -> false
	is TextWordConstant  -> false

	TokenColon	 -> true
	TokenComma	 -> true
	TokenDollar	 -> true
	TokenDot	 -> true
	TokenEndOfLine	 -> true
	TokenHyphen	 -> true
	TokenSemicolon	 -> true
	TokenSharp	 -> true
	TokenSpace	 -> true

	is AuthorName  -> true
	is Comment  -> true
	is DateValue  -> true
	is FilePath  -> true
	is KeywordWithZ2Hash  -> true
	is NextName  -> true
	is QmHash  -> true
	is Signature  -> true
	is Spot  -> true
	is Tic  -> true
	is Z2Hash  -> true

	is KeywordWithDate     -> true
    	is KeywordWithFile     -> true
    	is KeywordWithInteger  -> true
    	is KeywordWithQmHash   -> true
    	is KeywordWithString   -> true
        is KeywordWithPersonName  -> true

	TokenSkipped     -> true
	TokenUnknown     -> true
	TokenVee	 -> true
	TokenEmptyLine   -> true
	TokenEmptySharpedLine  -> true
    }
    exiting(here + " with result '$result'")
    return result
}

fun isInTextOfLexeme(lex: Lexeme, caller: String): Boolean {
    val here = functionName()
    entering(here, caller)

    println("$here: input lex '$lex'")
    val result = when (lex) {
	is TextRecordConstant  -> true
	is TextStringConstant  -> true
	is TextVariableSubstituable  -> true
	is TextWordConstant  -> true

	TokenColon	 -> true
	TokenComma	 -> true
	TokenDollar	 -> true
	TokenDot	 -> true
	TokenEndOfLine	 -> true
	TokenHyphen	 -> true
	TokenSemicolon	 -> true
	TokenSharp	 -> true
	TokenSpace	 -> true

	is AuthorName  -> false
	is Comment  -> false
	is DateValue  -> false
	is FilePath  -> false
	is KeywordWithZ2Hash  -> false
	is NextName  -> false
	is QmHash  -> false
	is Signature  -> false
	is Spot  -> false
	is Tic  -> false
	is Z2Hash  -> false

	is KeywordWithDate     -> false
    	is KeywordWithFile     -> false
    	is KeywordWithInteger  -> false
    	is KeywordWithQmHash   -> false
    	is KeywordWithString   -> false
        is KeywordWithPersonName  -> false

	TokenSkipped     -> false
	TokenUnknown     -> false
	TokenVee	 -> false
	TokenEmptyLine   -> false
	TokenEmptySharpedLine  -> false
    }
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

fun isKeywordWithOfLexeme(lex: Lexeme, caller: String): Boolean {
    val here = functionName()
    entering(here, caller)

    println("$here: input lex '$lex'")
    val result = when (lex) {
    	is KeywordWithZ2Hash -> true 
	is KeywordWithDate -> true     
    	is KeywordWithFile -> true 
    	is KeywordWithInteger -> true 
    	is KeywordWithQmHash -> true  
    	is KeywordWithString -> true   
        is KeywordWithPersonName -> true
	else -> false
    }
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

fun isTokenOfChar(cha: Char, caller: String) : Boolean {
    val here = functionName()
    entering(here, caller)

    if(debug) println("$here: input cha '$cha'")
    val result = when (cha) {
		'#' -> true
		'$' -> true
		' ' -> true 
		'\n' -> true
		'/' -> true
		',' -> true
		':' -> true
		';' -> true
		'.' -> true
		'-' -> true
		'a','z' -> true
		else -> false
    }
    
  if(debug) println ("$here: output result '$result'")	
  exiting(here)
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
    	      var currentCharacter = lin.get(position)
	      val lexeme = when (currentCharacter) {
	      ' ' -> {
	      	  tokenOfChar(currentCharacter, position, lin, here)
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
	      	  tokenOfChar(currentCharacter, position, lin, here)
		  }
	      '$' -> {
	      	  tokenOfChar(currentCharacter, position, lin, here)
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
	      	  tokenOfChar(currentCharacter, position, lin, here)
		  }
	      'n' -> {
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
	      	  tokenOfChar(currentCharacter, position, lin, here)
		  }
	      '$' -> {
	      	  tokenOfChar(currentCharacter, position, lin, here)
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
	      	  tokenOfChar(currentCharacter, position, lin, here)
		  }
	      '$' -> {
	      	  tokenOfChar(currentCharacter, position, lin, here)
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
	      	  tokenOfChar(currentCharacter, position, lin, here)
		  }
	      '$' -> {
	      	  tokenOfChar(currentCharacter, position, lin, here)
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
	      	  tokenOfChar(currentCharacter, position, lin, here)
		  }
	      'v' -> {
	      	  tokenOfChar(currentCharacter, position, lin, here)
		  }
	      '$' -> {
	      	  tokenOfChar(currentCharacter, position, lin, here)
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
	      	  tokenOfChar(currentCharacter, position, lin, here)
		  }
	      'S' -> {
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
	      	  tokenOfChar(currentCharacter, position, lin, here)
		  }
	      's' -> {
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
	      	  tokenOfChar(currentCharacter, position, lin, here)
		  }
	      't' -> {
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

fun lexemeStackOfLexemeList (lex_l: List<Lexeme>, caller: String): Stack<Lexeme> {
    var stack = Stack<Lexeme>()
    lex_l.reversed().forEach { lex -> stack.push (lex)}
    return stack
}

fun lexemeOfKeyword (keyword: String, caller: String) : Lexeme {
    val here = functionName()
    entering(here, caller)

    println("$here: input keyword: '$keyword'")

   var lexeme = when (keyword) {
       "Author" -> KeywordWithPersonName (keyword)
       "Date" -> KeywordWithDate (keyword)
       "Source" -> KeywordWithFile (keyword)
       "Signature" -> KeywordWithString (keyword)      
       "mutable" -> KeywordWithFile (keyword)
       "parents" -> KeywordWithQmHash (keyword)
       "previous" -> KeywordWithQmHash (keyword)
       "next" -> KeywordWithString (keyword)
       "tic" -> KeywordWithInteger (keyword)       
       "qm" -> KeywordWithZ2Hash (keyword)
       "spot" -> KeywordWithInteger (keyword)       
       else -> {
       	    val message = "$here: Error unknown Keyword '$keyword'"
	    throw Exception(message)
	}
  }
  
  println ("$here: output lexeme '$lexeme'")	
  exiting(here)
  return lexeme
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

fun nextWordOfString(pos:Int, lin: String, caller: String): String {
    val here = functionName()
    entering(here, caller)

    println("$here: input pos '$pos'")
    println("$here: input lin '$lin'")
    
    val str = lin.substring(pos)
    var word = ""    
    for (c in str){
	  if (debug) println("$here: c '$c'")
	  if (isTokenOfChar(c, here)) {break}
	  word = word.plus(c.toString())
    }

    assert (word.isNotEmpty())
    
    println("$here: output word '$word'")
    exiting(here)
    return word
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

fun stringValueOfLexeme (lexeme: Lexeme): String {
    val string = when (lexeme) {

	is AuthorName -> lexeme.name
	is Comment -> lexeme.name
	is DateValue -> lexeme.value
	is FilePath -> lexeme.name
	is KeywordWithZ2Hash -> lexeme.name
	is NextName -> lexeme.name
	is QmHash -> lexeme.hash
	is Signature -> lexeme.value	
	is Spot -> lexeme.value
	is TextRecordConstant -> lexeme.record
	is TextStringConstant -> lexeme.string
	is TextVariableSubstituable -> lexeme.variable	
	is TextWordConstant -> lexeme.word
	is Tic -> lexeme.value	
	is Z2Hash -> lexeme.hash

	is KeywordWithDate    -> lexeme.name
    	is KeywordWithFile    -> lexeme.name
    	is KeywordWithInteger -> lexeme.name
    	is KeywordWithQmHash  -> lexeme.name
    	is KeywordWithString  -> lexeme.name
        is KeywordWithPersonName -> lexeme.name

	TokenColon	-> ":"
	TokenComma	-> ","
	TokenDollar	-> "\$"
	TokenDot	-> "."
	TokenEmptyLine  -> ""
	TokenEmptySharpedLine -> ""
	TokenEndOfLine	-> "\n"
	TokenHyphen	-> "-"
	TokenSemicolon	-> ";"
	TokenSharp	-> "#"
	TokenSpace	-> " "
	TokenVee	-> "v"
	TokenUnknown    -> "unknown"
	TokenSkipped    -> "skipped"
	}
    return string
}

fun stringValueListOfLexemeList (lex_l: List<Lexeme>) : List<String> {
  val str_l = lex_l.map({l -> stringValueOfLexeme (l) })
  return str_l 
}

fun tokenOfChar(cha: Char, pos: Int, lin: String, caller: String) : Lexeme {
    val here = functionName()
    entering(here, caller)

    println("$here: input cha '$cha'")

    val token = when (cha) {
		'#' -> TokenSharp
		'$' -> TokenDollar
		' ' -> TokenSpace
		'\n' -> TokenEndOfLine
		'v' -> TokenVee
		',' -> TokenComma
		':' -> TokenColon
		';' -> TokenSemicolon
		'.' -> TokenDot	
		'-' -> TokenHyphen
	else -> {
             val message = "$here: Error unknown Character '$cha' at position $pos of line '$lin'"
    	     throw Exception(message)
	     	}
	}

	println("$here: output token '$token'")
	exiting(here)
	return token
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

fun writeLexemeList (caller: String) {
    val here = functionName()
    entering(here, caller)

    val lex_l = provideLexemeList (here)
    val str_l = fullnameListOfLexemeList (lex_l)
    val content = stringOfGlueOfStringList ("\n", str_l)

    val lexFileName = provideAnyFileNameOfWhat("Lexeme", here)
    outputWrite (lexFileName, content, here)

    val siz = lex_l.size
    println("$here: $siz lexemes written to File '$lexFileName'")
}

// IRP

fun buildLexemeList(ymlFileName: String, caller: String) : List<Lexeme> {
    val here = functionName()
    entering(here, caller)

    val lex_l = lexemeListOfYmlFile (ymlFileName, here)

    if (debug) println("$here: output lexeme List '$lex_l'")
    exiting(here)
    return lex_l
}

fun buildAndStoreLexemeList(caller: String) {
    val here = functionName()
    entering(here, caller)

    val ymlFileName = provideAnyFileNameOfWhat ("Yml", here)
    var lex_l = buildLexemeList(ymlFileName, here)
    lexemeListRegister.store (lex_l)

    if (debug) println("$here: output lexeme List '$lex_l'")
    exiting(here)
}

fun provideLexemeList(caller: String) : List<Lexeme> {
    val here = functionName()
    entering(here, caller)

    if (lexemeListRegister.isEmpty()){
       buildAndStoreLexemeList(here)
    }
    
    val lex_l = lexemeListRegister.retrieve(here)

    if (debug) println("$here: output lexeme List '$lex_l'")
    exiting(here)
    return lex_l
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
// Header 
	'#' -> {
	    if (lin.length > 1) {
	       val lexeme = tokenOfChar(currentCharacter, position, lin, here)
	       lexemeList.add(lexeme)
	       position = position + 1
	    }
	    else {
	       Done = true
	       TokenEmptySharpedLine
	    }
	}
	' ' -> {
	    val lexeme = tokenOfChar(currentCharacter, position, lin, here)
	    lexemeList.add(lexeme)
	    position = position + 1	 
	}
	'$' -> {
	    val lexeme = tokenOfChar(currentCharacter, position, lin, here)
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

    var lexemeList = mutableListOf<Lexeme>()
    
    println("$here: input rec '$rec'")
    var stack = characterStackOfString (rec)
    var cha = nullChar
    var word = ""
    
    while (! stack.isEmpty()) {
      cha = stack.pop()
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
		  word = w
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
		  word = w
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

fun stackOfLexemeOfLexemeStack (lexeme: Lexeme, lex_s: Stack<Lexeme>, caller: String): Stack<Lexeme> {
    val here = functionName()
    entering(here, caller)

    println ("$here: input lexeme '$lexeme'")
    var lex = lex_s.pop()
    
    while (lex != lexeme) {
      lex = lex_s.pop()
      println ("$here: while lex '$lex'")
    }
    println ("$here: output lex_s '$lex_s'")
	
    exiting(here)
    return lex_s
}



