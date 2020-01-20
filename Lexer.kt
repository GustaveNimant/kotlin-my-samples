import java.io.File
import java.util.Stack
import java.lang.Character.MIN_VALUE as nullChar

var level = 0
var dots = "........|........|........|........|........|........|........|"

fun functionName():String {
    val sta = Thread.currentThread().stackTrace[2]
    val str = sta.getMethodName()
    return str	
}

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

fun read_input(caller:String):String {
    val here = functionName()
    entering(here, caller)
	
    val str = readLine().toString()
    exiting(here)
    return str
}

fun stringListOfFileName (nof: String) : MutableList<String> {

    val result = mutableListOf<String>()
 
    File(nof).useLines {
    	lines -> lines.forEach { result.add(it)}
	}

     return result
}

fun main(args: Array<String>) {
    val here = functionName()
    entering(here,"resolve")

    val stack = Stack<Char>()

//    println("Enter file name. Ex. 'current-block-test.yml'")
//    val fileName = read_input(here)
    val fileName = "current-block-test.yml"
    println("Entered file name : $fileName")

    println("Read the whole file as a List of String :")
    val stringList = stringListOfFileName (fileName)

    stringList.forEach {
    
    	var maxCount = it.length

    	println("input line: '" + it + "'")
    	println("maxCount:" + maxCount)


        it.forEach { c -> stack.push(c) }
	
	countChar = 0 
	stack.addAll(it)
    	while (stack.count > 1) {

       	      var currentChar = stack.pop()
       	      var nextChar = stack.pop()
       
	      countChar = countChar + 1
       	      println("currentChar # " + countChar + " " + currentChar)

//       	      when (currentChar) {
//       	      '#' -> readSharpLine (it)
//      	      }
    }
    }
    exiting(here)
}

