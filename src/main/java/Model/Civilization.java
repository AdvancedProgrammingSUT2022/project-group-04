package Model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Civilization {
    private String username;
    private String nickname;
    private int score;
    private int turn;
    private ArrayList<Tile> tiles;
    private ArrayList<City> cities;
    private int gold;
    private ArrayList<Technology> technologies;


    public Civilization(String username, String nickname) {
        this.username = username;
        this.nickname = nickname;
        this.score = 0;
        this.turn = 0;
        this.tiles = new ArrayList<Tile>();
        this.cities = new ArrayList<City>();
        this.gold = 0;
        this.technologies = new ArrayList<Technology>();
    }

    public ArrayList<Technology> getTechnologies() {
        return technologies;
    }

    public void assignTiles() {

    }

    public void addScore(int amount) {
        this.score += amount;
    }

    public void addGold(int amount) {
        this.gold += amount;
    }

    public void addTechnology(Technology newTechnology) {
        this.technologies.add(newTechnology);
    }

    public void addTile(Tile newTile) {
        this.tiles.add(newTile);
    }

    public void addCity(City newCity) {
        this.cities.add(newCity);
    }

    public String getUsername() {
        return this.username;
    }

    public String getNickname() {
        return this.nickname;
    }

    public int getScore() {
        return this.score;
    }

    public int getTurn() {
        return this.turn;
    }

    public ArrayList<Tile> getTiles() {
        return this.tiles;
    }

    public ArrayList<City> getCities() {
        return this.cities;
    }

    public int getGold() {
        return this.gold;
    }

    public void removeTile(Tile conqueredTile) {
        for (int i = 0; i < this.tiles.size(); i++) {
            if (this.tiles.get(i).equals(conqueredTile)) {
                this.tiles.remove(i);
                return;
            }
        }
    }

    public void removeCity(City conqueredCity) {
        for (int i = 0; i < this.cities.size(); i++) {
            if (this.cities.get(i).equals(conqueredCity)) {
                this.cities.remove(i);
                return;
            }
        }
    }


    public boolean isTileInCivilization(int x, int y) {
        for (Tile tile : tiles) {
            if (tile.getX() == x && tile.getY() == y)
                return true;
        }
        return false;
    }

    public boolean isTechnologyForThisCivilization(Technology technology) {
        for (int i = 0; i < this.technologies.size(); i++) {
            if (this.technologies.get(i).equals(technology)) {
                return true;
            }
        }
        return false;

    }

    public ArrayList<Tile> tilesOnBorder(){

        ArrayList<Tile> tilesOnBorder = new ArrayList<>();
        for (Tile tile:tiles){
            boolean isBorderTile = false;
            for (Tile adjacent:tile.getAdjacentTiles()){
                if (!tiles.contains(adjacent)){
                    isBorderTile = true;
                }
            }
            if (isBorderTile){
                tilesOnBorder.add(tile);
            }
        }
        return tilesOnBorder;

    }

    public ArrayList<Tile> firstClassAdjacentTiles(){

        ArrayList<Tile> firstClassAdjacentTiles = new ArrayList<>();
        for (Tile tile : tilesOnBorder()){
            if (!tile.getUnits().isEmpty() || !tile.getBuildings().isEmpty()) {
                for (Tile adjacent : tile.getAdjacentTiles()) {
                    if (!firstClassAdjacentTiles.contains(adjacent)
                            && !tiles.contains(adjacent)) {
                        firstClassAdjacentTiles.add(adjacent);
                    }
                }
            }
        }
        return firstClassAdjacentTiles;

    }

    public ArrayList<Tile> secondClassAdjacentTiles(){

        ArrayList<Tile> secondClassAdjacentTiles = new ArrayList<>();
        for (Tile tile:firstClassAdjacentTiles()){
            for (Tile adjacent : tile.getAdjacentTiles()){
                if (!secondClassAdjacentTiles.contains(adjacent)
                        && !firstClassAdjacentTiles().contains(adjacent)
                        && !tiles.contains(adjacent)) {
                    secondClassAdjacentTiles.add(adjacent);
                }
            }
        }

        return secondClassAdjacentTiles;
    }

    public ArrayList<Tile> clearTiles(){
        ArrayList<Tile> clearTiles = new ArrayList<>();
        clearTiles.addAll(tiles);
        clearTiles.addAll(firstClassAdjacentTiles());
        clearTiles.addAll(secondClassAdjacentTiles());
        return clearTiles;
    }

    public ArrayList<Tile> visibleTiles(){
        return null;
    }
}
