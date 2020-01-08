// https://www.callicoder.com/kotlin-type-checks-smart-casts/

fun main(args: Array<String>) {
   val mixedTypeList: List<Any> = listOf("I", "am", 5, "feet", 9.5, "inches", "tall")

   for(value in mixedTypeList) {
        if (value is String) {
            println("String: '$value' of length ${value.length} ")
        } else if (value is Int) {
            println("Integer: '$value'")
        } else if (value is Double) {
            println("Double: '$value' with Ceil value ${Math.ceil(value)}")
        } else {
            println("Unknown Type")
        }
    }

}
