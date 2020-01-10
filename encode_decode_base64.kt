// https://grokonez.com/kotlin/kotlin-encode-decode-fileimage-base64

package com.javasampleapproach.kotlin.base64
 
import java.io.File
import java.util.Base64
 
var level = 0
var dots = "........|........|........|........|........|........|........|"

fun notYetImplemented(fun_nam:String){
    throw Exception("function $fun_nam is not yet implemented")
}

fun functionName():String {
    val sta = Thread.currentThread().stackTrace[2]
    val str = sta.getMethodName()
    return str	
}

fun entering(here:String, caller:String):Unit {
    level = level + 1
    if (level > 70) {
       println ("Error maximum number of nesting levels reached")
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

    exiting(here+" with str = $str")
    return str
}

fun readInput(caller:String):String {
    val here = functionName()
    entering(here, caller)
	
    val str = readLine().toString()
    
    exiting(here)
    return str
}

fun encoder(filePath: String, caller:String): String{
    val here = functionName()
    entering(here, caller)

    println("$here: input filePath $filePath")
    
    val bytes = File(filePath).readBytes()
    val base64 = Base64.getEncoder().encodeToString(bytes)

    exiting(here)
    return base64
}

fun decoder(base64Str: String, pathFile: String, caller:String): Unit{
    val here = functionName()
    entering(here, caller)
    
    println("$here: input base64Str $base64Str")
    println("$here: pathFile $pathFile")
	
    val imageByteArray = Base64.getDecoder().decode(base64Str)
    File(pathFile).writeBytes(imageByteArray)

    exiting(here)
}

fun main(args: Array<String>) {
    val here = functionName()
    entering(here,"top")

    val path = "/home/achadde/sources/kotlin/hello.kt"
    println("$here : path to encode '$path'")

    val base64PathString = encoder(path, here)
    println("$here : Base64PathString = $base64PathString")	

    var file = "/home/achadde/sources/kotlin/file_decoded.txt" 
    decoder(base64PathString, file, here)
    println("$here : decoded file '$file'")
	
    exiting(here)
}

