// https://www.tutorialspoint.com/kotlin/kotlin_basic_types.htm


fun main(args: Array<String>) {
   val i:Int  = 2
   for (j in 1..4) 
   print(" "+j) // prints "1234"
   
   if (i in 1..10) { // equivalent of 1 < = i && i < = 10
      println("\nwe found your number --"+i)
   }
}