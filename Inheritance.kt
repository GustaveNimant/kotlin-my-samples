// https://www.tutorialspoint.com/kotlin/kotlin_inheritance.htm

import java.util.Arrays

open class ABC{ 
 open fun think (){ 
  print("Hey!! i am thiking ") 
 } 
} 
class BCD: ABC(){ // inheritence happend using default constructor  
   override fun think() {
      print("I Am from Child\n")
   }
}

fun main(args: Array<String>) { 
 var  a=BCD() 
 a.think() 
}