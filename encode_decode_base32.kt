// https://www.programcreek.com/java-api-examples/index.php?api=org.apache.commons.codec.binary.Base32

package org.apache.commons.codec.binary.Base32
//import java.io.File
//import java.util.Base32
 
var level = 0
var dots = "........|........|........|........|........|........|........|"

fun notYetImplemented(fun_nam: String){
    throw Exception("function $fun_nam is not yet implemented")
}

fun functionName(): String {
    val sta = Thread.currentThread().stackTrace[2]
    val str = sta.getMethodName()
    return str	
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

fun inputRead(caller: String): String {
    val here = functionName()
    entering(here, caller)
	
    val str = readLine().toString()

    exiting(here+" with str = $str")
    return str
}

fun readInput(caller: String): String {
    val here = functionName()
    entering(here, caller)
	
    val str = readLine().toString()
    
    exiting(here)
    return str
}

fun encoder(str: String, caller: String): String{
    val here = functionName()
    entering(here, caller)

    println("$here: input str $str")

    val str_base32 = Base32.encode(str)

    exiting(here)
    return str_base32
}

fun decoder(base32Str: String, caller: String): Unit{
    val here = functionName()
    entering(here, caller)
    
    println("$here: input base32Str $base32Str")
	
    val str = Base32.decode(base32Str)

    exiting(here)
    return str
}

fun main(args: Array<String>) {
    val here = functionName()
    entering(here,"top")

    val str = "/home/achadde/sources/kotlin/hello.kt"
    println("$here : string to encode '$str'")

    val base32String = encoder(str, here)
    println("$here : Base32String = $base32String")	

    val str_dec = decoder(base32PathString, here)

    println("$here : decoded string '$str_dec'")
	
    exiting(here)
}

