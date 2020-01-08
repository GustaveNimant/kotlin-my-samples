import io.ipfs.kotlin.IPFS

val hash = IPFS()
        .add
        .string("Hello world.")
        .Hash

println("hash: $hash")

fun main(args: Array<String>) {
 println("Hello, World!")
}