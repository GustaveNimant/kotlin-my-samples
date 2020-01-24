fun main(args: Array<String>) {

    val str = "/my/perl/script/kwextract.pl"
    val pattern = Regex("""^(/\w[a-zA-Z_0-9]*)(/([a-zA-Z_0-9]+))*\.\w+$""")

if (pattern.matches(input = str)) {
       println("Ok")  	    
    }      
    else {
    	 println("Not Ok")		
    }  
}

