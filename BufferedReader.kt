import java.io.File
import java.io.BufferedReader

fun stringOfFileName (nof: String) : String  {
    val bufferedReader: BufferedReader = File(nof).bufferedReader()

    val result = bufferedReader.use { it.readText() }
    return result
}

fun stringListOfFileName (nof: String) : MutableList<String> {
    val bufferedReader: BufferedReader = File(nof).bufferedReader()

    val result = mutableListOf<String>()
 
    bufferedReader.useLines {
    	lines -> lines.forEach { result.add(it)}
	}

     return result
}

fun main(args: Array<String>) {
    val fileName = "current-block-test.yml"

    println("Read the whole file as a String:")
    val string = stringOfFileName (fileName)
    println(string)

    println("Read the whole file as a List of String :")
    val stringList = stringListOfFileName (fileName)

    stringList.forEach{println(">  " + it)}

}