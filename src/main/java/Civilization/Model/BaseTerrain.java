package Civilization.Model;


import java.util.ArrayList;

public class BaseTerrain {

    private String type;
    private int gold;
    private int foodNum;
    private int production;
    private int changesInCombat;
    private int movementPrice;
    private ArrayList<TerrainFeatures> possibleFeatures;
    private ArrayList<Resources> possibleResources;
    private TerrainFeatures features;
    private Resources resources;
    private boolean movementIsPossible;

    public BaseTerrain(String type) {
        this.type = type;
        possibleFeatures = new ArrayList<TerrainFeatures>();
        possibleResources = new ArrayList<Resources>();
        switch (type) {
            case "Desert":
                foodNum = 0;
                gold = 0;
                production = 0;
                changesInCombat = -33;
                movementPrice = 1;
                movementIsPossible = true;
                possibleFeatures.add(new TerrainFeatures("Oasis"));
                possibleFeatures.add(new TerrainFeatures("Prairie"));
                possibleResources.add(new StrategicsResources("Iron"));
                possibleResources.add(new Resources("Gold"));
                possibleResources.add(new Resources("Silver"));
                possibleResources.add(new Resources("Gemstone"));
                possibleResources.add(new Resources("Marble"));
                possibleResources.add(new Resources("Incense"));
                possibleResources.add(new Resources("Sheep"));
                break;
            case "Meadow":
                foodNum = 2;
                gold = 0;
                production = 0;
                changesInCombat = -33;
                movementPrice = 1;
                movementIsPossible = true;
                possibleFeatures.add(new TerrainFeatures("Jungle"));
                possibleFeatures.add(new TerrainFeatures("Oasis"));
                possibleResources.add(new StrategicsResources("Iron"));
                possibleResources.add(new StrategicsResources("Horse"));
                possibleResources.add(new StrategicsResources("Coal"));
                possibleResources.add(new Resources("Gold"));
                possibleResources.add(new Resources("Cow"));
                possibleResources.add(new Resources("Gemstone"));
                possibleResources.add(new Resources("Marble"));
                possibleResources.add(new Resources("Cotton"));
                possibleResources.add(new Resources("Sheep"));
                break;
            case "Hill":
                foodNum = 0;
                gold = 0;
                production = 2;
                changesInCombat = 25;
                movementPrice = 2;
                movementIsPossible = true;
                possibleFeatures.add(new TerrainFeatures("Jungle"));
                possibleFeatures.add(new TerrainFeatures("Dense_Jungle"));
                possibleResources.add(new StrategicsResources("Iron"));
                possibleResources.add(new StrategicsResources("Coal"));
                possibleResources.add(new Resources("Gold"));
                possibleResources.add(new Resources("Gazelle"));
                possibleResources.add(new Resources("Gemstone"));
                possibleResources.add(new Resources("Marble"));
                possibleResources.add(new Resources("Silver"));
                possibleResources.add(new Resources("Sheep"));
                break;
            case "Mountain":
                foodNum = 0;
                gold = 0;
                production = 0;
                changesInCombat = 0;
                movementPrice = 0;
                movementIsPossible = false;
                break;
            case "Ocean":
                foodNum = 0;
                gold = 0;
                production = 0;
                changesInCombat = 0;
                movementPrice = 0;
                movementIsPossible = false;
                break;
            case "Plain":
                foodNum = 1;
                gold = 0;
                production = 1;
                changesInCombat = -33;
                movementPrice = 1;
                movementIsPossible = true;
                possibleFeatures.add(new TerrainFeatures("Jungle"));
                possibleFeatures.add(new TerrainFeatures("Dense_Jungle"));
                possibleResources.add(new StrategicsResources("Iron"));
                possibleResources.add(new StrategicsResources("Horse"));
                possibleResources.add(new StrategicsResources("Coal"));
                possibleResources.add(new Resources("Wheat"));
                possibleResources.add(new Resources("Gold"));
                possibleResources.add(new Resources("Ivory"));
                possibleResources.add(new Resources("Gemstone"));
                possibleResources.add(new Resources("Marble"));
                possibleResources.add(new Resources("Cotton"));
                possibleResources.add(new Resources("Incense"));
                possibleResources.add(new Resources("Sheep"));
                break;
            case "Snow":
                foodNum = 0;
                gold = 0;
                production = 0;
                changesInCombat = -33;
                movementPrice = 1;
                movementIsPossible = true;
                possibleResources.add(new StrategicsResources("Iron"));
                break;
            case "Tundra":
                foodNum = 1;
                gold = 0;
                production = 0;
                changesInCombat = -33;
                movementPrice = 1;
                movementIsPossible = true;
                possibleFeatures.add(new TerrainFeatures("Jungle"));
                possibleResources.add(new StrategicsResources("Iron"));
                possibleResources.add(new StrategicsResources("Horse"));
                possibleResources.add(new Resources("Gemstone"));
                possibleResources.add(new Resources("Marble"));
                possibleResources.add(new Resources("Gazelle"));
                possibleResources.add(new Resources("Silver"));
                break;
        }
    }

    public String getType() {
        return this.type;
    }

    public int getFoodNum() {
        return this.foodNum;
    }

    public int getGold() {
        return this.gold;
    }

    public int getProduction() {
        return this.production;
    }

    public int getChangesInCombat() {
        return this.changesInCombat;
    }

    public ArrayList<Resources> getPossibleResources() {
        return this.possibleResources;
    }

    public ArrayList<TerrainFeatures> getPossibleFeatures() {
        return this.possibleFeatures;
    }

    public boolean IsMovementPossible() {
        return this.movementIsPossible;
    }

    public void setFeature(TerrainFeatures terrainFeature) {
        this.features = terrainFeature;
    }

    public void addResource(Resources resource) {
        this.resources = resource;
    }

    public void discoverResource() {
        this.resources = null;
    }

    public Resources getResources() {
        return resources;
    }

    public TerrainFeatures getFeature() {
        return features;
    }

    public int getMovementPrice() {
        return movementPrice;
    }

    public void setMovementPrice(int movementPrice) {
        this.movementPrice = movementPrice;
    }

    public boolean hasFeature(String feature) {
        if (features.getType().equals(feature)) return true;
        return false;
    }

    public void removeFeature() {
        features = null;
    }
}
