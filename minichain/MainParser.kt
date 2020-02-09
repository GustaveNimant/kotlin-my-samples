import java.io.File
import java.util.Stack
import java.lang.Character.MIN_VALUE as nullChar

import my.lexeme.*
import my.lexeme.list.provider.*
import my.lexer.*
import my.library.*
import my.parser.*

fun main(args: Array<String>) {
  val here = functionName()
  entering(here, "Parser")

  ParameterMap = parameterMapOfArguments(args, here)

  println ("Parameters are:")
  for ( (k, v) in ParameterMap) {
       println ("$k => $v")
  }

  writeLexemeList (here)

  val lex_l = provideLexemeList (here)
  val tree =
      if (hasKeywordPreviousOfLexemeList(lex_l, here)) {
        provideBlockCurrentTreeNode(here)
      } else {
        provideBlockGenesisTreeNode(here)
      }
      
    if (isDebug(here)) println ("$here: tree '$tree'")
    println("\nnormal termination")
    exiting(here)
}
