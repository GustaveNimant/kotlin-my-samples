// The Parser : List of Lexemes => Tree of Domain Entities

import java.io.File
import java.util.Stack
import java.lang.Character.MIN_VALUE as nullChar

import my.library.*
import my.lexer.*

// kotlinc MyLibrary.kt -include-runtime -d MyLibrary.jar 
// kotlinc -classpath MyLibrary.jar Lexer.kt -include-runtime -d Lexer.jar
// kotlinc -classpath MyLibrary.jar:Lexer.jar Parser.kt -include-runtime -d Parser.jar 
// java -esa --class-path MyLibrary.jar:Lexer.jar:Parser.jar ParserKt

class TreeNode<T>(value:T){
    var value:T = value

    var parent:TreeNode<T>? = null

    var children:MutableList<TreeNode<T>> = mutableListOf()

    fun addChild(node:TreeNode<T>){
        children.add(node)
        node.parent = this
    }

    override fun toString(): String {
        var s = "${value}"
        if (!children.isEmpty()) {
            s += " {" + children.map { it.toString() } + " }"
        }
        return s
    }
}

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

fun blockKindOfMetaLexemeList (met_l: List<Lexeme>, caller: String): String {
    val here = functionName()
    entering(here, caller)

    var result = "genesis"
    for (lex in met_l) {
      if (isKeywordWithQmHashOfLexeme(lex, here)) {
      if (lex.name == "previous") {	
             	println("$here: lex is Keyword '$lex'")
		}
		}
      if (lex is KeywordWithQmHash && lex.name == "previous") {	
       	println("$here: lex '$lex'")	
       	result = "current"
	break
      }
    }	

    exiting(here)
    return result
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

    val blockKind = blockKindOfMetaLexemeList (met_l, here)
    println("$here: blockKind '$blockKind'")
	   
//    val informationTree = TreeNode<String> ("blockchain")
    println("\nnormal termination")
    exiting(here)
}
