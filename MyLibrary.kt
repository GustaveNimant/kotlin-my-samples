package MyLibrary

import java.io.File
import java.util.Base64
import java.util.Stack
import java.lang.Character.MIN_VALUE as nullChar

class MyLibrary

// beginning of library

val debug = false
var level = 0
var dots = "........|........|........|........|........|........|........|"

fun entering(here:String, caller:String):Unit {
    level = level + 1
    if (level > 70) {
       println ("Errot maximum number of nesting levels reached")
    } else {
        var points = dots.substring(0, level)
        println("$points Entering  in $here called by $caller")
    }
}

fun exiting(here:String):Unit {
    var points = dots.substring(0, level)
    println("$points Exiting from $here")
    level = level - 1	
}

fun functionName():String {
    val sta = Thread.currentThread().stackTrace[2]
    val str = sta.getMethodName()
    return str	
}

fun countOfCharOfString (cha: Char, str:String, caller:String) : Int {
    val here = functionName()
    entering(here, caller)

    var count = 0

    for (c in str) {
        if (c == cha){count = count + 1}
    }	
		
    exiting(here + " with count " + count.toString())
    return count
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

fun notYetImplemented(fun_nam: String){
    throw Exception("Error: function '$fun_nam' is not yet implemented")
}

fun read_input(caller:String):String {
    val here = functionName()
    entering(here, caller)
	
    val str = readLine().toString()
    
    exiting(here)
    return str
}

fun write_output(fileName:String, content: String, caller:String) {
    val here = functionName()
    entering(here, caller)
	
    File(fileName).bufferedWriter().use { out -> out.write(content)}
    
    exiting(here)
}

fun nextWordOfEndCharOfString(del: Char, str: String, caller: String): String {
    val here = functionName()
    entering(here, caller)

    println("$here: input del '$del'")
    println("$here: input str '$str'")
    
    var word = ""    
    for (c in str){
	  println("$here: c '$c'")
	  if (c.equals(del)) {break}
	  word = word.plus(c.toString())
    }

    assert (word.isNotEmpty())
    
    println("$here: output word '$word'")
    exiting(here)
    return word
}

fun lineStackOfLineList (str_l: List<String>) : Stack<String> {
    var stack = Stack<String>()
    str_l.reversed().forEach { l -> stack.push(l) }
    return stack
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

fun stringOfGlueOfStringList (glue: String, str_l: List<String>) : String {
 val str = str_l.fold("", {acc, s -> acc + s + glue })
 return str 
}

fun stringOfStringList (str_l: List<String>) : String {
 val str = str_l.fold("", {acc, s -> acc + s })
 return str 
}

// end of library
