CC=kotlinc

all: $(run)
	echo doing $(run)

$(run): $(run).jar
	java -jar $^

%.jar: %.kt
	$(CC) $< -include-runtime -d $@ 

clean:
	rm -rf *.jar

mrproper: clean
	rm -rf $(run)
