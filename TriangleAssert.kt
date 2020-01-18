// https://dzone.com/articles/kotlin-the-tuple-type

import kotlin.math.*

data class Tuple2<out A, out B>(val _1: A, val _2: B)
data class Tuple3<out A, out B, out C>(val _1: A, val _2: B, val _3:C)

data class Point (val name: String, val x: Double, val y: Double) {
     fun print () {
    	  println ("Point "+name)
	  println ("x ="+x+" y ="+y)
    }
}

data class Segment (val name: String, val origin: Point, val end: Point) {
     fun length(): Double {
	val dx = end.x - origin.x
	val dy = end.y - origin.y
	val result = sqrt (dx * dx + dy * dy)
	
	return result
     }
     fun print () {
    	  println ("Segment "+name)
	  println ("origin =")
	  origin.print()
	  println ("end =")
	  end.print()
    }
}

sealed class Triangle {
    class Isoceles(val name: String, val summit:Point, val basis: Segment) : Triangle() {

    fun isIsoceles (): Boolean {
        val a = summit
    	val b = basis.origin
	val c = basis.end
	val ab = Segment ("AB", a, b)
	val ac = Segment ("AC", a, c)

	return ab.length() == ac.length()
     }

     fun print () {
    	  println ("Triangle "+name)
	  summit.print()
	  basis.print()
    	  }
    }

    class Scalene(val name: String, val vertices:Tuple3<Point, Point, Point>) : Triangle() {
         fun print () {
    	  println ("Triangle "+name)
	  vertices._1.print()
	  vertices._2.print()
	  vertices._3.print()
    	  }
    }
}

fun main(args: Array<String>) {

    val a = Point ("A",  0.0, 1.0)
    val b = Point ("B", -1.0, 0.0)
    val c = Point ("C",  1.0, 0.0)
    val bc = Segment ("BC", b, c)
    val t = Triangle.Isoceles ("T", a, bc)

    assert(t.isIsoceles())
	
    a.print()
    println ("Triangle:")
    t.print()
}

