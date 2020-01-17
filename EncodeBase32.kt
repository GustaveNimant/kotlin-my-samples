import org.apache.commons.codec.binary.Base32

fun main(args: Array<String>) {
    println(Base32.encodeAsString("test".getBytes()))
}

