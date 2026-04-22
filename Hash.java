/**
 * Implement a hash table.
 * Data: Strings
 * Hash function: sfold
 * Collision Resolution: Quadratic probing
 *
 * @author <Your name(s) here
 * @version <Put something here>
 */

public class Hash {
    private int[] hash;
    private String name;
    private int occupiedRecords = 0;
    private int hashSize;
    private Graph graph;

    private static final int EMPTY = -1;
    private static final int TOMBSTONE = -2;

    /**
     * Create a new Hash object.
     *
     * @param n
     *            String for the table name
     * @param isize
     *            The initial size (and increment size) for the table
     */
    public Hash(String n, int isize, Graph graph) {
        this.hash = new int[isize]; // Create an array of ints (node_ids
        this.hashSize = isize;
        this.name = n;
        this.graph = graph;
        for (int i = 0; i < isize; i++) {
            hash[i] = EMPTY;
        }
    }


    public int[] getTable() {
        return hash;
    }


    public String expandHash() {
        int newHashSize = hashSize * 2;
        int[] newHash = new int[newHashSize];
        occupiedRecords = 0;
        
        for (int i = 0; i < newHashSize; i++) {
            newHash[i] = EMPTY;
        }

        for (int i = 0; i < hashSize; i++) {
            int currentNode = this.hash[i];
            

            if (currentNode != EMPTY && currentNode != TOMBSTONE) {
                int anchorIdx = this.h((String)graph.getValue(currentNode),
                    newHashSize);
                int skip = 0;

                while (skip < newHashSize) {
                    int pos = (anchorIdx + skip * skip) % newHashSize;

                    if (newHash[pos] == EMPTY) {
                        newHash[pos] = currentNode;
                        occupiedRecords++;
                        break;
                    }
                    skip++;
                }

            }

        }
        this.hash = newHash;
        this.hashSize = newHashSize;
        return name + " hash table size doubled\n";

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
     * @return Result of insertion
     */
    public String insert(String s, int nodeId) {
        int possibleSpot = -1;
        String str = "";
        if (occupiedRecords + 1 > hashSize / 2) {
            str += this.expandHash();
        }

        int idx = this.h(s, hashSize);
        int skip = 0;

        while (skip < hashSize) {
            int pos = (idx + skip * skip) % hashSize;
            int currVal = this.hash[pos];

            if (currVal == EMPTY) {
                if (possibleSpot == -1) { // If first pass
                    possibleSpot = pos;
                }
                break;
            }
            else if (currVal == TOMBSTONE) {
                if (possibleSpot == -1) // If first pass
                    possibleSpot = pos;
            }
            else {
                String graphVal = (String)graph.getValue(currVal);
                if (graphVal.equals(s)) {
                    return "Duplicate";
                }
            }
            skip++;
        }
        hash[possibleSpot] = nodeId;
        occupiedRecords++;
        if (name.equals("song")) {
            str += "|" + s + "| is added to the Song database\r\n";
        }
        else if (name.equals("artist")) {
            str += "|" + s + "| is added to the Artist database\r\n";
        }
        // doubling + adding
        return str;
    }


    /**
     * Check if string exists in database. Return the idx
     * If it lands on empty, nothing is furthur, and nothing is found.
     * 
     * O(1) lookup time
     * Returns -1 if entry not found
     * 
     * @param s
     * @return
     */
    public int get(String s) {
        int possibleSpot = -1;

        int idx = this.h(s, hashSize);
        int skip = 0;

        while (skip < hashSize) {
            int pos = (idx + skip * skip) % hashSize;
            int currVal = this.hash[pos];

            if (currVal == EMPTY) {
                return -1;
            }
            else if (currVal == TOMBSTONE) {
                if (possibleSpot == -1) // If first pass
                    possibleSpot = pos;
            }
            else {
                String graphVal = (String)graph.getValue(currVal);
                if (graphVal.equals(s)) { // Found it
                    return pos;
                }
            }
            skip++;
        }
        return -1; // Not in table
    }


    public int getHashSize() {
        return this.hashSize;
    }
    
    public int getOccupiedRecords() {
        return this.occupiedRecords;
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
