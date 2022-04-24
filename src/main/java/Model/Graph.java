package Model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Graph {
    ArrayList<Tile> allTiles = new ArrayList<Tile>();
    public Graph(ArrayList<Tile> copyOfMap){
        this.allTiles = copyOfMap;
    }

    void add_neighbor(Tile origin ,Tile neighbourTile){
        origin.getNeighbors().add(neighbourTile);
        neighbourTile.getNeighbors().add(origin);
    }

    void bfs(Tile start, Tile end){

    }
}
