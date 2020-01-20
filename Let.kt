fun processString(str: String) =
    str.let {
    	    when {
	    	 it.isEmpty() -> "Empty"
		 it.isBlank() -> "Blank"
		 else -> it.capitalize()
		 }
    }
   
fun main(args: Array<String>) {
    val s = processString("abc")
    println("s "+s)
}