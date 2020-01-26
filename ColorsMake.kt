sealed class Color () 
    data class Red (val hue: String) : Color()
    data class Blue (val hue: String) : Color()

fun make(name: String, hue: String) = when (name) {
            "Red" -> Red (hue)
            "Blue" -> Blue (hue)
	    else -> throw Exception ("Error hue '$hue' for color '$name'") 
}

fun eval(c: Color) = when (c) {
            is Red -> c.hue
            is Blue -> c.hue
}

fun main(args: Array<String>) {
    val rd = make ("Red", "Dark")
    val h = eval(rd)
    println ("h "+h)
}

