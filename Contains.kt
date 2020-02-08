// https://www.tutorialspoint.com/kotlin/kotlin_basic_types.htm

fun main(args: Array<String>) {
   val cha_l = listOf('a', 'b', '\'')
   println("cha_l $cha_l")

   val cha = 'x'
   val result = cha_l.contains(cha)
   println ("result $result")


}