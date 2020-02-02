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

fun provideMetaLexemeList (caller: String) : List<Lexeme> {
    val here = functionName()
    entering(here, caller)

    val lex_l = provideLexemeList (here)

    var metaList = mutableListOf<Lexeme>()
    var is_meta = false

    for (lex in lex_l) {
    	if (debug) println ("$here: for lex '$lex'")

	if (lex is TokenSharp) {
	   is_meta = true
	}
	
	if (is_meta) {
	   metaList.add (lex)
	   if (debug) println ("$here: added lex '$lex'")
	}
	
	if (is_meta && (lex is TokenEndOfLine)){
	   is_meta = false
	   if (debug) println ("$here: meta set to false")
	}
	
    }
    
    println ("$here: output metaList "+ fullnameListOfLexemeList(metaList))
    
    exiting(here)
    return metaList
}

fun provideTextLexemeList (caller: String) : List<Lexeme> {
    val here = functionName()
    entering(here, caller)

    val lex_l = provideLexemeList (here)

    var textList = mutableListOf<Lexeme>()
    var is_meta = false

    for (lex in lex_l) {
    	if (debug) println ("$here: for lex '$lex'")

	if (lex is TokenSharp) {
	   is_meta = true
	}
	
	if (is_meta && (lex is TokenEndOfLine)){
	   is_meta = false
	   if (debug) println ("$here: meta set to false")
	}

	if (! is_meta) {
	   textList.add (lex)
	   if (debug) println ("$here: added lex '$lex'")
	}
	
    }
    
    println ("$here: output textList "+ fullnameListOfLexemeList(textList))
    
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
// Record are rebuilt from Lexemes and Not Parsed Yet
// Need to interpolate variables

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

fun provideTreeMetaRecordList (caller: String) : List<TreeNode<String>> {
    val here = functionName()
    entering(here, caller)

// <TreeMetaRecordList> ::= { <TreeMetaRecord> }
// <TreeMetaRecord>     ::= Node(Record)-Leaf(value)
//         Source        Date
//           |            |
//        file_path   dd/mm/yyyy

    val lex_met_l = provideMetaLexemeList (here)
    var lex_met_s = teeStackOfTeeList (lex_met_l)
    
    var tree_l = mutableListOf<TreeNode<String>>()
    var Done = false
    
    while (! Done) {
      try {	  
      	var lex = lex_met_s.pop()
      	println ("$here: while lex '$lex'")
      	if (lex is TokenSharp) {
	  var (tree, lex_s) = leafedNodeAndStackOfLexemeMetaStack (lex_met_s, here)
	  tree_l.add(tree)
	  lex_met_s = lex_s
	  println ("$here: while added tree '$tree")	
	  println ("$here: while lex_met_s '$lex_met_s")	
        }
      }
      catch (e:java.util.EmptyStackException) {
        println ("$here: end of Stack reached")	
        Done = true
      }
    }
    println ("$here: output tree_l '$tree_l'")	

    exiting(here)
    return tree_l
}

fun provideBlockMetaTreeNode (caller: String) : TreeNode<String> {
    val here = functionName()
    entering(here, caller)

// <TreeMeta> ::= TreeMetaRecordList ::= { TreeMetaRecord }

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

fun leafedNodeAndStackOfLexemeMetaStack (lex_met_s: Stack<Lexeme>, caller: String): Pair<TreeNode<String>, Stack<Lexeme>> {
// Set up a Leafed Node (ex.: qm / z2....)
    val here = functionName()
    entering(here, caller)

    var node = TreeNode<String>("")
    var Done = false
    
    while (! Done) {
      try {
        var lex = lex_met_s.pop()
        println ("$here: while lex '$lex'")

      	when (lex) {
      	  is TokenEndOfLine -> {
	     Done=true
	     println ("$here: while EndOfLine reached")
	  }
     	  is KeywordWithDate -> {
	    var nod_nam = lex.name
	    node = TreeNode<String>(nod_nam)
	  }
     	  is KeywordWithFile -> {
	    var nod_nam = lex.name
	    node = TreeNode<String>(nod_nam)
	  }
	  is KeywordWithQmHash -> {
	    var nod_nam = lex.name
	    node = TreeNode<String>(nod_nam)
	  }
	  is KeywordWithString -> {
	    var nod_nam = lex.name
	    node = TreeNode<String>(nod_nam)
	  }
	  is KeywordWithZ2Hash -> {
	    var nod_nam = lex.name
	    node = TreeNode<String>(nod_nam)
	  }
     	  is KeywordWithInteger -> {
	    var nod_nam = lex.name
	    node = TreeNode<String>(nod_nam)
	  }
	  is KeywordWithPersonName -> {
	    var nod_nam = lex.name
	    node = TreeNode<String>(nod_nam)
	  }
	  is AuthorName -> {
	    var lea_val = lex.name
	    var leaf = TreeNode<String>(lea_val)
	    node.addChild(leaf)
	  }
	  is NextName -> {
	    var lea_val = lex.name
	    var leaf = TreeNode<String>(lea_val)
	    node.addChild(leaf)
	  }
	  is FilePath -> {
	    var lea_val = lex.name
	    var leaf = TreeNode<String>(lea_val)
	    node.addChild(leaf)
          }
	  is DateValue -> {
	    var lea_val = lex.value
	    var leaf = TreeNode<String>(lea_val)
	    node.addChild(leaf)
	  }
	  is QmHash -> {
	    var lea_val = lex.hash
	    var leaf = TreeNode<String>(lea_val)
	    node.addChild(leaf)
	  }
	  is Z2Hash -> {
	    var lea_val = lex.hash
	    var leaf = TreeNode<String>(lea_val)
	    node.addChild(leaf)
	  }
	  is Signature -> {
	    var lea_val = lex.value
	    var leaf = TreeNode<String>(lea_val)
	    node.addChild(leaf)
	  }
	  is Spot -> {
	    var lea_val = lex.value
	    var leaf = TreeNode<String>(lea_val)
	    node.addChild(leaf)
	  }
	  is Tic  -> {
	    var lea_val = lex.value
	    var leaf = TreeNode<String>(lea_val)
	    node.addChild(leaf)
	  }
	  is TokenDollar, is TokenVee, is TokenSharp, is TokenSpace -> {
	    println ("$here: lexeme skipped '$lex'")
	    TreeNode<String>("skipped")
	  }
	  else -> {
	    println ("$here: lexeme skipped '$lex'")
	    TreeNode<String>("skipped")
	  }
       }
       }
    catch (e:java.util.EmptyStackException) {Done = true }
    } 
    println ("$here: output node '$node'")
    println ("$here: output lex_met_s '$lex_met_s'")
	
    exiting(here)
    return Pair (node, lex_met_s)
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
