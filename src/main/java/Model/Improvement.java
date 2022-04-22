package Model;

import Enums.Resource;

import java.util.ArrayList;

public class Improvement {
    private String name;
    private Technology requiredTechnology;
    private ArrayList<BaseTerrain> BaseTerrainThatCanBeBuilt;
    private ArrayList<TerrainFeatures> TerrainFeaturesThatCanBeBuilt;
    private int cityFoodChange;
    private int cityProductionChange;

    public Improvement(String name){
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public Technology getRequiredTechnology() {
        return requiredTechnology;
    }

    public ArrayList<BaseTerrain> getBaseTerrainThatCanBeBuilt() {
        return BaseTerrainThatCanBeBuilt;
    }

    public ArrayList<TerrainFeatures> getTerrainFeaturesThatCanBeBuilt() {
        return TerrainFeaturesThatCanBeBuilt;
    }

    public int getCityFoodChange() {
        return cityFoodChange;
    }

    public int getCityProductionChange() {
        return cityProductionChange;
    }
}

