// https://stackoverflow.com/questions/48181751/get-name-of-current-function-in-kotlin

fun main(args: Array<String>) {
    val str = Thread.currentThread().stackTrace[1].methodName
    println("current function name : $str")
}