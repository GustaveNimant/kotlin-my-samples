fun main(args: Array<String>) {
    val string = "Hello \$world\$ Hello"

    val charsMap = mutableMapOf<Char, Int>()

    string.forEach{
	charsMap[it] = charsMap.getOrDefault(it, 0) + 1
	}

	print(charsMap)

println() 
val count :Int  = 4
val reste = count.rem (2)

} 