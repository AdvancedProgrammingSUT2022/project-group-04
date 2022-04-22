package Database;

import Model.Civilization;
import Model.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GameDatabase {

    public static ArrayList<Civilization> players;
    public static ArrayList<Tile> map;
    private static final int length = 50;
    private static final int width = 50;

    public static void setPlayers(ArrayList<Civilization> players) {
        GameDatabase.players = players;
    }

    /**
     * @param civilizationName
     * @return selected civilization
     */
    public static Civilization getCivilizationByName(String civilizationName) {
        for (int i = 0; i < GameDatabase.players.size(); i++) {
            if (GameDatabase.players.get(i).getUsername().equals(civilizationName)) {
                return GameDatabase.players.get(i);
            }
        }
        return null;
    }

    public static Tile getBlockByXandY(int x, int y) {
        for (Tile tile : map) {
            if (tile.getX() == x && tile.getY() == y) return tile;
        }
        return null;

    }

    public static void readMapFromFile() {

    }

    public static void writeMapOnTheFile() {

    }

    public static void generateMap(int numberOfPlayers) {
        Random random = new Random();
        int[] possibilities = {10, 10, 10, 10, 10, 10, 10, 10};
        int sumOfPossibilities = 0;
        int possibilityOfEdgeBeingRiver = 10;// from 1000
        //initialize and set hashmap
        HashMap<Integer, String> baseTerrains = new HashMap<>();
        baseTerrains.put(0, "Desert");
        baseTerrains.put(1, "Meadow");
        baseTerrains.put(2, "Hill");
        baseTerrains.put(3, "Mountain");
        baseTerrains.put(4, "Ocean");
        baseTerrains.put(5, "Plain");
        baseTerrains.put(6, "Snow");
        baseTerrains.put(7, "Tundra");
        //
        for (int i = 0; i < possibilities.length; i++) {
            sumOfPossibilities += possibilities[i];
        }
        //random initialize tiles of map
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                int randomGenerate = random.nextInt(sumOfPossibilities);
                int counter = 0;
                int flag = -1;
                for (int k = 0; k < possibilities.length; k++) {
                    counter += possibilities[k];
                    if (randomGenerate < counter) {
                        flag = k;
                        break;
                    }
                }
                Tile tile = new Tile(baseTerrains.get(flag), i, j);
                map.add(tile);
            }
        }
        //random initialize river
        int[] deltaX = {-1, -1, 0, 1, 0, -1};
        int[] deltaY = {0, 1, 1, 0, -1, -1};
        int[] numbOfEdge = {3, 4, 5, 0, 1, 2};
        for (int i = 0; i < map.size(); i++) {
            Tile tile = map.get(i);
            for (int j = 0; j < 6; j++) {
                int xOfTile = tile.getX() + deltaX[j];
                int yOfTile = tile.getY() + deltaY[j];
                if (xOfTile > length || xOfTile < 0
                        || yOfTile > width || yOfTile < 0) continue;
                int randomGenerate = random.nextInt(1000);
                if (randomGenerate < possibilityOfEdgeBeingRiver) {
                    Tile tile1 = getBlockByXandY(xOfTile, yOfTile);
                    tile.setRiverByEdgeIndex(j);
                    tile1.setRiverByEdgeIndex(numbOfEdge[j]);
                }
            }
        }
        //

    }
}
