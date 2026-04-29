// package S26P4Graph;

import java.util.Arrays;
import student.TestCase;

/**
 * Graph project tests
 *
 * @author CS3114/5040 Staff
 * @version Spring 2026
 */
public class GraphProjTest extends TestCase {
    private GPInterface it;

    /**
     * Set up the tests that follow.
     */
    public void setUp() { // Nothing needed yet

    }


    // ----------------------------------------------------------
    /**
     * Test various bad inputs
     *
     * @throws Exception
     */
    public void testBadInput() throws Exception {
        it = new GraphDB();
        assertFalse(it.clear()); // Not been initialized yet
        assertFuzzyEquals("Initial hash table size must be positive", it.create(
            -1));

        assertFuzzyEquals("Database not initialized", it.insert("a", "b"));
        assertFuzzyEquals("Database not initialized", it.remove("song", "a"));
        assertFuzzyEquals("Database not initialized", it.print("artist"));

        assertNull(it.create(32));
        assertFuzzyEquals("Database already exists", it.create(32));
        assertFuzzyEquals("Bad print parameter", it.print("dum"));
        assertFuzzyEquals("Bad type value |Dum| on remove", it.remove("Dum",
            "Dum"));

        assertFuzzyEquals("Input strings cannot be null or empty", it.print(
            ""));
        assertFuzzyEquals("Input strings cannot be null or empty", it.print(
            null));

        assertFuzzyEquals("Input strings cannot be null or empty", it.insert("",
            "b"));
        assertFuzzyEquals("Input strings cannot be null or empty", it.insert(
            null, "b"));
        assertFuzzyEquals("Input strings cannot be null or empty", it.insert(
            "a", ""));
        assertFuzzyEquals("Input strings cannot be null or empty", it.insert(
            "a", null));

        assertFuzzyEquals("Input strings cannot be null or empty", it.remove(
            "song", ""));
        assertFuzzyEquals("Input strings cannot be null or empty", it.remove(
            "song", null));
        assertFuzzyEquals("Input strings cannot be null or empty", it.remove("",
            "a"));
        assertFuzzyEquals("Input strings cannot be null or empty", it.remove(
            null, "a"));
    }


    // ----------------------------------------------------------
    /**
     * Test various uses of empty or missing data
     *
     * @throws Exception
     */
    public void testEmpty() throws Exception {
        it = new GraphDB();
        it.create(10);

        assertFuzzyEquals("total artists: 0", it.print("artist"));
        assertFuzzyEquals("total songs: 0", it.print("song"));
        it.insert("Hello World", "Hello World2");
        assertFuzzyEquals("|Dum| does not exist in the Artist database", it
            .remove("artist", "Dum"));
        assertFuzzyEquals("|Dum| does not exist in the song database", it
            .remove("song", "Dum"));
    }


