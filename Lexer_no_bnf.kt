import java.io.File
import java.util.Stack
import java.lang.Character.MIN_VALUE as nullChar
 
var level = 0
var dots = "........|........|........|........|........|........|........|"

sealed class Lexeme ()
       sealed class Keyword () : Lexeme ()

       	  data class AuthorKW (val value: String) : Lexeme ()
	  data class DateKW (val value: String) : Lexeme ()
	  data class MutableKW (val value: String) : Lexeme () 
	  data class NextKW (val value: String) : Lexeme ()
	  data class ParentsKW (val value: String) : Lexeme () 
	  data class PreviousKW (val value: String) : Lexeme () 
	  data class QmKW (val value: String) : Lexeme ()	
	  data class SignatureKW (val value: String) : Lexeme () 	  
	  data class SourceKW (val value: String) : Lexeme ()
	  data class SpotKW (val value: String) : Lexeme ()	  
	  data class TextKW (val value: String) : Lexeme ()
	  data class TicKW (val value: String) : Lexeme ()


	  object UnknownKW : Lexeme ()
	  object SkippedKW : Lexeme ()	  

       data class Comment (val value: String) : Lexeme () 
       

data class pairString (val first: String, val second: String)

fun stringOfLexeme (lexeme: Lexeme): String {
    val string = when (lexeme) {
        is AuthorKW -> "Author " + lexeme.value
    	is DateKW -> "Date " + lexeme.value
    	is SourceKW -> "Source " + lexeme.value
    	is SignatureKW -> "Signature " + lexeme.value
    	is MutableKW -> "mutable " + lexeme.value
    	is ParentsKW -> "parents " + lexeme.value
    	is PreviousKW -> "previous " + lexeme.value
    	is NextKW -> "next " + lexeme.value
    	is TicKW -> "tic " + lexeme.value

	is TextKW -> "Text " + lexeme.value
    	is QmKW -> "qm " + lexeme.value
    	is SpotKW -> "Spot " + lexeme.value
	is Comment -> "Comment " + lexeme.value
	UnknownKW -> "unknown "
	SkippedKW -> "skipped "
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

fun functionName():String {
    val sta = Thread.currentThread().stackTrace[2]
    val str = sta.getMethodName()
    return str	
}

fun entering(here:String, caller:String):Unit {
    level = level + 1
    if (level > 70) {
       println ("Error maximum number of nesting levels reached")
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

fun lineListOfFileName (nof: String) : MutableList<String> {

    val result = mutableListOf<String>()
 
    File(nof).useLines {
    	lines -> lines.forEach { result.add(it)}
	}

     return result
}

fun wordListOfString (str: String): List<String> {

    val trimedString = str.trim(' ')    
    val regex = Regex("\\s+")

    val result = trimedString.split(regex)

    return result
}

fun lineStackOfLineList (str_l: List<String>) : Stack<String> {
    var stack = Stack<String>()
    str_l.reversed().forEach { l -> stack.push(l) }
    return stack
}

fun wordStackOfLine (lin: String) : Stack<String> {
    var stack = Stack<String>()
    var wor_l = wordListOfString (lin)
    wor_l.reversed().forEach { w -> stack.push(w)}
    return stack
}

fun lexemeOfKeywordOfValue (keyword:String, value: String, caller: String) : Lexeme {

    val here = functionName()
    entering(here, caller)

    println("$here: input keyword: '$keyword'")
    println("$here: input value: '$value'")

   var lexeme = when (keyword) {
       "Author" -> AuthorKW (value)
       "Date" -> DateKW (value)
       "Source" -> SourceKW (value)
       "Signature" -> SignatureKW (value)      
       "mutable" -> MutableKW (value)
       "parents" -> ParentsKW (value)
       "previous" -> PreviousKW (value)
       "next" -> NextKW (value)
       "tic" -> TicKW (value)       
       "qm" -> QmKW (value)
       "spot" -> SpotKW (value)       

       else -> {
       	    val message = "Error unknown Keyword '$keyword'"
	    throw Exception(message)
	}
  }

  exiting(here + " with lexeme '$lexeme'")
  return lexeme
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

fun isKeywordOfString(str:String, caller: String): Boolean {
    val here = functionName()
    entering(here, caller)

   println("$here: input str '$str'")
    val result = str.startsWith('$') && str.endsWith(':')

    exiting(here + " with result '$result'")
    return result
}

fun isKeywordValueOfString(str:String, caller: String): Boolean {
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

fun keywordValueOfString (str:String, caller: String): String {
    val here = functionName()
    entering(here, caller)

   println("$here: str '$str'")

    assert (isKeywordValueOfString(str, here))
    var result = str.trimEnd({ c -> c.equals('$')})

    exiting(here + " with result "+result)
    return result
}

fun lexemeOfSharpedLine (lin: String, caller:String) : Lexeme {
// # $Source: /my/perl/script/kwextract.pl,v$
    val here = functionName()
    entering(here, caller)

    val lineTrimed = lin.trim() 
    if (lineTrimed.isNullOrBlank()) {
      var lexeme = SkippedKW

      exiting(here)
      return lexeme	
   }
   else if (lineTrimed.startsWith('$')) {
   	var currentAndNext = keywordAndStringOfSharpedLine (lineTrimed, here)
	val currentWord  = currentAndNext.first
   	val nextWord  = currentAndNext.second

	println("$here: currentWord: '" + currentWord + "'")
   	println("$here: nextWord: '" + nextWord + "'")

	assert (isKeywordOfString(currentWord, here))
	val keyword = keywordOfString (currentWord, here)
	val keywordValue = keywordValueOfString (nextWord, here)
	var lexeme = lexemeOfKeywordOfValue (keyword, keywordValue, here)

	exiting(here + " with lexeme '$lexeme'")
	return lexeme
    }
    else {
        val message = "$here: Error unknown first Character in line '$lineTrimed'"
    	throw Exception(message)
   }
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
    val lineList = lineListOfFileName (fileName)

    for (line in lineList) {
	    println("$here: line: '" + line + "'")

	    if (line.startsWith('#'))  {
	        var subline = line.substring(1)
	      	val lexeme = lexemeOfSharpedLine (subline, here)
		lexemeList.add (lexeme)
		}		
	    else {
	    	val lexeme = TextKW(line)
		lexemeList.add (lexeme)
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

