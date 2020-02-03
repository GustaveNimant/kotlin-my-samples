run = $(basename $(arg))

KFLAGS := -include-runtime

KT_FILES = MyLibrary.kt Lexeme.kt LexemeListProvider.kt Lexer.kt Parser.kt Main.kt

JAR_FILES = MyLibrary.jar Lexeme.jar LexemeListProvider.jar Lexer.jar Parser.jar Main.jar 

all: $(run)

MyLibrary.jar: MyLibrary.kt
	kotlinc $< $(KFLAGS) -d $@

Lexeme.jar: Lexeme.kt 
	kotlinc $< -classpath MyLibrary.jar $(KFLAGS) -d $@

LexemeListTools.jar: LexemeListTools.kt 
	kotlinc $< -classpath MyLibrary.jar:Lexeme.jar $(KFLAGS) -d $@

LexemeListOfStack.jar: LexemeListOfStack.kt 
	kotlinc $< -classpath MyLibrary.jar:Lexeme.jar $(KFLAGS) -d $@

LexemeListOfString.jar: LexemeListOfString.kt 
	kotlinc $< -classpath MyLibrary.jar:Lexeme.jar $(KFLAGS) -d $@

Lexer.jar: Lexer.kt 
	kotlinc $< -classpath MyLibrary.jar:Lexeme.jar $(KFLAGS) -d $@

LexemeListProvider.jar: LexemeListProvider.kt 
	kotlinc $< -classpath MyLibrary.jar:Lexeme.jar $(KFLAGS) -d $@

$(run): $(JAR_FILES)
	java -esa --class-path MyLibrary.jar:$(run).jar $(run)Kt

%Kt.class: %.kt
	kotlinc $<

%.class: %.java
	javac $<

%.jar: %.kt
	kotlinc -classpath MyLibrary.jar $< $(KFLAGS) -d $@ 

.PHONY: clean

clean:
	rm -rf *.jar
	rm *~

mrproper: clean
	rm -rf $(run)
