package utilities

class Utilities {
      var level = 0
      var dots = "........|........|........|........|........|........|........|"

      fun functionName(): String {
      	  val sta = Thread.currentThread().stackTrace[2]
    	  val str = sta.getMethodName()
    	  return str	
	  }

	  fun entering(here: String, caller: String):Unit {
    	  level = level + 1
	  if (level > 70) {
	     println ("Error maximum number of nesting levels reached")
	  } else {
            var points = dots.substring(0, level)
            println("$points Entering  in $here called by $caller")
    	  }
}

fun exiting(here: String):Unit {
    var points = dots.substring(0, level)
    println("$points Exiting from $here")
    level = level - 1	
}

}