import MyLibrary.*

fun main(args: Array<String>) {
    val here = functionName()
    entering(here,"resolve")
    
    val words = listOf("a", "abc", "ab", "def", "abcd")
    val withLength = words.associateWith { it.length }
    println(withLength.keys) // [a, abc, ab, def, abcd]
    println(withLength.values) // [1, 3, 2, 3, 4]

    exiting(here)
}
