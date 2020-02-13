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

    	   abstract fun print ()
	   
    sealed class TriangleIsoceles () {

    	   abstract fun print ()
	   
    	   data class TriangleIsocelesAcute constructor (val name: String, val summit:Point, val basis: Segment): TriangleIsoceles () {
	   final override fun print () {
	       	  println ("\nTriangle "+name)
	  	  summit.print()
	  	  basis.print()
		  }
		  
    	   }
	   data class TriangleIsocelesEquilateral constructor (val name: String, val summit:Point, val basis: Segment): TriangleIsoceles () {
	   final override fun print () {
	       	  println ("\nTriangle "+name)
	  	  summit.print()
	  	  basis.print()
		  }
		  
	   }
    	   data class TriangleIsocelesObtuse constructor (val name: String, val summit:Point, val basis: Segment) : TriangleIsoceles () {
	   final override fun print () {
	       	  println ("\nTriangle "+name)
	  	  summit.print()
	  	  basis.print()
		  }
		  
    	   }
    	   data class TriangleIsocelesRight constructor (val name: String, val summit:Point, val basis: Segment): TriangleIsoceles () {

	   final override fun print () {
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

    	   abstract fun print ()
	   
    	   data class TriangleScaleneAcute constructor (val name: String, val summit:Point, val basis: Segment) : TriangleScalene () {
	   final override fun print () {
	       	  println ("\nTriangle "+name)
	  	  summit.print()
	  	  basis.print()
		  }
		  
    	   }
    	   data class TriangleScaleneObtuse constructor (val name: String, val summit:Point, val basis: Segment) : TriangleScalene () {
	   final override fun print () {
	       	  println ("\nTriangle "+name)
	  	  summit.print()
	  	  basis.print()
		  }
		  
    	   }
    	   data class TriangleScaleneRight constructor (val name: String, val summit:Point, val basis: Segment) : TriangleScalene () {
	   final override fun print () {
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
	  is Triangle.TriangleIsoceles -> {ti -> 

    	when (ti) {
	  is Triangle.TriangleIsoceles.TriangleIsocelesAcute -> ti.print()
	  is Triangle.TriangleIsoceles.TriangleIsocelesEquilateral -> ti.print()
   	  is Triangle.TriangleIsoceles.TriangleIsocelesRight -> ti.print()
	  is Triangle.TriangleIsoceles.TriangleIsocelesObtuse -> ti.print()
	  }
	  }	
	  is Triangle.TriangleScalene -> { ts -> 
    	when (ts) {
	  is Triangle.TriangleScalene.TriangleScaleneAcute -> ts.print()
	  is Triangle.TriangleScalene.TriangleScaleneObtuse -> ts.print()
     	  is Triangle.TriangleScalene.TriangleScaleneRight -> ts.print()
     	  }
	  }
	  }
     printOfTriangle(abg)
     printOfTriangle(acf)
     printOfTriangle(adf)
     printOfTriangle(aeg)

}

