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

fun getEmail(str:String, caller:String):String? {
    val here = functionName()
    entering(here, caller)

    val emailPattern = Regex("""\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,6}""")
    val email:String? = emailPattern.find(str)?.value

    exiting(here+" with "+email)	
    return email
}

fun getPhone(str:String, caller:String):String? {
    val here = functionName()
    entering(here, caller)

// "phone: 123-456-7890"
    val phonePattern = Regex("""\d{3}-\d{3}-\d{4}""")
    val phone:String? = phonePattern.find(str)?.value

    exiting(here+" with "+phone)	
    return phone
}

fun readInput(caller:String):String {
    val here = functionName()
    entering(here, caller)
	
    val str = readLine().toString()
    
    exiting(here)
    return str
}

fun main(args: Array<String>) {
    val here = functionName()
    entering(here,"top")

    println("$here: enter \"phone number\" ex: \"phone: 123-456-7890\"")
    val str = readInput(here)	
    val phone = getPhone(str, here) 
    println("$here: my phone is '"+phone+"'")
    
    exiting(here)
}

