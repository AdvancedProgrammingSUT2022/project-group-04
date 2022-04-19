package Model;

import java.util.ArrayList;

public class Civilization {
    private String username;
    private String nickname;
    private int score;
    private int turn;
    private ArrayList<Tile> tiles;
    private ArrayList<City> cities;
    private int gold;
    //private ArrayList<Technology> technologies;

    public Civilization(String username, String nickname) {
        this.username = username;
        this.nickname = nickname;
        this.score = 0;
        this.turn = 0;
        this.tiles = new ArrayList<Tile>();
        this.cities = new ArrayList<City>();
        this.gold = 0;
        //this.technologies = new ArrayList<Technology>();
    }

    public void assignTiles() {

    }

    public void addScore(int amount) {
        this.score += amount;
    }

    public void addGold(int amount) {
        this.gold += amount;
    }

    /*
    public void addTechnology(Technology newTechnology) {
        this.technologies.add(newTechnology);
    }
    */

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
        for(int i=0; i<this.tiles.size(); i++) {
            if(this.tiles.get(i).equals(conqueredTile)) {
                this.tiles.remove(i);
                return;
            }
        }
    }

    public void removeCity(City conqueredCity) {
        for(int i=0; i<this.cities.size(); i++) {
            if(this.cities.get(i).equals(conqueredCity)) {
                this.cities.remove(i);
                return;
            }
        }
    }
}
