package my.lexeme.list.stack

import java.io.File
import java.util.Stack
import java.lang.Character.MIN_VALUE as nullChar

import my.library.*
import my.lexeme.*

fun lexemeListOfAuthoredStack (input_cha_s, caller: String) : List<Lexeme> {
// Ex: Author: untel@here$'
    val here = functionName()
    entering(here, caller)

// input :
    println("$here: input_cha_s '$input_cha_s'")

// output :
    val lexemeList = mutableListOf<Lexeme>()

    var Done = false
    
    while (!Done) {
      try {
    	var cha = input_cha_s.pop()	
 	when (cha) {
	  'A' -> {
	          input_cha_s.push(cha)
		  val (word, output_cha_s) = WordAndStackOfEndCharOfStack(':', input_cha_s)
		  var lexeme = lexemeOfKeyword (word, here)
		  lexemeList.add(lexeme)
		  input_cha_s = output_cha_s
		  }		  
		  }
 	  else -> {
		     val message = "$here: Error unknown current Character '$cha'"
		     throw Exception(message)
	          }
    }

   println("$here: output lexemeList "+lexemeList)
   exiting(here)
   return lexemeList
}

fun lexemeListOfDollaredStack (input_cha_s, caller: String) : List<Lexeme> {
// Ex: $Author: michel$'
    val here = functionName()
    entering(here, caller)

// input :
    println("$here: input_cha_s '$input_cha_s'")

// output :
    val lexemeList = mutableListOf<Lexeme>()

    var Done = false
    
    while (!Done) {
      try {
    	var cha = input_cha_s.pop()	
 	when (cha) {
	  '$' -> {
	          val lexeme = tokenOfChar(cha, here)
	          lexemeList.add(lexeme)
		  }
         ' ' -> {
	          val lexeme = tokenOfChar(cha, here)
	          lexemeList.add(lexeme)
		  }
	  'A' -> {
	          input_cha_s.push(cha) 
	          val lex_l = lexemeListOfAuthoredStack(input_cha_s, here)
	  	  lexemeList.addAll(lex_l)
		  }
	  'D' -> {
	          input_cha_s.push(cha) 
	          val lex_l = lexemeListOfDatedStack(input_cha_s, here)
	  	  lexemeList.addAll(lex_l)
		  }
	  'm' -> {
	      	 val nex_cha = input_cha_s.pop()
		 when (nex_cha) {
		   'u' ->
		       input_cha_s.push(nex_cha) 
	               input_cha_s.push(cha) 
	               val lex_l = lexemeListOfMutabledStack(input_cha_s, here)
	  	       lexemeList.addAll(lex_l)
		    }
		   'e' ->
		       input_cha_s.push(nex_cha) 
	               input_cha_s.push(cha) 
	               val lex_l = lexemeListOfMemberedStack(input_cha_s, here)
	  	       lexemeList.addAll(lex_l)
		    }
		  }
	  'n' -> {
	          input_cha_s.push(cha) 
	          val lex_l = lexemeListOfNextedStack(input_cha_s, here)
	  	  lexemeList.addAll(lex_l)
		  }
	  'p' -> {
	      	 val nex_cha = input_cha_s.pop()
		 when (nex_cha) {
		   'a' ->
		       input_cha_s.push(nex_cha) 
	               input_cha_s.push(cha) 
	               val lex_l = lexemeListOfParentsedStack(input_cha_s, here)
	  	       lexemeList.addAll(lex_l)
		    }
		   'r' ->
		       input_cha_s.push(nex_cha) 
	               input_cha_s.push(cha) 
	               val lex_l = lexemeListOfPreviousedStack(input_cha_s, here)
	  	       lexemeList.addAll(lex_l)
		    }
		  }
	  'q' -> {
	          input_cha_s.push(cha) 
	          val lex_l = lexemeListOfQmedStack(input_cha_s, here)
	  	  lexemeList.addAll(lex_l)
		  }
	  's' -> {
	          input_cha_s.push(cha) 
	          val lex_l = lexemeListOfSpotedStack(input_cha_s, here)
	  	  lexemeList.addAll(lex_l)
		  }
	  'S' -> {
	      	 val nex_cha = input_cha_s.pop()
		 when (nex_cha) {
		   'i' ->
		       input_cha_s.push(nex_cha) 
	               input_cha_s.push(cha) 
	               val lex_l = lexemeListOfSignaturededStack(input_cha_s, here)
	  	       lexemeList.addAll(lex_l)
		    }
		   'o' ->
		       input_cha_s.push(nex_cha) 
	               input_cha_s.push(cha) 
	               val lex_l = lexemeListOfSourcedStack(input_cha_s, here)
	  	       lexemeList.addAll(lex_l)
		    }
		  }
	  't' -> {
	          input_cha_s.push(cha) 
	          val lex_l = lexemeListOfTicedStack(input_cha_s, here)
	  	  lexemeList.addAll(lex_l)
		  }
 	  else -> {
		     val message = "$here: Error unknown current Character '$cha'"
		     throw Exception(message)
	          }
    }

   println("$here: output lexemeList "+lexemeList)
   exiting(here)
   return lexemeList
}

