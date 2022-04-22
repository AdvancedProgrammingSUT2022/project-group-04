package Model;

import java.util.ArrayList;

public class Tile {

    protected BaseTerrain baseTerrain;
    protected int x;
    protected int y;
    protected String contains;
    protected ArrayList<Unit> units;
    private Civilization civilization;
    private boolean isRaided;
    private boolean[] isRiver;


    public Tile(String type, int x, int y) {
        this.baseTerrain = new BaseTerrain(type);
        this.x = x;
        this.y = y;
        this.units = new ArrayList<Unit>();
        this.isRiver = new boolean[6];
    }


    public String getType() {
        return this.baseTerrain.getType();
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
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

    public void setCivilization(Civilization civilization) {
        this.civilization = civilization;
    }

    public Civilization getCivilization() {
        return this.civilization;
    }

    public boolean isRiverByNumberOfEdge(int indexOfEdge) {
        return isRiver[indexOfEdge];
    }

    public BaseTerrain getBaseTerrain() {
        return baseTerrain;
    }
}
