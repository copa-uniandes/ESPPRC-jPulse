/**

* This class stores a graph as an array on nodes. Also holds the global information used by the algorithm.

 * Ref.: Lozano, L., Duque, D., and Medaglia, A. L. (2013). 

 * An exact algorithm for the elementary shortest path problem with resource constraints. Technical report COPA 2013-2  

 * @author L. Lozano & D. Duque

 * @affiliation Universidad de los Andes - Centro para la Optimización y Probabilidad Aplicada (COPA)

 * @url http://copa.uniandes.edu.co/

 **/

package pulse;


public class GraphManager{

	public static Node[] nodes;			// Array of nodes
	public static int[] visited;			// Binary indicator for detecting cycles in the bounding stage
	public static int[][] visitedMT;		// Binary indicator for detecting cycles one for each thread
	public static double PrimalBound;		// Primal bound updated through the execution of the algorithm
	public static double naiveDualBound;	// Naive dual bound 
	public static double[][] boundsMatrix;	// Bounds matrix
	public static double[] bestCost;		// Best cost found for each node at each iteration of the bounding stage
	public static double overallBestCost;	// Overall best cost found at each iteration of the bounding stage
	public static double timeIncumbent;		// Time incumbent for the bounding stage
	
	public static FinalNode finalNode;		// The final node overrides the class node and is different because it stops the recursion
		
	// Class constructor
	public GraphManager( int numNodes) {
		
		nodes = new Node[numNodes];
		visited = new int[numNodes];
		visitedMT = new int[numNodes][DataHandler.numThreads+1];
		boundsMatrix= new double [numNodes][(int) Math.ceil(DataHandler.tw_b[0]/DataHandler.boundStep)+1];
		bestCost= new double [numNodes];
		for(int i=1; i<numNodes; i++){
			bestCost[i]=Double.POSITIVE_INFINITY;
		}
		
		PrimalBound= 0;
		overallBestCost=0;
		finalNode = new FinalNode(numNodes, 0, 0, 0, DataHandler.tw_b[0]);
		
	}

	// This method adds a vertex to the graph
	public boolean addVertex(Node v) {
		nodes[v.getID()] = v;
		return true;
	}
	
	// This method returns the array of nodes
	public Node[] getNodes(){
		return nodes;
	}
	
	//This method finds the best arc regarding the cost/time ratio
	public static void  calNaiveDualBound() {
		GraphManager.naiveDualBound=Double.POSITIVE_INFINITY;
		for (int i = 0; i < DataHandler.numArcs; i++) {
			if(DataHandler.timeList[i]!=0 && DataHandler.costList[i]/DataHandler.timeList[i]<=GraphManager.naiveDualBound ){
				GraphManager.naiveDualBound=DataHandler.costList[i]/DataHandler.timeList[i];
				}
			}
		}

}
