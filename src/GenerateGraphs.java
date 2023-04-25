import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class GenerateGraphs {
    static int[] vSize = {10,20,30,40,50};
    static int[] eSize = {20,35,50,65,80};
    
    public static void GenerateNewGraphs(){
        try{
            File f = new File("graphs.txt");
            if (f.createNewFile()){
                System.out.println("New Graphs being Created in graphs.txt...");
            }
            else{
                System.out.println("New Graphs being Created... Overwriting current graphs.txt file");
            }
            
            String eNums = "";
            String vNums = "";
            for(int b = 0; b < vSize.length; b++){
                vNums = vNums + " " + Integer.toString(vSize[b]);
            }
            for(int b = 0; b < eSize.length; b++){
                eNums = eNums + " " + Integer.toString(eSize[b]);
            }
            System.out.println("Creating graphs with verticies of lengths:" + vNums);
            System.out.println("for each number of verticies, generating number of edges:" + eNums);
            FileWriter fw = new FileWriter("graphs.txt");
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
    
            for(int i = 0; i < 5; i++){
                for(int o = 0; o < 5; o++){
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
                    for(int n = 0; n < eNodes.size(); n++){
                        fw.write(eNodes.get(n) + "\n");
                    }
                    System.out.println("Graph " + (i+1) + " Complete, With: " + vSize[i] + " Verticies, and: " + eSize[o] + " Edges\n");
                    fw.write("Verticies:Edges for Above Graph: " + vSize[i] + ":" + eSize[o] + " \n");
                    fw.write("\n");
                    vNodes.clear();
                    eNodes.clear();
                }
            }
            System.out.println("Graphs Created under graphs.txt");
            fw.close();
        }
        catch(IOException e){
            System.out.println("an error has occured");
        }


    }
}
