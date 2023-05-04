import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.StringTokenizer;


// Graph class: evaluate shortest paths.
//
// CONSTRUCTION: with no parameters.
//
// ******************PUBLIC OPERATIONS**********************
// void addEdge( String v, String w, double cvw )
//                              --> Add additional edge
// void printPath( String w )   --> Print path after alg is run
// void unweighted( String s )  --> Single-source unweighted
// void dijkstra( String s )    --> Single-source weighted
// void negative( String s )    --> Single-source negative weighted
// void acyclic( String s )     --> Single-source acyclic
// ******************ERRORS*********************************
// Some error checking is performed to make sure graph is ok,
// and to make sure graph satisfies properties needed by each
// algorithm.  Exceptions are thrown if errors are detected.

public class Graph
{
    static Integer opcount_e = 0;
    static Integer opcount_v = 0;
    static Integer opcount_pq = 0;
    static Integer opcount_pql = 0;
    static Integer numFiles, counter;
    static String verticies,edges = "";
    static ArrayList<String> infotofile = new ArrayList<String>();
    public static final double INFINITY = Double.MAX_VALUE;
    private Map<String,Vertex> vertexMap = new HashMap<String,Vertex>( );

    /**
     * Add a new edge to the graph.
     */
    public void addEdge( String sourceName, String destName, double cost )
    {
        Vertex v = getVertex( sourceName );
        Vertex w = getVertex( destName );
        v.adj.add( new Edge( w, cost ) );
    }

    /**
     * Driver routine to handle unreachables and print total cost.
     * It calls recursive routine to print shortest path to
     * destNode after a shortest path algorithm has run.
     */
    public void printPath( String destName )
    {
        Vertex w = vertexMap.get( destName );
        if( w == null )
            throw new NoSuchElementException( "Destination vertex not found" );
        else if( w.dist == INFINITY )
            System.out.println( destName + " is unreachable" );
        else
        {
            System.out.print( "(Cost is: " + w.dist + ") " );
            printPath( w );
            System.out.println( );
        }
    }

    /**
     * If vertexName is not present, add it to vertexMap.
     * In either case, return the Vertex.
     */
    private Vertex getVertex( String vertexName )
    {
        Vertex v = vertexMap.get( vertexName );
        if( v == null )
        {
            v = new Vertex( vertexName );
            vertexMap.put( vertexName, v );
        }
        return v;
    }

    /**
     * Recursive routine to print shortest path to dest
     * after running shortest path algorithm. The path
     * is known to exist.
     */
    private void printPath( Vertex dest )
    {
        if( dest.prev != null )
        {
            printPath( dest.prev );
            System.out.print( " to " );
        }
        System.out.print( dest.name );
    }
    
    /**
     * Initializes the vertex output info prior to running
     * any shortest path algorithm.
     */
    private void clearAll( )
    {
        for( Vertex v : vertexMap.values( ) )
            v.reset( );
    }

    /**
     * Single-source weighted shortest-path algorithm. (Dijkstra) 
     * using priority queues based on the binary heap. 
     * It also resets the edge process, vertex process, priority queue counter, and priority queue log counter to 0.
     */
    public void dijkstra( String startName )
    {
        opcount_pq = 0;
        opcount_pql = 0;
        opcount_v = 0;
        opcount_e = 0;
        PriorityQueue<Path> pq = new PriorityQueue<Path>( );

        Vertex start = vertexMap.get( startName );
        if( start == null )
            throw new NoSuchElementException( "Start vertex not found" );

        clearAll( );
        pq.add( new Path( start, 0 ) ); start.dist = 0;
        
        int nodesSeen = 0;
        while( !pq.isEmpty( ) && nodesSeen < vertexMap.size( ) )
        {
            //Incremeting pq counter
            opcount_pq += 1;
            opcount_pql += (int)(Math.log(pq.size()) / Math.log(2));

            Path vrec = pq.remove( );
            Vertex v = vrec.dest;
            if( v.scratch != 0 )  // already processed v
                continue;
            
            //vertex is being processed
            opcount_v++;

            v.scratch = 1;
            nodesSeen++;

            for( Edge e : v.adj )
            {
                Vertex w = e.dest;
                double cvw = e.cost;
                
                if( cvw < 0 )
                    throw new GraphException( "Graph has negative edges" );
                
                //edge is being processed
                opcount_e ++;
                    
                if( w.dist > v.dist + cvw )
                {
                    w.dist = v.dist +cvw;
                    w.prev = v;
                    pq.add( new Path( w, w.dist ) );
                    opcount_pq += (int)(Math.log(pq.size()) / Math.log(2));
                }
            }
        }
    }

