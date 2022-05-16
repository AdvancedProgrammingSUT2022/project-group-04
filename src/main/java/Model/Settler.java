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
        //taking only one turn to build a city
        City city = new City(name, 10, tile.baseTerrain.getFoodNum(), tile.baseTerrain.getGold(), 5,
                tile.baseTerrain.getProduction(), 0, 0, GameDatabase.getCivilizationByTurn(this.getCivilizationIndex()).getNickname(),
                false, tile.type, tile.getBaseTerrainType(), tile.getX(), tile.getY(), tile);
        civilization.addCity(city);
        civilization.getCityByXAndY(x, y).removeSettler(this);//kill the settler after making city
        if (!civilization.isTileInCivilization(tile.x,tile.y)) civilization.addTile(tile);
        city.addTile(tile);
        tile.removeSettler(this);
    }

    @Override
    public boolean isSettler() {
        return true;
    }

    public boolean isAssigned() {
        return isAssigned;
    }
}
