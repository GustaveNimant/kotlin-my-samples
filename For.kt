// https://www.tutorialspoint.com/kotlin/kotlin_control_flow.htm

fun main(args: Array<String>) {
   val items = listOf(1, 22, 83, 4)	
   for (i in items) println("values of the array"+i)

   for ((index, value) in items.withIndex()){ 
    	println("the element at $index is $value") 
	}    
}