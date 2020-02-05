package my.lexeme

import java.io.File
import java.util.Stack
import java.lang.Character.MIN_VALUE as nullChar

import my.library.*

sealed class Lexeme ()
  data class KeywordWithPersonName (val name: String) : Lexeme ()
  data class KeywordWithDate (val name: String) : Lexeme ()
  data class KeywordWithQmHash (val name: String) : Lexeme ()
  data class KeywordWithZ2Hash (val name: String) : Lexeme () 
  data class KeywordWithString (val name: String) : Lexeme ()
  data class KeywordWithFile (val name: String) : Lexeme ()
  data class KeywordWithInteger (val name: String) : Lexeme ()

  data class Comment (val name: String) : Lexeme ()
  data class DateValue (val value: String) : Lexeme ()
  data class AuthorName (val name: String) : Lexeme ()
  data class NextName (val name: String) : Lexeme ()
  data class FilePath (val name: String) : Lexeme ()
  data class QmHash (val hash: String) : Lexeme ()
  data class Z2Hash (val hash: String) : Lexeme ()
  data class Signature (val value: String) : Lexeme ()
  data class Spot (val value: String) : Lexeme ()
  data class Tic (val value: String) : Lexeme ()	  

  data class TextRecordConstant (val record: String) : Lexeme ()
  data class TextStringConstant (val string: String) : Lexeme ()
  data class TextWordConstant (val word: String) : Lexeme ()
  data class TextVariableSubstituable (val variable: String) : Lexeme ()
	  
  object TokenUnknown : Lexeme ()
  object TokenSkipped : Lexeme ()

  object TokenEmptySharpedLine : Lexeme ()
  object TokenEmptyLine : Lexeme ()

  object TokenAt : Lexeme ()
  object TokenDollar : Lexeme ()
  object TokenSpace : Lexeme ()
  object TokenEndOfLine : Lexeme ()
  object TokenVee : Lexeme ()
  object TokenComma : Lexeme ()
  object TokenColon : Lexeme ()
  object TokenSemicolon : Lexeme ()
  object TokenSharp : Lexeme ()
  object TokenDot : Lexeme ()
  object TokenHyphen : Lexeme ()

fun fullnameOfLexeme (lexeme: Lexeme): String {
    val string = when (lexeme) {

	is AuthorName -> "AuthorName("+lexeme.name+")"
	is Comment -> "Comment("+lexeme.name+")"
	is DateValue -> "DateValue("+lexeme.value+")"
	is FilePath -> "FilePath("+lexeme.name+")"
	is KeywordWithZ2Hash -> "KeywordWithZ2Hash("+lexeme.name+")"
	is NextName -> "NextName("+lexeme.name+")"
	is QmHash -> "QmHash("+lexeme.hash+")"
	is Signature -> "Signature("+lexeme.value+")"	
	is Spot -> "Spot("+lexeme.value+")"
	is TextRecordConstant -> "TextRecordConstant("+lexeme.record+")"
	is TextStringConstant -> "TextStringConstant("+lexeme.string+")"
	is TextVariableSubstituable -> "TextVariableSubstituable("+lexeme.variable+")"	
	is TextWordConstant -> "TextWordConstant("+lexeme.word+")"
	is Tic -> "Tic("+lexeme.value+")"	
	is Z2Hash -> "Z2Hash("+lexeme.hash+")"

	is KeywordWithDate    -> "KeywordWithDate("+lexeme.name+")"
    	is KeywordWithFile    -> "KeywordWithFile("+lexeme.name+")"
    	is KeywordWithInteger -> "KeywordWithInteger("+lexeme.name+")"
    	is KeywordWithQmHash  -> "KeywordWithQmHash("+lexeme.name+")"
    	is KeywordWithString  -> "KeywordWithString("+lexeme.name+")"
        is KeywordWithPersonName -> "KeywordWithPersonName("+lexeme.name+")"

	TokenAt	-> "TokenAt"
	TokenColon	-> "TokenColon"
	TokenComma	-> "TokenComma"
	TokenDollar	-> "TokenDollar"
	TokenDot	-> "TokenDot"
	TokenEmptyLine  -> "TokenEmptyLine"
	TokenEmptySharpedLine -> "TokenEmptySharpedLine"
	TokenEndOfLine	-> "TokenEndOfLine"
	TokenHyphen	-> "TokenHyphen"
	TokenSemicolon	-> "TokenSemicolon"
	TokenSharp	-> "TokenSharp"
	TokenSkipped    -> "skipped "
	TokenSpace	-> "TokenSpace"
	TokenUnknown    -> "unknown "
	TokenVee	-> "TokenVee"
	}
    return string
}

