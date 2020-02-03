// https://www.tutorialspoint.com/kotlin/kotlin_basic_types.htm

fun main(args: Array<String>) {
   var str ="a string ending with space    "
   println("str '$str'")
   
   val result = str.dropLastWhile({c -> c.equals(' ')})
   println ("result '$result'")
}