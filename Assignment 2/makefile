Test.class:
	javac *.java

clean:
	rm -f *.class

run: Test.class
	java -Xmx64m Test
	
tar : 
	tar -cvz *.java makefile -f u20425997.tar