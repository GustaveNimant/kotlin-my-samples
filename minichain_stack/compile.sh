#!/bin/bash
kotlinc MyLibrary.kt -include-runtime -d MyLibrary.jar
kotlinc -classpath MyLibrary.jar Lexeme.kt -include-runtime -d Lexeme.jar 
kotlinc -classpath MyLibrary.jar:Lexeme.jar LexemeListOfStack.kt -include-runtime -d LexemeListOfStack.jar
kotlinc -classpath MyLibrary.jar:Lexeme.jar LexemeListOfString.kt -include-runtime -d LexemeListOfString.jar
kotlinc -classpath MyLibrary.jar:Lexeme.jar:LexemeListOfStack.jar:LexemeListOfString.jar Lexer.kt -include-runtime -d Lexer.jar 
kotlinc -classpath MyLibrary.jar:Lexer.jar Parser.kt -include-runtime -d Parser.jar 



java -esa --class-path MyLibrary.jar:Lexer.jar:Parser.jar ParserKt
