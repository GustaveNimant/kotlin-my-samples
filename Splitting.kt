/* https://www.baeldung.com/kotlin-regular-expressions */

fun main(args: Array<String>){  
    val regex = """\W+""".toRegex()
    val beautiful = "Roses are red, Violets are blue"
 
 assertEquals(listOf(
	 "Roses", "are", "red", "Violets are blue"), regex.split(beautiful, 4))

}