#Program Make File for Djikstra's Algorithm and for Generating graphs to test the Algorithm.
#Noah Gonsenhauser
#04/05/2023

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
CLASSES=Vertex.class Edge.class Path.class GraphException.class GraphExperiment.class \

#This creates a substitution reference which will produce a list of file names with the same names but with the directory prefix
CLASS_FILES=$(CLASSES:%.class=$(BINDIR)/%.class)

#this genereates the class files in the BINDIR
$(BINDIR)/%.class: $(SRCDIR)/%.java
	$(JAVAC) -d $(BINDIR)/ -cp $(BINDIR) $(SRCDIR)/*.java

#this generates all the class files for the program and generates the javadocs for the program
default: $(CLASS_FILES)
	$(JAVADOC) $(SRCDIR)/*.java -d $(DOCDIR)

#this removes all files from the bin directory (class files) the document directory (javadocs) and all of 
#the data files (graphs) along with the output of the Graph program (djikstraout.txt)
clean:
	rm -r $(DATADIR)/*
	rm -r $(DOCDIR)/*
	rm $(BINDIR)/*.class

#this does the same as 'clean' but keeps the data files incase you wanted to rerun the graph program without
#changing your previously generated data
clean_alt:
	rm -r $(DOCDIR)/*
	rm $(DATADIR)/Djikstraout.txt
	rm $(BINDIR)/*.class

#this runs the same commands as default when run, but also runs the Graph Generation program and then the Pathfinding Program
#which runs on each graph stored in "Data"
run: $(CLASS_FILES)
	$(JAVADOC) $(SRCDIR)/*.java -d $(DOCDIR)
	$(JAVA) -cp bin GenerateGraphs
	$(JAVA) -cp bin GraphExperiment

#this runs the same commands as default when run, this also only runs the graph program 
#which will only work if data (graphs) already exists
graph: $(CLASS_FILES)
	$(JAVADOC) $(SRCDIR)/*.java -d $(DOCDIR)
	$(JAVA) -cp bin GraphExperiment

#this runs the same commands as default when run, this also only runs the GraphGeneration program
#for if you want the generate the graphs first without running the Dijkstra algorithm on it
generate: $(CLASS_FILES)
	$(JAVADOC) $(SRCDIR)/*.java -d $(DOCDIR)
	$(JAVA) -cp bin GenerateGraphs

