import java.io.File;
import java.util.*;

public class Graph {
    private int vertexCt;  // Number of vertices in the graph.
    private int[][] capacity;  // Adjacency  matrix
    private int[][] residual; // residual matrix
    private int[][] edgeCost; // cost of edges in the matrix
    private String graphName;  //The file from which the graph was created.
    private int totalFlow; // total achieved flow
    private int source = 0; // start of all paths
    private int target; // end of all paths
    private int[] predicessor;
    private int[] cost;

    public Graph(String fileName) {
        this.vertexCt = 0;
        source  = 0;
        this.graphName = "";
        makeGraph(fileName);
    }

    /**
     * Method to add an edge
     *
     * @param source      start of edge
     * @param destination end of edge
     * @param cap         capacity of edge
     * @param weight      weight of edge, if any
     * @return edge created
     */
    private boolean addEdge(int source, int destination, int cap, int weight) {
        if (source < 0 || source >= vertexCt) return false;
        if (destination < 0 || destination >= vertexCt) return false;
        capacity[source][destination] = cap;
        residual[source][destination] = cap;
        edgeCost[source][destination] = weight;
        edgeCost[destination][source] = -weight;
        return true;
    }

    /**
     * Method to get a visual of the graph
     *
     * @return the visual
     */
    public String printMatrix(String label, int[][] m) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n " + label+ " \n     ");
        for (int i=0; i < vertexCt; i++)
            sb.append(String.format("%5d", i));
        sb.append("\n");
        for (int i = 0; i < vertexCt; i++) {
            sb.append(String.format("%5d",i));
            for (int j = 0; j < vertexCt; j++) {
                sb.append(String.format("%5d",m[i][j]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Method to make the graph
     *
     * @param filename of file containing data
     */
    private void makeGraph(String filename) {
        try {
            graphName = filename;
            System.out.println("\n****Find Flow " + filename);
            Scanner reader = new Scanner(new File(filename));
            vertexCt = reader.nextInt();
            capacity = new int[vertexCt][vertexCt];
            residual = new int[vertexCt][vertexCt];
            edgeCost = new int[vertexCt][vertexCt];

            for (int i = 0; i < vertexCt; i++) {
                for (int j = 0; j < vertexCt; j++) {
                    capacity[i][j] = 0;
                    residual[i][j] = 0;
                    edgeCost[i][j] = 0;
                }
            }

            // If weights, need to grab them from file
            while (reader.hasNextInt()) {
                int v1 = reader.nextInt();
                int v2 = reader.nextInt();
                int cap = reader.nextInt();
                int weight = reader.nextInt();
                if (!addEdge(v1, v2, cap, weight))
                    throw new Exception();
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        target = vertexCt - 1;
        this.predicessor = new int[vertexCt];
        System.out.println( printMatrix("Edge Cost" ,edgeCost));
    }

    //Bellman Ford algorithm
    public boolean hasCheapestPath() {
        int INF = 9999;
        cost = new int[this.vertexCt];

        for (int i = 0; i < this.vertexCt; i++) {
            cost[i] = INF;
            predicessor[i] = -1;
        }

        cost[source] = 0;
        for (int repeat = 0; repeat < vertexCt; repeat++) {
            for (int j = 0; j < vertexCt; j++) {
                for (int k = 0; k < vertexCt; k++) {
                    if (this.residual[j][k] > 0 && cost[j] + this.edgeCost[j][k] < cost[k]) {
                        cost[k] = cost[j] + this.edgeCost[j][k];
                        predicessor[k] = j;
                    }
                }
            }
        }
        return predicessor[target] >= 0;
    }

    public void printPath() {
        StringBuilder path = new StringBuilder();
        int previous = 0;
        int bestFlow = 9999;
        String totalCostList = "";
        int counter = 0;

        System.out.println("Paths found in order");
        while (this.hasCheapestPath()) {
            int node = vertexCt - 1;
            int totalCost = 0;

            while (node > 0) {
                previous = predicessor[node];
                bestFlow = Math.min(bestFlow, this.residual[previous][node]);
                this.residual[previous][node] -= bestFlow;
                this.residual[node][previous] += bestFlow;
                totalCost += this.edgeCost[previous][node];
                totalCostList = " $ " + totalCost;
                path.append(" ").append(node);
                node = previous;
            }
            path.append(" 0 ");
            path.append("\n");

            String[] paths = path.toString().split("\n");
            for (int i = 0; i < paths.length; i++) {
                if (i == counter) {
                    for (int j = paths[i].length() - 1; j >= 0; j--) {
                        System.out.print(paths[i].charAt(j));
                    }
                    counter ++;
                    System.out.print("(" + bestFlow + ")");
                    System.out.print(totalCostList);
                }
            }
            System.out.println();
        }
        System.out.println();

        // iterate over all connections
        System.out.println("Final flow on each edge");
        for (int i = 1; i < vertexCt - 1; i++) {
                for (int j = 1; j < vertexCt - 1; j++) {
                    if (this.capacity[i][j] - this.residual[i][j] > 0) {
                        System.out.println("Flow " + i + " -> " + j + " " + " (" +  (this.capacity[i][j] - this.residual[i][j]) + ")" +  " $" + this.edgeCost[i][j]);
                }
            }
        }
    }


    public void minCostMaxFlow(){
        System.out.println(printMatrix("Capacity", capacity));
        //findWeightedFlow();
        System.out.println(printMatrix("Residual", residual));
        //finalEdgeFlow();
    }

    public static void main(String[] args) {
        String[] files = {"match0.txt", "match1.txt", "match2.txt", "match3.txt", "match4.txt", "match5.txt", "match6.txt", "match7.txt", "match8.txt", "match9.txt"};
        for (String fileName : files) {
            Graph graph = new Graph(fileName);
            graph.minCostMaxFlow();
            graph.printPath();
        }
    }
}