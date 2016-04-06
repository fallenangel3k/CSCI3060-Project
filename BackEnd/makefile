JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	FileReader.java \
	FileWriter.java \
	Transactions.java \
	Main.java 

default: 
	$(JC) $(JFLAGS) $(CLASSES)

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class