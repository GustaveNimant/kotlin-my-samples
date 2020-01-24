fun main(args: Array<String>) {
 
    var listA = mutableListOf("Example", "Program", "Tutorial")
    var listB = mutableListOf("And", "This") 
    listA.add("Sample")
    println("after add "+listA)
    
    listA.union(listB)
    println("after union "+listA)
    
    listA.addAll(listB)

    println("after addAll "+listA)

} 