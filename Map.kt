import MyLibrary.*

fun main(args: Array<String>) {
    val here = functionName()
    entering(here,"resolve")

/*
    val words = listOf("a", "abc", "ab", "def", "abcd")
    val withLength = words.associateWith { it.length }
    println(withLength.keys) // [a, abc, ab, def, abcd]
    println(withLength.values) // [1, 3, 2, 3, 4]
*/
    val chars = mapOf(97 to "a", 98 to "b", 120 to "x")
    println(chars)

    val user = mapOf("name" to "Luke", "age" to "23")
    println(user) 

    val items = HashMap<String, Int>()

    items["A"] = 90
    items["B"] = 80
    items["C"] = 70

    for ((k, v) in items) {
        println("$k = $v")
    }

    exiting(here)
}
