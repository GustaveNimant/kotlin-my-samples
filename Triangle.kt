// https://dzone.com/articles/kotlin-the-tuple-type

data class Tuple2<out A, out B>(val _1: A, val _2: B)
data class Tuple3<out A, out B, out C>(val _1: A, val _2: B, val _3:C)

data class Point (val name: String, val coordinates: Tuple2<Double, Double>) {
     fun print () {
    	  println ("Point "+name)
	  println ("x ="+coordinates._1+" y ="+coordinates._2)
    }
}

sealed class Triangle {
    class Isoceles(val name: String, val vertices:Tuple3<Point, Point, Point>) : Triangle() {
//    assertThat(t._1).isEqualTo("elem1")

      fun print () {
    	  println ("Triangle "+name)
	  vertices._1.print()
	  vertices._2.print()
	  vertices._3.print()
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

    val a = Point ("A", Tuple2( 0.0, 1.0))
    val b = Point ("B", Tuple2(-1.0, 0.0))
    val c = Point ("C", Tuple2( 1.0, 0.0))

    val t = Triangle.Isoceles ("T", Tuple3(a, b, c))

    a.print()
    println ("Triangle:")
    t.print()
}

