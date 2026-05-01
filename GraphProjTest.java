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

        assertFuzzyEquals("1", Integer.toString(tree.find(1)));
        tree.union(0, 1);
        assertFuzzyEquals("1", Integer.toString(tree.find(0)));
        assertFuzzyEquals("1", Integer.toString(tree.find(1)));
        tree.union(0, 0);
        assertFuzzyEquals("1", Integer.toString(tree.find(0)));
        assertFuzzyEquals("1", Integer.toString(tree.find(1)));
    }


    /**
     * Tests general hash table inserts, removals, and some edge cases.
     * More defined in HashTest
     * 
     * @throws Exception
     */
    public void testHash() throws Exception {
        Graph graph = new Graph(10);
        Hash hash = new Hash("song", 4, graph);
        int[] arr = { -1, -1, -1, -1 };

        assertFuzzyEquals(Arrays.toString(arr), Arrays.toString(hash
            .getTable()));
        assertFuzzyEquals("a is added to the song database", hash.insert("a",
            graph.addNode("a")));
        assertFuzzyEquals("b is added to the song database", hash.insert("b",
            graph.addNode("b")));

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

        assertFuzzyEquals("graph size doubled to 4\r\n"
            + "artist hash table size doubled\r\n"
            + "song is added to the artist database\r\n"
            + "song hash table size doubled\r\n"
            + "aa is added to the song database", it.insert("song", "Aa"));
        assertFuzzyEquals("graph size doubled to 8\r\n"
            + "song hash table size doubled\r\n"
            + "bb is added to the song database", it.insert("song", "BB"));
        assertFuzzyEquals("aaaa is added to the song database", it.insert(
            "song", "aaaa"));

        Graph graph2 = new Graph(20);
        Hash hash2 = new Hash("song", 5, graph2);

        int id1 = graph2.addNode("Aa");
        int id2 = graph2.addNode("BB");
        int id3 = graph2.addNode("AaAa");
        int id4 = graph2.addNode("BbBb");
        hash2.insert("Aa", id1);
        hash2.insert("BB", id2);
        hash2.insert("AaAa", id3);
        hash2.insert("BbBb", id4);
        int pos1 = hash2.get("Aa");
        int pos2 = hash2.get("BB");
        int pos3 = hash2.get("AaAa");
        int pos4 = hash2.get("BbBb");
        assertTrue(pos1 != pos2);
        assertTrue(pos2 != pos3);
        assertTrue(pos1 != pos3);
        assertTrue(pos1 != pos4);
        assertTrue(pos1 != -1);
        assertTrue(pos2 != -1);
        assertTrue(pos3 != -1);
        assertTrue(pos4 != -1);

        graph2 = new Graph(20);
        hash2 = new Hash("song", 4, graph2);
        String[] vals = { "Aa", "BB", "AaAa", "BBBB", "AaBB" };
        for (String s : vals) {
            int id = graph2.addNode(s);
            hash2.insert(s, id);
        }
        assertTrue(hash2.getHashSize() > 4);
        for (String s : vals) {
            assertTrue(hash2.get(s) != -1);
        }

        graph2 = new Graph(50);
        hash2 = new Hash("song", 3, graph2);
        hash2.insert("1", graph2.addNode("1"));
        hash2.insert("2", graph2.addNode("2"));
        hash2.insert("3", graph2.addNode("3"));
        hash2.insert("4", graph2.addNode("4"));
        assertTrue(hash2.get("4") != -1);

        graph2 = new Graph(50);
        hash2 = new Hash("song", 3, graph2);
        id1 = graph2.addNode("A");
        id2 = graph2.addNode("B");
        hash2.insert("A", id1);
        pos1 = hash2.get("A");
        hash2.remove(pos1, "A");
        hash2.insert("B", id2);
        pos2 = hash2.get("B");
        assertTrue(pos2 != -1);

        graph2 = new Graph(10);
        hash2 = new Hash("song", 5, graph2);
        id1 = graph2.addNode("Aa");
        id2 = graph2.addNode("BB");
        hash2.insert("Aa", id1);
        hash2.insert("BB", id2);
        assertFuzzyEquals("Duplicate", hash2.insert("Aa", id1));

        graph2 = new Graph(10);
        hash2 = new Hash("song", 5, graph2);
        String[] vals2 = { "Aa", "BB", "AaAa", "BBBB" };
        for (String s : vals2) {
            hash2.insert(s, graph2.addNode(s));
        }
        for (String s : vals2) {
            assertTrue(hash2.get(s) != -1);
        }

        Graph g = new Graph(10);
        Hash h = new Hash("song", 7, g);
        id1 = g.addNode("Aa");
        id2 = g.addNode("BB");
        h.insert("Aa", id1);
        h.insert("BB", id2);
        pos1 = h.get("Aa");
        pos2 = h.get("BB");
        assertTrue(pos1 != pos2);
        assertTrue(Math.abs(pos1 - pos2) != 1);

        assertEquals(7, h.getHashSize());

        g = new Graph(10);
        h = new Hash("song", 5, g);
        id1 = g.addNode("Aa");
        id2 = g.addNode("BB");
        id3 = g.addNode("AaAa");
        h.insert("Aa", id1);
        h.insert("BB", id2);
        pos1 = h.get("Aa");
        h.remove(pos1, "Aa");
        h.insert("AaAa", id3);
        pos3 = h.get("AaAa");
        assertTrue(pos3 > -1);
        assertTrue(h.get("BB") != -1);

        assertEquals(5, h.getHashSize());

        g = new Graph(10);
        h = new Hash("song", 3, g);
        String[] vals3 = { "Aa", "BB", "AaAa", "BBBB", "AaBB" };
        for (String s : vals3) {
            h.insert(s, g.addNode(s));
        }
        for (String s : vals3) {
            assertTrue("Missing: " + s, h.get(s) != -1);
        }

        assertEquals(12, h.getHashSize());

        h = new Hash("song", 100, new Graph(10));
        int h1 = h.h("abcd", 100);
        int h2 = h.h("abce", 100);
        int h3 = h.h("abcde", 100);
        assertTrue(h1 != h2);
        assertTrue(h2 != h3);

        g = new Graph(10);
        Hash artistHash = new Hash("artist", 5, g);
        String res = artistHash.insert("Drake", g.addNode("Drake"));
        assertTrue(res.contains("Artist"));

        assertEquals(100, h.getHashSize());
    }


    /**
     * Tests general GraphDB functions
     * 
     * @throws Exception
     */
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

        it = new GraphDB();
        it.create(0);
        assertFalse(it.clear());

        it = new GraphDB();
        it.create(10);
        it.insert("john music", "john song");
        assertFuzzyEquals("not john music is added to the artist database", it
            .insert("not john music", "john song"));

    }


    /**
     * Tests graph specific functions with more detail
     * 
     * @throws Exception
     */
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

        graph = new Graph(3);
        int node = graph.addNode("0");
        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 0, 1);
        graph.addEdge(1, 2, 1);
        graph.addEdge(2, 1, 1);
        graph.removeNode(node);
        assertEquals(3, graph.nodeCount());

        graph = new Graph(3);
        graph.addEdge(1, 1, 5);
        assertEquals(1, graph.edgeCount());
        graph.removeNode(1);
        assertEquals(0, graph.edgeCount());

        graph = new Graph(6);
        graph.setValue(0, "A");
        graph.setValue(1, "B");
        graph.setValue(2, "C");
        graph.setValue(3, "D");
        graph.addEdge(0, 1, 1);
        graph.addEdge(2, 3, 1);
        String res = graph.getGraphInfo(new ParPtrTree(10));
        assertTrue(res.contains("There are 2 connected components"));
        assertTrue(res.contains("has 2 elements"));

        graph = new Graph(6);
        for (int i = 0; i < 6; i++) {
            graph.setValue(i, "" + i);
        }
        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 1);
        graph.addEdge(3, 4, 1);
        graph.addEdge(4, 5, 1);
        graph.addEdge(5, 3, 1);
        res = graph.getGraphInfo(new ParPtrTree(10));
        assertTrue(res.contains("diameter"));
    }


    /**
     * Tests addEdge mutations
     * 
     * @throws Exception
     */
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


    /**
     * Tests removeNode mutations
     * 
     * @throws Exception
     */
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


    /**
     * Tests neighbors mutations
     * 
     */
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


    /**
     * Tests addEdge and removeEdge mutations
     * 
     * @throws Exception
     */
    public void testPrevPointers() throws Exception {
        Graph g = new Graph(5);
        g.addEdge(0, 1, 1);
        g.addEdge(0, 3, 1);
        g.removeEdge(0, 1);
        Graph.Edge sentinel = g.find(0, 3); // returns sentinel (predecessor of
                                            // 3)
        assertSame(sentinel, sentinel.next.prev);

        g = new Graph(5);
        g.addEdge(0, 1, 1);
        g.addEdge(0, 3, 1);
        g.addEdge(0, 2, 1); // inserts between 1 and 3 in nodeArray (middle)
        Graph.Edge edge2 = g.find(0, 3); // returns Edge(2) (predecessor of 3)
        assertSame(edge2, edge2.next.prev);
    }

}
