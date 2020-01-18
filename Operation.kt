// https://www.tutorialkart.com/kotlin/kotlin-sealed-class/

sealed class Operation {
 
 class Add (val value: Int): Operation()
 class Substract (val value: Int): Operation()
 class Multiply (val value: Int): Operation()
 class Divide (val value: Int): Operation()
 object Increment : Operation()
 object Decrement : Operation()
}

fun execute(x: Int, op: Operation) =
    when (op) {
    	 is Operation.Add -> x + op.value
    	 is Operation.Substract -> x - op.value
    	 is Operation.Multiply -> x * op.value
    	 is Operation.Divide -> x / op.value
    	 Operation.Increment -> x + 1
    	 Operation.Decrement -> x - 1
}

fun main(args: Array<String>) {

    var ope = Operation.Add(3)
 
    var result = execute(4, ope)
 
    println("Result : "+result)
}
 
