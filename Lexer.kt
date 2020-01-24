import java.io.File
import java.util.Stack
import java.lang.Character.MIN_VALUE as nullChar

val debug = false
var level = 0
var dots = "........|........|........|........|........|........|........|"

sealed class Lexeme ()  // Sharped Line 
       	  data class KeywordWithPersonName (val name: String) : Lexeme ()
	  data class KeywordWithDate (val name: String) : Lexeme ()
	  data class KeywordWithHash (val name: String) : Lexeme () 
	  data class KeywordWithString (val name: String) : Lexeme ()
	  data class KeywordWithFile (val name: String) : Lexeme ()
	  data class KeywordWithInteger (val name: String) : Lexeme ()

	  data class Comment (val name: String) : Lexeme ()
	  data class DateValue (val value: String) : Lexeme ()
	  data class AuthorName (val name: String) : Lexeme ()
  	  data class NextName (val name: String) : Lexeme ()
	  data class FilePath (val name: String) : Lexeme ()
	  data class QmHash (val name: String) : Lexeme ()
	  data class Signature (val value: String) : Lexeme ()
	  data class Spot (val value: String) : Lexeme ()
	  data class Tic (val value: String) : Lexeme ()	  
	  data class TextRecordConstant (val record: String) : Lexeme ()
	  data class TextRecordSubstituable (val record: String) : Lexeme ()
	  
	  object UnknownKW : Lexeme ()
	  object SkippedKW : Lexeme ()

	  object EmptySharpedLine : Lexeme ()
	  object TokenSharp : Lexeme ()

 data class TokenAlphabetical (val character: Char): Lexeme ()
 data class TokenAlphanumerical (val character: Char): Lexeme ()
 data class TokenNumerical (val character: Char) : Lexeme ()

 object TokenDollar : Lexeme ()
 object TokenSpace : Lexeme ()
 object TokenEndOfLine : Lexeme ()
 object TokenVee : Lexeme ()
 object TokenComma : Lexeme ()
 object TokenColon : Lexeme ()
 object TokenSemicolon : Lexeme ()
 object TokenDot : Lexeme ()
 object TokenHyphen : Lexeme ()

data class pairString (val first: String, val second: String)

fun functionName():String {
    val sta = Thread.currentThread().stackTrace[2]
    val str = sta.getMethodName()
    return str	
}

fun entering(here:String, caller:String):Unit {
    level = level + 1
    if (level > 70) {
       println ("Errot maximum number of nesting levels reached")
    } else {
        var points = dots.substring(0, level)
        println("$points Entering  in $here called by $caller")
    }
}

fun exiting(here:String):Unit {
    var points = dots.substring(0, level)
    println("$points Exiting from $here")
    level = level - 1	
}

fun notYetImplemented(fun_nam: String){
    val here = functionName()
	
    throw Exception("Error: function '$fun_nam' is not yet implemented")

    exiting(here)
    return 
}

fun read_input(caller:String):String {
    val here = functionName()
    entering(here, caller)
	
    val str = readLine().toString()
    
    exiting(here)
    return str
}

fun write_output(fileName:String, content: String, caller:String) {
    val here = functionName()
    entering(here, caller)
	
    File(fileName).bufferedWriter().use { out -> out.write(content)}
    
    exiting(here)
}

fun countOfCharOfString (cha: Char, str:String, caller:String) : Int {
    val here = functionName()
    entering(here, caller)

    var count = 0

    for (c in str) {
        if (c == cha){count = count + 1}
    }	
		
    exiting(here + " with count " + count.toString())
    return count
}

fun lineListOfFileName (nof: String, caller: String) : MutableList<String> {
    val here = functionName()
    entering(here, caller)

    println("$here: input nof '$nof'")

    val result = mutableListOf<String>()
 
    File(nof).useLines {
    	lines -> lines.forEach { result.add(it)}
	}

  println ("$here: output result '$result'")	
  exiting(here)
  return result
}

