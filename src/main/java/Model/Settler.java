package Model;

import Database.GameDatabase;

import java.util.ArrayList;

public class Settler extends Citizen {

    public Settler(int x, int y, int Vx, int Vy, int power, int cost, int movementPoint, boolean isSleeping, boolean isReady, String era, int HP, int civilizationIndex, boolean isAssigned) {
        super(x, y, Vx, Vy, power, cost, movementPoint, "Civilian Settler", isSleeping, isReady, era, HP, civilizationIndex, isAssigned);
    }

    public void nextTurn() {

    }

    public void createCity(String name, int xOfCity, int yOfCity) {
        Tile tile = GameDatabase.getTileByXAndY(xOfCity, yOfCity);
        Tile tileOfOriginalCity = GameDatabase.getTileByXAndY(this.x, this.y);
        Civilization civilization = GameDatabase.getCivilizationByTile(tileOfOriginalCity);
        ArrayList<Tile> tiles = tile.getNeighbors();
        if (!civilization.isCityInCivilization(xOfCity,yOfCity)
                && tiles.contains(tileOfOriginalCity)) {
            //TODO edit if there is more than one turn for creating city
            civilization.addCity( new City(name, 0, tile.baseTerrain.getFoodNum(), tile.baseTerrain.getGold(),
                    0, tile.baseTerrain.getProduction(), 0, 0, null, ""
                    , false, "", tile.getBaseTerrainType(), tile.x, tile.y));
            civilization.getCityByXAndY(x,y).removeSettler();//kill the settler after making city
        }
    }

    @Override
    public boolean isSettler() {
        return true;
    }

}
