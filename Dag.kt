// export CLASSPATH=/usr/share/java/commons-codec.jar
// https://kotlinlang.org/docs/reference/enum-classes.html
// https://www.baeldung.com/kotlin-enum
// https://www.tutorialkart.com/kotlin/enum-classes-in-kotlin/

sealed class Keyword {
    class MutableKeyword : Keyword()
    class ImmutableKeyword : Keyword()
}

sealed class Block {
    class Genesis(val from: String) : Block()
    class NodeBlock(val payload:Payload) : Block()
    class HeadBlock(val payload:Payload, val genesis:Genesis) : Block()
}

sealed class Payload {
    class Text(val body: String) : Payload()
    class Image(val url: String, val caption: String) : Payload()
    class Audio(val url: String, val duration: Int) : Payload()
}

data class BlockChain (val name: String, val chain: List<Block>)

fun main(args: Array<String>) {

    val chain = listOf(
        Block.Genesis ("Genesis Block"),
	Block.NodeBlock (Payload.Text ("Payload # 1")),
	Block.NodeBlock (Payload.Text ("Payload # 2")),
	Block.HeadBlock (Payload.Text ("Payload # 2"), Block.Genesis ("Genesis Block"))
    )
    
    val bch = BlockChain ("Truc", chain)
    println ("BlockChain:")
    println (bch)	
}

