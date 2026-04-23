//package S26P4Graph;


import java.io.IOException;

public class GraphDBTest {
    
    
    public static void main(String[] args) throws IOException {
    GraphDB db = new GraphDB();
    
    String res = db.create(10);
    db.insert("Taylor Swift", "SongA");
    db.insert("Taylor Swift", "SongB");
    db.insert("Taylor Swift", "SongC");
    db.insert("Bish", "SongC");
    db.insert("Bish", "DongD");
    db.printgraph();
    db.remove("song", "SongC");
    }
}
