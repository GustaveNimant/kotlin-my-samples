// https://www.tutorialspoint.com/kotlin/kotlin_constructors.htm

fun main(args: Array<String>) {  
    val person1 = Person("TutorialsPoint.com", 15)  
    println("First Name = ${person1.firstName}") 
    println("Age = ${person1.age}") 

    val HUman = HUman("TutorialsPoint.com", 25)  
    print("${HUman.message} "+" ${HUman.firstName} "+" Welcome to the example of Secondary  constructor, Your Age is-${HUman.age}\n") 
}  

class Person(val firstName: String, var age: Int) {  
}

class HUman(val firstName: String, var age: Int) { 
      val message: String ="Hey!!!" 
      constructor(name : String , age :Int ,message : String):this(name,age){ 
      } 
}