import java.util.ArrayList;
import java.util.Random;

public class GenerateGraphs {
    static int[] vSize = {10,20,30,40,50};
    static int[] eSize = {20,35,50,65,80};

    public static void GenerateNewGraphs(){
        Random randc = new Random();
        Random randStart = new Random();
        Random randEnd = new Random();
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
            for(int j = 0; j < vSize[i]; j++){
                vNodes.add(""+j);
                System.out.println(j);
            }
            System.out.println("");
            for(int l = 0; l < vSize[i]-1; l++){
                cost = 1 + randc.nextInt(8);
                eNodes.add(l + " " + (l+1) + " " + cost);
                System.out.println(l + " " + (l+1) + " " + cost);
            }
            System.out.println("");
            k = 0;
            while(k < eSize[i]-vSize[i]+1){
                cost = 1 + randc.nextInt(8);
                start = randStart.nextInt(vSize[i]);
                end = randStart.nextInt(vSize[i]);
                if((start != end) && !(start > end)){
                    addnow = true;
                    toadd = start + " " + end + " " + cost;
                    for(int m = 0; m < eNodes.size(); m ++){
                        addcheck = toadd.substring(0, 3);
                        addcheckm = eNodes.get(m).substring(0, 3);
                        if(addcheck.equals(addcheckm)){
                            addnow = false;
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
            for(i = 0; i < eNodes.size(); i++){
                System.out.println(eNodes.get(i));
            }
            vNodes.clear();
            eNodes.clear();
            break;
        }
    }
}
