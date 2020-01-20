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

sealed class Triangle () {

    sealed class TriangleIsoceles () {

    	   abstract fun print ()
	   
    	   data class TriangleIsocelesAcute (val name: String, val summit:Point, val basis: Segment): Triangle () {
	   fun print () {
	       	  println ("\nTriangle "+name)
	  	  summit.print()
	  	  basis.print()
		  }
		  
    	   }
	   data class TriangleIsocelesEquilateral (val name: String, val summit:Point, val basis: Segment): Triangle () {
	   fun print () {
	       	  println ("\nTriangle "+name)
	  	  summit.print()
	  	  basis.print()
		  }
		  
	   }
    	   data class TriangleIsocelesObtuse (val name: String, val summit:Point, val basis: Segment) : Triangle () {
	   fun print () {
	       	  println ("\nTriangle "+name)
	  	  summit.print()
	  	  basis.print()
		  }
		  
    	   }
    	   data class TriangleIsocelesRight (val name: String, val summit:Point, val basis: Segment): Triangle () {

	   fun print () {
	       	  println ("\nTriangle "+name)
	  	  summit.print()
	  	  basis.print()
		  }
		  
	    fun isIsoceles (ab: Segment, ac: Segment): Boolean {
		return ab.length() == ac.length()
     		}
	} 

    }

    sealed class TriangleScalene () {

    	   data class TriangleScaleneAcute (val name: String, val summit:Point, val basis: Segment) : Triangle () {
	   fun print () {
	       	  println ("\nTriangle "+name)
	  	  summit.print()
	  	  basis.print()
		  }
		  
    	   }
    	   data class TriangleScaleneObtuse (val name: String, val summit:Point, val basis: Segment) : Triangle () {
	   fun print () {
	       	  println ("\nTriangle "+name)
	  	  summit.print()
	  	  basis.print()
		  }
		  
    	   }
    	   data class TriangleScaleneRight (val name: String, val summit:Point, val basis: Segment) : Triangle () {
	   fun print () {
	       	  println ("\nTriangle "+name)
	  	  summit.print()
	  	  basis.print()
		  }
    	   }
    }
}

fun main(args: Array<String>) {

    val a = Point ("A",  0.0, 0.0)
    val b = Point ("B",  1.0, 0.5)
    val c = Point ("C",  1.0, 1.0)
    val d = Point ("D",  1.0, sqrt(3.0))
    val e = Point ("E",  1.0, 2.0)
    val f = Point ("F",  2.0, 0.0)
    val g = Point ("G",  3.0, 0.0)
    
    val af = Segment ("AF", a, f)
    val ag = Segment ("AG", a, g)
    
    val abg = Triangle.TriangleScalene.TriangleScaleneObtuse ("ABG", b, ag)
    val acf = Triangle.TriangleIsoceles.TriangleIsocelesRight ("ACF", c, af)
    val adf = Triangle.TriangleIsoceles.TriangleIsocelesEquilateral ("ADF", d, af)
    val aeg = Triangle.TriangleScalene.TriangleScaleneAcute ("AEG", e, ag)

    fun printOfTriangle (t:Triangle) =
    	when (t) {
//	  is Triangle.TriangleIsoceles -> println ("\nTriangle Isisoceles:")

	  is Triangle.TriangleIsoceles.TriangleIsocelesAcute -> t.print()
	  is Triangle.TriangleIsoceles.TriangleIsocelesEquilateral -> t.print()
   	  is Triangle.TriangleIsoceles.TriangleIsocelesRight -> t.print()
	  is Triangle.TriangleIsoceles.TriangleIsocelesObtuse -> t.print()

//	  is Triangle.TriangleScalene -> println ("\nTriangle Scalene:")

	  is Triangle.TriangleScalene.TriangleScaleneAcute -> t.print()
	  is Triangle.TriangleScalene.TriangleScaleneObtuse -> t.print()
     	  is Triangle.TriangleScalene.TriangleScaleneRight -> t.print()
     	  }

     printOfTriangle(abg)
     printOfTriangle(acf)
     printOfTriangle(adf)
     printOfTriangle(aeg)

}

