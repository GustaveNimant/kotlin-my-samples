import kotlin.math.*

sealed class Figure {

data class Point (val name: String, val x: Double, val y: Double): Figure() {
     fun print () {
    	  println ("..Point "+name)
	  println ("x ="+x+" y ="+y)
    }
}

data class Segment (val name: String, val origin: Point, val end: Point): Figure() {
     fun length(): Double {
	val dx = end.x - origin.x
	val dy = end.y - origin.y
	val result = sqrt (dx * dx + dy * dy)
	
	return result
     }

     fun print () {
    	  println (".Segment "+name)
	  println ("..origin =")
	  origin.print()
	  println ("..end =")
	  end.print()
    }
}

data class Triangle (val name: String, val summit:Point, val basis: Segment):Figure () {

	   fun print () {
	       	  println ("Triangle "+name)
	  	  summit.print()
	  	  basis.print()
		  }

	   fun isIsoceles (ab: Segment, ac: Segment): Boolean {
		return ab.length() == ac.length()
     		}
}
}

fun main(args: Array<String>) {

    val a = Figure.Point ("A",  0.0, 1.0)
    val b = Figure.Point ("B", -1.0, 0.0)
    val c = Figure.Point ("C",  1.0, 0.0)
    val bc = Figure.Segment ("BC", b, c)
    val tir = Figure.Triangle ("TIR", a, bc)

    fun print (fig:Figure) =
    	when (fig) {
    	  is Figure.Point -> fig.print()
	  is Figure.Segment -> fig.print()
     	  is Figure.Triangle -> fig.print()
     	  }

	  print(tir)

print(a)
}

