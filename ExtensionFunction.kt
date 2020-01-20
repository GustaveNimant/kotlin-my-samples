// Extends MutableList<T> by function swap

open class Shape

class Rectangle: Shape()

fun Shape.getName() = "Shape"
fun Rectangle.getName() = "Rectangle"

fun printClassName(s: Shape) {
    println(s.getName())
}

class Example {
    fun printFunctionType() { println("Class method") }
}
fun Example.printFunctionType() { println("Extension function") }

fun main(args: Array<String>) {
    fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
    	val tmp = this[index1] // 'this' corresponds to the list
    	this[index1] = this[index2]
    	this[index2] = tmp
	}

	val list = mutableListOf(1, 2, 3)
	println("list "+list)
	list.swap (0, 2)
	println("extension with swap")
	println("list "+list)

	println("extension with getName not applied due to signature")
	printClassName(Rectangle())

	println("extension with printFunctionType : member function wins")
	Example().printFunctionType()

}