import java.io.File

fun stringListOfFileName (nof: String) : MutableList<String> {

    val result = mutableListOf<String>()
 
    File(nof).useLines {
    	lines -> lines.forEach { result.add(it)}
	}

     return result
}

fun main(args: Array<String>) {
    val fileName = "current-block-test.yml"

    println("Read the whole file as a List of String :")
    val stringList = stringListOfFileName (fileName)

    stringList.forEach{println(">  " + it)}
}