import java.io.File
import java.util.Stack
import java.lang.Character.MIN_VALUE as nullChar

import my.library.*
import my.lexer.*

// kotlinc MyLibrary.kt -include-runtime -d MyLibrary.jar 
// kotlinc -classpath MyLibrary.jar Lexer.kt -include-runtime -d Lexer.jar
// kotlinc -classpath MyLibrary.jar:Lexer.jar Parser.kt -include-runtime -d Parser.jar 
// java -esa --class-path MyLibrary.jar:Lexer.jar:Parser.jar ParserKt

fun provideMetaList (caller: String) : List<Lexeme> {
    val here = functionName()
    entering(here, caller)

    val lex_l = provideLexemeList (here)

    var metaList = lex_l.filter { l ->
    		   stringOfLexeme(l).substring(0, 4) != "Text"
	}

    exiting(here)
    return metaList
}

fun provideTextList (caller: String) : List<Lexeme> {
    val here = functionName()
    entering(here, caller)

    val lex_l = provideLexemeList (here)

    var textList = lex_l.filter { l ->
    		   stringOfLexeme(l).substring(0, 4) == "Text"
	}

    exiting(here)
    return textList
}

fun main(args: Array<String>) {
    val here = functionName()
    entering(here, "Parser")

    writeLexemeList (here)

    val met_l = provideMetaList (here)
    var str_l = stringListOfLexemeList (met_l)
    printStringList (str_l)

    val tex_l = provideTextList (here)
    str_l = stringListOfLexemeList (tex_l)
    printStringList (str_l)
	
    println("\nnormal termination")
    exiting(here)
}
