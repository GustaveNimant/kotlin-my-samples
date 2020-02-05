package my.lexeme.list.provider

import java.io.File
import java.util.Stack
import java.lang.Character.MIN_VALUE as nullChar

import my.library.*
import my.lexeme.*
import my.lexer.*

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

fun buildAndStoreLexemeList(caller: String) {
    val here = functionName()
    entering(here, caller)

    val ymlFileName = provideAnyFileNameOfWhat ("Yml", here)
    var lex_l = lexemeListOfFileName(ymlFileName, here)
    lexemeListRegister.store (lex_l)

    if (isDebug(here)) println("$here: output lexeme List '$lex_l'")
    exiting(here)
}

fun provideLexemeList(caller: String) : List<Lexeme> {
    val here = functionName()
    entering(here, caller)

    if (lexemeListRegister.isEmpty()){
       buildAndStoreLexemeList(here)
    }
    
    val lex_l = lexemeListRegister.retrieve(here)

    if (isDebug(here)) println("$here: output lexeme List '$lex_l'")
    exiting(here)
    return lex_l
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

