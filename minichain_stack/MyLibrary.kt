package my.library

import java.io.File
import java.util.Base64
import java.util.Stack
import java.lang.Character.MIN_VALUE as nullChar

class MyLibrary

// beginning of library

data class pairString (val first: String, val second: String)

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

var debug = false
var level = 0
var dots = "........|........|........|........|........|........|........|"

fun characterListOfFileName (fil_nam: String) : List<Char> {
    val here = functionName()
    
    val cha_l = mutableListOf<Char>()
    val str_l = lineListOfFileName (fil_nam, here)
    str_l.forEach({l ->
    		  l.forEach ({c -> cha_l.add(c)})})
    return cha_l
}

fun characterStackOfFileName (fil_nam: String) : Stack<Char> {
    val cha_l = characterListOfFileName (fil_nam)
    val cha_s = teeStackOfTeeList(cha_l)
    return cha_s
}

fun characterStackOfString (str: String) : Stack<Char> {
    var stack = Stack<Char>()
    str.reversed().forEach { c -> stack.push(c)}
    return stack
}

fun countOfCharOfString (cha: Char, str: String, caller: String) : Int {
    val here = functionName()
    entering(here, caller)

    var count = 0

    for (c in str) {
        if (c == cha){count = count + 1}
    }	
		
    exiting(here + " with count " + count.toString())
    return count
}

fun entering(here: String, caller: String):Unit {
    level = level + 1
    if (level > 70) {
       println ("Error maximum number of nesting levels reached")
    } else {
        var points = dots.substring(0, level)
        println("$points Entering  in $here called by $caller")
    }
}

fun exiting(here: String):Unit {
    var points = dots.substring(0, level)
    println("$points Exiting from $here")
    level = level - 1	
}

fun fatalErrorPrint (expecting: String, found: String, cure: String, where: String):Unit {
  val message: String = "\n$where: Expecting $expecting\n" + "$where: Found '$found'\n" + "$where: Cure: $cure\n"

  throw Exception(message)
}

fun fileExtensionOfFileName (fil_nam:String): String {
    val file = File(fil_nam)
    val ext = file.extension
    return ext
}

fun functionName(): String {
    val sta = Thread.currentThread().stackTrace[2]
    val str = sta.getMethodName()
    return str	
}

fun inputRead(caller: String): String {
    val here = functionName()
    entering(here, caller)
	
    val str = readLine().toString()
    
    exiting(here)
    return str
}

fun isAlphabeticalOfChar(cha: Char, caller: String): Boolean {
    val here = functionName()
    entering(here, caller)
    
    val pattern = Regex("[a-zA-Z_]")
    println("$here: input cha '$cha'")
    val result = pattern.matches(cha.toString())

    exiting(here + " with result '$result'")
    return result
}

fun isAlphanumericalOfChar(cha: Char, caller: String): Boolean {
    val here = functionName()
    entering(here, caller)
    
    val pattern = Regex("[a-zA-Z_0-9]")
    println("$here: input cha '$cha'")
    val result = pattern.matches(cha.toString())

    exiting(here + " with result '$result'")
    return result
}

fun isNumericalOfChar(cha: Char, caller: String): Boolean {
    val here = functionName()
    entering(here, caller)
    
    val pattern = Regex("[0-9]")
    println("$here: input cha '$cha'")
    val result = pattern.matches(cha.toString())

    exiting(here + " with result '$result'")
    return result
}

fun lineListOfFileName (nof: String, caller: String) : MutableList<String> {
    val here = functionName()
    entering(here, caller)

    println("$here: input nof '$nof'")

    val result = mutableListOf<String>()
 
    File(nof).useLines {
    	lines -> lines.forEach { result.add(it)}
	}

  println ("$here: output result '$result'")	
  exiting(here)
  return result
}

fun lineStackOfLineList (str_l: List<String>) : Stack<String> {
    var stack = Stack<String>()
    str_l.reversed().forEach { l -> stack.push(l) }
    return stack
}


fun nextWordAndStackOfEndCharOfCharacterStack(del: Char, cha_s: Stack<Char>, caller: String): Pair<String, Stack<Char>> {
    val here = functionName()
    entering(here, caller)

    if (debug) println("$here: input del '$del'")
    if (debug) println("$here: input cha_s '$cha_s'")
    var done = false
    var word = ""
    var cha = cha_s.pop()
    
    while (! done){
      if (debug) println("$here: cha '$cha'")
      word = word.plus(cha.toString())
      try {
      	  cha = cha_s.pop()
          done = cha.equals(del)
	  if (done) {cha_s.push(cha)}
      }
      catch (e: java.util.EmptyStackException) {
            done = true			       
      }
    }

    assert (word.isNotEmpty())
    
    if (debug) println("$here: output word '$word'")
    if (debug) println("$here: output cha_s '$cha_s'")
    exiting(here)
    return Pair (word, cha_s)
}