fun lexemeOfKeyword (keyword:String, caller: String) : Lexeme {
    val here = functionName()
    entering(here, caller)

    println("$here: input keyword: '$keyword'")

   var lexeme = when (keyword) {
       "Author" -> KeywordWithPersonName (keyword)
       "Date" -> KeywordWithDate (keyword)
       "Source" -> KeywordWithFile (keyword)
       "Signature" -> KeywordWithString (keyword)      
       "mutable" -> KeywordWithHash (keyword)
       "parents" -> KeywordWithHash (keyword)
       "previous" -> KeywordWithHash (keyword)
       "next" -> KeywordWithString (keyword)
       "tic" -> KeywordWithInteger (keyword)       
       "qm" -> KeywordWithHash (keyword)
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

fun nextWordOfString(pos:Int, lin: String, caller: String): String {
    val here = functionName()
    entering(here, caller)

    println("$here: input pos '$pos'")
    println("$here: input lin '$lin'")
    
    val str = lin.substring(pos)
    var word = ""    
    for (c in str){
	  println("$here: c '$c'")
	  if (isTokenOfChar(c, here)) {break}
	  word = word.plus(c.toString())
    }

    assert (word.isNotEmpty())
    
    println("$here: output word '$word'")
    exiting(here)
    return word
}

fun nextWordOfEndCharOfString(del: Char, str: String, caller: String): String {
    val here = functionName()
    entering(here, caller)

    println("$here: input del '$del'")
    println("$here: input str '$str'")
    
    var word = ""    
    for (c in str){
	  println("$here: c '$c'")
	  if (c.equals(del)) {break}
	  word = word.plus(c.toString())
    }

    assert (word.isNotEmpty())
    
    println("$here: output word '$word'")
    exiting(here)
    return word
}

fun lineStackOfLineList (str_l: List<String>) : Stack<String> {
    var stack = Stack<String>()
    str_l.reversed().forEach { l -> stack.push(l) }
    return stack
}


fun wordListOfString (str: String): List<String> {

    val trimedString = str.trim(' ')    
    val regex = Regex("\\s+")

    val result = trimedString.split(regex)

    return result
}

fun wordStackOfLine (lin: String) : Stack<String> {
    var stack = Stack<String>()
    var wor_l = wordListOfString (lin)
    wor_l.reversed().forEach { w -> stack.push(w)}
    return stack
}

fun isAlphabeticalOfChar(cha: Char, caller: String): Boolean {
    val here = functionName()
    entering(here, caller)
    
    val pattern = Regex("[a-zA-Z_]")
    println("$here: input cha '$cha'")
    val result = pattern.matches(cha.toString())

    exiting(here + " with result '$result'")
    return result
}

fun isAlphanumericalOfChar(cha: Char, caller: String): Boolean {
    val here = functionName()
    entering(here, caller)
    
    val pattern = Regex("[a-zA-Z_0-9]")
    println("$here: input cha '$cha'")
    val result = pattern.matches(cha.toString())

    exiting(here + " with result '$result'")
    return result
}

fun isAuthorNameOfString(str: String, caller: String): Boolean {
    val here = functionName()
    entering(here, caller)

    println("$here: input str '$str'")

    val pattern = Regex("[a-zA-Z][a-zA-Z_]*")
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

fun isKeywordOfString(str:String, caller: String): Boolean {
    val here = functionName()
    entering(here, caller)

    println("$here: input str '$str'")
    val result = str.startsWith('$') && str.endsWith(':')

    exiting(here + " with result '$result'")
    return result
}

fun isKeywordNameOfString(str:String, caller: String): Boolean {
    val here = functionName()
    entering(here, caller)

    println("$here: input str '$str'")
    val result = str.endsWith('$')

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

fun isNumericalOfChar(cha: Char, caller: String): Boolean {
    val here = functionName()
    entering(here, caller)
    
    val pattern = Regex("[0-9]")
    println("$here: input cha '$cha'")
    val result = pattern.matches(cha.toString())

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
    
    val pattern = Regex("""\d+""")
    println("$here: input str '$str'")
    val result = pattern.matches(str)

    exiting(here + " with result '$result'")
    return result
}

fun isTicOfString(str: String, caller: String): Boolean {
    val here = functionName()
    entering(here, caller)
    
    val pattern = Regex("""\d+""")
    println("$here: input str '$str'")
    val result = pattern.matches(str)

    exiting(here + " with result '$result'")
    return result
}

fun isTokenOfChar(cha: Char, caller:String) : Boolean {
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

fun keywordOfString(str:String, caller: String): String {
    val here = functionName()
    entering(here, caller)

   println("$here: input str '$str'")

    assert (isKeywordOfString(str, here))
    val result = (str.substring(1)).trimEnd({c -> c.equals(':')})

    exiting(here + " with result '$result'")
    return result
}

fun keywordNameOfString (str:String, caller: String): String {
    val here = functionName()
    entering(here, caller)

   println("$here: str '$str'")

    assert (isKeywordNameOfString(str, here))
    var result = str.trimEnd({ c -> c.equals('$')})

    exiting(here + " with result "+result)
    return result
}

fun lexemeListOfAuthorLine (lin: String, caller:String) : MutableList<Lexeme> {
// # $Author: michel$'
    val here = functionName()
    entering(here, caller)

    println("$here: input lin '$lin'")
    
    val lexemeList = mutableListOf<Lexeme>()

    var Done = false
    var position = 0
    var previousChar:Char? = nullChar
    
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
	     	   println("$here: setting End Of Line")
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

fun lexemeListOfDateLine (lin: String, caller:String) : MutableList<Lexeme> {
// # $Date: now$
    val here = functionName()
    entering(here, caller)

    println("$here: input lin '$lin'")
    
    val lexemeList = mutableListOf<Lexeme>()

    var Done = false
    var position = 0
    var previousCharacter: Char? = nullChar
    
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
	  	previousCharacter = lin.get(position-1)
	  	if (previousCharacter.equals('$')) {
	     	   var lexeme = TokenEndOfLine
	     	   println("$here: setting End Of Line")
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

fun lexemeListOfMutableLine (lin: String, caller:String) : MutableList<Lexeme> {
// mutable: /.brings/system/bin/kwextract.pl$
    val here = functionName()
    entering(here, caller)

    println("$here: input lin '$lin'")
    
    val lexemeList = mutableListOf<Lexeme>()

    var Done = false
    var position = 0
    var previousChar: Char? = nullChar
    
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
	     	   println("$here: setting End Of Line")
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

fun lexemeListOfNextLine (lin: String, caller:String) : MutableList<Lexeme> {
// # $next: unknown$'
    val here = functionName()
    entering(here, caller)

    println("$here: input lin '$lin'")
    
    val lexemeList = mutableListOf<Lexeme>()

    var Done = false
    var position = 0
    var previousChar:Char? = nullChar
    
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
	     	   println("$here: setting End Of Line")
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

fun lexemeListOfParentsLine (lin: String, caller:String) : MutableList<Lexeme> {
// parents: QmU1RDLsAGNPVuwDjKD3RQx7R6aEuQfcmSiubviDZ2XRVC$
    val here = functionName()
    entering(here, caller)

    println("$here: input lin '$lin'")
    
    val lexemeList = mutableListOf<Lexeme>()

    var Done = false
    var position = 0
    var previousChar: Char? = nullChar
    
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
	     	   println("$here: setting End Of Line")
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

fun lexemeListOfPreviousLine (lin: String, caller:String) : MutableList<Lexeme> {
// previous: QmU1RDLsAGNPVuwDjKD3RQx7R6aEuQfcmSiubviDZ2XRVC$
    val here = functionName()
    entering(here, caller)

    println("$here: input lin '$lin'")
    
    val lexemeList = mutableListOf<Lexeme>()

    var Done = false
    var position = 0
    var previousChar: Char? = nullChar
    
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
	     	   println("$here: setting End Of Line")
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

fun lexemeListOfQmHashLine (lin: String, caller:String) : MutableList<Lexeme> {
// qm: z2U1RDLsAGNPVuwDjKD3RQx7R6aEuQfcmSiubviDZ2XRVC$
    val here = functionName()
    entering(here, caller)

    println("$here: input lin '$lin'")
    
    val lexemeList = mutableListOf<Lexeme>()

    var Done = false
    var position = 0
    var previousChar: Char? = nullChar
    
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
	     	   println("$here: setting End Of Line")
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

fun lexemeListOfSharpedLine (lin: String, caller:String) : MutableList<Lexeme> {
// Source: /my/perl/script/kwextract.pl,v$
    val here = functionName()
    entering(here, caller)

    println("$here: input lin'$lin'")
    
    val lexemeList = mutableListOf<Lexeme>()

    var Done = false
    var position = 0
    var previousCharacter: Char? = nullChar
    
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
	       EmptySharpedLine
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
	  	previousCharacter = lin.get(position-1)
	  	if (previousCharacter.equals('$')) {
	     	   var lexeme = TokenEndOfLine
	     	   println("$here: setting End Of Line")
	     	   Done = true			
	     	}
		else {
		     val message = "$here: Error previous character '$previousCharacter' (position $position) should be '$'"
	    throw Exception(message)
		}
	  }
   }

   println("$here: output lexemeList: "+ stringListOfLexemeList(lexemeList))
   exiting(here)
   return lexemeList
}

fun lexemeListOfSourceLine (lin: String, caller:String) : MutableList<Lexeme> {
// Source: /my/perl/script/kwextract.pl,v$
    val here = functionName()
    entering(here, caller)

    println("$here: input lin '$lin'")
    
    val lexemeList = mutableListOf<Lexeme>()

    var Done = false
    var position = 0
    var previousChar: Char? = nullChar
    
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
	     	   println("$here: setting End Of Line")
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

fun lexemeListOfSignatureLine (lin: String, caller:String) : MutableList<Lexeme> {
// # $spot: 1579373044$'
    val here = functionName()
    entering(here, caller)

    println("$here: input lin '$lin'")
    
    val lexemeList = mutableListOf<Lexeme>()

    var Done = false
    var position = 0
    var previousChar:Char? = nullChar
    
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
	     	   println("$here: setting End Of Line")
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

fun lexemeListOfSpotLine (lin: String, caller:String) : MutableList<Lexeme> {
// # $spot: 1579373044$'
    val here = functionName()
    entering(here, caller)

    println("$here: input lin '$lin'")
    
    val lexemeList = mutableListOf<Lexeme>()

    var Done = false
    var position = 0
    var previousChar:Char? = nullChar
    
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
	     	   println("$here: setting End Of Line")
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

fun lexemeListOfTextRecord (lin: String, caller:String) : MutableList<Lexeme> {
    val here = functionName()
    entering(here, caller)

    val count = countOfCharOfString ('$', lin, here) 
    var lexemeList = mutableListOf<Lexeme>()
    
    if (count == 0)  {
	var lexeme = TextRecordConstant (lin)
        lexemeList.add (lexeme)
	exiting(here + " with added lexeme '$lexeme'")
	return lexemeList
    }
    else if (count.rem (2) == 0)  {
	var lexeme = TextRecordSubstituable (lin)
        lexemeList.add (lexeme)
	exiting(here + " with added lexeme '$lexeme'")
	return lexemeList
    }
    else {
        val message = "$here: Error found $count \$ characters in line '$lin'"
    	throw Exception(message)
   }
}

fun lexemeListOfTicLine (lin: String, caller:String) : MutableList<Lexeme> {
// # $tic: 1579373044$'
    val here = functionName()
    entering(here, caller)

    println("$here: input lin '$lin'")
    
    val lexemeList = mutableListOf<Lexeme>()

    var Done = false
    var position = 0
    var previousChar:Char? = nullChar
    
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
	     	   println("$here: setting End Of Line")
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

fun stringOfLexeme (lexeme: Lexeme): String {
    val string = when (lexeme) {
        is KeywordWithPersonName -> lexeme.name
    	is KeywordWithDate -> lexeme.name
    	is KeywordWithFile -> lexeme.name
    	is KeywordWithHash -> lexeme.name
    	is KeywordWithString -> lexeme.name
    	is KeywordWithInteger -> lexeme.name
	is AuthorName -> lexeme.name
	is NextName -> lexeme.name	
	is FilePath -> lexeme.name
	is QmHash -> lexeme.name
	is Spot -> lexeme.value
	is Tic -> lexeme.value	
	is Signature -> lexeme.value	
	is DateValue -> lexeme.value
	is TextRecordConstant -> lexeme.record
	is TextRecordSubstituable -> lexeme.record	
	is Comment -> lexeme.name
	UnknownKW -> "unknown "
	EmptySharpedLine -> "EmptySharpedLine"
	SkippedKW -> "skipped "
TokenSharp	-> "TokenSharp"
TokenDollar	-> "TokenDollar"
TokenSpace	-> "TokenSpace"
TokenEndOfLine	-> "TokenEndOfLine"
TokenVee	-> "TokenVee"
TokenComma	-> "TokenComma"
TokenColon	-> "TokenColon"
TokenSemicolon	-> "TokenSemicolon"
TokenDot	-> "TokenDot"
TokenHyphen	-> "TokenHyphen"
is TokenAlphabetical -> lexeme.character.toString()
is TokenAlphanumerical -> lexeme.character.toString()
is TokenNumerical -> lexeme.character.toString()
}
    return string
}

fun stringOfStringList (str_l: List<String>) : String {
 val str = str_l.fold("", {acc, s -> acc + s })
 return str 
}

fun stringOfGlueOfStringList (glue: String, str_l: List<String>) : String {
 val str = str_l.fold("", {acc, s -> acc + s + glue })
 return str 
}

fun stringListOfLexemeList (lex_l: List<Lexeme>) : List<String> {
 val str_l = lex_l.map({l -> stringOfLexeme (l) })
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
             val message = "$here: Error unknown Character '$cha'"
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

fun main(args: Array<String>) {
    val here = functionName()
    entering(here,"resolve")

    var lexemeList = mutableListOf<Lexeme>()
    
//    println("$here: Enter file name. Ex. 'current-block-test.yml'")
//    val fileName = read_input(here)
    val fileName = "t.yml"
    println("$here: Entered file name : $fileName")

    println("Read the whole file as a List of String :")
    val lineList = lineListOfFileName (fileName, here)

    var count = 0
    for (line in lineList) {
      count = count + 1
      println("$here: for line # $count '$line'")
      if (line.startsWith('#'))  {
      	 val lexeme_l = lexemeListOfSharpedLine (line, here)
	 lexemeList.addAll (lexeme_l)
	 }		
      else {
	 val lexeme_l = lexemeListOfTextRecord (line, here)
	 lexemeList.addAll (lexeme_l)
	 }
    }
    
    val siz = lexemeList.size
    println("$here: total of $siz lexemes in List")
    
    val str_l = stringListOfLexemeList (lexemeList)
    val content = stringOfGlueOfStringList ("\n", str_l)
    write_output ("some.txt", content, here)
    
    println("\nnormal termination")
    exiting(here)
}

