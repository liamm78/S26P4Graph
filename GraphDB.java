//package S26P4Graph;

import java.io.IOException;

/**
 * The database implementation for this project.
 * We have two hash tables and graph.
 *
 * @author CS3114/5040 Staff
 * @version Spring 2026
 */
public class GraphDB implements GPInterface {
    private Graph graph;
    private Hash artistTable;
    private Hash songTable;
    private int size;

    // ----------------------------------------------------------
    /**
     * Create a new GraphDB object.
     * But don't set anything -- that gets done by "create"
     */
    public GraphDB() {
    }


    /**
     * Create a brave new World.
     * GraphDB has two closed hash tables and the Graph itself
     *
     * @param inHash
     *            Initial size for hash tables
     * @return Error messages if appropriate
     */
    public String create(int inHash) {
        if (inHash < 0) {
            return "Initial hash table size must be positive";
        }
        if (graph != null) {
            return "Database already exists";
        }

        this.graph = new Graph(inHash);
        this.artistTable = new Hash("artist", inHash, graph);
        this.songTable = new Hash("song", inHash, graph);
        this.size = inHash;
        return null;
    }


    /**
     * Re-initialize the database
     * 
     * @return true on successful clear of database
     */
    public boolean clear() {
        if (graph == null || graph.getNumEntries() == 0) {
            return false;
        }
        this.graph = new Graph(this.size);
        this.artistTable = new Hash("artist", this.size, graph);
        this.songTable = new Hash("song", this.size, graph);
        return true;
    }


    // ----------------------------------------------------------
    /**
     * Insert to the hash table
     * 
     * First, check if there is space in the hash table, then insert nodes to
     * Graph
     *
     * @param artistString
     *            Artist string to insert
     * @param songString
     *            Song string to insert
     * @return Status message as appropriate
     * @throws IOException
     */
    public String insert(String artistString, String songString)
        throws IOException {
        if (graph == null) {
            return "Database not initialized";
        }
        if (artistString == null || songString == null || artistString.equals(
            "") || songString.equals("")) {
            return "Input strings cannot be null or empty";
        }
        String str = "";
        int artistIdx = artistTable.get(artistString);
        int songIdx = songTable.get(songString);
        int artistNode = -1;
        int songNode = -1;

        // Both exist in hash
        if (artistIdx != -1 && songIdx != -1) {
            artistNode = artistTable.getTable()[artistIdx];
            songNode = songTable.getTable()[songIdx];
            if (graph.hasEdge(artistNode, songNode)) {
                return "|" + artistString + "<SEP>" + songString
                    + "| duplicates a record already in the database\r\n";
            }
        }

        // If artist entry doesn't exist, create it. Otherwise use what's there
        if (artistIdx == -1) {
            artistNode = graph.getNumEntries();
            graph.setValue(artistNode, artistString);
            str += artistTable.insert(artistString, artistNode);
        }
        else {
            artistNode = artistTable.getTable()[artistIdx];
        }

        // If song entry dosn't exist, create it. Otherwise use what's there
        if (songIdx == -1) {
            songNode = graph.getNumEntries();
            graph.setValue(songNode, songString);
            str += songTable.insert(songString, songNode);
        }
        else {
            songNode = songTable.getTable()[songIdx];
        }
        // Artist, Song exists, and have an edge already
        graph.addEdge(artistNode, songNode, 1);
        graph.addEdge(songNode, artistNode, 1);

        return str;
    }


    // ----------------------------------------------------------
    /**
     * Remove from the hash table
     *
     * @param type
     *            The table to be removed from
     * @param nameString
     *            The string to be removed from the table
     * @return Status message as appropriate
     * @throws IOException
     */
    public String remove(String type, String nameString) throws IOException {
        if (graph == null) {
            return "Database not initialized";
        }
        if (type == null || nameString == null || type.equals("") || nameString
            .equals("")) {
            return "Input strings cannot be null or empty";
        }
        if (!type.equals("song") && !type.equals("artist")) {
            return "Bad type value |" + type + "| on remove";
        }

        return "Success";
    }


    // ----------------------------------------------------------
    /**
     * Print out the hash table contents
     *
     * @param type
     *            Controls what object is being printed
     * @return Status message as appropriate
     * @throws IOException
     */
    public String print(String type) throws IOException {
        if (graph == null) {
            return "Database not initialized";
        }
        if (type == null || type.equals("")) {
            return "Input strings cannot be null or empty";
        }

        if (!type.equals("song") && !type.equals("artist")) {
            return "Bad print parameter";
        }
        String str = "";
        
        Hash table = null;
        if (type.equals("artist")) {
            table = artistTable;
        }
        else if (type.equals("song")) {
            table = songTable;
        }

        for (int i = 0; i < table.getHashSize(); i++) {
            int nodeVal = table.getTable()[i];
            if(nodeVal != -1) {
            String val = (String)graph.getValue(nodeVal);
            str += i+": |"+val+"|\r\n";
            }
        }
        str += "total "+type+"s: "+table.getOccupiedRecords()+"\r\n";
        return str;
    }


    // ----------------------------------------------------------
    /**
     * Print out the graph information
     *
     * @return The string that was printed
     */
    public String printgraph() {
        return null;
    }
}
