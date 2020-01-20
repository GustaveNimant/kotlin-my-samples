fun main(args: Array<String>) {
    val eps = 1E-15 

    tailrec fun findFixPoint(x: Double = 1.0): Double
        = if (Math.abs(x - Math.cos(x)) < eps) x
	  else {
	  println("x "+x)
	  findFixPoint(Math.cos(x))
	  }	  
	val result = findFixPoint(2.0)
	println("result "+result)
}