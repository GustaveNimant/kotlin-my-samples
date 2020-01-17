// export CLASSPATH=/usr/share/java/commons-codec.jar
// https://kotlinlang.org/docs/reference/enum-classes.html
// https://www.baeldung.com/kotlin-enum
// https://www.tutorialkart.com/kotlin/enum-classes-in-kotlin/

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
/**
* Kotlin Example to Enum Classes in Kotlin
*/
fun main(args: Array<String>) {
    val mobile1: Mobile = Mobile("IPhone", MobileColor.GOLD)
    val mobile2: Mobile = Mobile("SONY", MobileColor.BLACK)
    val mobile3: Mobile = Mobile("HUAWEI", MobileColor.RED)

// access enum variables
    println("The color of my "+mobile1.name+" is "+mobile1.color)
    println("The color of my "+mobile2.name+" is "+mobile2.color)
    println("The color of my "+mobile3.name+" is "+mobile3.color)

// access the value of the variable in Enum Object
    println(mobile1.color.toString() + " value is "+mobile1.color.value)
    println(mobile2.color.toString() + " value is "+mobile2.color.value)
    println(mobile3.color.toString() + " value is "+mobile3.color.value)
}

data class Mobile(val name:String, val color: MobileColor)

enum class MobileColor(val value: Int) {
    GOLD(0xffd323),
    SILVER(0xeaeaea),
    WHITE(0xffffff),
    BLACK(0x000000),
    RED(0xFF0000)
}
 