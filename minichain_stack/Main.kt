import java.io.File
import java.util.Stack
import java.lang.Character.MIN_VALUE as nullChar

import my.library.*
//import my.lexer.*

fun main(args: Array<String>) {
    val here = functionName()
    entering(here, "Parser")

    for (arg in args) {
      when(arg) {
      "debug" -> debug = true
      else -> {
      	   debug=false
        }
      }
    }

    writeLexemeList (here)

    val lex_l = provideLexemeList (here)
    
    val tree =
      if (hasKeywordPreviousOfLexemeList(lex_l, here)) {
        provideBlockCurrentTreeNode(here)
      } else {
        provideBlockGenesisTreeNode(here)
      }
      
    println ("$here: tree '$tree'")
    println("\nnormal termination")
    exiting(here)
}
