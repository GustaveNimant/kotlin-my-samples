import java.io.File
import java.util.Stack
import java.lang.Character.MIN_VALUE as nullChar

fun characterStackOfString (str: String) : Stack<Char> {
    var stack = Stack<Char>()
    str.reversed().forEach { c -> stack.push(c)}
    return stack
}

fun main(args: Array<String>) {
    val str = "# some string"
    val stack = characterStackOfString (str)

    var done = false
    while(! stack.isEmpty()) {
    	    var cha = stack.pop() 
    	    println("cha '$cha'")
	    
    }
}

