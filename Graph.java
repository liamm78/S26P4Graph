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
        numEntries++;
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
    public int getIslands() {
        ParPtrTree tree = new ParPtrTree(nodeValues.length);
        int islandCount = nodeValues.length;
        // Loop over every vertex
        for (int i = 0; i < nodeArray.length; i++) {
            Edge curr = nodeArray[i].next; // Get the real edge past sentinnel
            while (curr != null) {
                int v = i;
                int w = curr.vertex;
                if (tree.FIND(v) != tree.FIND(w)) {
                    tree.UNION(v, w);
                    islandCount--;
                }
                curr = curr.next;
            }
        }
        return islandCount;
    }


    public int getNumEntries() {
        return numEntries;
    }

}
