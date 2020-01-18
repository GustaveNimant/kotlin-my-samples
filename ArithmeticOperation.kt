// https://www.tutorialkart.com/kotlin/kotlin-sealed-class/

sealed class ArithmeticOperation {
 
 class Add(var a: Int, var b: Int): ArithmeticOperation()
 class Subtract(var a: Int, var b: Int): ArithmeticOperation()
 class Multiply(var a: Int, var b: Int): ArithmeticOperation()
 class Divide(var a: Int, var b: Int): ArithmeticOperation()
}

fun execute(op: ArithmeticOperation) =
    when (op) {
    	 is ArithmeticOperation.Add -> op.a + op.b
    	 is ArithmeticOperation.Subtract -> op.a - op.b
	 is ArithmeticOperation.Multiply -> op.a * op.b
	 is ArithmeticOperation.Divide -> op.a / op.b
}

fun main(args: Array<String>) {

    var operation = ArithmeticOperation.Add(4, 3)
 
    var result = execute(operation)
 
    println("Result : "+result)
}
 