    // ----------------------------------------------------------
    /**
     * Define output formats
     *
     * @throws Exception
     */
    public void testSampleInput() throws Exception {
        it = new GraphDB();
        it.create(10);

        assertFuzzyEquals(
            "|When Summer's Through| does not exist in the Song database", it
                .remove("song", "When Summer's Through"));
        assertFuzzyEquals("total songs: 0\r\n", it.print("song"));
        assertFuzzyEquals("total artists: 0\r\n", it.print("artist"));
        assertFuzzyEquals("There are 0 connected components\r\n"
            + "The largest connected component has 0 elements\r\n"
            + "The diameter of the largest component is 0\r\n", it
                .printgraph());
        assertFuzzyEquals(
            "|Blind Lemon Jefferson| is added to the Artist database\r\n"
                + "|Long Lonesome Blues| is added to the Song database\r\n", it
                    .insert("Blind Lemon Jefferson", "Long Lonesome Blues"));
        assertFuzzyEquals(
            "|Blind Lemon Jefferson<SEP>Long Lonesome Blues| duplicates a record already in the database\r\n",
            it.insert("Blind Lemon Jefferson", "Long Lonesome Blues"));
        assertFuzzyEquals(
            "|Long   Lonesome Blues| is added to the Song database\r\n", it
                .insert("Blind Lemon Jefferson", "Long   Lonesome Blues"));
        assertFuzzyEquals(
            "|long Lonesome Blues| is added to the Song database\r\n", it
                .insert("Blind Lemon Jefferson", "long Lonesome Blues"));
        assertFuzzyEquals("|Ma Rainey| is added to the Artist database\r\n"
            + "|Ma Rainey's Black Bottom| is added to the Song database\r\n", it
                .insert("Ma Rainey", "Ma Rainey's Black Bottom"));
        assertFuzzyEquals("", it.insert("Ma Rainey", "Long Lonesome Blues"));
        assertFuzzyEquals(
            "|Mississippi Boweavil Blues| is added to the Song database\r\n", it
                .insert("Ma Rainey", "Mississippi Boweavil Blues"));
        assertFuzzyEquals("song hash table size doubled\r\n"
            + "|Fixin' To Die Blues| is added to the Song database\r\n", it
                .insert("Ma Rainey", "Fixin' To Die Blues"));
        assertFuzzyEquals("0: |Blind Lemon Jefferson|\r\n"
            + "7: |Ma Rainey|\r\n" + "total artists: 2\r\n", it.print(
                "artist"));
        assertFuzzyEquals("1: |Fixin' To Die Blues|\r\n"
            + "2: |Mississippi Boweavil Blues|\r\n"
            + "7: |long Lonesome Blues|\r\n" + "15: |Long Lonesome Blues|\r\n"
            + "16: |Ma Rainey's Black Bottom|\r\n"
            + "19: |Long   Lonesome Blues|\r\n" + "total songs: 6\r\n", it
                .print("song"));
        assertFuzzyEquals("There are 1 connected components\r\n"
            + "The largest connected component has 8 elements\r\n"
            + "The diameter of the largest component is 4\r\n", it
                .printgraph());
        assertFuzzyEquals("|Sleepy| does not exist in the Song database\r\n", it
            .remove("song", "Sleepy"));
        assertFuzzyEquals(
            "|ma rainey| does not exist in the Artist database\r\n", it.remove(
                "artist", "ma rainey"));
        assertFuzzyEquals("|Ma Rainey| is removed from the Artist database\r\n",
            it.remove("artist", "Ma Rainey"));
        assertFuzzyEquals("0: |Blind Lemon Jefferson|\r\n" + "7: TOMBSTONE\r\n"
            + "total artists: 1\r\n", it.print("artist"));
        assertFuzzyEquals(
            "|Barenaked Ladies| is added to the Artist database\r\n"
                + "|Sarah Yellin| is added to the Song database\r\n", it.insert(
                    "Barenaked Ladies", "Sarah Yellin"));
        assertFuzzyEquals(
            "|Gerard Lenorman| is added to the Artist database\r\n"
                + "Graph size doubled to 20\r\n"
                + "|Oversleeping| is added to the Song database", it.insert(
                    "Gerard Lenorman", "Oversleeping"));
        assertTrue(it.clear());
    }


    /**
     * Tests the ParPtrTree class
     * 
     * @throws Exception
     */
    public void testParPtrTree() throws Exception {
        ParPtrTree tree = new ParPtrTree(10);

        assertFuzzyEquals("1", Integer.toString(tree.FIND(1)));
        tree.UNION(0, 1);
    }


