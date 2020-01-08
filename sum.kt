/* https://kotlinlang.org/docs/reference/basic-syntax.html */

fun sum(a: Int, b: Int): Int {
    return a + b
}

fun printSum(a: Int, b: Int): Unit {
    val s = "is"
    val somme:Int = sum (a, b)
    println("sum of $a and $b ${s.replace ("is", "was")} ${somme}")
}	
	
fun main() {
    printSum(3, 5)
}