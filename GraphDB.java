package S26P4Graph;

import java.io.IOException;

/**
 * The database implementation for this project.
 * We have two hash tables and graph.
 *
 * @author CS3114/5040 Staff
 * @version Spring 2026
 */
public class GraphDB implements GPInterface
{
    Graph graph;
    Hash artistTable;
    Hash songTable;


    // ----------------------------------------------------------
    /**
     * Create a new GraphDB object.
     * But don't set anything -- that gets done by "create"
     */
    public GraphDB()
    {
    }


    /**
     * Create a brave new World.
     * GraphDB has two closed hash tables and the Graph itself
     *
     * @param inHash
     *            Initial size for hash tables
     * @return Error messages if appropriate
     */
    public String create(int inHash)
    {
        graph = new Graph(inHash);
        artistTable = new Hash("artist",6, graph);
        songTable = new Hash("song",6, graph);    
        return null;
    }


    /**
     * Re-initialize the database
     * @return true on successful clear of database
     */
    public boolean clear() {
        return true;
    }


    // ----------------------------------------------------------
    /**
     * Insert to the hash table
     * 
     * First, check if there is space in the hash table, then insert nodes to Graph
     *
     * @param artistString
     *            Artist string to insert
     * @param songString
     *            Song string to insert
     * @return Status message as appropriate
     * @throws IOException
     */
    public String insert(String artistString, String songString)
        throws IOException
    {
        String res = artistTable.insert(artistString);
        
        return null;
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
        return null;
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
    return null;
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
