package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class Graph {
    public void add_neighbor(Tile origin ,Tile neighbourTile){
        origin.getNeighbors().add(neighbourTile);
        neighbourTile.getNeighbors().add(origin);
    }

    public void bfs(Tile start, Tile end){
        Queue<Tile> queue = new LinkedList<>();
        start.setVisited(true);
        queue.add(start);
        while(!queue.isEmpty()){
            Tile currentTile = queue.poll();
            for(Tile tile: currentTile.neighbors){
                if(!tile.visited){
                    tile.visited =true;
                    queue.add(tile);
                    tile.prev = currentTile;
                    if(tile.equals(end)){
                        queue.clear();
                        break;
                    }
                }
            }
        }
    }

    public ArrayList<Tile> route(Tile start, Tile end){
        Tile tile = end;
        ArrayList<Tile> route = new ArrayList<>();
        while (tile != null){
            route.add(tile);
            tile = tile.getPrev();
        }
        Collections.reverse(route);
        return route;
    }
}
