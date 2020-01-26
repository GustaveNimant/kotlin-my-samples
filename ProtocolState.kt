// export CLASSPATH=/usr/share/java/commons-codec.jar
// https://kotlinlang.org/docs/reference/enum-classes.html

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

fun main(args: Array<String>) {
    val here = functionName()
    entering(here,"top")

    val wai = ProtocolState.valueOf("WAITING")
    
    println("$here : ProtocolState "+wai)
    println("$here : signal is "+wai.signal())
    exiting(here)
}

   enum class ProtocolState {
    	 WAITING {
         override fun signal() = TALKING
    	 },

    	 TALKING {
         override fun signal() = WAITING
    	 };

    abstract fun signal(): ProtocolState
    }

