CC=kotlinc

run = $(basename $(arg))
all: $(run)

$(run): $(run).jar
	java -ea -jar $^

%.jar: %.kt
	$(CC) $< -include-runtime -d $@ 

clean:
	rm -rf *.jar
	rm *~

mrproper: clean
	rm -rf $(run)
