import java.io.File
import java.util.Stack
import java.lang.Character.MIN_VALUE as nullChar
 
var level = 0
var dots = "........|........|........|........|........|........|........|"

sealed class Lexeme ()
       sealed class Keyword () : Lexeme ()

       	  data class AuthorKW (val s: String) : Lexeme ()
	  data class DateKW (val s: String) : Lexeme ()
	  data class MutableKW (val s: String) : Lexeme () 
	  data class ParentsKW (val s: String) : Lexeme () 
	  data class PreviousKW (val s: String) : Lexeme () 
	  data class SourceKW (val s: String) : Lexeme () 
	  data class TicKW (val s: String) : Lexeme ()
	  data class TextKW (val s: String) : Lexeme ()


	  object UnknownKW : Lexeme ()
	  object Skipped : Lexeme ()	  

       data class Comment (val s: String) : Lexeme () 
       

data class pairString (val first: String, val second: String)

fun makeLexemeOfThreeCharacters (thr: String, nex: String) =
	    when (thr) {
	      "\$Au" -> AuthorKW (nex)
	      "\$Da" -> DateKW (nex)
	      "\$So" -> SourceKW (nex)
	      "\$mu" -> MutableKW (nex)
	      "\$pa" -> ParentsKW (nex)
	      "\$pr" -> PreviousKW (nex)
	      "\$ti" -> TicKW (nex)
	      else -> {
	      	      val message = "Error unknown threeFirstChar: '" + thr + "'"
    		      throw Exception(message)
		      }
	      }

	      
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
       "Author" -> AuthorKW (keyword)
       "Date" -> DateKW (keyword)
       "Source" -> SourceKW (keyword)
       "mutable" -> MutableKW (keyword)
       "parents" -> ParentsKW (keyword)
       "previous" -> PreviousKW (keyword)
       "tic" -> TicKW (keyword)

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
      var lexeme = Skipped

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
    lexemeList.forEach{println(it)}

    println("\nnormal termination")
    exiting(here)
}
