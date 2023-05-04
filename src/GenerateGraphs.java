import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;

/**
 * @author Noah Gonsenhauser, 
 * 4/05/2023, 
 * Djikstra Graph Generator
 */
public class GenerateGraphs {
    
    /** 
     * Generates new Graphs to be run by the Dijkstra's Algorithm
     * @param args
     */
    public static void main(String[] args) {
       GenerateNewGraphs(new int[]{10,20,30,40,50}); 
    }
    
    /**
     * Graph Generation Function
     */
    public static void GenerateNewGraphs(int[] varr){
        //array vSize contains the vertexes that graphs will be generated for
        int[] vSize = varr;
        int[] eSize = new int[10];

        try{
            //This generates the graph txt files that will be populated with the graphs.
            Integer numfiles = vSize.length * eSize.length;
            for(int i = 0; i < numfiles; i++){
                File f = new File("data/graph" + i + ".txt");
                f.createNewFile();
            }

            //This creates the info file for a user to view when they want to see the information on the graphs in the graph file.
            //This file is also used to tell the Graph program how many files to look for
            File infoFile = new File("data/infoFile.txt");
            infoFile.createNewFile();
            FileWriter iw = new FileWriter(infoFile);
            iw.write(numfiles + "\n" + "Files Exist" + "\n");
            Random randc = new Random();
            Random randNode = new Random();
            ArrayList<String> vNodes = new ArrayList<>();
            ArrayList<String> eNodes = new ArrayList<>();
            int cost;
            int start;
            int end;
            int k = 0;
            String toadd;
            String addcheck;
            String addcheckm;
            boolean addnow = false;;
            int graphnum = 0;
            double edgenummath;
            
            //loops through given verticies and generates 10 graphs for each, with |E| that are, 
            //26%,32%,38%,44%,50%,56%,62%,68%,74% and 80% of |V|^2
            for(int i = 0; i < vSize.length; i++){
                edgenummath = 0.2;
                for(int h = 0; h < eSize.length; h++){
                    edgenummath += 0.06;
                    int numedges = (int)Math.round(Math.pow(vSize[i], 2)*edgenummath);
                    eSize[h] = numedges;
                }
                for(int o = 0; o < eSize.length; o++){
                    //adds the number of nodes (vertexes) to the vnodes arraylist to pick from
                    for(int j = 0; j < vSize[i]; j++){
                        vNodes.add(""+j);
                    }
                    System.out.println("");
                    k = 0;
                    //This loop generates a random cost, and then attemps to create an edge, while making sure the start 
                    //and end nodes aren't equal and that the edge doesn't already exist and this repeats until 
                    //the requested number of edges are created
                    while(k < eSize[o]){
                        cost = 1 + randc.nextInt(9);
                        
                        start = randNode.nextInt(vSize[i]);
                        end = randNode.nextInt(vSize[i]);
                        if((start != end)){
                            addnow = true;
                            toadd = start + " " + end + " " + cost;
                            for(int m = 0; m < eNodes.size(); m ++){
                                addcheck = toadd.substring(0, toadd.length()-2);
                                addcheckm = eNodes.get(m).substring(0, eNodes.get(m).length()-2);
                                if(addcheck.equals(addcheckm)){
                                    addnow = false;
                                    break;
                                }
                            }
                            if(addnow){
                                eNodes.add(toadd);
                                k++;
                            }
                        }
                        else{
                            continue;
                        }
                    }
                    //This filewriter writes the node information to the text file of a given graph
                    FileWriter fw = new FileWriter("data/graph" + graphnum + ".txt");
                    for(int n = 0; n < eNodes.size(); n++){
                        fw.write(eNodes.get(n) + "\n");
                    }
                    //This outputs the graph information for a newly generated graph, it's name, number of vertexes possilbe and number of edges 
                    //To both the program and to the infofile for a user to read later
                    iw.write("Graph " + graphnum + ", has: " + vSize[i] + " Verticies, and: " + eSize[o] + " Edges\n");
                    System.out.println("Graph " + (graphnum) + " Complete, With: " + vSize[i] + " Verticies, and: " + eSize[o] + " Edges\n");
                    fw.write(vSize[i] + ":" + eSize[o]);
                    graphnum++;
                    vNodes.clear();
                    eNodes.clear();
                    fw.close();
                }
            }
            System.out.println("Information on files recorded to infoFile.txt");
            iw.close();
        }
        catch(IOException e){
            System.out.println("an error has occured");
        }
        
    }
}
