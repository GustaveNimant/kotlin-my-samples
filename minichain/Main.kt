import java.io.File
import java.util.Stack
import java.lang.Character.MIN_VALUE as nullChar

import my.library.*
import my.lexeme.*
import my.lexer.*
import my.lexeme.list.provider.*
import my.parser.*

fun main(args: Array<String>) {
    val here = functionName()
    entering(here, "Parser")

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
