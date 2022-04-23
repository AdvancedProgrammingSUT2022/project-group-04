package Model;

import Database.GameDatabase;

import java.util.ArrayList;

public class Resources {
    private String name;
    private String type;
    private int gold;
    private int foodNum;
    private int production;
    private ArrayList<String> canBeFoundOnBaseTerrain;
    private ArrayList<String> canBeFoundOnTerrainFeature;
    private Improvement improvementNeeded;

    public Resources(String name) {
        this.name = name;
        this.canBeFoundOnBaseTerrain = new ArrayList<String>();
        this.canBeFoundOnTerrainFeature = new ArrayList<String>();
        switch (name) {
            case "banana":
                this.type = "bonus";
                this.foodNum = 1;
                this.production = 0;
                this.gold = 0;
                canBeFoundOnTerrainFeature.add("Dense_Jungle");
                improvementNeeded = new Improvement("Field");
                break;
            case "Cow":
                this.type = "bonus";
                this.foodNum = 1;
                this.production = 0;
                this.gold = 0;
                canBeFoundOnBaseTerrain.add("Meadow");
                improvementNeeded = new Improvement("Pasture");
                break;
            case "Gazelle":
                this.type = "bonus";
                this.foodNum = 1;
                this.production = 0;
                this.gold = 0;
                canBeFoundOnBaseTerrain.add("Tundra");
                canBeFoundOnBaseTerrain.add("Hill");
                canBeFoundOnTerrainFeature.add("Jungle");
                improvementNeeded = new Improvement("Camp");
                break;
            case "Sheep":
                this.type = "bonus";
                this.foodNum = 1;
                this.production = 0;
                this.gold = 0;
                canBeFoundOnBaseTerrain.add("Plain");
                canBeFoundOnBaseTerrain.add("Meadow");
                canBeFoundOnBaseTerrain.add("Desert");
                canBeFoundOnBaseTerrain.add("Hill");
                improvementNeeded = new Improvement("Pasture");
                break;
            case "Wheat":
                this.type = "bonus";
                this.foodNum = 1;
                this.production = 0;
                this.gold = 0;
                canBeFoundOnBaseTerrain.add("Plain");
                canBeFoundOnTerrainFeature.add("Prairie");
                improvementNeeded = new Improvement("Farm");
                break;
            case "Cotton":
                this.type = "luxury";
                this.foodNum = 0;
                this.production = 0;
                this.gold = 2;
                canBeFoundOnBaseTerrain.add("Plain");
                canBeFoundOnBaseTerrain.add("Desert");
                canBeFoundOnBaseTerrain.add("Meadow");
                improvementNeeded = new Improvement("Field");
                break;
            case "Color":
                this.type = "luxury";
                this.foodNum = 0;
                this.production = 0;
                this.gold = 2;
                canBeFoundOnTerrainFeature.add("Jungle");
                canBeFoundOnTerrainFeature.add("Dense_Jungle");
                improvementNeeded = new Improvement("Field");
                break;
            case "Fur":
                this.type = "luxury";
                this.foodNum = 0;
                this.production = 0;
                this.gold = 2;
                canBeFoundOnTerrainFeature.add("Jungle");
                canBeFoundOnBaseTerrain.add("Tundra");
                improvementNeeded = new Improvement("Camp");
                break;
            case "Gemstone":
                this.type = "luxury";
                this.foodNum = 0;
                this.production = 0;
                this.gold = 3;
                canBeFoundOnTerrainFeature.add("Dense_Jungle");
                canBeFoundOnBaseTerrain.add("Tundra");
                canBeFoundOnBaseTerrain.add("Plain");
                canBeFoundOnBaseTerrain.add("Desert");
                canBeFoundOnBaseTerrain.add("Meadow");
                canBeFoundOnBaseTerrain.add("Hill");
                improvementNeeded = new Improvement("Mine");
                break;
            case "Gold":
                this.type = "luxury";
                this.foodNum = 0;
                this.production = 0;
                this.gold = 2;
                canBeFoundOnBaseTerrain.add("Plain");
                canBeFoundOnBaseTerrain.add("Desert");
                canBeFoundOnBaseTerrain.add("Meadow");
                canBeFoundOnBaseTerrain.add("Hill");
                improvementNeeded = new Improvement("Mine");
                break;
            case "Incense":
                this.type = "luxury";
                this.foodNum = 0;
                this.production = 0;
                this.gold = 2;
                canBeFoundOnBaseTerrain.add("Plain");
                canBeFoundOnBaseTerrain.add("Desert");
                improvementNeeded = new Improvement("Field");
                break;
            case "Ivory":
                this.type = "luxury";
                this.foodNum = 0;
                this.production = 0;
                this.gold = 2;
                canBeFoundOnBaseTerrain.add("Plain");
                improvementNeeded = new Improvement("Camp");
                break;
            case "Marble":
                this.type = "luxury";
                this.foodNum = 0;
                this.production = 0;
                this.gold = 2;
                canBeFoundOnBaseTerrain.add("Tundra");
                canBeFoundOnBaseTerrain.add("Plain");
                canBeFoundOnBaseTerrain.add("Desert");
                canBeFoundOnBaseTerrain.add("Meadow");
                canBeFoundOnBaseTerrain.add("Hill");
                improvementNeeded = new Improvement("StoneMine");
                break;
            case "Silk":
                this.type = "luxury";
                this.foodNum = 0;
                this.production = 0;
                this.gold = 2;
                canBeFoundOnTerrainFeature.add("Jungle");
                improvementNeeded = new Improvement("Field");
                break;
            case "Silver":
                this.type = "luxury";
                this.foodNum = 0;
                this.production = 0;
                this.gold = 2;
                canBeFoundOnBaseTerrain.add("Tundra");
                canBeFoundOnBaseTerrain.add("Desert");
                canBeFoundOnBaseTerrain.add("Hill");
                improvementNeeded = new Improvement("Mine");
                break;
            case "Sugar":
                this.type = "luxury";
                this.foodNum = 0;
                this.production = 0;
                this.gold = 2;
                canBeFoundOnTerrainFeature.add("Prairie");
                canBeFoundOnTerrainFeature.add("Swamp");
                improvementNeeded = new Improvement("Field");
                break;
            case "Coal":
                this.type = "Strategics";
                this.foodNum = 0;
                this.production = 1;
                this.gold = 0;
                canBeFoundOnBaseTerrain.add("Plain");
                canBeFoundOnBaseTerrain.add("Meadow");
                canBeFoundOnBaseTerrain.add("Hill");
                improvementNeeded = new Improvement("Mine");
                break;
            case "Horse":
                this.type = "Strategics";
                this.foodNum = 0;
                this.production = 1;
                this.gold = 0;
                canBeFoundOnBaseTerrain.add("Tundra");
                canBeFoundOnBaseTerrain.add("Plain");
                canBeFoundOnBaseTerrain.add("Meadow");
                improvementNeeded = new Improvement("Pasture");
                break;
            case "Iron":
                this.type = "Strategics";
                this.foodNum = 0;
                this.production = 1;
                this.gold = 0;
                canBeFoundOnBaseTerrain.add("Tundra");
                canBeFoundOnBaseTerrain.add("Plain");
                canBeFoundOnBaseTerrain.add("Desert");
                canBeFoundOnBaseTerrain.add("Hill");
                canBeFoundOnBaseTerrain.add("Meadow");
                canBeFoundOnBaseTerrain.add("Snow");
                improvementNeeded = new Improvement("Mine");
                break;
            default:
                break;
        }
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getGold() {
        return gold;
    }

    public int getFoodNum() {
        return foodNum;
    }

    public int getProduction() {
        return production;
    }

    public ArrayList<String> getCanBeFoundOnBaseTerrain() {
        return canBeFoundOnBaseTerrain;
    }

    public ArrayList<String> getCanBeFoundOnTerrainFeature() {
        return canBeFoundOnTerrainFeature;
    }

    public Improvement getImprovementNeeded() {
        return improvementNeeded;
    }

}

