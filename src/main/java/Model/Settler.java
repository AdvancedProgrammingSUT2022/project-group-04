package Model;

import Database.GameDatabase;

import java.util.ArrayList;

public class Settler extends Citizen {

    public Settler(int x, int y, int Vx, int Vy, int power, int cost, int movementPoint, boolean isSleeping, boolean isReady, String era, int HP, int civilizationIndex, boolean isAssigned) {
        super(x, y, Vx, Vy, power, cost, movementPoint, "Civilian Settler", isSleeping, isReady, era, HP, civilizationIndex, isAssigned);
    }

    public void nextTurn() {

    }

    public void createNewCity(String name) {
        Tile tile = GameDatabase.getTileByXAndY(this.x, this.y);
        Civilization civilization = GameDatabase.getCivilizationByTile(tile);
        //TODO edit if there is more than one turn for creating city
        civilization.addCity(new City(name, 0, tile.baseTerrain.getFoodNum(), tile.baseTerrain.getGold(), 0,
                tile.baseTerrain.getProduction(), 0, 0, GameDatabase.getCivilizationByTile(GameDatabase.getTileByXAndY(this.x, this.y)).getNickname(),
                false, "", tile.getBaseTerrainType(), tile.getX(), tile.getY(),tile));
        civilization.getCityByXAndY(x, y).removeSettler(this);//kill the settler after making city

        tile.removeUnit(this);
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
