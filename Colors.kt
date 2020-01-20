sealed class Color () {
  sealed class Dark (): Color() {
    class DarkRed : Color()
    class DarkBlue : Color()
    }   
  sealed class Light (): Color () {
    class LightRed : Color()
    class LightBlue : Color()
    }
}   

fun eval(c: Color) =
        when (c) {
	    is Color.Dark -> println("Dark")
            is Color.Dark.DarkRed -> println("Dark Red")
            is Color.Dark.DarkBlue -> println("Dark Blue")

	    is Color.Light -> println("Light")
            is Color.Light.LightRed -> println("Light Red")
            is Color.Light.LightBlue -> println("Light Blue")
}

fun main(args: Array<String>) {
    val dr = Color.Dark.DarkRed()
    eval(dr)
}