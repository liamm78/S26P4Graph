
// package S26P4Graph;

import java.util.Arrays;

import student.TestCase;

/**
 * Graph project tests
 *
 * @author CS3114/5040 Staff
 * @version Spring 2026
 */
public class HashTest extends TestCase {
    private Hash it;

    /**
     * Set up the tests that follow.
     */
    public void setUp() { // Nothing needed yet

    }


    /**
     * Tests expanding the hash table
     * 
     * @throws Exception
     *             e
     */
    public void testInsert() throws Exception {
        Graph g = new Graph(5);
        it = new Hash("artist", 5, g);
        g.setValue(0, "A");
        g.setValue(1, "K");
        assertEquals("|A| is added to the Artist database\r\n" + "", it.insert(
            "A", 0));
        assertEquals("|K| is added to the Artist database\r\n" + "", it.insert(
            "K", 1));
        assertEquals(2, it.getOccupiedRecords());
        assertTrue(it.get("K") >= 0); // K must be findable
        assertTrue(it.get("A") >= 0); // A must be findabl

        System.out.println(it.h("A", 5));
        System.out.println(it.h("K", 5));

    }


    public void testExpandHash() throws Exception {
        // h("A",4)=1, h("I",4)=1 → collide in size-4
        // h("A",8)=1, h("I",8)=1 → still collide in size-8
        // Forces expandHash to probe for "I", killing lines 64, 65, 67
        Graph g = new Graph(10);
        g.setValue(0, "A");
        g.setValue(1, "I");
        g.setValue(2, "X"); // h("X",4)=0, no collision, triggers expansion
        Hash h = new Hash("artist", 4, g);
        h.insert("A", 0); // slot 1, occupiedRecords=1
        h.insert("I", 1); // probes to slot 2, occupiedRecords=2
        h.insert("X", 2); // 2+1>2 → expand to 8, then insert X
        assertEquals(8, h.getHashSize());
        assertTrue(h.get("A") != -1);
        assertTrue(h.get("I") != -1); // needs correct probing inside expandHash
        assertTrue(h.get("X") != -1);
    }


    /**
     * Tests entires that collide
     * 
     * @throws Exception
     *             e
     */
    public void testCollisionProbing() throws Exception {
        Graph g = new Graph(10);
        g.setValue(0, "A");
        g.setValue(1, "K");
        g.setValue(2, "U");
        Hash h = new Hash("artist", 10, g);
        h.insert("A", 0);
        h.insert("K", 1);
        h.insert("U", 2);
        assertEquals(5, h.get("A"));
        assertEquals(6, h.get("K"));
        assertEquals(9, h.get("U"));
    }


    /**
     * Test removing and inserting a new entry in that tombstone
     * 
     * @throws Exception
     *             e
     */
    public void testTombstoneReuse() throws Exception {
        Graph g = new Graph(10);
        g.setValue(0, "A");
        Hash h = new Hash("artist", 10, g);
        h.insert("A", 0);
        h.remove(5, "A");
        g.setValue(1, "K");
        h.insert("K", 1);
        assertEquals(5, h.get("K"));
    }


    /**
     * Tests reusing a tombstone
     * 
     * @throws Exception
     */
    public void testFirstTombstoneUsed() throws Exception {
        Graph g = new Graph(10);
        g.setValue(0, "A");
        g.setValue(1, "K");
        Hash h = new Hash("artist", 10, g);
        h.insert("A", 0); // slot 5
        h.insert("K", 1); // slot 6 (collision, probes to 6)
        h.remove(5, "A"); // slot 5 → TOMBSTONE
        h.remove(6, "K"); // slot 6 → TOMBSTONE
        g.setValue(2, "U");
        h.insert("U", 2);
        assertEquals(5, h.get("U"));
    }


    /**
     * Test inserting a duplicate
     * 
     * @throws Exception
     *             e
     */
    public void testDuplicateDetection() throws Exception {
        Graph g = new Graph(10);
        g.setValue(0, "A");
        Hash h = new Hash("artist", 10, g);
        h.insert("A", 0);
        assertEquals("Duplicate", h.insert("A", 0));
    }


    /**
     * Test retrieving the song message
     * 
     * 
     * @throws Exception
     *             e
     */
    public void testSongMessage() throws Exception {
        Graph g = new Graph(10);
        g.setValue(0, "B");
        Hash h = new Hash("song", 10, g);
        assertEquals("|B| is added to the Song database\r\n", h.insert("B", 0));
    }

}
