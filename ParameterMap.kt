import java.util.Stack
import my.library.*

fun main(args: Array<String>) {
  val here = functionName()
  entering(here, "main")

  ParameterMap = parameterMapOfArguments(args, "main")

  println ("Parameters are:")
  for ( (k, v) in ParameterMap) {
       println ("$k => $v")
  }

  debug = isDebug(here)
  println ("debug '$debug'")
  
  exiting(here)

 }