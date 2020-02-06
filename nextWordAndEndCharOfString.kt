import my.library.*

fun nextWordAndEndCharOfEndCharListOfString(cha_l: List<Char>, str: String, caller: String): Pair<String, Char> {
    val here = functionName()
    entering(here, caller)

    if (isTrace(here)) println("$here: input cha_l '$cha_l'")
    if (isTrace(here)) println("$here: input str '$str'")
    
    var word = ""
    var end_cha = 'x'
    for (c in str){
	  if (isDebug(here)) println("$here: c '$c'")
	  if (cha_l.contains(c)) {
	  end_cha = c
	  break
	  }
	  word = word.plus(c.toString())
    }

    assert (word.isNotEmpty())
    
    if (isTrace(here)) println("$here: output word '$word'")
    exiting(here)
    return Pair(word, end_cha)
}

fun main(args: Array<String>) {
   val str = "abcd $"
   val cha_l = listOf (' ', '$')
   val (word, end_cha) = nextWordAndEndCharOfEndCharListOfString(cha_l, str, "main")
   
   println("word '$word'")
   println("end_cha '$end_cha'")

}

