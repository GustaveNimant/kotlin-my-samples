// export CLASSPATH=/usr/share/java/commons-codec.jar
// https://kotlinlang.org/docs/reference/enum-classes.html
// https://www.baeldung.com/kotlin-enum
// https://www.tutorialkart.com/kotlin/enum-classes-in-kotlin/

var level = 0
var dots = "........|........|........|........|........|........|........|"

fun notYetImplemented(fun_nam: String){
    throw Exception("function $fun_nam is not yet implemented")
}

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

fun inputRead(caller: String): String {
    val here = functionName()
    entering(here, caller)
	
    val str = readLine().toString()

    exiting(here+" with str = $str")
    return str
}

fun readInput(caller: String): String {
    val here = functionName()
    entering(here, caller)
	
    val str = readLine().toString()
    
    exiting(here)
    return str
}

fun main(args: Array<String>) {

    println(Months.January)                            //prints January
    println("values().size are "+Months.values().size) //prints 3
    println("valueOf is "+Months.valueOf("March"))     //prints March
    println("values are :")

    for (enum in Months.values()) {
        println(enum.name)
    }

    println("throws exception:")
    println(Months.valueOf("Mar")) //throws java.lang.IllegalArgumentException: No enum constant Months.Mar
}

enum class DayOfWeek(val dayNumber: Int) {
  MONDAY(1),
  TUESDAY(2),
  WEDNESDAY(3),
  THURSDAY(4),
  FRIDAY(5),
  SATURDAY(6),
  SUNDAY(7)

  private int dayNumber;
   private DayOfWeek(int dayNumber) {
      this.dayNumber = dayNumber;
   }
   public int getDayNumber() {
      return dayNumber;
   }
}