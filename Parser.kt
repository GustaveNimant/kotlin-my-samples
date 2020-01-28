import java.io.File
import java.util.Stack
import java.lang.Character.MIN_VALUE as nullChar

import MyLibrary.*
import Lexer.*

// kotlinc MyLibrary.kt -include-runtime -d MyLibrary.jar 
// kotlinc -classpath MyLibrary.jar Lexer.kt -include-runtime -d Lexer.jar
// java -esa --class-path MyLibrary.jar:Lexer.jar LexerKt

fun main(args: Array<String>) {
    val here = functionName()
    entering(here,"resolve")

    println("$here: enter input Yml file name. Default 'current-block-test.yml'")
    var ymlFileName = "current-block-test.yml" 
    val yml_f = inputRead(here)
    if (! yml_f.isNullOrBlank()) {
        ymlFileName = yml_f
    }
    println("$here: input Yml File name is '$ymlFileName'")
    
    var lexFileName = ymlFileName.replace (Regex("yml"), "lex")
    println("$here: enter output file name. Default '$lexFileName'")

    val lex_f = inputRead(here)
    if (! lex_f.isNullOrBlank()) {
        lexFileName = lex_f
    }

    writeLexemeListOfYmlFileOfOuputFile (ymlFileName, lexFileName, here)
    println("$here: Lexeme output File '$lexFileName'")
    
    println("\nnormal termination")
    exiting(here)
}
