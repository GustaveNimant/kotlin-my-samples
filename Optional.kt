import kotlin.math.*

sealed class Optional<out V> {
    // ...
    abstract fun isPresent(): Boolean
}
 
data class Some<out V>(val value: V) : Optional<V>() {
    // ...
    override fun isPresent(): Boolean = true
}
 
class None<out V> : Optional<V>() {
    // ...
    override fun isPresent(): Boolean = false
}

fun main(args: Array<String>) {

    val result: Optional<Double> = divide(1.0, 0.0)
    val message = when (result) {
    	is Some -> "Answer: ${result.value}"
    	is None -> "No result"
	}
	println(message)

}