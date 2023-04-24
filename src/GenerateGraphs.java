import java.util.ArrayList;

public class GenerateGraphs {
    static int[] vSize = {10,20,30,40,50};
    static int[] eSize = {20,35,50,65,80};

    public static void GenerateNewGraphs(){
        ArrayList<Vertex> vNodes = new ArrayList<>();
        ArrayList<Edge> eNodes = new ArrayList<>();
        
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < vSize[i]; j++){
                vNodes.add(new Vertex("Node" + j));
            }
            for(int k = 0; k < eSize[i]; k++){
                eNodes.add(i, null);
            }
            vNodes.clear();
        }
    }
}
