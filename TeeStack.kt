import java.io.File
import java.util.Base64
import java.util.Stack
import java.lang.Character.MIN_VALUE as nullChar

fun <T> teeStackOfTeeList (tee_l: List<T>): Stack<T> {
    var stack = Stack<T>()
    tee_l.reversed().forEach { t -> stack.push (t)}
    return stack
}

fun <T> teeStackFromTeeOfTeeStack (tee:T, tee_s: Stack<T>): Stack<T> {
// return the subStack starting at tee (excluded)

   var cha = tee_s.pop()
   var done = false
   if (cha == tee) {return tee_s}
   while (! done) {
      try {
      	cha = tee_s.pop()
	done = tee == cha
      }
      catch (e: java.util.EmptyStackException) {
            done = true			       
      }
    }
	
    return tee_s
}

fun main(args: Array<String>) {

  val tee_l = listOf('a', 'b', 'c', 'd')
  val tee_s = teeStackOfTeeList (tee_l)
  println ("tee_s"+tee_s)

 val cha = 'b'

 val cha_s = teeStackFromTeeOfTeeStack (cha, tee_s)

  println ("cha_s"+cha_s)
}