    public void testHash() throws Exception {
        Graph graph = new Graph(10);
        Hash hash = new Hash("song", 4, graph);
        int[] arr = { -1, -1, -1, -1 };

        assertFuzzyEquals(Arrays.toString(arr), Arrays.toString(hash
            .getTable()));
        assertFuzzyEquals("a is added to the song database", hash.insert("a",
            0));
        assertFuzzyEquals("b is added to the song database", hash.insert("b",
            1));

        // testing tombstones
        it = new GraphDB();
        it.create(2);
        assertFuzzyEquals("a is added to the artist database\r\n"
            + "a is added to the song database", it.insert("A", "A"));
        assertFuzzyEquals("a is removed from the song database", it.remove(
            "song", "A"));
        assertFuzzyEquals("a is removed from the artist database", it.remove(
            "artist", "A"));
        assertFuzzyEquals("|a| does not exist in the song database", it.remove(
            "song", "A"));
        assertFuzzyEquals("|a| does not exist in the artist database", it
            .remove("artist", "A"));
        assertFuzzyEquals("a is added to the artist database\r\n"
            + "a is added to the song database", it.insert("A", "A"));

    }


    public void testGraphDB() throws Exception {
        it = new GraphDB();
        it.create(2);

        assertFuzzyEquals("a is added to the artist database\r\n"
            + "a is added to the song database", it.insert("A", "A"));
        assertFuzzyEquals("true", Boolean.toString(it.clear()));

        assertFuzzyEquals("a is added to the artist database\r\n"
            + "a is added to the song database", it.insert("A", "A"));
        assertFuzzyEquals("a is removed from the song database", it.remove(
            "song", "A"));

        assertFuzzyEquals("there are 1 connected components\r\n"
            + "the largest connected component has 1 elements\r\n"
            + "the diameter of the largest component is 0", it.printgraph());
    }


    public void testGraph() throws Exception {
        Graph graph = new Graph(3);

        assertFuzzyEquals("3", Integer.toString(graph.nodeCount()));
        assertFuzzyEquals("0", Integer.toString(graph.edgeCount()));

        graph.addEdge(1, 1, 0);
        graph.addEdge(0, 1, 1);
        graph.addEdge(0, 1, 2);
        graph.removeEdge(1, 1);
        graph.removeEdge(0, 1);

        int[] arr = { 1 };

        assertFuzzyEquals("", Arrays.toString(graph.neighbors(1)));

        graph.setValue(0, "yeah");
        graph.setValue(1, "nah");
        graph.addEdge(0, 1, 1);

        graph.addEdge(1, 0, 1);
        graph.addEdge(1, 0, 0);
        graph.addEdge(1, 2, 1);
        graph.addEdge(0, 2, 10);
        graph = new Graph(3);
        graph.addEdge(0, 2, 1);
        graph.addEdge(0, 1, 1);
        graph = new Graph(3);
        graph.addEdge(0, 1, 1);
        graph.addEdge(0, 3, 1);
        graph.addEdge(0, 2, 1);
        graph = new Graph(3);
        graph.addEdge(0, 1, 1);
        graph.addEdge(0, 2, 1);
        graph.addEdge(0, 3, 1);
        graph = new Graph(3);
        graph.addEdge(0, 3, 1);
        graph.addEdge(0, 0, 1);

        graph = new Graph(5);
        assertFuzzyEquals("", Arrays.toString(graph.neighbors(0)));
        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 1);
        graph.removeEdge(0, 0);
        graph.addEdge(2, 3, 1);
        graph.removeEdge(2, 3);

        assertFuzzyEquals(Integer.toString(2), Integer.toString(graph
            .edgeCount()));
        assertFuzzyEquals(Integer.toString(1), Integer.toString(graph.weight(0,
            1)));
        assertFuzzyEquals(Integer.toString(0), Integer.toString(graph.weight(0,
            0)));
        assertFuzzyEquals(Integer.toString(0), Integer.toString(graph.weight(0,
            10)));
        assertFuzzyEquals("graph size doubled to 10", graph.expandGraph());
        assertFuzzyEquals("", graph.getMessage());
        assertFuzzyEquals(Integer.toString(2), Integer.toString(graph
            .edgeCount()));

