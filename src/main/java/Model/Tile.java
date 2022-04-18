package Model;

import java.util.ArrayList;

public class Tile {

    private String type;
    private int X;
    private int Y;
    private String contains;
    private ArrayList<Unit> units;
    private boolean isRaided;
    private boolean[] isRiver;


    public Tile(String type, int X, int Y) {
        this.type = type;
        this.X = X;
        this.Y = Y;
        this.units = new ArrayList<Unit>();
        this.isRiver = new boolean[6];
    }



    public String getType() {
        return this.type;
    }

    public int getX() {
        return this.X;
    }

    public int getY() {
        return this.Y;
    }

    private void setRaidedModel(boolean isRaided) {
        this.isRaided = isRaided;
    }

    public void addUnit(Unit newUnit) {
        this.units.add(newUnit);
    }

    public void removeUnit(Unit unit) {
        for(int i=0; i<this.units.size(); i++) {
            if(this.units.get(i).equals(unit)) {
                this.units.remove(i);
                return;
            }
        }
    }

}
