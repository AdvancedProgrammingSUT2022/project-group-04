package Database;

import Model.City;
import Model.Civilization;
import Model.Tile;

import java.util.ArrayList;
import java.util.Random;

public class GameDatabase {

    public static ArrayList<Civilization> players = new ArrayList<Civilization>();
    public static ArrayList<Tile> map = new ArrayList<Tile>();
    private static final int length = 50;
    private static final int width  = 50;

    public static void setPlayers(ArrayList<Civilization> players) {
        GameDatabase.players = players;
    }

    /**
     *
     * @param civilizationName
     * @return selected civilization
     */
    public static Civilization getCivilizationByName(String civilizationName) {
        for (int i = 0; i < GameDatabase.players.size(); i++) {
            if(GameDatabase.players.get(i).getUsername().equals(civilizationName)) {
                return GameDatabase.players.get(i);
            }
        }
        return null;
    }

    public static City getCityByName(String cityName) {
        for (int i = 0; i < GameDatabase.players.size(); i++) {
            for (int j = 0; j < GameDatabase.players.get(i).getCities().size(); j++) {
                if(GameDatabase.players.get(i).getCities().get(j).getName().equals(cityName)) {
                    return GameDatabase.players.get(i).getCities().get(j);
                }
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

    public static Civilization getCivilizationByTile(Tile tile) {
        for (int i = 0; i < GameDatabase.players.size(); i++) {
            for (int j = 0; j < GameDatabase.players.get(i).getTiles().size(); j++) {
                if(GameDatabase.players.get(i).getTiles().get(j).equals(tile)) {
                    return GameDatabase.players.get(i);
                }
            }
        }
        return null;
    }

    public static void readMapFromFile(){

    }

    public static void writeMapOnTheFile(){

    }

    public static void generateMap(int numberOfPlayers){
        Random random = new Random();
        int[] possibilities = {10,10,10,10,10,10,10,10};
        for (int i=0;i<length;i++){
            for (int j=0;j<width;j++){

            }
        }
    }
}
