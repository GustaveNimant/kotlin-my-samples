package my.lexer

import java.io.File
import java.util.Stack
import java.lang.Character.MIN_VALUE as nullChar

import my.library.*
import my.lexeme.*

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

fun lexemeListOfYmlFile (ymlFileName: String, caller: String): List<Lexeme> {
    val here = functionName()
    entering(here, caller)

// local :
    var Done = false

// needs :
    val fil_nam = provideAnyFileNameOfWhat("Yml", here)
    val input_cha_s = characterStackOfFileName(fil_nam)
    println("$here: input ymlFileName '$fil_nam'")

// output :
    var lexemeList = mutableListOf<Lexeme>()

    while (! Done)
      try {
      	var cha = input_cha_s.pop()
	println("$here: cha '$cha'")

	when (cha) {
	'#' -> {
	   input_cha_s.push(cha)
	   val (lex_l, output_cha_s) = lexemeListOfSharpedStack (input_cha_s, here)
	   lexemeList.addAll (lex_l)
	   input_cha_s = output_cha_s
	   }
	 else -> {
	   val (lex_s, output_cha_s) = lexemeListOfTextStack (input_cha_s, here)
	   lexemeList.addAll (lex_l)
	   input_cha_s = output_cha_s
	   }
         }
      }
      catch (e: java.util.EmptyStackException) {
        Done = true			       
      }
    
    if (debug) println("$here: output lexemeList $lexemeList")
    exiting(here)

    return lexemeList
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



