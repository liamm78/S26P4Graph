public class ParPtrTree {
    // Data structure for handling parent relationships/root finding
    
    
  private int[] array;     // Parent ptr array. 

  ParPtrTree(int size) {
    array = new int[size]; // Create node array
    for (int i=0; i<size; i++) {
      array[i] = -1;       // Each node is its own root to start
    }
  }

  // Merge two subtrees if they are different
  public void UNION(int a, int b) {
    int root1 = FIND(a);     // Find root of node a
    int root2 = FIND(b);     // Find root of node b
    if (root1 != root2) {          // Merge two trees
      array[root1] = root2;
    }
  }

  // Return the root of curr's tree
  public int FIND(int curr) {
    while (array[curr] != -1) {
      curr = array[curr];
    }
    return curr; // Now at root
  }
}