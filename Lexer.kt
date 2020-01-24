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

	  data class FilePath (val name: String) : Lexeme ()
	  data class DateValue (val value: String) : Lexeme ()
	  data class TextRecordSubstituable (val record: String) : Lexeme ()
	  data class TextRecordConstant (val record: String) : Lexeme ()
	  data class Comment (val name: String) : Lexeme ()
	  
	  object UnknownKW : Lexeme ()
	  object SkippedKW : Lexeme ()
	  
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

fun nextWordOfEndCharOfString(del:Char, str: String, caller: String): String {
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

fun isAlphabeticalOfChar(cha:Char, caller: String): Boolean {
    val here = functionName()
    entering(here, caller)
    
    val pattern = Regex("[a-zA-Z_]")
    println("$here: input cha '$cha'")
    val result = pattern.matches(cha.toString())

    exiting(here + " with result '$result'")
    return result
}

fun isDateOfString(str: String, caller: String): Boolean {
    val here = functionName()
    entering(here, caller)
    
    println("$here: input str '$str'")
    val pattern = Regex("[a-zA-Z_]")
    val result = pattern.matches(str)

    exiting(here + " with result '$result'")
    return result
}

fun isNumericalOfChar(cha:Char, caller: String): Boolean {
    val here = functionName()
    entering(here, caller)
    
    val pattern = Regex("[0-9]")
    println("$here: input cha '$cha'")
    val result = pattern.matches(cha.toString())

    exiting(here + " with result '$result'")
    return result
}

fun isAlphanumericalOfChar(cha:Char, caller: String): Boolean {
    val here = functionName()
    entering(here, caller)
    
    val pattern = Regex("[a-zA-Z_0-9]")
    println("$here: input cha '$cha'")
    val result = pattern.matches(cha.toString())

    exiting(here + " with result '$result'")
    return result
}

fun isFilePathOfString(str: String, caller: String): Boolean {
    val here = functionName()
    entering(here, caller)
    
    println("$here: input str '$str'")
    val pattern = Regex("""^(/\w[a-zA-Z_0-9]*)(/([a-zA-Z_0-9]+))*\.\w+$""")
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

fun lexemeListOfDateLine (lin: String, caller:String) : MutableList<Lexeme> {
// # $Date: now$
    val here = functionName()
    entering(here, caller)

    println("$here: input lin '$lin'")
    
    val lexemeList = mutableListOf<Lexeme>()

    var Done = false
    var position = 0
    var previousCharacter:Char? = nullChar
    
    while (!Done) {
    	  try {
    	      var currentCharacter = lin.get(position)
	      val lexeme = when (currentCharacter) {
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
		     val message = "$here: Error unknown Character '$currentCharacter'"
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
// # $Source: /my/perl/script/kwextract.pl,v$
    val here = functionName()
    entering(here, caller)

    println("$here: input lin'$lin'")
    
    val lexemeList = mutableListOf<Lexeme>()

    var Done = false
    var position = 0
    var previousCharacter:Char? = nullChar
    
    while (!Done) {
    	  try {
    	      var currentCharacter = lin.get(position)	
	    try {
	      var lexeme = tokenOfChar(currentCharacter, position, lin, here)
	      lexemeList.add(lexeme)
	      position = position + 1
	      previousCharacter = currentCharacter
	    }
	    catch (e: Exception) { // "tokenOfChar: Error unknown Character 'S'"
	  	var unknownCharacter : Char? = unknownCharacterOfMessage(e.message, here)
		val lexeme = when (unknownCharacter) {
		    'D' -> {
		    	val str = lin.substring(position)
		    	lexemeList.union (lexemeListOfDateLine (str, here)) 
			}
		    'S','P' -> {
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
		    	    	 val message = "$here: Error word '$word' is not a File Path"
	    		     	 throw Exception(message)
			    }
			    }
		else -> {
		     val message = "$here: Error unknown Character '$unknownCharacter'"
		     throw Exception(message)
			 }
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

fun stringOfLexeme (lexeme: Lexeme): String {
    val string = when (lexeme) {
        is KeywordWithPersonName -> lexeme.name
    	is KeywordWithDate -> lexeme.name
    	is KeywordWithFile -> lexeme.name
    	is KeywordWithHash -> lexeme.name
    	is KeywordWithString -> lexeme.name
    	is KeywordWithInteger -> lexeme.name
	is FilePath -> lexeme.name
	is DateValue -> lexeme.value
	is TextRecordConstant -> lexeme.record
	is TextRecordSubstituable -> lexeme.record	
	is Comment -> lexeme.name
	UnknownKW -> "unknown "
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

    for (line in lineList) {
	    println("$here: line: '" + line + "'")

	    if (line.startsWith('#'))  {
	      	lexemeList = lexemeListOfSharpedLine (line, here)
		lexemeList.union (lexemeList)
		}		
	    else {
	    	lexemeList = lexemeListOfTextRecord (line, here)
		lexemeList.union (lexemeList)
	    }
    }
    
    println("lexemeList:")
    lexemeList.forEach{ l -> println(stringOfLexeme(l))}

    val str_l = stringListOfLexemeList (lexemeList)
    val content = stringOfGlueOfStringList ("\n", str_l)
    write_output ("some.txt", content, here)
    
    println("\nnormal termination")
    exiting(here)
}

