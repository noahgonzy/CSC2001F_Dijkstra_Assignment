import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class GenerateGraphs {
    public static void main(String[] args) {
       GenerateNewGraphs(); 
    }

    static int[] vSize = {10,15,20,25,30};
    static int[] eSize = {40,50,60,70,80};
    
    public static void GenerateNewGraphs(){
        try{
            
            Integer numfiles = vSize.length * eSize.length;
            for(int i = 0; i < numfiles; i++){
                File f = new File("data/graph" + i + ".txt");
                f.createNewFile();
            }
            
            String eNums = "";
            String vNums = "";
            for(int b = 0; b < vSize.length; b++){
                vNums = vNums + " " + Integer.toString(vSize[b]);
            }
            for(int b = 0; b < eSize.length; b++){
                eNums = eNums + " " + Integer.toString(eSize[b]);
            }
            File infoFile = new File("data/infoFile.txt");
            infoFile.createNewFile();
            FileWriter iw = new FileWriter(infoFile);
            iw.write(numfiles + "\n" + "Files Exist" + "\n");
            System.out.println("Creating graphs with verticies of lengths:" + vNums);
            System.out.println("for each number of verticies, generating number of edges:" + eNums);
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
            
            for(int i = 0; i < vSize.length; i++){
                for(int o = 0; o < eSize.length; o++){
                    for(int j = 0; j < vSize[i]; j++){
                        vNodes.add(""+j);
                    }
                    System.out.println("");
                    k = 0;
                    while(k < eSize[o]){
                        cost = 1 + randc.nextInt(9);
                        
                        start = randNode.nextInt(vSize[i]);
                        end = randNode.nextInt(vSize[i]);
                        if((start != end)){
                            addnow = true;
                            toadd = start + " " + end + " " + cost;
                            for(int m = 0; m < eNodes.size(); m ++){
                                addcheck = toadd.substring(0, 3);
                                addcheckm = eNodes.get(m).substring(0, 3);
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
                    FileWriter fw = new FileWriter("/Users/noahgonsenhauser/Dropbox/UCT/CSC2001F/Assignment5/data/graph" + graphnum + ".txt");
                    for(int n = 0; n < eNodes.size(); n++){
                        fw.write(eNodes.get(n) + "\n");
                    }
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
