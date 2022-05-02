package Model;

import Database.GameDatabase;
import Enums.Resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

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
    private ArrayList<Worker> workers;
    private ArrayList<Building> buildings;
    private ArrayList<Resources> discoveredResources;
    private ArrayList<Settler> settlers;
    private Tile capital;
    private ArrayList<Tile> tiles;
    private boolean isGarrison;
    private boolean isDivision;
    private int HP;
    private String civilizationName;
    private boolean isColonized;
    private boolean isCapital;
    private int food;
    private int leftoverFood;
    private int production;
    private boolean isGettingWorkedOn;

    public City(String name, int power, int foodGeneratingRate, int goldGeneratingRate, int scienceGenerating, int productionGenerating, int timeToExtendBorders, int timeTopPopulate, String civilizationName, boolean isCapital, String type, String baseTerrainType, int x, int y, Tile capital) {
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
        this.workers = new ArrayList<Worker>();
        this.settlers = new ArrayList<Settler>();
        this.buildings = new ArrayList<Building>();
        this.discoveredResources = new ArrayList<Resources>();
        this.HP = 0;
        this.civilizationName = civilizationName;
        this.isCapital = isCapital;
        this.food = 0;
        this.leftoverFood = 0;
        this.production = 0;
        this.capital = capital;
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

    public ArrayList<Worker> getWorkers() {
        return workers;
    }

    public ArrayList<Settler> getSettlers() {
        return settlers;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public void reduceHP(int amount) {
        this.HP -= amount;
    }

    public void regainHP(int amount) {
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
            if (building.getName().equals(name) && building.getTurnsNeedToBuild() == 0) {
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
        result += "\t production: " + Integer.toString(this.production);
        result += "\t food: " + Integer.toString(this.food);
        result += "\t Power: " + Integer.toString(this.power) + "\n";
        result += "\t population: " + Integer.toString(this.workers.size() + this.settlers.size());
        result += "\t Hit Point: " + Integer.toString(this.HP);
        return result;
    }

    public void buildBuilding(Building building, boolean build) {
        building.setCityName(this.name);
        building.setTurnsNeedToBuild(this.production, this.productionGenerating);
        this.buildings.add(building);
        if (!build) {
            GameDatabase.getCivilizationByNickname(this.civilizationName).addGold(-building.getCost());
        }
    }

    public boolean isResourceDiscoveredByThisCity(String resourceName) {
        for (Resources discoveredResource : this.discoveredResources) {
            if (discoveredResource.getName().equals(resourceName)) {
                return true;
            }
        }
        return false;
    }

    public void addFood(int amount) {
        this.food += amount;
    }

    @Override
    public void nextTurn() {
        this.production += this.productionGenerating;
        this.food += this.foodGeneratingRate;
        for (Building building : this.buildings) {
            if (!building.wasBuilt()) {
                building.build();
            } else {
                building.nextTurn();
            }
        }
        for (Improvement improvement : this.improvements) {
            this.food += improvement.getCityFoodChange();
            this.production += improvement.getCityProductionChange();
            GameDatabase.getCivilizationByNickname(this.civilizationName).addGold(improvement.getCivilizationGoldChange());
        }
        for (Resources resource : this.discoveredResources) {
            resource.nextTurn(this.name);
        }
        countFood();//setting the food for the next turn
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

    public void countFood() {
        int count = food + leftoverFood;
        //add resources food
        for (Resources discoveredResource : discoveredResources) {
            count += discoveredResource.foodNum;
        }
        //sub citizens food
        for (Citizen citizen : workers) {
            if (citizen.isAssigned) count -= 2;
        }
        for (Settler settler : settlers) {
            if (settler.isAssigned) count -= 2;
        }
        if (count < 0) {
            count = citizensDyingForHunger(count);
        } else {
            count = checkNewCitizen(count);
        }
        //updating the leftover
        leftoverFood = count;
    }

    private int checkNewCitizen(int count) {
        double size = (double) workers.size() + (double) workers.size();
        if (count > Math.pow(2.0, size)) {
            count -= Math.pow(2.0, size);//TODO change initializing fields
            workers.add(new Worker(x, y, 0, 0, 0, 0, "sth", 0, 0, false));
        }
        return count;
    }

    private int citizensDyingForHunger(int count) {
        while (count > 0) {
            if (settlers.size() > 0) settlers.remove(0);
            else if (workers.size() > 0) workers.remove(0);
            count += 2;
        }
        return count;
    }

    //
    public void createSettler() {
        if (workers.size() + settlers.size() > 1) {
            settlers.add(new Settler(x, y, 0, 0, 0, 89, 0, true, true, "?", 0, 0, false));//TODO change initializing fields
            leftoverFood = 0;//damn immigrants why they gotta be eating everything
        }
    }

    public void removeSettler(Settler settler) {
        settlers.remove(settler);
    }

    public void addResource(Resources resources) {
        discoveredResources.add(resources);
        if (resources.getType().equals("luxury")
                && GameDatabase.getCivilizationByNickname(this.civilizationName).isResourceNew(resources)) {
            GameDatabase.getCivilizationByNickname(this.civilizationName).addHappiness(4);
        }
    }

    public ArrayList<Worker> getUnemployedWorkers() {
        ArrayList<Worker> workerArrayList = new ArrayList<>();
        for (Worker worker : workers) {
            if (!worker.isAssigned) workerArrayList.add(worker);
        }
        return workerArrayList;
    }

    public ArrayList<Settler> getUnemployedSettlers() {
        ArrayList<Settler> settlerArrayList = new ArrayList<>();
        for (Settler settler : settlers) {
            if (!settler.isAssigned) settlerArrayList.add(settler);
        }
        return settlerArrayList;
    }

    public void addTile(Tile tile) {
        tiles.add(tile);
    }

    public void changeCapital(Tile newCapital) {
        this.capital = newCapital;
    }

    public ArrayList<Tile> getTiles() {
        ArrayList<Tile> tileArrayList = new ArrayList<>();
        Collections.copy(tileArrayList, tiles);
        return tileArrayList;
    }

    public Tile getCapital() {
        return capital;
    }

}
