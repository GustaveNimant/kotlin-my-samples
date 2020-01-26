// https://kotlinlang.org/docs/reference/reflection.html

fun isOdd(x: Int) = x % 2 != 0
fun isOdd(s: String) = s == "brillig" || s == "slithy" || s == "tove"

fun <A, B, C> compose(f: (B) -> C, g: (A) -> B): (A) -> C {
    return { x -> f(g(x)) }
}

fun length(s: String) = s.length

val oddLength = compose(::isOdd, ::length)
val strings = listOf("a", "ab", "abc")

fun main(args: Array<String>) {
    val numbers = listOf(1, 2, 3)

    println("Odd numbers:")	    
    println(numbers.filter(::isOdd)) // refers to isOdd(x: Int)

    println("String with odd length:")
    println(strings.filter(oddLength))
}