fun isInMetaOfLexeme(lex: Lexeme, caller: String): Boolean {
    val here = functionName()
    entering(here, caller)

    println("$here: input lex '$lex'")
    val result = when (lex) {
	is TextRecordConstant  -> false
	is TextStringConstant  -> false
	is TextVariableSubstituable  -> false
	is TextWordConstant  -> false

	TokenAt	         -> true
	TokenColon	 -> true
	TokenComma	 -> true
	TokenDollar	 -> true
	TokenDot	 -> true
	TokenEndOfLine	 -> true
	TokenHyphen	 -> true
	TokenSemicolon	 -> true
	TokenSharp	 -> true
	TokenSpace	 -> true

	is AuthorName  -> true
	is Comment  -> true
	is DateValue  -> true
	is FilePath  -> true
	is KeywordWithZ2Hash  -> true
	is NextName  -> true
	is QmHash  -> true
	is Signature  -> true
	is Spot  -> true
	is Tic  -> true
	is Z2Hash  -> true

	is KeywordWithDate     -> true
    	is KeywordWithFile     -> true
    	is KeywordWithInteger  -> true
    	is KeywordWithQmHash   -> true
    	is KeywordWithString   -> true
        is KeywordWithPersonName  -> true

	TokenSkipped     -> true
	TokenUnknown     -> true
	TokenVee	 -> true
	TokenEmptyLine   -> true
	TokenEmptySharpedLine  -> true
    }
    exiting(here + " with result '$result'")
    return result
}

fun isInTextOfLexeme(lex: Lexeme, caller: String): Boolean {
    val here = functionName()
    entering(here, caller)

    println("$here: input lex '$lex'")
    val result = when (lex) {
	is TextRecordConstant  -> true
	is TextStringConstant  -> true
	is TextVariableSubstituable  -> true
	is TextWordConstant  -> true

	TokenAt		 -> true
	TokenColon	 -> true
	TokenComma	 -> true
	TokenDollar	 -> true
	TokenDot	 -> true
	TokenEndOfLine	 -> true
	TokenHyphen	 -> true
	TokenSemicolon	 -> true
	TokenSharp	 -> true
	TokenSpace	 -> true

	is AuthorName  -> false
	is Comment  -> false
	is DateValue  -> false
	is FilePath  -> false
	is KeywordWithZ2Hash  -> false
	is NextName  -> false
	is QmHash  -> false
	is Signature  -> false
	is Spot  -> false
	is Tic  -> false
	is Z2Hash  -> false

	is KeywordWithDate     -> false
    	is KeywordWithFile     -> false
    	is KeywordWithInteger  -> false
    	is KeywordWithQmHash   -> false
    	is KeywordWithString   -> false
        is KeywordWithPersonName  -> false

	TokenSkipped     -> false
	TokenUnknown     -> false
	TokenVee	 -> false
	TokenEmptyLine   -> false
	TokenEmptySharpedLine  -> false
    }
    exiting(here + " with result '$result'")
    return result
}

fun lexemeOfKeyword (keyword: String, caller: String) : Lexeme {
    val here = functionName()
    entering(here, caller)

    println("$here: input keyword: '$keyword'")

   var lexeme = when (keyword) {
       "Author" -> KeywordWithPersonName (keyword)
       "Date" -> KeywordWithDate (keyword)
       "Source" -> KeywordWithFile (keyword)
       "Signature" -> KeywordWithString (keyword)      
       "members" -> KeywordWithString (keyword)
       "mutable" -> KeywordWithFile (keyword)
       "parents" -> KeywordWithQmHash (keyword)
       "previous" -> KeywordWithQmHash (keyword)
       "next" -> KeywordWithString (keyword)
       "tic" -> KeywordWithInteger (keyword)       
       "qm" -> KeywordWithZ2Hash (keyword)
       "spot" -> KeywordWithInteger (keyword)       
       else -> {
       	    val message = "$here: Error unknown Keyword '$keyword'"
	    throw Exception(message)
	}
  }
  
  if (isTrace(here)) println ("$here: output lexeme '$lexeme'")	
  exiting(here)
  return lexeme
 }

