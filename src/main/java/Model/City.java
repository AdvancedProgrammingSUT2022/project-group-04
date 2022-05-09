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
    //private ArrayList<Resources> discoveredResources;
    private ArrayList<Settler> settlers;
    private ArrayList<Unit> attackingUnits;
    private Tile capital;
    private ArrayList<Tile> tiles;
    private boolean isGarrison;
    private boolean isDivision;
    private int HP;
    private String civilizationName;
    private boolean isColonized;
    private boolean isCapital;
    //private int food;
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
        //this.discoveredResources = new ArrayList<Resources>();
        this.HP = 10;
        this.civilizationName = civilizationName;
        this.isCapital = isCapital;
        //this.food = 0;
        this.leftoverFood = 0;
        this.production = 0;
        this.capital = capital;
        this.attackingUnits = new ArrayList<>();
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

    public void addHP(int amount) {
        this.HP += amount;
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
        result += "\t food: " + Integer.toString(this.leftoverFood);
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
        for (Tile tile : tiles) {
            if (tile.baseTerrain.getResources().getName().equals(resourceName)){
                return true;
            }
        }
        return false;
    }

    public void addFood(int amount) {
        this.leftoverFood += amount;
    }

    @Override
    public void nextTurn() {
        int addingFood = 0;
        this.production += this.productionGenerating;
        addingFood += this.foodGeneratingRate;
        //adding building bonus
        for (Building building : this.buildings) {
            if (!building.wasBuilt()) {
                building.build();
            } else {
                building.nextTurn();
            }
        }
        for (Tile tile : tiles) {
            //adding feature bonus
            addingFood += tile.baseTerrain.getFoodNum();
            //adding resources bonus
            addingFood += tile.baseTerrain.getResources().foodNum;
        }
        //adding improvement bonus
        for (Improvement improvement : this.improvements) {
            addingFood += improvement.getCityFoodChange();
            this.production += improvement.getCityProductionChange();
            GameDatabase.getCivilizationByNickname(this.civilizationName).addGold(improvement.getCivilizationGoldChange());
        }
//        for (Resources resource : this.discoveredResources) {
//            resource.nextTurn(this.name);
//            addingFood += resource.foodNum;
//        }
        Civilization civilization = GameDatabase.getCivilizationForCity(this.name);
        if (!civilization.isHappy()) addingFood /= 3;
        this.leftoverFood += addingFood;
        costFood();//setting the food for the next turn
    }

//    //public ArrayList<Resources> getDiscoveredResources() {
//        return discoveredResources;
//    }



    public int getFood() {
        return leftoverFood;
    }

    public int getProduction() {
        return production;
    }

    public void addProduction(int amount) {
        this.production += amount;
    }

    public void costFood() {
        //adding Food is already handled
        int count = leftoverFood;
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
            //TODO blah
            Worker newWorker = new Worker(x, y, 1, 1, 0, 2, "sth", 1, 0, false);
            workers.add(newWorker);
            capital.addUnit(newWorker);
        }
        return count;
    }

    private int citizensDyingForHunger(int count) {
        while (count > 0) {
            if (settlers.size() > 0) {
                GameDatabase.findTileBySettler(settlers.get(0)).units.remove(settlers.get(0));
                settlers.remove(0);
            }
            else if (workers.size() > 0){
                workers.remove(0);
                GameDatabase.findTileByWorker(workers.get(0)).units.remove(workers.get(0));
            }
            count += 2;
        }
        return count;
    }

    //
    public void createSettler(int x,int y) {
        if (workers.size() + settlers.size() > 1) {
            Settler newSettler = new Settler(x, y, 1, 1, 0, 89, 2, true, true, "?", 1, 0, false);
            settlers.add(newSettler);
            GameDatabase.getCityByXAndY(x,y).addUnit(newSettler);
            leftoverFood = 0;//damn immigrants why they gotta be eating everything
        }
    }

    public void removeSettler(Settler settler) {
        settlers.remove(settler);
    }

//    public void addResource(Resources resources) {
//        discoveredResources.add(resources);
//        if (resources.getType().equals("luxury")
//                && GameDatabase.getCivilizationByNickname(this.civilizationName).isResourceNew(resources)) {
//            GameDatabase.getCivilizationByNickname(this.civilizationName).addHappiness(4);
//        }
//    }

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
