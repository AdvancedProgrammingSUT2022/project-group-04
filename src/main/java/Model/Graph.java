package Model;

import Database.GameDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class Graph {
    public boolean areAdjacent(Tile tile1, Tile tile2) {
        if (tile1.getY() % 2 == 0) {
            if (tile1.getX() == tile2.getX()
                    && (tile1.getY() + 1 == tile2.getY()
                    || tile1.getY() - 1 == tile2.getY())) {
                return true;
            }
            if (tile1.getX() - 1 == tile2.getX() && tile1.getY() == tile2.getY()) {
                return true;
            }
            if (tile1.getX() + 1 == tile2.getX()
                    && (tile1.getY() + 1 == tile2.getY()
                    || tile1.getY() - 1 == tile2.getY()
                    || tile1.getY() == tile2.getY())) {
                return true;
            }
            return false;
        } else {
            if (tile1.getX() == tile2.getX()
                    && (tile1.getY() + 1 == tile2.getY()
                    || tile1.getY() - 1 == tile2.getY())) {
                return true;
            }
            if (tile1.getX() + 1 == tile2.getX()
                    && tile1.getY() == tile2.getY()) {
                return true;
            }
            if (tile1.getX() - 1 == tile2.getX()
                    && (tile1.getY() + 1 == tile2.getY()
                    || tile1.getY() - 1 == tile2.getY()
                    || tile1.getY() == tile2.getY())) {
                return true;
            }
            return false;
        }
    }

    public int commonEdgeNumber(Tile tile1, Tile tile2) {//WRT tile1
        if (tile1.getY() % 2 == 0) {
            if (tile1.getX() == tile2.getX()
                    && tile1.getY() + 1 == tile2.getY()) {
                return 1;
            }
            if (tile1.getX() == tile2.getX()
                    && tile1.getY() - 1 == tile2.getY()) {
                return 5;
            }
            if (tile1.getX() - 1 == tile2.getX()
                    && tile1.getY() == tile2.getY()) {
                return 0;
            }
            if (tile1.getX() + 1 == tile2.getX()
                    && tile1.getY() + 1 == tile2.getY()) {
                return 2;
            }
            if (tile1.getX() + 1 == tile2.getX()
                    && tile1.getY() - 1 == tile2.getY()) {
                return 4;
            }
            if (tile1.getX() + 1 == tile2.getX()
                    && tile1.getY() == tile2.getY()) {
                return 3;
            }
            return 6;
        } else {
            if (tile1.getX() == tile2.getX()
                    && tile1.getY() + 1 == tile2.getY()) {
                return 2;
            }
            if (tile1.getX() == tile2.getX()
                    && tile1.getY() - 1 == tile2.getY()) {
                return 4;
            }
            if (tile1.getX() - 1 == tile2.getX()
                    && tile1.getY() == tile2.getY()) {
                return 0;
            }
            if (tile1.getX() - 1 == tile2.getX()
                    && tile1.getY() + 1 == tile2.getY()) {
                return 1;
            }
            if (tile1.getX() - 1 == tile2.getX()
                    && tile1.getY() - 1 == tile2.getY()) {
                return 5;
            }
            if (tile1.getX() + 1 == tile2.getX()
                    && tile1.getY() == tile2.getY()) {
                return 3;
            }

            return 6;
        }
    }

    public void setEdges(ArrayList<Tile> copyOfMap) {
        for (int i = 0; i < copyOfMap.size(); i++) {
            for (int j = i + 1; j < copyOfMap.size(); j++) {
                if (areAdjacent(copyOfMap.get(i), copyOfMap.get(j))) {
                    if (copyOfMap.get(i).canBePassed()
                            && copyOfMap.get(j).canBePassed()) {
                        int numberOfRiverEdge = commonEdgeNumber(copyOfMap.get(i), copyOfMap.get(j));
                        if (!copyOfMap.get(i).isRiverByNumberOfEdge(numberOfRiverEdge)) {
                            add_neighbor(copyOfMap.get(i), copyOfMap.get(j));
                        }
                    }
                }
            }
        }
    }

    public void add_neighbor(Tile origin, Tile neighbourTile) {
        origin.getNeighbors().add(neighbourTile);
        neighbourTile.getNeighbors().add(origin);
    }

    public boolean bfs(Tile start, Tile end, ArrayList<Tile> copyOfMap) {
        System.out.println("kire babat");
        Queue<Tile> queue = new LinkedList<>();
        start.setVisited(true);
        queue.add(start);
        boolean exists = false;
        while (!queue.isEmpty()) {
            exists = false;
            Tile currentTile = queue.poll();
            System.out.println(currentTile.getX() + " " + currentTile.getY());
            /*for (Tile temp : copyOfMap){
                if (currentTile.getX() == temp.getX() && currentTile.getY() == temp.getY()){
                    currentTile = temp;
                }
            }
            System.out.println(currentTile.getX() +  " " + currentTile.getY() + " this is a test");
            for (Tile tile: currentTile.getAdjacentTiles()){
                add_neighbor(currentTile, tile);
                //System.out.println(currentTile.getNeighbors().size());
            }*/
            for (Tile tile : currentTile.neighbors) {
                System.out.println(currentTile.neighbors.size());
                System.out.println(tile.getX() + "- " + tile.getY());
                if (!tile.visited) {
                    tile.visited = true;
                    queue.add(tile);
                    tile.prev = currentTile;
                    if (tile.equals(end)) {
                        queue.clear();
                        exists = true;
                        break;
                    }
                }
            }
            if (exists)
                break;
        }
        System.out.println(exists);
        return exists;
    }

    public ArrayList<Tile> route(Tile start, Tile end, ArrayList<Tile> copyOfMap) {
        for (Tile tile : copyOfMap) {
            tile.neighbors.clear();
            tile.setVisited(false);
            tile.prev = null;
        }
        setEdges(copyOfMap);

        if (bfs(start, end, copyOfMap)) {//bfs path exists then return the route
            System.out.println("salam");
            Tile tile = end;
            ArrayList<Tile> route = new ArrayList<>();
            while (tile != null) {
                route.add(tile);
                tile = tile.getPrev();
            }
            Collections.reverse(route);
            return route;
        } else { // bfs bath doesn't exist to the point we want
            System.out.println("bfs path not found");
            ArrayList<Tile> route = new ArrayList<>();
            int minLength = Integer.MAX_VALUE;
            outer:
            for (int i = 1; i < 1000; i++) {
                boolean found = false;
                inner:
                for (Tile adj : end.getAdjacentTilesByLayer(i)) { // trying to find a tile that is reachable
                    if (bfs(start, adj, copyOfMap)) {
                        found = true;
                        Tile tile = end;
                        ArrayList<Tile> routeTemp = new ArrayList<>();
                        while (tile != null) {
                            routeTemp.add(tile);
                            tile = tile.getPrev();
                        }
                        if (routeTemp.size() < minLength) { // trying to find the one with minimum length
                            minLength = routeTemp.size();
                            route.addAll(routeTemp);
                        }
                    }
                }
                if (found) {
                    break;
                }
            }
            return route;
        }

    }

}
