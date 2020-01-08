// https://kotlinlang.org/docs/reference/reflection.html

val x = 1

fun main() {
    println(::x.get()) // 1
    println(::x.name)  // x
}