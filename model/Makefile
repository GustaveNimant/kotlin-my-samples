CC=kotlinc

run = $(basename $(arg))

all: $(run)

MyLibrary.jar: MyLibrary.kt
	$(CC) $< -include-runtime -d $@

$(run): MyLibrary.jar $(run).jar
	java -esa --class-path MyLibrary.jar:$(run).jar $(run)Kt

%.jar: %.kt
	$(CC) -classpath MyLibrary.jar $< -include-runtime -d $@ 

clean:
	rm -rf *.jar
	rm *~

mrproper: clean
	rm -rf $(run)
