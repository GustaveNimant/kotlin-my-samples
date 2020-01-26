/* https://kotlinlang.org/docs/reference/basic-syntax.html */

package com.javasampleapproach.kotlin.base64
 
import java.io.File
import java.util.Base64
 
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

// ---------- end of header ----------
fun read_input(caller: String): String {
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

fun ipms_api (files_stat: String, mfs_path: String, hash_equal: String, caller: String): String {
    val here = functionName()
    entering(here, caller)

    println ("$here: files_stat is $files_stat");
    println ("$here: mfs_path is $mfs_path");
    println ("$here: hash_equal is $hash_equal");

    notYetImplemented(here)
    val result = "Not yet translated"

    exiting(here)
    return result
}

fun ipms_local_mutable_resolve (mfs_path: String, caller: String): String {
    val here = functionName()
    entering(here, caller)
    println ("$here: input mfs_path is '$mfs_path'");
    
    mfs_path.replace ("^mfs:", "")
    println ("$here: mfs_path is $mfs_path");

    val mh = ipms_api ("files/stat", mfs_path, "&hash=1", here);
    
    println ("$here mh = $mh")

    val result = "Not translated '/ipfs/'.\$mh->{Hash}"

    exiting(here)
    return result
}

fun hashOfPath (str: String, caller: String): String {
    val here = functionName()
    entering(here, caller)
    println ("$here: input str is '$str'");
    
    var hash = ""

    if (Regex("""\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,6}""").containsMatchIn(str)) {
        println("$here: '$str' is an email address")
    }
    else if (Regex("""\d{3}-\d{3}-\d{4}""").containsMatchIn(str)) {
        println("$here: '$str' is a phone number")
    }
    
// ipfs
    else if (Regex("""ipfs:/(/.*)""").containsMatchIn(str)) {
    	 println("$here: '$str' is an ipfs://file")
    }	     
    else if (Regex("""(/ipfs/.*)""").containsMatchIn(str)) {
        println("$here: '$str' is a /ipfs/file")
    }
// ipms
    else if (Regex("""(\w+)\@ipms:(/.*)""").containsMatchIn(str)) {
        println("$here: '$str' is an ipms remote file nickname@ipms:/my/identity/public.yml ")
    }
    else if (Regex("""/ipms/(\w+)(/.*)""").containsMatchIn(str)) {
        println("$here: '$str' is an ipms nickname file /ipms/nickname/path")
    }
    
// ipns
  else if (Regex("""\w+:(/ipns/.*)""").containsMatchIn(str)) {
        println("$here: '$str' is an ipns file P2P request 127.0.0.1:8080")
    }

// mfs	
  else if (Regex("^(?:self@)?(?:mfs:|/files)(/.*)").containsMatchIn(str)) {
             println("$here: '$str' is a mfs file")
    }
    else if (Regex("""^(?:self@)?(?:mfs:|/files)(/.*)""").containsMatchIn(str)) {
        println("$here: '$str' is a self@mfs file")
    }
    else if (Regex("""(\w+)\@mfs:(/.*)""").containsMatchIn(str)) {
        println("$here: '$str' is a who@mfs file using keys")
    }
    else if (Regex("""(^(?:self@)?(?:mfs:|/files)(/.*))""").containsMatchIn(str)) {
        println("$here: '$str' is local mutable address of type:")
	println(" . self@mfs:/mfspath")
	println(" . mfs:/mfspath")
	println(" . /files/mfspath (webui)")

	val mfs_path ="Not yet done"	  
	hash = ipms_local_mutable_resolve("mfs:"+ mfs_path, here)
    }    
    else {	
    	 println("$here: '$str' is unknown")
    }

    return hash	
    exiting(here)
}

fun main(args: Array<String>) {
    val here = functionName()
    entering(here,"top")

    println("$here: enter \"phone number\" ex: \"phone: 123-456-7890\"")
    println("example : emilea@mfs:/my/files/some_file.txt")
    println("example : 127.0.0.1:8080/ipfs/my/files/some_file.txt")	
    println("example : nickname@ipms:/my/files/some_file.txt")
    println("example : ipms/nickname/my/files/some_file.txt")	
    println("example : peeridkey@mfs:/mfspath")	
    println("example : self@mfs:/mfspath")
    println("example : mfs:/mfspath")
    println("example : /files/mfspath (webui)")
    println("$here: enter a file name")

    val path = readInput(here)

    var hash = hashOfPath(path, here)

    println("$here : hash = $hash")

    exiting(here)
}

