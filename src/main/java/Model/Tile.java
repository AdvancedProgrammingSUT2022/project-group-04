package Model;

import Database.GameDatabase;

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
    protected boolean isRaided;
    protected boolean[] isRiver;
    ArrayList<Tile> neighbors = new ArrayList<Tile>();

    public boolean[] getIsRiver() {
        return isRiver;
    }

    boolean visited;
    Tile prev;


    public Tile(String type, String baseTerrainType, int x, int y) {
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
        for (TerrainFeatures features : getBaseTerrain().getFeatures()) {
            movementPriceSum += features.getMovementPrice();
        }
        return movementPriceSum;
    }

    public boolean canBeSeenByUnitOrBuilding() {
        return false;
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

}
