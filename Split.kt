import java.io.File
import java.util.Stack
import java.lang.Character.MIN_VALUE as nullChar

fun wordListOfString (str: String): List<String> {
    val trimedString = str.trim(' ')    

    val regex = Regex("\\s+")

    val result = trimedString.split(
	regex
    )
    return result
}

fun main(args: Array<String>) {
    val string = "# one  two  three "
    val list = wordListOfString (string)
    list.forEach{println("word '"+it+"'")}
}

