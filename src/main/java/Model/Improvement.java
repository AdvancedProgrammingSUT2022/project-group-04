package Model;


import Database.GameDatabase;

import java.util.ArrayList;

public class Improvement {
    private String name;
    private Technology requiredTechnology;
    private ArrayList<String> BaseTerrainThatCanBeBuilt;
    private ArrayList<String> TerrainFeaturesThatCanBeBuilt;
    private int turnsNeed;
    private int cityFoodChange;
    private int cityProductionChange;
    private int civilizationGoldChange;
    private boolean isBroken;

    public Improvement(String name) {
        this.name = name;
        this.BaseTerrainThatCanBeBuilt = new ArrayList<String>();
        this.TerrainFeaturesThatCanBeBuilt = new ArrayList<String>();
        this.turnsNeed = 6;
        isBroken = false;
        switch (name) {
            case "Camp":
                this.requiredTechnology = new Technology("Trapping");
                this.BaseTerrainThatCanBeBuilt.add("Hill");
                this.BaseTerrainThatCanBeBuilt.add("Plain");
                this.BaseTerrainThatCanBeBuilt.add("Tundra");
                this.TerrainFeaturesThatCanBeBuilt.add("Jungle");
                this.cityFoodChange = 0;
                this.cityProductionChange = 0;
                this.civilizationGoldChange = 0;
                break;
            case "Farm":
                this.requiredTechnology = new Technology("Agriculture");
                this.BaseTerrainThatCanBeBuilt.add("Desert");
                this.BaseTerrainThatCanBeBuilt.add("Plain");
                this.BaseTerrainThatCanBeBuilt.add("Meadow");
                this.cityFoodChange = 1;
                this.cityProductionChange = 0;
                this.civilizationGoldChange = 0;

                break;
            case "LumberMill":
                this.requiredTechnology = new Technology("Construction");
                this.TerrainFeaturesThatCanBeBuilt.add("Jungle");
                this.cityFoodChange = 0;
                this.cityProductionChange = 1;
                this.civilizationGoldChange = 0;
                break;
            case "Mine":
                this.requiredTechnology = new Technology("Mining");
                this.BaseTerrainThatCanBeBuilt.add("Desert");
                this.BaseTerrainThatCanBeBuilt.add("Plain");
                this.BaseTerrainThatCanBeBuilt.add("Meadow");
                this.BaseTerrainThatCanBeBuilt.add("Tundra");
                this.BaseTerrainThatCanBeBuilt.add("Snow");
                this.TerrainFeaturesThatCanBeBuilt.add("Jungle");
                this.TerrainFeaturesThatCanBeBuilt.add("DenseJungle");
                this.TerrainFeaturesThatCanBeBuilt.add("Swamp");
                this.cityFoodChange = 0;
                this.cityProductionChange = 1;
                this.civilizationGoldChange = 0;
                break;
            case "Pasture":
                this.requiredTechnology = new Technology("AnimalHusbandry");
                this.BaseTerrainThatCanBeBuilt.add("Desert");
                this.BaseTerrainThatCanBeBuilt.add("Plain");
                this.BaseTerrainThatCanBeBuilt.add("Meadow");
                this.BaseTerrainThatCanBeBuilt.add("Tundra");
                this.BaseTerrainThatCanBeBuilt.add("Hill");
                this.cityFoodChange = 0;
                this.cityProductionChange = 0;
                this.civilizationGoldChange = 0;
                break;
            case "Plantation":
                this.requiredTechnology = new Technology("Calendar");
                this.BaseTerrainThatCanBeBuilt.add("Desert");
                this.BaseTerrainThatCanBeBuilt.add("Plain");
                this.BaseTerrainThatCanBeBuilt.add("Meadow");
                this.TerrainFeaturesThatCanBeBuilt.add("Jungle");
                this.TerrainFeaturesThatCanBeBuilt.add("DenseJungle");
                this.TerrainFeaturesThatCanBeBuilt.add("Swamp");
                this.TerrainFeaturesThatCanBeBuilt.add("Prairie");
                this.cityFoodChange = 0;
                this.cityProductionChange = 0;
                this.civilizationGoldChange = 0;
                break;
            case "Quarry":
                this.requiredTechnology = new Technology("Masonry");
                this.BaseTerrainThatCanBeBuilt.add("Desert");
                this.BaseTerrainThatCanBeBuilt.add("Plain");
                this.BaseTerrainThatCanBeBuilt.add("Meadow");
                this.BaseTerrainThatCanBeBuilt.add("Tundra");
                this.BaseTerrainThatCanBeBuilt.add("Hill");
                this.cityFoodChange = 0;
                this.cityProductionChange = 0;
                this.civilizationGoldChange = 0;
                break;
            case "TradingPost":
                this.requiredTechnology = new Technology("Trapping");
                this.BaseTerrainThatCanBeBuilt.add("Desert");
                this.BaseTerrainThatCanBeBuilt.add("Plain");
                this.BaseTerrainThatCanBeBuilt.add("Meadow");
                this.BaseTerrainThatCanBeBuilt.add("Tundra");
                this.cityFoodChange = 0;
                this.cityProductionChange = 0;
                this.civilizationGoldChange = 1;
                break;
            case "Factory":
                this.requiredTechnology = new Technology("Engineering");
                this.BaseTerrainThatCanBeBuilt.add("Desert");
                this.BaseTerrainThatCanBeBuilt.add("Plain");
                this.BaseTerrainThatCanBeBuilt.add("Meadow");
                this.BaseTerrainThatCanBeBuilt.add("Tundra");
                this.BaseTerrainThatCanBeBuilt.add("Snow");
                this.cityFoodChange = 0;
                this.cityProductionChange = 2;
                this.civilizationGoldChange = 0;
                break;
        }
    }

    public void setTurnsNeed(Tile tile) {
        switch (this.name) {
            case "Farm":
                switch (tile.getBaseTerrain().getFeature().getType()) {
                    case "Jungle" -> this.turnsNeed = 10;
                    case "DenseJungle" -> this.turnsNeed = 13;
                    case "Swamp" -> this.turnsNeed = 12;
                    default -> this.turnsNeed = 6;
                }
            case "Mine":
                switch (tile.getBaseTerrain().getFeature().getType()) {
                    case "Jungle" -> this.turnsNeed = 10;
                    case "DenseJungle" -> this.turnsNeed = 13;
                    case "Swamp" -> this.turnsNeed = 12;
                    default -> this.turnsNeed = 6;
                }
            default:
                this.turnsNeed = 6;
        }
    }

    public String getName() {
        return name;
    }

    public Technology getRequiredTechnology() {
        return requiredTechnology;
    }

    public ArrayList<String> getBaseTerrainThatCanBeBuilt() {
        return BaseTerrainThatCanBeBuilt;
    }

    public ArrayList<String> getTerrainFeaturesThatCanBeBuilt() {
        return TerrainFeaturesThatCanBeBuilt;
    }

    public int getCityFoodChange() {
        return cityFoodChange;
    }

    public int getCityProductionChange() {
        return cityProductionChange;
    }

    public int getCivilizationGoldChange() {
        return this.civilizationGoldChange;
    }

    public void fix() {
        isBroken = false;
    }

    public void breakImprovement(){
        isBroken = true;
    }

    public boolean isBroken(){
        return isBroken;
    }
}

