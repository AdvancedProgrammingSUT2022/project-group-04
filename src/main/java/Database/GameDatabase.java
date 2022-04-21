package Database;

import Model.Civilization;
import Model.Tile;

import java.util.ArrayList;

public class GameDatabase {

    public static ArrayList<Civilization> players;
    public static ArrayList<Tile> map;

    public static void setPlayers(ArrayList<Civilization> players) {
        GameDatabase.players = players;
    }

    public static Civilization getCivilizationByName(String civilizationName) {
        for (int i = 0; i < GameDatabase.players.size(); i++) {
            if(GameDatabase.players.get(i).getUsername().equals(civilizationName)) {
                return GameDatabase.players.get(i);
            }
        }
        return null;
    }

    public static Tile getBlockByXandY(int x,int y){
        for (Tile tile : map) {
            if (tile.getX()==x && tile.getY()==y) return tile;
        }
        return null;

    }
    public static void readMapFromFile(){

    }

    public static void writeMapOnTheFile(){

    }

    public static void generateMap(int numberOfPlayers){

    }
}
