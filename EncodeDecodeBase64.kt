// https://grokonez.com/kotlin/kotlin-encode-decode-fileimage-base64

//package com.javasampleapproach.kotlin.base64
 
import java.io.File
import java.util.Base64
import MyLibrary.*
 
fun encoder(filePath: String, caller: String): String{
    val here = functionName()
    entering(here, caller)

    println("$here: input filePath $filePath")
    
    val bytes = File(filePath).readBytes()
    val base64 = Base64.getEncoder().encodeToString(bytes)

    exiting(here)
    return base64
}

fun decoder(base64Str: String, pathFile: String, caller: String): Unit{
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

    val path = "/home/achadde/sources/kotlin/Hello.kt"
    println("$here : path to encode '$path'")

    val base64PathString = encoder(path, here)
    println("$here : Base64PathString = $base64PathString")	

    var file = "/home/achadde/sources/kotlin/file_decoded.txt" 
    decoder(base64PathString, file, here)
    println("$here : decoded file '$file'")
	
    exiting(here)
}

