package Server.Model;

import Client.Database.GameDatabase;

import java.io.IOException;
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
    private Worker worker;
    private ArrayList<Building> buildings;
    private Settler settler;
    private ArrayList<Citizen> citizens;
    private ArrayList<Unit> attackingUnits;
    private Tile capital;
    private ArrayList<Tile> tiles;
    private Unit garrison;
    private boolean isGarrisoned;
    private boolean isDivision;
    private int HP;
    private String civilizationName;
    private boolean isColonized;
    private boolean isCapital;
    private int leftoverFood;
    private int production;
    private boolean isGettingWorkedOn;

    public City(String name, int power, int foodGeneratingRate, int goldGeneratingRate, int scienceGenerating, int productionGenerating, int timeToExtendBorders, int timeTopPopulate, String civilizationName, boolean isCapital, String type, String baseTerrainType, int x, int y, Tile capital) throws IOException {
        super(type, baseTerrainType, x, y);
        this.name = name;
        this.power = power;
        this.longRangePower = 5;
        this.range = 0;
        this.foodGeneratingRate = foodGeneratingRate;
        this.goldGeneratingRate = goldGeneratingRate;
        this.scienceGenerating = scienceGenerating;
        this.productionGenerating = productionGenerating;
        this.timeToExtendBorders = timeToExtendBorders;
        this.timeTopPopulate = timeTopPopulate;
        this.worker = null;
        this.settler = null;
        this.buildings = new ArrayList<Building>();
        this.HP = 10;
        this.civilizationName = civilizationName;
        this.isCapital = isCapital;
        this.leftoverFood = 0;
        this.production = 0;
        this.capital = capital;
        this.citizens = new ArrayList<Citizen>();
        this.attackingUnits = new ArrayList<>();
        this.tiles = new ArrayList<Tile>();
        tiles.add(GameDatabase.getTileByXAndY(this.x, this.y));
        capitalCalculator();
    }

    private void capitalCalculator() throws IOException {
        if(GameDatabase.getCivilizationByNickname(this.civilizationName).getCities().size() == 0) {
            this.isCapital = true;
            return;
        }
        for (City city : GameDatabase.getCivilizationByNickname(this.civilizationName).getCities()) {
            if(city.isCapital) {
                return;
            }
        }
        this.isCapital = true;
    }

    // This constructor is just for Unit Test
    public City(String cityName) throws IOException {
        super("clear", "Hill", 0, 0);
        this.name = cityName;
        this.tiles = new ArrayList<>();
        this.citizens = new ArrayList<>();
        this.buildings = new ArrayList<>();
        this.improvements = new ArrayList<>();
        this.civilizationName = "n1";
    }

    public String getName() {
        return name;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
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

    public Worker getWorker() {
        return worker;
    }

    public Settler getSettler() {
        return settler;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public void setIsGettingWorkedOn(boolean isGettingWorkedOn){
        this.isGettingWorkedOn = isGettingWorkedOn;
    }

    public boolean getIsGettingWorkedOn(){
        return this.isGettingWorkedOn;
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

    public void setCivilizationName(String name){
        civilizationName = name;
    }
    public Unit getGarrison() {
        return garrison;
    }

    public void setGarrison(Unit garrison) {
        this.garrison = garrison;
    }

    public boolean isGarrisoned() {
        return isGarrisoned;
    }

    public void setGarrisoned(boolean garrisoned) {
        isGarrisoned = garrisoned;
    }

    public boolean isCapital() {
        return isCapital;
    }

    public void generate() throws IOException {
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

    private void generateGold() throws IOException {
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
        result += "\t population: " + Integer.toString(this.citizens.size() + (this.settler == null ? 0 : 1)
                + (this.worker == null ? 0 : 1));
        result += "\t Hit Point: " + Integer.toString(this.HP);
        return result;
    }

    public void buildBuilding(Building building, boolean build) throws IOException {
        building.setCityName(this.name);
        building.setTurnsNeedToBuild(this.production, this.productionGenerating);
        this.buildings.add(building);
        if (!build) {
            building.setTurnsNeedToBuild(1);
            GameDatabase.getCivilizationByNickname(this.civilizationName).addGold(-building.getCost());
        }
    }

    public boolean isResourceDiscoveredByThisCity(String resourceName) {
        for (Tile tile : tiles) {
            if (tile.isResourceDiscoveredByThisTile(resourceName)) {
                return true;
            }
        }
        return false;
    }

    public void addFood(int amount) {
        this.leftoverFood += amount;
    }

    @Override
    public void nextTurn() throws IOException {
        int addingFood = 0;
        this.production += this.productionGenerating;
        addingFood += this.foodGeneratingRate;
        GameDatabase.getCivilizationByNickname(this.civilizationName).addScience(this.scienceGenerating);
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
            if(tile.baseTerrain.getResources() != null ) {
                addingFood += tile.baseTerrain.getResources().foodNum;
            }
        }
        //adding improvement bonus
        for (Improvement improvement : this.improvements) {
            addingFood += improvement.getCityFoodChange();
            this.production += improvement.getCityProductionChange();
            GameDatabase.getCivilizationByNickname(this.civilizationName).addGold(improvement.getCivilizationGoldChange());
        }

        Civilization civilization = GameDatabase.getCivilizationForCity(this.name);
        if (!civilization.isHappy()) addingFood /= 3;
        this.leftoverFood += addingFood;
        costFood();//setting the food for the next turn
    }


    public int getFood() {
        return leftoverFood;
    }

    public void setLeftoverFood(int leftoverFood) {
        this.leftoverFood = leftoverFood;
    }

    public int getProduction() {
        return production;
    }

    public void addProduction(int amount) {
        this.production += amount;
    }

    public void costFood() throws IOException {
        //adding Food is already handled
        int count = leftoverFood;
        //sub citizens food
        if (worker != null && worker.isAssigned) count -= 2;
        if (settler != null && settler.isAssigned) count -= 2;
        for (Unit unit : units) {
            count -= 2;
        }
        for (Citizen citizen : citizens) {
            count -= 2;
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
        double size = (double) citizens.size();
        if (count > Math.pow(3.0, size)) {
            count -= Math.pow(3.0, size);//3 can be changed to any constant
            Citizen newCitizen = new Citizen(x, y, "Citizen", 1, 0, false);
            citizens.add(newCitizen);
            capital.addUnit(newCitizen);
        }
        return count;
    }

    public void removeCapital() {
        if (isCapital()) {
            this.isCapital = false;
        }
    }

    public void setCapital() {
        this.isCapital = true;
    }

    private int citizensDyingForHunger(int count) throws IOException {
        while (count > 0) {
            if (citizens.size() > 0) {
                GameDatabase.findTileByCitizen(citizens.get(0)).removeCitizen(citizens.get(0));
                citizens.remove(0);
            }
            count += 2;
        }
        return count;
    }

    public void createSettler(int x, int y) throws IOException {
        //System.out.println("create settler");
        if (settler == null) {
            this.settler = new Settler(x, y, GameDatabase.getCivilizationIndex(civilizationName));
            GameDatabase.getCityByXAndY(x, y).addSettler(this.settler);
            GameDatabase.getTileByXAndY(x, y).addSettler(this.settler);
            this.settler.tileOfUnit = GameDatabase.getTileByXAndY(x, y);
            leftoverFood = 0;//damn immigrants why they gotta be eating everything
            //System.out.println("I'm in if");
        }
    }

    public void createWorker(int x, int y) throws IOException {
        Worker newWorker = new Worker(x, y, GameDatabase.getCivilizationIndex(civilizationName));
        this.worker = newWorker;
        GameDatabase.getCityByXAndY(x, y).addWorker(this.worker);
        GameDatabase.getTileByXAndY(x, y).addWorker(this.worker);
        this.worker.tileOfUnit =  GameDatabase.getTileByXAndY(x, y);
    }

    public void removeSettler(Settler settler) {
        if (settler == this.settler) this.settler = null;
    }

    public void addTile(Tile tile) {
        tiles.add(tile);
    }

    public void changeCapital(Tile newCapital) {
        this.capital = newCapital;
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public Tile getCapital() {
        return capital;
    }

    private ArrayList<Tile> addFirstTiles() throws IOException {
        ArrayList<Tile> firstTiles = new ArrayList<Tile>();
        firstTiles.add(GameDatabase.getTileByXAndY(this.x, this.y));
        ArrayList<Tile> nearTiles = getAdjacentTiles();
        for (Tile tile : nearTiles) {
            if (tile.isTileValidForAddingToCity()) {
                firstTiles.add(tile);
            }
        }
        return firstTiles;
    }

    public boolean isTileForThisCity(Tile tile) {
        if(tile == null) {
            return false;
        }
        for (Tile cityTile : this.tiles) {
            if (cityTile.getY() == tile.getY() && cityTile.getX() == tile.getX()) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Resources> getStrategicResources() {
        ArrayList<Resources> strategicsResources = new ArrayList<Resources>();
        for (Tile tile : this.tiles) {
            for (Resources resource : tile.getDiscoveredResources()) {
                if (resource.getType().equals("Strategics")) {
                    strategicsResources.add(resource);
                }
            }
        }
        return strategicsResources;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof City)) {
            return false;
        }
        if (this.name.equals(((City) obj).getName())) {
            return true;
        }
        return false;
    }

    public ArrayList<Citizen> getCitizens() {
        return citizens;
    }

    public void attackUnit(Unit unit) throws IOException {
        if (isTileInRangeOfUnit(unit.getTileOfUnit())){
            unit.setHP(unit.getHP() - this.longRangePower);

        }
    }


    public boolean isTileInRangeOfUnit(Tile tile) throws IOException {
        for (int i = 1; i <= 2; i++) {

            if (tile.getAdjacentTilesByLayer(i).contains(tile)) {
                return true;
            }
        }
        return false;
    }

    public boolean isBuildingInCity(String building) {
        for (Building building1 : this.buildings) {
            if(building1.getName().equals(building)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Tile> getValidTilesForBuying() throws IOException {
        ArrayList<Tile> tiles = new ArrayList<>();
        for (Tile tile : getTiles()) {
            for (Tile adjacentTile : tile.getAdjacentTiles()) {
                if(!isTileForThisCity(adjacentTile) && isTileNewInArray(adjacentTile, tiles)) {
                    if(GameDatabase.getCivilizationByNickname(this.civilizationName).getGold() > 10) {
                        tiles.add(adjacentTile);
                    }
                }
            }
        }
        return tiles;
    }

    private boolean isTileNewInArray(Tile adjacentTile, ArrayList<Tile> tiles) {
        if(adjacentTile == null) {
            return false;
        }
        for (Tile tile : tiles) {
            if(adjacentTile.getX() == tile.getX() && adjacentTile.getY() == tile.getY()) {
                return false;
            }
        }
        return true;
    }

    public void buyTile(Tile tile) throws IOException {
        this.tiles.add(tile);
        GameDatabase.getCivilizationByNickname(this.civilizationName).addTile(tile);
        GameDatabase.getCivilizationByNickname(this.civilizationName).addGold(-10);
    }
}