    /**
     * Process a request; return false if end of file.
     */
    public static boolean processRequest(Graph g)
    {
        try
        {
            
            String startName = "0";
            String destName = String.valueOf(g.vertexMap.size()-1);

            g.dijkstra( startName );
            
            infotofile.add(counter + " " + verticies + " " + g.vertexMap.size() + " " + edges + " " + opcount_v + " " + opcount_e + " " + opcount_pq + " " + opcount_pql);
            System.out.println("graph " + counter);
            System.out.println("Potential Vetexes: " + verticies);
            System.out.println("Vertex Map Size: " + g.vertexMap.size());
            System.out.println("Edges: " + edges);
            System.out.println("Vertex operations: " + opcount_v);
            System.out.println("Edge operations: " + opcount_e);
            System.out.println("Priority Queue operations: " + opcount_pq);
            System.out.println("Priority Queue log operations: " + opcount_pql);
            g.printPath( destName );
            System.out.println("");
            
        }
        catch( NoSuchElementException e )
          { return false; }
        catch( GraphException e )
          { System.err.println( e ); }
        return true;
    }

    /**
     * A main routine that:
     * 1. Reads a file containing edges (supplied as a command-line parameter);
     * 2. Forms the graph;
     * 3. Repeatedly prompts for two vertices and
     *    runs the shortest path algorithm.
     * The data file is a sequence of lines of the format:
     *    source destination cost
     */
    public static void main( String [ ] args )
    {
        try{
            FileReader fi = new FileReader("data/infoFile.txt");
            Scanner ifs = new Scanner(fi);
            String line = ifs.nextLine();
            ifs.close();
            numFiles = Integer.parseInt(line);
        }
        catch(FileNotFoundException e){
            System.out.println("No Info File, Found");
        }
        setGraph();
    }
    
    /**
     * This function actaully assigns all the graphs for the algorithm to run on. 
     */
    public static void setGraph(){
        for(counter = 0; counter < numFiles; counter++){
            Graph g = new Graph( );
            try{   
                FileReader f = new FileReader("data/graph" + counter + ".txt");
                Scanner graphFile = new Scanner(f);

                // Read the edges and insert
                    String line;
                    while(graphFile.hasNextLine())
                    {
                        line = graphFile.nextLine( );
                        if(line.indexOf(":") != -1){
                            edges = line.substring(line.indexOf(":") +1 , line.length());
                            verticies = line.substring(0, line.indexOf(":"));
                            break;
                        }
                        StringTokenizer st = new StringTokenizer( line );

                        try
                        {
                            if( st.countTokens( ) != 3 )
                            {
                                System.err.println( "Skipping ill-formatted line " + line );
                                continue;
                           }
                           String source  = st.nextToken( );
                           String dest    = st.nextToken( );
                           int    cost    = Integer.parseInt( st.nextToken( ) );
                           g.addEdge( source, dest, cost );
                       }
                       catch( NumberFormatException e )
                            { 
                                System.err.println( "Skipping ill-formatted line " + line ); 
                            }
                    }
                    graphFile.close();
                }
                catch( IOException e )
                    { 
                        System.err.println( e ); 
                    }
                System.out.println( "File read..." );
                System.out.println( g.vertexMap.size( ) + " vertices" );
                System.out.println( edges + " edges" );
                processRequest(g);
            }
            try{
                FileWriter fw = new FileWriter("Djikstraout.txt");
                fw.write("graph PotentialVertexes VertexMapSize Edges VertexOperations EdgeOperations PQOperations PQLOperations\n");
                for(int j = 0; j < infotofile.size(); j++){
                    fw.write(infotofile.get(j) + "\n");
                }
                fw.close();
            }
            catch(IOException e){
                System.out.println("An error has occured");
            }
    }
}
