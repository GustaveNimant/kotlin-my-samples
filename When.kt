// https://www.tutorialspoint.com/kotlin/kotlin_control_flow.htm

fun main(args: Array<String>) {
   val x:Int = 5
   when (x) {
      1 -> print("x == 1")
      in 2..6 -> print("x == 2 or 3 or 4 or 5 or 6\n")
      
      else -> {
         print("x is neither 1 nor 2\n")
      }

  }// when
}