fun main(args: Array<String>) {

    val alphabetical = Regex("[a-zA-Z_]")
    var cha = "c"
    
    println("character $cha")  	    

    if (alphabetical.matches(input = cha)) {
       println("alphabetical")  	    
    }      
    else {
    	 println("Not alphabetical")		
    }  

    var cha2 = "#"
 
    println("character $cha2")  	    
    if (alphabetical.matches(input = cha2)) {
       println("alphabetical")  	    
    }      
    else {
    	 println("Not alphabetical")		
    }  

      val numerical = Regex("[0-9]")

    println("character $cha")  	    
    if (numerical.matches(input = cha)) {
       println("numerical")  	    
    }      
    else {
    	 println("Not numerical")		
    }  

      val alphanumerical = Regex("[a-zA-Z0-9_]")

    println("character $cha")  	    
    if (alphanumerical.matches(input = cha)) {
       println("alphanumerical")  	    
    }      
    else {
    	 println("Not alphanumerical")		
    }  
}

