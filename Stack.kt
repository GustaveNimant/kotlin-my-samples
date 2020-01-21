import java.io.File
import java.util.Stack
import java.lang.Character.MIN_VALUE as nullChar

fun main(args: Array<String>) {
    val string = "# some string"
    val stack = Stack<Char>()
    var Done = false
    
    string.reversed().forEach { c ->
    		   println("c "+c )
    		   stack.push(c) }

    var currentChar = stack.pop()
    var nextChar = stack.pop()
    stack.push(nextChar)
    var previousChar = nullChar

    while ( ! Done) {
       println()
       println("previousChar '" + previousChar + "'")
       println("currentChar '" + currentChar + "'")
       println("nextChar '" + nextChar + "'")

       previousChar = currentChar
       currentChar = nextChar
       try {
             nextChar = stack.pop() 
       }
       catch (e: java.util.EmptyStackException) {
       	     Done = true			
       }
    }
}

