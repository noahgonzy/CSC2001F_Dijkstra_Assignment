import java.util.ArrayList;
import java.util.Random;

public class GenerateGraphs {
    static int[] vSize = {10,20,30,40,50};
    static int[] eSize = {20,35,50,65,80};

    public static void GenerateNewGraphs(){
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
                    System.out.println(eNodes.get(n));
                }
                System.out.println("Graph " + (i+1) + " Complete, With: " + vSize[i] + " Verticies, and: " + eSize[o] + " Edges\n");
                vNodes.clear();
                eNodes.clear();
            }
        }
    }
}