fun WordAndStackOfEndCharOfStack(del: Char, input_cha_s: Stack<Char>, caller: String) : Pair<String, Stack<Char>> {
    val here = functionName()
    entering(here, caller)

// input :
    println("$here: input_cha_s '$input_cha_s'")
    println("$here: input del '$del'")

// output :
    var Done = false
    
    var word = ""    
    while (! Done){
      try {
    	var cha = input_cha_s.pop()	
      	println("$here: cha '$cha'")
	  if (cha.equals(del)) {break}
	  word = word.plus(cha.toString())
    	  }
      catch (e: java.util.EmptyStackException) {
        val message = "$here: Error Character '$del' is missing in Stack"
        throw Exception(message)
      }
    }
    assert (word.isNotEmpty())
    
    println("$here: output word '$word'")
    println("$here: output_cha_s '$input_cha_s'")
    exiting(here)
    return Pair (word, input_cha_s)
}

fun nextWordOfEndCharOfString(del: Char, str: String, caller: String): String {
    val here = functionName()
    entering(here, caller)

    println("$here: input del '$del'")
    println("$here: input str '$str'")
    
    var word = ""    
    for (c in str){
	  if (debug) println("$here: c '$c'")
	  if (c.equals(del)) {break}
	  word = word.plus(c.toString())
    }

    assert (word.isNotEmpty())
    
    println("$here: output word '$word'")
    exiting(here)
    return word
}

fun notYetImplemented(fun_nam: String){
    throw Exception("Error: function '$fun_nam' is not yet implemented")}

fun printStringList (str_l: List<String>) {
    val content = stringOfGlueOfStringList ("\n", str_l)
    println (content)
}

fun provideAnyFileNameOfWhat(what: String, caller: String): String {
    val here = functionName()
    entering(here, caller)

    val whatLc = what.toLowerCase()
    var anyFileName =
      when (whatLc) {
        "lexeme" -> "test.lex"
	"block" -> what+".yml"
	"yml" -> "test.yml"
        else -> what+".txt"
      }
    println("$here: enter file name for '$what'. Default '$anyFileName'")
    val any_f = inputRead(here)
    if (! any_f.isNullOrBlank()) {
        anyFileName = any_f
    }
    println("$here: input File name is '$anyFileName'")

    exiting(here)
    return anyFileName
}

fun stringOfGlueOfStringList (glue: String, str_l: List<String>) : String {
 val str = str_l.fold("", {acc, s -> acc + s + glue })
 return str 
}

fun stringOfStringList (str_l: List<String>) : String {
 val str = str_l.fold("", {acc, s -> acc + s })
 return str 
}

fun <T> teeStackOfTeeList (tee_l: List<T>): Stack<T> {
    var stack = Stack<T>()
    tee_l.reversed().forEach { t -> stack.push (t)}
    return stack
}

fun <T> teeStackFromTeeOfTeeStack (tee:T, tee_s: Stack<T>): Stack<T> {
// return the subStack where all elements untill tee (excluded) are poped-off

   var cha = tee_s.pop()
   var done = false
   
   if (cha == tee) {return tee_s}

   while (! done) {
      try {
      	cha = tee_s.pop()
	done = tee == cha
      }
      catch (e: java.util.EmptyStackException) {
            done = true			       
      }
    }
	
    return tee_s
}

fun unknownCharacterOfMessage (mes: String?, caller: String): Char? {
    val here = functionName()
    entering(here, caller)

    val messageType : String?
    
    println("$here: input mes '$mes'")
    
    var unknownCharacter = try {
    	messageType = mes?.substring (0, 36)
	println("$here: messageType '$messageType'")
	mes?.get (38)
	}
    catch (e: Exception) {nullChar}

    println("$here: output character '$unknownCharacter'")
    exiting(here)

    return unknownCharacter
}

fun wordListOfString (str: String): List<String> {
    val trimedString = str.trim(' ')    
    val regex = Regex("""\s+""")

    val result = trimedString.split(regex)

    return result
}

fun wordStackOfLine (lin: String) : Stack<String> {
    var stack = Stack<String>()
    var wor_l = wordListOfString (lin)
    wor_l.reversed().forEach { w -> stack.push(w)}
    return stack
}

fun outputWrite(fileName: String, content: String, caller: String) {
    val here = functionName()
    entering(here, caller)
	
    File(fileName).bufferedWriter().use { out -> out.write(content)}
    
    exiting(here)
}

// end of library
