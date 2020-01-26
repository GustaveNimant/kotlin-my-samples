// https://www.tutorialspoint.com/kotlin/kotlin_basic_types.htm

fun main(args: Array<String>) {
   val items = listOf(1, 2, 3, 4)
   println("First Element of our list----"+items.first())
   println("Last Element of our list----"+items.last())
   println("Even Numbers of our List----"+items.filter { it % 2 == 0 })   // returns [2, 4]
   
   val readWriteMap = hashMapOf("foo" to 1, "bar" to 2)
   println(readWriteMap["foo"])  // prints "1"
   
   val strings = hashSetOf("a", "b", "c", "c")
   println("My Set Values are"+strings)
}