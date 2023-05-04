#Program Make File
#Noah Gonsenhauser

#Showing the make program where all the different commands lie 
JAVAC=/usr/bin/javac
JAVA=/usr/bin/java
JAVADOC=/usr/bin/javadoc

#showing the suffixes of files to be used later along with the src and bin directories of the folder
.SUFFIXES: .java .class
SRCDIR=src
BINDIR=bin
DOCDIR=doc
DATADIR=data

#This code compiles the java files into class files and puts them in the bin directory
$(BINDIR)/%.class:$(SRCDIR)/%.java
	$(JAVAC) -d $(BINDIR)/ -cp $(BINDIR) $<

#this points the make file to what the classes are that will be compiles
CLASSES=Vertex.class Edge.class Path.class GraphException.class Graph.class \

#This creates a substitution reference which will produce a list of file names with the same names but with the directory prefix
CLASS_FILES=$(CLASSES:%.class=$(BINDIR)/%.class)

#this runs CLASS_FILES and generates javadocs by default when run without a parameter
$(BINDIR)/%.class: $(SRCDIR)/%.java
	$(JAVAC) -d $(BINDIR)/ -cp $(BINDIR) $(SRCDIR)/*.java

default: $(CLASS_FILES)
	$(JAVADOC) $(SRCDIR)/*.java -d $(DOCDIR)

#this removes all files from the bin directory and the document directory
clean:
	rm $(BINDIR)/*.class
	rm -r $(DOCDIR)/*
	rm Djikstraout.txt
	rm -r $(DATADIR)/*

clean_alt:
	rm $(BINDIR)/*.class
	rm -r $(DOCDIR)/*
	rm Djikstraout.txt

#this runs the same commands as default when run, but also runs the program
run: $(CLASS_FILES)
	$(JAVADOC) $(SRCDIR)/*.java -d $(DOCDIR)
	$(JAVA) -cp bin GenerateGraphs
	$(JAVA) -cp bin Graph

graph: $(CLASS_FILES)
	$(JAVADOC) $(SRCDIR)/*.java -d $(DOCDIR)
	$(JAVA) -cp bin Graph

generate: $(CLASS_FILES)
	$(JAVADOC) $(SRCDIR)/*.java -d $(DOCDIR)
	$(JAVA) -cp bin GenerateGraphs

