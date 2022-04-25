package Model;

import java.util.ArrayList;

public class Tile {
    protected String type; //fogOfWar , Visible , Clear
    protected BaseTerrain baseTerrain;
    protected int x;
    protected int y;
    protected String contains;
    protected ArrayList<Unit> units;
    protected ArrayList<Building> buildings;
    protected ArrayList<Improvement> improvements;
    private boolean isRaided;
    private boolean[] isRiver;
    ArrayList<Tile> neighbors = new ArrayList<Tile>();
    boolean visited;
    Tile prev;


    public Tile(String type ,String baseTerrainType, int x, int y) {
        this.type = type;
        this.baseTerrain = new BaseTerrain(baseTerrainType);
        this.x = x;
        this.y = y;
        this.units = new ArrayList<Unit>();
        this.improvements = new ArrayList<Improvement>();
        this.isRiver = new boolean[6];
        for (int i = 0; i < this.isRiver.length; i++) {
            this.isRiver[i] = false;
        }
    }

    public String getBaseTerrainType() {
        return this.baseTerrain.getType();
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

    public Tile getPrev() {
        return prev;
    }

    public void setPrev(Tile prev) {
        this.prev = prev;
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

    public void setRiverByEdgeIndex(int edgeIndex){
        this.isRiver[edgeIndex] = true;
    }

    public BaseTerrain getBaseTerrain() {
        return baseTerrain;
    }
    public boolean canBePassed(){
        return this.baseTerrain.IsMovementPossible();
    }
    public int movementPriceForTile(){
        int movementPriceSum = getBaseTerrain().getMovementPrice();
        for (TerrainFeatures features:getBaseTerrain().getFeatures()){
            movementPriceSum += features.getMovementPrice();
        }
        return movementPriceSum;
    }

    public boolean canBeSeenByUnitOrBuilding(){
        return false;
    }
}
