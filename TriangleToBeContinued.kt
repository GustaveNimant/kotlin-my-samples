// https://dzone.com/articles/kotlin-the-tuple-type

import kotlin.math.*

data class Point (val name: String, val x: Double, val y: Double) {
     fun print () {
    	  println ("..Point "+name)
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
    	  println (".Segment "+name)
	  println ("..origin =")
	  origin.print()
	  println ("..end =")
	  end.print()
    }
}

sealed class Triangle (val name: String, val summit:Point, val basis: Segment) {


    sealed class TriangleIsoceles (val name: String, val summit:Point, val basis: Segment) {

    	   class TriangleIsocelesAcute (val name: String, val summit:Point, val basis: Segment){
    	   }
    	   class TriangleIsocelesObtuse (val name: String, val summit:Point, val basis: Segment) {
    	   }
    	   class TriangleIsocelesRight (val name: String, val summit:Point, val basis: Segment){
	   fun print () {
	       	  println ("Triangle "+name)
	  	  summit.print()
	  	  basis.print()
		  }
		  
        val a = summit
    	val b = basis.origin
	val c = basis.end
	val ab = Segment ("AB", a, b)
	val ac = Segment ("AC", a, c)

        fun isIsoceles (ab: Segment, ac: Segment): Boolean {
		return ab.length() == ac.length()
     	}
//	   assert(isIsoceles(ab, ac))
	
//ab.print ()


} // TriangleIsocelesRight 

    }

    sealed class TriangleScalene (val name: String, val summit:Point, val basis: Segment) {

    	   class TriangleScaleneAcute (val name: String, val summit:Point, val basis: Segment){
    	   }
    	   class TriangleScaleneObtuse (val name: String, val summit:Point, val basis: Segment) {
    	   }
    	   class TriangleScaleneRight (val name: String, val summit:Point, val basis: Segment){
    	   }
    }
}

fun main(args: Array<String>) {

    val a = Point ("A",  0.0, 1.0)
    val b = Point ("B", -1.0, 0.0)
    val c = Point ("C",  1.0, 0.0)
    val bc = Segment ("BC", b, c)
    val tir = Triangle.TriangleIsoceles.TriangleIsocelesRight ("TIR", a, bc)

    a.print()
    println ("\nTriangle:")
    tir.print()
}

