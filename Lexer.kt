import java.io.File
import java.util.Stack
import java.lang.Character.MIN_VALUE as nullChar
 
var level = 0
var dots = "........|........|........|........|........|........|........|"

sealed class Lexeme ()
       sealed class Keyword () : Lexeme () 
          data class SourceKW (val s: String) : Lexeme () 
	  data class MutableKW (val s: String) : Lexeme () 
	  data class PreviousKW (val s: String) : Lexeme () 
	  data class ParentsKW (val s: String) : Lexeme () 
	  data class DateKW (val s: String) : Lexeme () 
	  data class TicKW (val s: String) : Lexeme () 
	  object UnknownKW : Lexeme ()
	  object Skipped : Lexeme ()	  

       data class Comment (val s: String) : Lexeme () 

       data class Text (val s: String) : Lexeme ()
       

data class pairString (val first: String, val second: String)

fun makeLexemeOfThreeCharacters (thr: String, nex: String) =
	    when (thr) {
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

    val result = trimedString.split(
	regex
    )
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

fun lexemeOfKeywordOfString (keyWord:String, nextWord: String) : Lexeme {

   println("keyWord: '" + keyWord + "'")
   println("nextWord: '" + nextWord + "'")

   val threeFirstChar = keyWord.substring(0, 3)
   println("threeFirstChar: '" + threeFirstChar + "'")

   return makeLexemeOfThreeCharacters (threeFirstChar, nextWord)
 }

fun keywordAndStringOfSharpedLine (lin: String, caller: String) : pairString {
// # $Source: /my/perl/script/kwextract.pl,v$
    val here = functionName()
    entering(here, caller)

   println("$here: lin $lin")
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

fun lexemeOfSharpedLine (lin: String, caller:String) : Lexeme {
// # $Source: /my/perl/script/kwextract.pl,v$
    val here = functionName()
    entering(here, caller)

   var currentAndNext = keywordAndStringOfSharpedLine (lin, here)

   val currentWord  = currentAndNext.first
   val nextWord  = currentAndNext.second

   println("$here: currentWord: '" + currentWord + "'")
   println("$here: nextWord: '" + nextWord + "'")

   var firstChar = currentWord.get(0) 
   println("$here: firstChar: '" + firstChar + "'")

   var lexeme = when (firstChar) {
	      '#' -> { Skipped }
	      '$' -> {
	      	  lexemeOfKeywordOfString (currentWord, nextWord)
		}
	      else -> {
	      	      val message = "$here: Error unknown firstChar: '" + firstChar + "' in line $lin"
    		      throw Exception(message)
		      }
	    	}
    exiting(here)
    return lexeme
}

fun main(args: Array<String>) {
    val here = functionName()
    entering(here,"resolve")

    var Done = false
    var lexemeList = mutableListOf<Lexeme>()
    
//    println("$here: Enter file name. Ex. 'current-block-test.yml'")
//    val fileName = read_input(here)
    val fileName = "t.yml"
    println("$here: Entered file name : $fileName")

    println("Read the whole file as a List of String :")
    val lineList = lineListOfFileName (fileName)
    var lineStack = lineStackOfLineList (lineList)

    while ( ! Done ) {
	try {
	    var currentLine = lineStack.pop()
	    println("$here: currentLine: '" + currentLine + "'")

            var firstChar = currentLine.get(0) 
	    println("$here: firstChar: '" + firstChar + "'")
	    var lexeme = when (firstChar) {
	      '#' -> lexemeOfSharpedLine (currentLine, here)
//	      ' ' -> {
//	        lineStack.push (currentLine)
//	      	lexemeListOfTextStack (lineStack)
//		}
	      else -> {
	      	      val message = "$here: Error unknown firstChar: '" + firstChar + "'"
    		      throw Exception(message)
		      }
	      }
	      lexemeList.add (lexeme)
	}
       	catch (e: java.util.EmptyStackException) {
       	     Done = true			
        }
    }
    exiting(here)
}

