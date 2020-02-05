import java.util.Stack
import my.library.*

fun parameterMapOfArguments(args: Array<String>, caller: String): MutableMap<String, MutableList<String>> {
  val here = functionName()
  entering(here, caller)

  var stack = Stack<String>()
  args.reversed().forEach ({s -> stack.push(s)})

  val arg_siz = args.size
  val str = stringOfGlueOfStringList (" ", args.toList())

  if (arg_siz < 2) {
    fatalErrorPrint("More than 1 argument", "Arguments are '$str'", "Add an argument", "main") 
  }
  
  val arg_0 = stack.pop()
  var command = arg_0.substring(1)
  val arg_1 = stack.pop()

  try {
    if (! arg_0.startsWith('-')) {
      fatalErrorPrint("First character in arguments were '-'", "Arguments are '$str'", "Reset arguments", "main") 
    }
  }
  catch (e: java.lang.ArrayIndexOutOfBoundsException) {
      fatalErrorPrint("There were arguments", "No Arguments", "Set arguments to program", "main") 
  }

  var Done = false
  var arg_l = mutableListOf(arg_1)
  var ParameterMap = mutableMapOf (command to arg_l)

  println ("ParameterMap")
  for ( (k, v) in ParameterMap) {
     println ("$k => $v")
  }	  

   while (!Done) {
     try {
       var arg = stack.pop()
       
       if (arg.startsWith('-')) {
         command = arg.substring(1)
	 arg_l = mutableListOf("Initial element")
	 println ("arg_l cleared")
       }
       else {
       	 println ("else arg_l '$arg_l'")
	 if (arg_l.first() == "Initial element") {
            arg_l = mutableListOf(arg)
	 }
	 else {
	    arg_l.add (arg)
	 }
	 ParameterMap.set(command, arg_l)
	 println ("command '$command' arg '$arg'")
       }
       println ("ParameterMap")
       for ( (k, v) in ParameterMap) {
       	  println ("$k => $v")
       }	  
     }
     catch (e: java.util.EmptyStackException) {Done=true}
   }

   exiting(here)
   return ParameterMap
}

fun main(args: Array<String>) {
    val here = functionName()
    entering(here, "main")

  val ParameterMap = parameterMapOfArguments(args, "main")
  
   for ( (k, v) in ParameterMap) {
       println ("$k => $v")
   }

   exiting(here)

 }