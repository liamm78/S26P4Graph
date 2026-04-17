/**
 * Implement a hash table.
 * Data: Strings
 * Hash function: sfold
 * Collision Resolution: Quadratic probing
 *
 * @author <Your name(s) here
 * @version <Put something here>
 */


public class Hash
{
    private int[] hash;
    private String name;
    private Graph graph;

    /**
     * Create a new Hash object.
     *
     * @param n
     *            String for the table name
     * @param isize
     *            The initial size (and increment size) for the table
     */
    public Hash(String n, int isize, Graph graph)
    {
        this.hash = new int[isize]; // Create an array of ints (node_ids
        this.name = n;
    }
    
    
    /**
     * Insert a string into a hash table.
     * 
     * If a tombstone is encountered, mark it as a possible spot.
     * Keep searching for null to confirm there are no empty spots.
     * 
     * Get a node_id from GraphDB
     * // Uses quadratic probing and the h hash function to find an index. Then
     * // assign the Handle to that index.
     * ///
     * Insert the expansion once more than half of the hash table is full.
     * ///
     * 
     * @param s
     * @return Node_ID from the Graph
     */
    public String insert(String s) {
        return null;
    }
    
    

    /**
     * Compute the hash function. Uses the "sfold" method from the OpenDSA
     * module on hash functions
     *
     * @param s
     *            The string that we are hashing
     * @param m
     *            The size of the hash table
     * @return The home slot for that string
     */
    public int h(String s, int m) {
        long sum = 0;
        long mult = 1;
        for (int i = 0; i < s.length(); i++) {
            mult = (i % 4 == 0) ? 1 : mult * 256;
            sum += s.charAt(i) * mult;
        }
        return (int)(Math.abs(sum) % m);
    }
}
