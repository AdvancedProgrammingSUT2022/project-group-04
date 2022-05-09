package Model;

import Database.GameDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class Tile {
    protected String type; //fogOfWar , Visible , Clear
    protected BaseTerrain baseTerrain;
    protected int x;
    protected int y;
    protected String contains;
    protected ArrayList<Unit> units;
    protected ArrayList<Building> buildings;
    protected ArrayList<Improvement> improvements;
    protected boolean isRaided;
    protected boolean[] isRiver;
    protected boolean hasRoad;
    protected boolean hasRailroad;
    ArrayList<Tile> neighbors = new ArrayList<Tile>();
    protected int[] roundsTillFinish;
    protected boolean isGettingWorkedOn;
    protected boolean isRoadBroken;
    protected boolean isRailroadBroken;
    protected City city;
    public boolean[] getIsRiver() {
        return isRiver;
    }

    boolean visited;
    Tile prev;


    public Tile(String type, String baseTerrainType, int x, int y) {
        this.type = type;
        this.hasRoad = false;
        this.baseTerrain = new BaseTerrain(baseTerrainType);
        this.x = x;
        this.y = y;
        this.units = new ArrayList<Unit>();
        this.improvements = new ArrayList<Improvement>();
        this.isRiver = new boolean[6];
        for (int i = 0; i < this.isRiver.length; i++) {
            this.isRiver[i] = false;
        }
        setNeighbors(getAdjacentTiles());//set neighbours
        initializeRoundsTillFinish(-1);
    }

    public String getType() {
        return type;
    }

    public boolean hasRailroad() {
        return hasRailroad;
    }

    public void initializeRoundsTillFinish(int flag) {
        int[] base = new int[25];
        base[0] = 3;
        base[1] = 3;
        base[2] = 6;
        base[3] = 6;
        //TODO improvements number of turns needed
        base[4] = 6;
        base[5] = 6;
        base[6] = 6;
        base[7] = 6;
        base[8] = 6;
        base[9] = 6;
        //
        base[10] = 4;
        base[11] = 7;
        base[12] = 6;
        base[13] = 3;
        base[14] = 3;
        //
        base[15] = 3;
        base[16] = 3;
        base[17] = 3;
        base[18] = 3;
        base[19] = 3;
        base[20] = 3;
        base[21] = 3;
        base[22] = 3;
        base[23] = 3;
        base[24] = 3;
        if (flag == -1) {
            roundsTillFinish = new int[25];
            for (int i=0;i<25;i++){
                roundsTillFinish[i] = base[i];
            }
        }
        else {
            roundsTillFinish[flag] = base[flag];
        }
    }

    public String getBaseTerrainType() {
        return this.baseTerrain.getType();
    }

    public boolean hasRoad() {
        return this.hasRoad;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public ArrayList<Tile> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(ArrayList<Tile> neighbors) {
        this.neighbors = neighbors;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Tile getPrev() {
        return prev;
    }

    public void setPrev(Tile prev) {
        this.prev = prev;
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(ArrayList<Building> buildings) {
        this.buildings = buildings;
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    private void setRaidedModel(boolean isRaided) {
        this.isRaided = isRaided;
    }

    public void addUnit(Unit newUnit) {
        this.units.add(newUnit);
    }

    public void removeUnit(Unit unit) {
        for (int i = 0; i < this.units.size(); i++) {
            if (this.units.get(i).equals(unit)) {
                this.units.remove(i);
                return;
            }
        }
    }


    public ArrayList<Improvement> getImprovements() {
        return improvements;
    }

    public void addImprovement(Improvement newImprovement) {
        this.improvements.add(newImprovement);
    }


    public boolean isRiverByNumberOfEdge(int indexOfEdge) {
        return isRiver[indexOfEdge];
    }

    public void setRiverByEdgeIndex(int edgeIndex) {
        this.isRiver[edgeIndex] = true;
    }

    public BaseTerrain getBaseTerrain() {
        return baseTerrain;
    }

    public boolean canBePassed() {
        return this.baseTerrain.IsMovementPossible();
    }

    public int movementPriceForTile() {
        int movementPriceSum = getBaseTerrain().getMovementPrice();
        movementPriceSum += getBaseTerrain().getFeature().getMovementPrice();
        return movementPriceSum;
    }

    public boolean canBeSeenByUnitOrBuilding() {
        return false;
    }

    @Override
    public String toString() {
        String result = "type = " + this.type + "\n";
        result += "X = " + Integer.toString(this.x) + " Y = " + Integer.toString(this.y);
        return result;
    }

    public void dryUpByEdge(int edge) {
        if(!isEdgeValid(edge)) {
            return;
        }
        this.isRiver[edge] = false;
    }

    private boolean isEdgeValid(int edge) {
        return edge >= 0 && edge < this.isRiver.length;
    }

    private void dryInNeighbors() {
        for (Tile tile : getAdjacentTiles()) {
            if(tile != null) {
                int edge = tile.getTileEdge(GameDatabase.getTileByXAndY(this.x, this.y));
                if(isEdgeValid(edge)) {
                    tile.dryUpByEdge(5 - edge);
                }
            }
        }
    }

    public int getTileEdge(Tile tile) {
        if(tile == null) {
            return -1;
        }
        int tileX = tile.getX();
        int tileY = tile.getY();
        if(tileX == this.x - 1 && tileY == this.y) {
            return 0;
        }
        if(tileX == this.x - 1 && tileY == this.y + 1) {
            return 1;
        }
        if(tileX == this.x && tileY == this.y + 1) {
            return 2;
        }
        if(tileX == this.x + 1 && tileY == this.y) {
            return 3;
        }
        if(tileX == this.x && tileY == this.y - 1) {
            return 4;
        }
        if(tileX == this.x - 1 && tileY == this.y - 1) {
            return 5;
        }
        return -1;
    }

    public void dryUp() {
        for(int i=0; i<this.isRiver.length; i++) {
            dryUpByEdge(i);
        }
        dryInNeighbors();
    }

    public ArrayList<Tile> getAdjacentTiles(){
        ArrayList<Tile> adjacentTiles = new ArrayList<>();
        if (this.y % 2 == 0){
            adjacentTiles.add(GameDatabase.getTileByXAndY(this.x - 1, this.y));
            adjacentTiles.add(GameDatabase.getTileByXAndY(this.x , this.y + 1));
            adjacentTiles.add(GameDatabase.getTileByXAndY(this.x + 1, this.y + 1));
            adjacentTiles.add(GameDatabase.getTileByXAndY(this.x + 1, this.y));
            adjacentTiles.add(GameDatabase.getTileByXAndY(this.x + 1, this.y - 1));
            adjacentTiles.add(GameDatabase.getTileByXAndY(this.x , this.y - 1));
        }
        else {
            adjacentTiles.add(GameDatabase.getTileByXAndY(this.x - 1, this.y));
            adjacentTiles.add(GameDatabase.getTileByXAndY(this.x - 1 , this.y + 1));
            adjacentTiles.add(GameDatabase.getTileByXAndY(this.x , this.y + 1));
            adjacentTiles.add(GameDatabase.getTileByXAndY(this.x + 1, this.y));
            adjacentTiles.add(GameDatabase.getTileByXAndY(this.x, this.y - 1));
            adjacentTiles.add(GameDatabase.getTileByXAndY(this.x - 1 , this.y - 1));
        }
        return adjacentTiles;
    }

    @Override
    public boolean equals(Object object) {
        if(!(object instanceof Tile)) {
            return false;
        }
        if(this.x == ((Tile) object).getX()
            && this.y == ((Tile) object).getY()) {
            return true;
        }
        return false;
    }

    public ArrayList<Tile> getAdjacentTilesByLayer(int n){

        ArrayList<Tile> adjacentTiles = new ArrayList<>();
        if (n == 1){
            return getAdjacentTiles();
        }
        else{
            for (Tile tile : getAdjacentTilesByLayer(n - 1)){
                for (Tile adjacent : tile.getAdjacentTiles()) {
                    if (!getAdjacentTilesByLayer(n - 2).contains(adjacent)
                            && !getAdjacentTilesByLayer(n - 1).contains(adjacent)
                            && !adjacentTiles.contains(adjacent)){
                        adjacentTiles.add(adjacent);
                    }
                }
            }
        }
        return adjacentTiles;

    }

    public int getRoundsTillFinishProjectByIndex(int indexOfProject) {
        return this.roundsTillFinish[indexOfProject];
    }

    public void reduceRoundsByIndex(int indexOfProject) {
        this.roundsTillFinish[indexOfProject] --;
    }

    public boolean isImprovementForThisTile(String improvementName) {
        for (Improvement improvement : this.improvements) {
            if(improvementName.equals(improvement.getName())) {
                return true;
            }
        }
        return false;
    }

    public boolean isThisFeatureForThisTile(String featureName) {
        return this.getBaseTerrain().getFeature().getType().equals(featureName);
    }

    public void nextTurn() {

    }

    public Settler returnSettler(){
        for (Unit unit : units) {
            if (unit.isSettler()) return (Settler)unit;
        }
        return null;
    }

    public void fixImprovementByName(String nameOfImprovement) {
        for (Improvement improvement : improvements) {
            if (improvement.getName().equals(nameOfImprovement)) improvement.fix();
        }
    }

    public Worker getAvailableWorker(){
        for (Unit unit : units) {
            if (unit.isWorker()) {
                if (!((Worker) unit).isLocked() && !((Worker) unit).isAssigned) {
                    return (Worker) unit;
                }
            }
        }
        return null;
    }

    public Worker getActiveWorker(){
        for (Unit unit : units) {
            if (unit.isWorker()){
                Worker worker = (Worker) unit;
                if (worker.isAssigned) return (Worker) unit;
            }
        }
        return null;
    }

    public void setIsGettingWorkedOn(boolean workedOn){
        this.isGettingWorkedOn = workedOn;
    }

    public boolean getIsGettingWorkedOn() {
        return isGettingWorkedOn;
    }

    public boolean isImprovementBroken(String improvementName) {
        for (Improvement improvement : this.improvements) {
            if(improvementName.equals(improvement.getName()) && improvement.isBroken()) {
                return true;
            }
        }
        return false;
    }

    public boolean isRoadBroken() {
        return isRoadBroken;
    }

    public boolean isRailroadBroken() {
        return isRailroadBroken;
    }
}
