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

    writeLexemeList (here)
    
    println("\nnormal termination")
    exiting(here)
}
