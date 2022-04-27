package Model;

import Database.GameDatabase;

import java.util.ArrayList;

public class City extends Tile {

    private String name;
    private int power;
    private int longRangePower;
    private int range;
    private int foodGeneratingRate;
    private int goldGeneratingRate;
    private int scienceGenerating;
    private int productionGenerating;
    private int timeToExtendBorders;
    private int timeTopPopulate;
    private ArrayList<Citizen> citizens;
    private ArrayList<Building> buildings;
    private boolean isGarrison;
    private boolean isDivision;
    private int hitPoint;
    private String civilizationName;
    private boolean isColonized;
    private boolean isCapital;
    private int happiness;

    public City(String name, int power, int foodGeneratingRate, int goldGeneratingRate, int scienceGenerating, int productionGenerating, int timeToExtendBorders, int timeTopPopulate, ArrayList<Citizen> citizens, String civilizationName, boolean isCapital, String type, String baseTerrainType, int x, int y) {
        super(type, baseTerrainType, x, y);
        this.name = name;
        this.power = power;
        this.longRangePower = 0;
        this.range = 0;
        this.foodGeneratingRate = foodGeneratingRate;
        this.goldGeneratingRate = goldGeneratingRate;
        this.scienceGenerating = scienceGenerating;
        this.productionGenerating = productionGenerating;
        this.timeToExtendBorders = timeToExtendBorders;
        this.timeTopPopulate = timeTopPopulate;
        this.citizens = citizens;
        this.buildings = new ArrayList<Building>();
        this.hitPoint = 0;
        this.civilizationName = civilizationName;
        this.isCapital = isCapital;
    }

    public String getName() {
        return name;
    }

    public int getPower() {
        return power;
    }

    public int getLongRangePower() {
        return longRangePower;
    }

    public int getRange() {
        return range;
    }

    public int getFoodGeneratingRate() {
        return foodGeneratingRate;
    }

    public int getGoldGeneratingRate() {
        return goldGeneratingRate;
    }

    public int getScienceGenerating() {
        return scienceGenerating;
    }

    public int getProductionGenerating() {
        return productionGenerating;
    }

    public int getTimeToExtendBorders() {
        return timeToExtendBorders;
    }

    public int getTimeTopPopulate() {
        return timeTopPopulate;
    }

    public ArrayList<Citizen> getCitizens() {
        return citizens;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public String getCivilizationName() {
        return civilizationName;
    }

    public boolean isCapital() {
        return isCapital;
    }

    public void generate() {
        generateGold();
    }

    @Override
    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public boolean isColonized() {
        return isColonized;
    }

    public boolean cityHasBuilding(String name) {
        for (Building building : this.buildings) {
            if(building.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    private void generateGold() {
        Civilization civilization = GameDatabase.getCivilizationByNickname(this.civilizationName);
        if (civilization != null) {
            civilization.addGold(this.goldGeneratingRate);
        }
    }

    @Override
    public String toString() {
        String result = this.name + ": \n";
        result += "\t Type: " + this.baseTerrain.getType() + "\n";
        result += "\t Power: " + Integer.toString(this.power) + "\n";
        result += "\t Happiness" + Integer.toString(this.happiness) + "\n";
        result += "\t Hit Point" + Integer.toString(this.hitPoint);
        return result;
    }

    public void buildBuilding(Building building) {
        this.buildings.add(building);
        GameDatabase.getCivilizationByNickname(this.civilizationName).addGold(-building.getCost());
    }
}
