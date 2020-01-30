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

fun provideMetaLexemeList (caller: String) : List<Lexeme> {
    val here = functionName()
    entering(here, caller)

    val lex_l = provideLexemeList (here)

    var metaList = lex_l.filter { lex -> isInMetaOfLexeme(lex, here)}

    exiting(here)
    return metaList
}

fun provideTextLexemeList (caller: String) : List<Lexeme> {
    val here = functionName()
    entering(here, caller)

    val lex_l = provideLexemeList (here)

    var textList = lex_l.filter { lex -> isInTextOfLexeme(lex, here) }

    exiting(here)
    return textList
}

fun blockKindOfMetaLexemeList (met_l: List<Lexeme>, caller: String): String {
    val here = functionName()
    entering(here, caller)

    var result = "genesis"
    for (lex in met_l) {
      	if (lex is KeywordWithQmHash && lex.name == "previous") {	
       	   result = "current"
	   break
      }
    }	

    exiting(here)
    return result
}

fun provideRecordTextList (caller: String) : List<String> {
    val here = functionName()
    entering(here, caller)

// A record is enclosed between two TokenEndOfLine
    val lex_l = provideTextLexemeList (here)

    var rec = ""
    var rec_l = mutableListOf<String>()
	
    for (lex in lex_l) {
        println ("$here: for lex '$lex'")	
        when (lex) {
       	   is TokenEndOfLine -> {
	      rec_l.add (rec)
	      rec = ""
	   }
	   else -> {
	        var str = stringValueOfLexeme (lex) 
	   	rec = rec + str
		println ("$here: for str '$str'")	
		println ("$here: for rec '$rec'")	
		}
      }
    }

    val result = rec_l.toList()
    println ("$here: output result '$result'")	
    exiting(here)
    return result
}

fun provideTreeTextRecordList (caller: String) : List<TreeNode<String>> {
    val here = functionName()
    entering(here, caller)

// <TreeTextRecordList> ::= { <TreeTextRecord> }

    val nam_l = provideRecordTextList (here)

    var rec_tl = mutableListOf<TreeNode<String>>()   
    for (nam in nam_l) {
        var nod = TreeNode<String>(nam)
    	rec_tl.add (nod)
    }

    val tree_l = rec_tl.toList()
    println ("$here: output tree_l '$tree_l'")	

    exiting(here)
    return tree_l
}

fun provideBlockTextTreeNode (caller: String) : TreeNode<String> {
    val here = functionName()
    entering(here, caller)

// <TreeText> ::= TreeTextRecordList

    val tree = TreeNode<String> ("block-text")
    val nod_l = provideTreeTextRecordList (here)

    for (nod in nod_l) {
    	tree.addChild (nod)
    }

    println ("$here: output tree '$tree'")	
    exiting(here)
    return tree
}

fun provideRecordNameList (caller: String) : List<String> {
    val here = functionName()
    entering(here, caller)

    val lex_met_l = provideMetaLexemeList (here)

    var nam_l = mutableListOf<String>()	  
    for (lex in lex_met_l) {
    	var nam = nameKeywordWithOfLexeme(lex, here)
    	nam_l.add (nam)
    }

    val result = nam_l.toList()
    println ("$here: output result '$result'")	
    exiting(here)
    return result
}

fun provideTreeMetaRecordList (caller: String) : List<TreeNode<String>> {
    val here = functionName()
    entering(here, caller)

// <TreeMetaRecordList> ::= { <TreeMetaRecord> }

    val nam_l = provideRecordNameList (here)

    var rec_tl = mutableListOf<TreeNode<String>>()   
    for (nam in nam_l) {
        var nod = TreeNode<String>(nam)
    	rec_tl.add (nod)
    }

    val tree_l = rec_tl.toList()
    println ("$here: output tree_l '$tree_l'")	

    exiting(here)
    return tree_l
}

fun provideBlockMetaTreeNode (caller: String) : TreeNode<String> {
    val here = functionName()
    entering(here, caller)

// <TreeMeta> ::= TreeMetaRecordList

    val tree = TreeNode<String> ("block-meta")
    val nod_l = provideTreeMetaRecordList (here)

    for (nod in nod_l) {
    	tree.addChild (nod)
    }

    println ("$here: output tree '$tree'")	
    exiting(here)
    return tree
}

fun provideBlockCurrentTreeNode (caller: String) : TreeNode<String> {
    val here = functionName()
    entering(here, caller)

// <BlockCurrent> ::= <TreeMeta> <TreeText>

    val tree = TreeNode<String> ("block-current")
    val treeMeta = provideBlockMetaTreeNode (here)
    val treeText = provideBlockTextTreeNode (here)

    tree.addChild (treeMeta)
    tree.addChild (treeText)

    println ("$here: output tree '$tree'")	
    exiting(here)
    return tree
}

fun provideBlockGenesisTreeNode (caller: String) : TreeNode<String> {
    val here = functionName()
    entering(here, caller)

// <BlockGenesis> ::= <TreeMeta> <TreeText>

    val tree = TreeNode<String> ("block-genesis")
    val treeMeta = provideBlockMetaTreeNode (here)
    val treeText = provideBlockTextTreeNode (here)

// Building 
    tree.addChild(treeMeta)
    tree.addChild(treeText)

    exiting(here)
    return tree
}

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
