//package S26P4Graph;

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
    private int numEntries;
    private int[] freeEntries;
    private int freeCount;
    private String lastMessage = "";

    // No real constructor needed
    Graph(int n) {
        init(n);
    }


    // Initialize the graph with n vertices
    public void init(int n) {
        this.nodeArray = new Edge[n];
        // List headers;
        for (int i = 0; i < n; i++) {
            this.nodeArray[i] = new Edge(-1, -1, null, null);
        }
        this.nodeValues = new Object[n];
        this.numEdge = 0;
        this.numEntries = 0;
        this.freeEntries = new int[n]; // Stack
        this.freeCount = 0;
    }


    // Return the number of vertices
    public int nodeCount() {
        return nodeArray.length;
    }


    // Return the current number of edges
    public int edgeCount() {
        return numEdge;
    }


    // Get the value of node with index v
    public Object getValue(int v) {
        return nodeValues[v];
    }


    /**
     * Adding a node in the graph
     * 
     * 
     * @param v
     * @param val
     */
    public void setValue(int v, Object val) {
        nodeValues[v] = val;
    }


    /**
     * Return the link in v's neighbor list that preceeds the
     * // one with w (or where it would be)
     * ///
     * ///
     * 
     * @param v
     * @param w
     * @return
     */
    private Edge find(int v, int w) {
        Edge curr = nodeArray[v]; // Begins at head
        // Stop until the neighbor list ends, or the node we want to connect is
        // already connected
        while ((curr.next != null) && (curr.next.vertex < w)) {
            curr = curr.next;
        }
        return curr;
    }


    /**
     * Connects node v to node w
     * / Find the edge before. If node is connected, update the weight
     * If node is not connected, link curr.next to our new node.
     * 
     * @param v
     * @param w
     * @param wgt
     */
    public void addEdge(int v, int w, int wgt) {
        if (wgt == 0) {
            return;
        }
        Edge curr = find(v, w);
        if ((curr.next != null) && (curr.next.vertex == w)) {
            curr.next.weight = wgt;
        }
        else {
            curr.next = new Edge(w, wgt, curr, curr.next);
            numEdge++;
            if (curr.next.next != null) {
                curr.next.next.prev = curr.next;
            }
        }
    }


    // Get the weight value for an edge
    public int weight(int v, int w) {
        Edge curr = find(v, w);
        if ((curr.next == null) || (curr.next.vertex != w)) {
            return 0;
        }
        else {
            return curr.next.weight;
        }
    }


    /**
     * Called when numEntries is full.
     * 
     * 
     */
    public String expandGraph() {
        int newSize = nodeArray.length * 2;
        Edge[] newNodeArray = new Edge[newSize];
        Object[] newNodeValues = new Object[newSize];
        int[] newFreeEntries = new int[newSize];
        
        // Blank slate
        for (int i = 0; i < newSize; i++) {
            newNodeArray[i] = new Edge(-1, -1, null, null);
        }
        for (int i = 0; i < nodeArray.length; i++) {
            newNodeArray[i] = nodeArray[i];
            newNodeValues[i]= nodeValues[i];
        }
        // Copy over free slots
        for (int i = 0; i < freeEntries.length; i++) {
            newFreeEntries[i] = freeEntries[i];
        }
        
        this.nodeArray = newNodeArray;
        this.nodeValues = newNodeValues;
        this.freeEntries = newFreeEntries;
        
        return "Graph size doubled to "+newSize+"\r\n";
    }

    
    public String getMessage() {
        return this.lastMessage;
    }

    /**
     * Check if there is a free spot, and use the
     * 
     * 
     * @param v
     */
    public int addNode(String value) {
        int node = 0;
        // There is a free spot
        if (freeCount > 0) {
            freeCount--;
            node = freeEntries[freeCount];
        }
        // Otherwise, try to insert at numEntries. but check if graph is full
        else {
            if (numEntries >= nodeArray.length) {
                lastMessage = expandGraph();
            }
            node = numEntries;
        }
        setValue(node, value);
        numEntries++;
        return node;

    }


    public void removeNode(int v) {

        // Remove edges going out from v
        Edge curr = nodeArray[v].next;
        while (curr != null) {
            numEdge--;
            curr = curr.next;
        }
        nodeArray[v].next = null;
        // Remove edges coming into v
        for (int i = 0; i < nodeArray.length; i++) {
            if (i == v) {
                removeEdge(i, v);
            }
        }
        freeEntries[freeCount] = v;
        freeCount++;
        nodeValues[v] = null;
        numEntries--;
    }


    // Removes the edge from the graph.
    public void removeEdge(int v, int w) {
        Edge curr = find(v, w);
        if ((curr.next == null) || curr.next.vertex != w) {
            return;
        }
        else {
            curr.next = curr.next.next;
            if (curr.next != null) {
                curr.next.prev = curr;
            }
        }
        numEdge--;
    }


    // Returns true iff the graph has the edge
    public boolean hasEdge(int v, int w) {
        return weight(v, w) != 0;
    }


    // Returns an array containing the indicies of the neighbors of v
    public int[] neighbors(int v) {
        int cnt = 0;
        Edge curr;
        for (curr = nodeArray[v].next; curr != null; curr = curr.next) {
            cnt++;
        }
        int[] temp = new int[cnt];
        cnt = 0;
        for (curr = nodeArray[v].next; curr != null; curr = curr.next) {
            temp[cnt++] = curr.vertex;
        }
        return temp;
    }


    /**
     * Uses Disjoint Set Union to find islands
     * 
     * @return
     */
    public String getGraphInfo(ParPtrTree ptr) {
        ParPtrTree tree = ptr;
        int[] sizes = new int[nodeArray.length]; //
        int maxCount = 0; // Max nodes for any islands
        int numIslands = 0;

        // Loop over every vertex
        for (int i = 0; i < nodeArray.length; i++) {
            Edge curr = nodeArray[i].next; // Get the real edge past sentinnel
            while (curr != null) {
                int v = i;
                int w = curr.vertex;
                if (tree.FIND(v) != tree.FIND(w)) {
                    tree.UNION(v, w);
                }
                curr = curr.next;
            }
        }

        for (int i = 0; i < nodeArray.length; i++) {
            if (getValue(i) != null) {
                int root = tree.FIND(i);
                sizes[root]++;

                if (sizes[root] > maxCount) {
                    maxCount = sizes[root];
                }
            }
        }
        for (int i = 0; i < sizes.length; i++) {
            System.out.println(sizes[i]);
        }
        // Get num islands
        for (int i = 0; i < sizes.length; i++) {
            if (sizes[i] > 0) {
                numIslands++;
            }
        }
        // Run Floyd on every root to see the max diameters
        int maxDiameter = 0;
        for (int i = 0; i < sizes.length; i++) {
            if (sizes[i] == maxCount) { // Check if this node leads to root i
                System.out.println("New root " + i);

                int[] currentIsland = new int[maxCount];
                int c = 0;
                for (int j = 0; j < nodeArray.length; j++) {
                    if (getValue(j) != null && tree.FIND(j) == i) {
                        currentIsland[c] = j;
                        c++;
                    }
                }
                int d = computeDiameter(currentIsland);
                if (d > maxDiameter) {
                    maxDiameter = d;
                }

            }
        }

        return "There are " + numIslands + " connected components\r\n"
            + "The largest connected component has " + maxCount
            + " elements\r\n" + "The diameter of the largest component is "
            + maxDiameter + "\r\n";
    }


    /**
     * Implements Floyds Algorithim
     * Computes diameter
     * 
     * @param nodes
     *            that are apart of this island
     * @return
     */
    public int computeDiameter(int[] nodes) {
        int n = nodes.length;
        int[][] D = new int[n][n];
        Graph G = this;

        for (int i = 0; i < n; i++) { // Initialize D with weights
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    int w = G.weight(nodes[i], nodes[j]);
                    if (w != 0) {
                        D[i][j] = 1; // all edges have a weight of one
                    }
                    else {
                        D[i][j] = Integer.MAX_VALUE;
                    }
                }
                else {
                    D[i][j] = 0;
                }
            }
        }
        System.out.println("D before floyds");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.println(D[i][j]);
            }
        }
        for (int k = 0; k < n; k++) { // Compute all k paths
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if ((D[i][k] != Integer.MAX_VALUE)
                        && (D[k][j] != Integer.MAX_VALUE) && (D[i][j] > (D[i][k]
                            + D[k][j]))) {
                        D[i][j] = D[i][k] + D[k][j];
                    }
                }
            }
        }
        System.out.println("D after floyds");

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.println(D[i][j]);
            }
        }

        int diameter = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (D[i][j] != Integer.MAX_VALUE && D[i][j] > diameter) {
                    diameter = D[i][j];
                }
            }
        }
        return diameter;
    }


    public int getNumEntries() {
        return numEntries;
    }

}