fun isKeywordWithOfLexeme(lex: Lexeme, caller: String): Boolean {
    val here = functionName()
    entering(here, caller)

    println("$here: input lex '$lex'")
    val result = when (lex) {
    	is KeywordWithZ2Hash -> true 
	is KeywordWithDate -> true     
    	is KeywordWithFile -> true 
    	is KeywordWithInteger -> true 
    	is KeywordWithQmHash -> true  
    	is KeywordWithString -> true   
        is KeywordWithPersonName -> true
	else -> false
    }
    exiting(here + " with result '$result'")
    return result
}

fun isTokenOfChar(cha: Char, caller: String) : Boolean {
    val here = functionName()
    entering(here, caller)

    if (isTrace(here)) println("$here: input cha '$cha'")
    val result = when (cha) {
		'#' -> true
		'$' -> true
		' ' -> true 
		'\n' -> true
		'/' -> true
		',' -> true
		':' -> true
		';' -> true
		'.' -> true
		'-' -> true
		'a','z' -> true
		else -> false
    }
    
  if (isTrace(here)) if (isTrace(here)) println ("$here: output result '$result'")	
  exiting(here)
  return result
 }

fun nextWordOfString(pos:Int, lin: String, caller: String): String {
    val here = functionName()
    entering(here, caller)

    println("$here: input pos '$pos'")
    println("$here: input lin '$lin'")
    
    val str = lin.substring(pos)
    var word = ""    
    for (c in str){
	  if (isDebug(here)) println("$here: c '$c'")
	  if (isTokenOfChar(c, here)) {break}
	  word = word.plus(c.toString())
    }

    assert (word.isNotEmpty())
    
    if (isTrace(here)) println("$here: output word '$word'")
    exiting(here)
    return word
}

fun stringValueOfLexeme (lexeme: Lexeme): String {
    val string = when (lexeme) {

	is AuthorName -> lexeme.name
	is Comment -> lexeme.name
	is DateValue -> lexeme.value
	is FilePath -> lexeme.name
	is KeywordWithZ2Hash -> lexeme.name
	is NextName -> lexeme.name
	is QmHash -> lexeme.hash
	is Signature -> lexeme.value	
	is Spot -> lexeme.value
	is TextRecordConstant -> lexeme.record
	is TextStringConstant -> lexeme.string
	is TextVariableSubstituable -> lexeme.variable	
	is TextWordConstant -> lexeme.word
	is Tic -> lexeme.value	
	is Z2Hash -> lexeme.hash

	is KeywordWithDate    -> lexeme.name
    	is KeywordWithFile    -> lexeme.name
    	is KeywordWithInteger -> lexeme.name
    	is KeywordWithQmHash  -> lexeme.name
    	is KeywordWithString  -> lexeme.name
        is KeywordWithPersonName -> lexeme.name

	TokenAt         -> "@"
	TokenColon	-> ":"	
	TokenComma	-> ","
	TokenDollar	-> "\$"
	TokenDot	-> "."
	TokenEmptyLine  -> ""
	TokenEmptySharpedLine -> ""
	TokenEndOfLine	-> "\n"
	TokenHyphen	-> "-"
	TokenSemicolon	-> ";"
	TokenSharp	-> "#"
	TokenSpace	-> " "
	TokenVee	-> "v"
	TokenUnknown    -> "unknown"
	TokenSkipped    -> "skipped"
	}
    return string
}

fun tokenOfChar(cha: Char, pos: Int, lin: String, caller: String) : Lexeme {
    val here = functionName()
    entering(here, caller)

    println("$here: input cha '$cha'")

    val token = when (cha) {
    		'@' -> TokenAt
		'#' -> TokenSharp
		'$' -> TokenDollar
		' ' -> TokenSpace
		'\n' -> TokenEndOfLine
		'v' -> TokenVee
		',' -> TokenComma
		':' -> TokenColon
		';' -> TokenSemicolon
		'.' -> TokenDot	
		'-' -> TokenHyphen
	else -> {
             val message = "$here: Error unknown Character '$cha' at position $pos of line '$lin'"
    	     throw Exception(message)
	     	}
	}

	if (isTrace(here)) println("$here: output token '$token'")
	exiting(here)
	return token
}

