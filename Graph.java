
public class Graph {
    
    private class Edge { // Doubly linked list node
        int vertex, weight;
        Edge prev, next;

        Edge(int v, int w, Edge p, Edge n) {
          vertex = v;
          weight = w;
          prev = p;
          next = n;
        }
      }
    
    private Edge[] nodeArray;
    private Object[] nodeValues;
    private int numEdge;

    // No real constructor needed
    Graph(int n) {
        init(n);
    }

    // Initialize the graph with n vertices
    public void init(int n) {
      nodeArray = new Edge[n]; 
      // List headers;
      for (int i=0; i<n; i++) { 
          nodeArray[i] = new Edge(-1, -1, null, null); 
          }
      nodeValues = new Object[n];
      numEdge = 0;
    }
    
    // Return the number of vertices
    public int nodeCount() { return nodeArray.length; }

    // Return the current number of edges
    public int edgeCount() { return numEdge; }

    // Get the value of node with index v
    public Object getValue(int v) { return nodeValues[v]; }

    // Set the value of node with index v
    public void setValue(int v, Object val) { nodeValues[v] = val; }
    
    
    public int insert() {
       return 0; 
    }
    
    
    public void printDiameter() {
        
    }

}
