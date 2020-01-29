#!/bin/bash
kotlinc MyLibrary.kt -include-runtime -d MyLibrary.jar
kotlinc -classpath MyLibrary.jar Lexer.kt -include-runtime -d Lexer.jar 
kotlinc -classpath MyLibrary.jar:Lexer.jar Parser.kt -include-runtime -d Parser.jar 



java -esa --class-path MyLibrary.jar:Lexer.jar:Parser.jar ParserKt
