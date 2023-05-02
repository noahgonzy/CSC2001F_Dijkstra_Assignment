import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;

public class GenerateGraphs {
    public static void main(String[] args) {
       GenerateNewGraphs(); 
    }
    
    public static void GenerateNewGraphs(){
        int[] vSize = new int[]{10,20,30,40,50};
        int[] eSize = new int[10];

        try{
            Integer numfiles = vSize.length * eSize.length;
            for(int i = 0; i < numfiles; i++){
                File f = new File("data/graph" + i + ".txt");
                f.createNewFile();
            }

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
            
            for(int i = 0; i < vSize.length; i++){

                edgenummath = 0.2;
                for(int h = 0; h < eSize.length; h++){
                    edgenummath += 0.06;
                    int numedges = (int)Math.round(Math.pow(vSize[i], 2)*edgenummath);
                    eSize[h] = numedges;
                }

                System.out.println(eSize.toString());
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
                    FileWriter fw = new FileWriter("data/graph" + graphnum + ".txt");
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
