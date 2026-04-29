// package S26P4Graph;

public class Graph {

    class Edge { // Doubly linked list node
        /**
         * Current vertex
         */
        int vertex;
        /**
         * Current weight of the edge
         */
        int weight;
        /**
         * Previous edge
         */
        Edge prev;
        /**
         * Next edge
         */
        Edge next;

        /**
         * Doubly linked list that defines an edge (prev, next) ptrs, and weight
         * assigned
         * 
         * @param v
         *            node for the edge
         * @param w
         *            weight for the egde
         * @param p
         *            prev Edge
         * @param n
         *            next Edge
         */
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


    /**
     * Initialize the graph with n nodes/vertices
     * 
     * @param n
     *            Number of nodes to initialize
     */
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


    /**
     * Return the current number of nodes
     * 
     * @return current number of nodes
     */
    public int nodeCount() {
        return nodeArray.length;
    }


    /**
     * Return the current number of edges
     * 
     * @return current number of edges
     */
    public int edgeCount() {
        return numEdge;
    }


    /**
     * Get the value of node with index v
     * 
     * @param v
     *            Node idx
     * @return Value of the node
     */
    public Object getValue(int v) {
        return nodeValues[v];
    }


    /**
     * Return the current number of free spots
     * 
     * @return number of free spots
     */
    public int freeCount() {
        return freeCount;
    }


    /**
     * Setting a value for a node in the graph
     * 
     * 
     * @param v
     *            Node to set the value for
     * @param val
     *            The value to set
     */
    public void setValue(int v, Object val) {
        nodeValues[v] = val;
    }


    /**
     * Return the link in v's neighbor list that preceeds the
     * // one with w (or where it would be)
     * 
     * @param v
     *            Node 1
     * @param w
     *            Node 2
     * @return The edge we found that connects v and w.
     */
    public Edge find(int v, int w) {
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
     *            Node 1
     * @param w
     *            Node 2
     * @param wgt
     *            Weight of edge
     */
    public void addEdge(int v, int w, int wgt) {
        if (wgt == 0) {
            return;
        }
        Edge curr = find(v, w);
        // 1. Edge already exists: Update weight
        if (curr.next != null && curr.next.vertex == w) {
            curr.next.weight = wgt;
        }
        // 2. Edge does not exist: Create new edge between curr and curr.next
        else {
            Edge newEdge = new Edge(w, wgt, curr, curr.next);
            // Edge is in the middle
            if (curr.next != null) {
                curr.next.prev = newEdge;
            }
            curr.next = newEdge;
            numEdge++;
        }
    }


    /**
     * Find weight value for an edge between v and w.
     * 
     * @param v
     *            Node 1
     * @param w
     *            Node 2
     * @return Edge's weight value. 0 if no edge exists
     */
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
     * Called when numEntries is full. Creates a copy of a new graph
     * double the current node size
     * and copies everything over
     * 
     * 
     * @return Graph expansion string
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
            newNodeValues[i] = nodeValues[i];
        }
        // Copy over free slots
        for (int i = 0; i < freeEntries.length; i++) {
            newFreeEntries[i] = freeEntries[i];
        }

        this.nodeArray = newNodeArray;
        this.nodeValues = newNodeValues;
        this.freeEntries = newFreeEntries;

        return "Graph size doubled to " + newSize + "\r\n";
    }


    /**
     * Used for fetching any messages not returned from the method (ie.
     * expandGraph)
     * 
     * @return last message logged
     */
    public String getMessage() {
        return this.lastMessage;
    }


    /**
     * Finds which node idx to insert the string at
     * Check if there is a free spot, and add a node in that position if its
     * free. Otherwise, try to insert at numEntries. If the graph is full,
     * expand it
     * 
     * 
     * @param value
     *            String to insert
     * @return node idx the string was inserted at
     * 
     */
    public int addNode(String value) {
        int node = 0;
        lastMessage = "";
        if (freeCount > 0) {
            freeCount--;
            node = freeEntries[freeCount];
        }
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


    /**
     * Removes a node from a graph
     * 
     * @param v
     *            Node to remove
     */
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
            if (i != v) {
                removeEdge(i, v);
            }
        }
        freeEntries[freeCount] = v;
        freeCount++;
        nodeValues[v] = null;
        numEntries--;
    }


    /**
     * Removes the edge from a graph
     * 
     * @param v
     *            Node 1
     * @param w
     *            Node 2
     */
    public void removeEdge(int v, int w) {
        Edge curr = find(v, w);
        if (curr.next == null) {
            return;
        }
        if (curr.next.vertex != w) {
            return;
        }
        else {
            curr.next = curr.next.next;
            if (curr.next != null) { // middle
                curr.next.prev = curr;
            }
        }
        numEdge--;
    }


    /**
     * Returns true if the graph has an edge v, w
     * 
     * @param v
     *            Node 1
     * @param w
     *            Node 2
     * @return If the edge containing v, w exists
     */
    public boolean hasEdge(int v, int w) {
        return weight(v, w) != 0;
    }


    /**
     * Returns an array containing the indicies of the neighbors of v
     * 
     * @param v
     *            Node we want to find neighbors for
     * @return Neighbors of node v in an array
     */
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
     * @param t
     *            ParPtrTree object we store the components in
     * 
     * @return String that contains the number of connected components, how many
     *         elements the largest connected component has, and the diameter of
     *         it
     */
    public String getGraphInfo(ParPtrTree t) {
        ParPtrTree tree = t;

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
     *            Nodes that are apart of this island
     * @return Diameter computed from Floyds
     */
    public int computeDiameter(int[] nodes) {
        int n = nodes.length;
        int[][] dMat = new int[n][n];
        Graph gMat = this;

        for (int i = 0; i < n; i++) { // Initialize D with weights
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    int w = gMat.weight(nodes[i], nodes[j]);
                    if (w != 0) {
                        dMat[i][j] = 1; // all edges have a weight of one
                    }
                    else {
                        dMat[i][j] = Integer.MAX_VALUE;
                    }
                }
                else {
                    dMat[i][j] = 0;
                }
            }
        }
        for (int k = 0; k < n; k++) { // Compute all k paths
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if ((dMat[i][k] != Integer.MAX_VALUE)
                        && (dMat[k][j] != Integer.MAX_VALUE)
                        && (dMat[i][j] > (dMat[i][k] + dMat[k][j]))) {
                        dMat[i][j] = dMat[i][k] + dMat[k][j];
                    }
                }
            }
        }

        int diameter = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (dMat[i][j] != Integer.MAX_VALUE && dMat[i][j] > diameter) {
                    diameter = dMat[i][j];
                }
            }
        }
        return diameter;
    }


    /**
     * Return number of current entries in graph
     * 
     * @return entires in Graph
     */
    public int getNumEntries() {
        return numEntries;
    }

}
