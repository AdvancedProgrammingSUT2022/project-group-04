package Model;

import Database.GameDatabase;
import Enums.Resource;

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
    private ArrayList<Resources> discoveredResources;
    private boolean isGarrison;
    private boolean isDivision;
    private int HP;
    private String civilizationName;
    private boolean isColonized;
    private boolean isCapital;
    private int food;
    private int leftoverFood;
    private int production;
    private boolean hasSettler;
    private boolean isGettingWorkedOn;

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
        this.discoveredResources = new ArrayList<Resources>();
        this.HP = 0;
        this.civilizationName = civilizationName;
        this.isCapital = isCapital;
        this.food = 0;
        this.leftoverFood = 0;
        this.production = 0;
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

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public void reduceHP(int amount){
        this.HP -= amount;
    }
    public void regainHP(int amount){
        this.HP += amount;
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
            if(building.getName().equals(name) && building.getTurnsNeedToBuild() == 0) {
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
        result += "\t Hit Point" + Integer.toString(this.HP);
        return result;
    }

    public void buildBuilding(Building building, boolean build) {
        building.setCityName(this.name);
        building.setTurnsNeedToBuild(this.production, this.productionGenerating);
        this.buildings.add(building);
        if(!build) {
            GameDatabase.getCivilizationByNickname(this.civilizationName).addGold(-building.getCost());
        }
    }

    public boolean isResourceDiscoveredByThisCity(String resourceName) {
        for (Resources discoveredResource : this.discoveredResources) {
            if(discoveredResource.getName().equals(resourceName)) {
                return true;
            }
        }
        return false;
    }

    public void addFood(int amount) {
        this.food+= amount;
    }

    @Override
    public void nextTurn() {
        this.production += this.productionGenerating;
        this.food += this.foodGeneratingRate;
        for (Building building : this.buildings) {
            if(!building.wasBuilt()) {
                building.build();
            } else {
                building.nextTurn();
            }
        }
        for (Improvement improvement : this.improvements) {
            this.food+= improvement.getCityFoodChange();
            this.production+= improvement.getCityProductionChange();
            GameDatabase.getCivilizationByNickname(this.civilizationName).addGold(improvement.getCivilizationGoldChange());
        }
        for (Resources resource : this.discoveredResources) {
            resource.nextTurn(this.name);
        }
    }

    public ArrayList<Resources> getDiscoveredResources() {
        return discoveredResources;
    }

    public int getFood() {
        return food;
    }

    public int getProduction() {
        return production;
    }

    public void addProduction(int amount) {
        this.production += amount;
    }

//    public int countFood(){
//        leftoverFood
//    }
//
//    public void createSettler(){
//        hasSettler = true;
//        citizens.add(new Settler(x, y, Vx, Vy, power, 89, 2, , isSleeping, isReady, era, HP, civilizationIndex, isAssigned));
//
//    }
}
