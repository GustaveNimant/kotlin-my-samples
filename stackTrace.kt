// https://stackoverflow.com/questions/48181751/get-name-of-current-function-in-kotlin
// https://docs.oracle.com/javase/8/docs/api/java/lang/StackTraceElement.html

fun functionName(): String {
    val sta_a = Thread.currentThread().stackTrace
    val sta_2 = sta_a[2]
    val str = sta_2.getMethodName()
    val fil_nam = sta_2.getFileName()
    println("file name: $fil_nam")

    var i = 0	
    for (sta in sta_a){
    	println("in functionName stackTrace[$i] = $sta")
    	i++
    }

    return str	
}

fun functionNameOfStack(stack:StackTraceElement): String {
    val str = stack.getMethodName()
    return str	
}

fun functionNameOfThread(thread:Thread): String {
    println("Entering in functionNameOfThread with thread: $thread")	
    val sta = thread.stackTrace
    println("in functionNameOfThread sta: $sta")	
    val str = sta[2].getMethodName()
    return str	
}

fun main(args: Array<String>) {
    val sta_a = Thread.currentThread().stackTrace

    var i = 0	
    for (sta in sta_a){
    	println("stackTrace[$i] = $sta")
    	i++
    }

    val thr = Thread.currentThread()
    println("thread: $thr")

    val fun_nam = functionName()
    println("function name: $fun_nam")	

    val fun_thr_nam = functionNameOfThread(thr)
    println("function name of thread: $fun_thr_nam")	

    val sta_1 = thr.stackTrace[1]
    println("stack[1]: $sta_1")
    
    val fun_sta_nam = functionNameOfStack(sta_1)
    println("function name of stack: $fun_sta_nam")	

    val cla_nam = sta_1.getClassName()
    println("class name: $cla_nam")

    val met_nam = sta_1.getMethodName()	
    println("method name: $met_nam")

    val lin_num = sta_1.getLineNumber()
    println("line number: $lin_num")
    
    val fil_nam = sta_1.getFileName()
    println("file name: $fil_nam")

}