fun lexemeListOfSharpedStack (input_cha_s: Stack<Char>, caller: String) : List<Lexeme> {
// Source: /my/perl/script/kwextract.pl,v$
    val here = functionName()
    entering(here, caller)

// input :
    println("$here: input_cha_s '$input_cha_s'")

// output :
    val lexemeList = mutableListOf<Lexeme>()

    var Done = false
    
    while (!Done) {
      try {
    	var cha = input_cha_s.pop()	
 	when (cha) {
	'#' -> {
	  val lexeme = tokenOfChar(cha, here)
	  lexemeList.add(lexeme)
	}
	' ' -> {
	  val lexeme = tokenOfChar(cha, here)
	  lexemeList.add(lexeme)
	}
	'$' -> {
	  input_cha_s.push(cha) 
	  val lex_l = lexemeListOfDollaredStack(input_cha_s, here)
	  lexemeList.addAll(lex_l)
	}
// Remainder	
	'A' -> {
	    val str = lin.substring(position)
	    lexemeList.addAll (lexemeListOfAuthorLine (str, here))
	    Done = true
	    }
	'D' -> {
	    val str = lin.substring(position)
	    lexemeList.addAll (lexemeListOfDateLine (str, here))
	    Done = true
	    }
        'm' -> {
	    val str = lin.substring(position)
	    lexemeList.addAll (lexemeListOfMutableLine (str, here)) 
	    Done = true
	    }
        'n' -> {
	    val str = lin.substring(position)
	    lexemeList.addAll (lexemeListOfNextLine (str, here)) 
	    Done = true
	    }
	'p' -> {
	    val nextCharacter = lin.get(position + 1)
	    if (nextCharacter.equals ('a')){
	       val str = lin.substring(position)
	       lexemeList.addAll (lexemeListOfParentsLine (str, here)) 
	    }
	    else if (nextCharacter.equals ('r')){
	       val str = lin.substring(position)
	       lexemeList.addAll (lexemeListOfPreviousLine (str, here)) 
	    }
	    else {
	    	 val message = "$here: Error next character '$nextCharacter' should be 'a' or 'r'"
	    	 throw Exception(message)
	    }
	    Done = true  
	    }
        'q' -> {
	    val str = lin.substring(position)
	    lexemeList.addAll (lexemeListOfQmHashLine (str, here)) 
	    Done = true
	    }
        's' -> {
	    val str = lin.substring(position)
	    lexemeList.addAll (lexemeListOfSpotLine (str, here)) 
	    Done = true
	    }
        'S' -> {
	    val nextCharacter = lin.get(position + 1)
	    if (nextCharacter.equals ('i')){
	       val str = lin.substring(position)
	       lexemeList.addAll (lexemeListOfSignatureLine (str, here)) 
	    }
	    else if (nextCharacter.equals ('o')){
	       val str = lin.substring(position)
	       lexemeList.addAll (lexemeListOfSourceLine (str, here)) 
	    }
	    else {
	    	 val message = "$here: Error next character '$nextCharacter' should be 'i' or 'o'"
	    	 throw Exception(message)
	    }
	    Done = true  
	    }
        't' -> {
	    val str = lin.substring(position)
	    lexemeList.addAll (lexemeListOfTicLine (str, here)) 
	    Done = true
	    }
	    else -> {
	    	 val message = "$here: Error unexpected current character '$cha' at position $position"
	    	 throw Exception(message)
	    }
	    }
      }
      catch (e: java.lang.StringIndexOutOfBoundsException) {
	  	val previousCharacter = lin.get(position-1)
	  	if (previousCharacter.equals('$')) {
	     	   var lexeme = TokenEndOfLine
             	   lexemeList.add (lexeme)
	     	   if (debug) println("$here: setting End Of Line")
	     	   Done = true			
	     	}
		else {
		     val message = "$here: Error previous character '$previousCharacter' (position $position) should be '$'"
	    throw Exception(message)
		}
	  }
   }

   println("$here: output lexemeList: "+ fullnameListOfLexemeList(lexemeList))
   exiting(here)
   return lexemeList
}