        graph = new Graph(2);
        assertFuzzyEquals(Integer.toString(0), Integer.toString(graph.addNode(
            "a")));
        assertFuzzyEquals(Integer.toString(1), Integer.toString(graph.addNode(
            "a")));
        assertFuzzyEquals(Integer.toString(2), Integer.toString(graph.addNode(
            "a")));
        graph.removeNode(0);
        assertFuzzyEquals(Integer.toString(0), Integer.toString(graph.addNode(
            "a")));
        assertFuzzyEquals(Boolean.toString(false), Boolean.toString(graph
            .hasEdge(0, 1)));
        graph.addEdge(0, 1, 1);
        assertFuzzyEquals(Boolean.toString(true), Boolean.toString(graph
            .hasEdge(0, 1)));
        assertFuzzyEquals("there are 2 connected components\r\n"
            + "the largest connected component has 2 elements\r\n"
            + "the diameter of the largest component is 1", graph.getGraphInfo(
                new ParPtrTree(10)));
        assertFuzzyEquals(Integer.toString(3), Integer.toString(graph
            .getNumEntries()));
        assertFuzzyEquals(Integer.toString(1), Integer.toString(graph
            .edgeCount()));
        assertFuzzyEquals(Integer.toString(0), Integer.toString(graph
            .freeCount()));

    }


    public void testGraph2() throws Exception {
        Graph g = new Graph(3);
        // Test null edge
        g.addEdge(1, 2, 0);
        assertEquals(0, g.edgeCount());

        // Always updates weight or inserts
        g.addEdge(1, 2, 5);
        g.addEdge(1, 2, 99);
        assertEquals(1, g.edgeCount());
        assertEquals(99, g.weight(1, 2));
        // always updates

        // always inserts,

    }


    public void testRemoveNode() throws Exception {
        Graph g = new Graph(3);

        // Edges going out from v (more than 1)
        g.addEdge(1, 2, 5);
        g.addEdge(1, 0, 3);
        assertEquals(2, g.edgeCount());
        g.removeNode(1); // infinite loop
        assertEquals(0, g.edgeCount());
        assertEquals(1, g.freeCount());

        // Edges going into v
        g = new Graph(3);
        assertEquals(0, g.freeCount());

        g.addEdge(2, 1, 5);
        g.addEdge(0, 1, 3);
        assertEquals(2, g.edgeCount());
        g.removeNode(1);
        assertEquals(0, g.edgeCount());
        assertEquals(0, g.weight(0, 1));
        assertEquals(0, g.weight(1, 2));
        assertEquals(1, g.freeCount());

        // Kills line 227 mutation (freeCount++ → freeCount = 1):
        // Two removals on the same graph: freeCount must reach 2
        g = new Graph(3);
        g.addEdge(2, 1, 5);
        g.addEdge(0, 1, 3);
        g.removeNode(1);
        assertEquals(1, g.freeCount());
        g.removeNode(0);
        assertEquals(2, g.freeCount());
    }


    public void testNeighbors() {
        Graph g = new Graph(4);
        g.addEdge(1, 0, 5);
        g.addEdge(1, 2, 3);
        g.addEdge(1, 3, 5);
        int[] arr = { 0, 2, 3 };
        for (int i = 0; i < 3; i++) {
            assertEquals(arr[i], g.neighbors(1)[i]);
        }

    }


    public void testPrevPointers() throws Exception {
        // Kills line 293 mutation (if curr.next != null → if false in
        // removeEdge):
        Graph g = new Graph(5);
        g.addEdge(0, 1, 1);
        g.addEdge(0, 3, 1);
        g.removeEdge(0, 1);
        Graph.Edge sentinel = g.find(0, 3); // returns sentinel (predecessor of
                                            // 3)
        assertSame(sentinel, sentinel.next.prev);

        // Kills line 154 mutation (if curr.next != null → if false in addEdge):
        g = new Graph(5);
        g.addEdge(0, 1, 1);
        g.addEdge(0, 3, 1);
        g.addEdge(0, 2, 1); // inserts between 1 and 3
        Graph.Edge edge2 = g.find(0, 3); // returns Edge(2) (predecessor of 3)
        assertSame(edge2, edge2.next.prev);
    }

}
