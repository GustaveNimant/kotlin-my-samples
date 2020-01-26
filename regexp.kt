/* https://kotlinlang.org/docs/reference/basic-syntax.html */

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
    exiting(here)
    return str
}

fun main(args: Array<String>) {
    val here = functionName()
    entering(here,"resolve")
    
    val addr = read_input(here)

    println("You have entered : $addr")
    val str = "^(?:self@)?(?:mfs:|/files)(/.*)"

    val emailPattern = Regex("""\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,6}""")

    val email:String? = emailParttern.find("this is my email emile.achadde@free.fr")?.value
    println(email)
    
    val regex = str.toRegex()  

    if (regex.matches(input = str)) {
       println("Ok")  	    
    }      
    else {
    	 println("Not Ok")		
    }  

    exiting(here)
}

