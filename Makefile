CC=kotlinc

run = $(basename $(arg))

all: $(run)

MyLibrary.jar: ../kotlin-minichain/MyLibrary.kt
	$(CC) $< -include-runtime -d $@

$(run): MyLibrary.jar $(run).jar
	java -esa -classpath MyLibrary.jar:$(run).jar $(run)Kt

%.jar: %.kt
	$(CC) -classpath MyLibrary.jar $< -include-runtime -d $@ 

clean:
	rm -rf *.jar
	rm *~

mrproper: clean
	rm -rf $(run)
