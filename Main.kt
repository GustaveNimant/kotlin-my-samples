// https://www.programcreek.com/java-api-examples/index.php?api=org.apache.commons.codec.binary.Base32
// export CLASSPATH=/usr/share/java/commons-codec.jar

import MyLibrary.*

fun main(args: Array<String>) {
    val here = functionName()
    entering(here,"top")

    val str = "/home/achadde/sources/kotlin/hello.kt"
    println("$here : string to encode '$str'")

    val base32String = encoder(str, here)
    println("$here : Base32String = $base32String")	

    val str_dec = decoder(base32PathString, here)

    println("$here : decoded string '$str_dec'")
	
    exiting(here)
}

