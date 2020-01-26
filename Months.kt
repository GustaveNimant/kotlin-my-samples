// export CLASSPATH=/usr/share/java/commons-codec.jar
// https://kotlinlang.org/docs/reference/enum-classes.html
// https://www.baeldung.com/kotlin-enum
// https://www.tutorialkart.com/kotlin/enum-classes-in-kotlin/

implicit MyLibrary.*

fun functionName(): String {
    val sta = Thread.currentThread().stackTrace[2]
    val str = sta.getMethodName()
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

    println(Months.January)                            //prints January
    println("values().size are "+Months.values().size) //prints 3
    println("valueOf is "+Months.valueOf("March"))     //prints March
    println("values are :")

    for (enum in Months.values()) {
        println(enum.name)
    }

    println("throws exception:")
    println(Months.valueOf("Mar")) //throws java.lang.IllegalArgumentException: No enum constant Months.Mar
}

enum class Months{
    January,
    February,
    March
}
