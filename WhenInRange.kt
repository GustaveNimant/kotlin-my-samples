fun main(args: Array<String>) {
   val c = 'd'
   when (c) {
      'a' -> print("c '$c'")
      in 'a'..'z' -> print("c '$c' is in range")
      
      else -> { // Note the block
         print("c is neither 1 nor 2\n")
      }
}
}