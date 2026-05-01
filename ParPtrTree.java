// package S26P4Graph;

public class ParPtrTree {
    // Data structure for handling parent relationships/root finding

    private int[] array; // Parent ptr array.

    /**
     * Class to manage parent pointer relationships between the graph
     * 
     * @param size
     *            Amount of nodes in graph
     */
    ParPtrTree(int size) {
        array = new int[size]; // Create node array
        for (int i = 0; i < size; i++) {
            array[i] = -1; // Each node is its own root to start
        }
    }


    /**
     * Merge two subtrees if they are different
     * 
     * @param a
     *            Node A to merge
     * @param b
     *            Node B to merge
     */
    public void union(int a, int b) {
        int root1 = find(a); // Find root of node a
        int root2 = find(b); // Find root of node b
        if (root1 != root2) { // Merge two trees
            array[root1] = root2;
        }
    }


    /**
     * Return root of current tree
     * 
     * @param curr
     *            Root to find
     * @return Root found
     */
    public int find(int curr) {
        while (array[curr] != -1) {
            curr = array[curr];
        }
        return curr; // Now at root
    }
}
