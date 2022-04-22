package Model;

import Enums.Resource;

import java.util.ArrayList;

public class Improvement {
    private String name;
    private ArrayList<Resource> resourcesThatNeedImprovement;
    private Technology requiredTechnology;
    private ArrayList<BaseTerrain> BaseTerrainThatCanBeBuilt;
    private ArrayList<TerrainFeatures> TerrainFeaturesThatCanBeBuilt;

    public Improvement(String name, ArrayList<Resource> resourcesThatNeedImprovement, Technology requiredTechnology, ArrayList<BaseTerrain> BaseTerrainThatCanBeBuilt,ArrayList<TerrainFeatures> TerrainFeaturesThatCanBeBuilt){
        this.name = name;
        this.resourcesThatNeedImprovement = resourcesThatNeedImprovement;
        this.requiredTechnology = requiredTechnology;
        this.BaseTerrainThatCanBeBuilt = BaseTerrainThatCanBeBuilt;
        this.TerrainFeaturesThatCanBeBuilt = TerrainFeaturesThatCanBeBuilt;
    }
    public String getName() {
        return name;
    }
    public ArrayList<Resource> getResourcesThatNeedImprovement() {
        return resourcesThatNeedImprovement;
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


}

