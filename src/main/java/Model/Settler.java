package Model;

import Database.GameDatabase;

import java.util.ArrayList;

public class Settler extends Citizen {


    public Settler(int x, int y, int civilizationIndex) {
        super(x, y, "Settler", 1, civilizationIndex, false);
        this.cost = 89;
        this.HP = 1;
        this.combatStrength = 0;
        this.rangedCombatStrength = 0;
        this.isHostage = false;
    }

    public void nextTurn() {

    }

    public void createNewCity(String name) {
        Tile tile = GameDatabase.getTileByXAndY(this.x, this.y);
        Civilization civilization = GameDatabase.getCivilizationByTurn(this.getCivilizationIndex());
        //TODO edit if there is more than one turn for creating city
        civilization.addCity(new City(name, 0, tile.baseTerrain.getFoodNum(), tile.baseTerrain.getGold(), 0,
                tile.baseTerrain.getProduction(), 0, 0, GameDatabase.getCivilizationByTurn(this.getCivilizationIndex()).getNickname(),
                false, "", tile.getBaseTerrainType(), tile.getX(), tile.getY(), tile));
        civilization.getCityByXAndY(x, y).removeSettler(this);//kill the settler after making city

        tile.removeSettler(this);
    }

//    public void addTileToTheCity(int xOfCity, int yOfCity) {
//        Tile tile = GameDatabase.getTileByXAndY(xOfCity, yOfCity);
//        Tile tileOfOriginalCity = GameDatabase.getTileByXAndY(this.x, this.y);
//        Civilization civilization = GameDatabase.getCivilizationByTile(tileOfOriginalCity);
//        //ArrayList<Tile> tiles = tile.getNeighbors();
//        City city = GameDatabase.getCityByXAndY(this.x,this.y);
//        if (!civilization.isCityInCivilization(xOfCity,yOfCity)) {
//            //TODO edit if there is more than one turn for creating city
//            city.addTile(tile);
//            civilization.getCityByXAndY(x,y).removeSettler(this);//kill the settler after making city
//            tile.removeUnit(this);
//        }
//    }

    @Override
    public boolean isSettler() {
        return true;
    }

    public boolean isAssigned() {
        return isAssigned;
    }
}
