package Model;

import Database.GameDatabase;

import java.util.ArrayList;

public class Unit {

    protected int x;
    protected int y;
    protected int vX;
    protected int vY;
    protected int power;
    protected int cost;
    protected Technology technologyRequired;
    protected Resources resourcesRequired;
    protected int movementPoint;
    protected String unitType;
    protected boolean isSleeping;
    protected boolean isReady;
    protected String era;
    protected int HP;
    protected int XP;
    protected Tile tileOfUnit;
    protected int civilizationIndex;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        y = y;
    }

    public int getVX() {
        return vX;
    }

    public void setVX(int vX) {
        vX = vX;
    }

    public int getVY() {
        return vY;
    }

    public void setVY(int vY) {
        vY = vY;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getMovementPoint() {
        return movementPoint;
    }

    public void setMovementPoint(int movementPoint) {
        this.movementPoint = movementPoint;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public boolean isSleeping() {
        return isSleeping;
    }

    public void setSleeping(boolean sleeping) {
        isSleeping = sleeping;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public String getEra() {
        return era;
    }

    public void setEra(String era) {
        this.era = era;
    }

    public int getHP() {
        return HP;
    }

    public void reduceHP(int amount){
        this.HP -= amount;
    }
    public void regainHP(int amount){
        this.HP += amount;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getXP() {
        return XP;
    }

    public void setXP(int XP) {
        this.XP = XP;
    }

    public int getCivilizationIndex() {
        return civilizationIndex;
    }

    public Unit(int x, int y, int vX, int vY, int power, int cost, int movementPoint, String unitType, boolean isSleeping, boolean isReady, String era, int HP, int civilizationIndex) {
        this.x = x;
        this.y = y;
        this.vX = vX;
        this.vY = vY;
        this.power = power;
        this.movementPoint = movementPoint;
        this.unitType = unitType;
        this.isSleeping = isSleeping;
        this.isReady = isReady;
        this.era = era;
        this.HP = HP;
        this.civilizationIndex = civilizationIndex;
    }

    public void setTileOfUnit(Tile isOnTile) {
        this.tileOfUnit = isOnTile;
    }

    public Tile getTileOfUnit() {
        return tileOfUnit;
    }

    public void moveOneTile(int direction) { //this function is to be used with graphics
        int x1 = x, y1 = y - 10; // value is temporary , will be changed
        int x2 = x + 7, y2 = y - 7;
        int x3 = x + 7, y3 = y + 7;
        int x4 = x, y4 = y + 10;
        int x5 = x - 7, y5 = y + 7;
        int x6 = x - 7, y6 = y - 7;
        switch (direction) {
            case 1:
                do {
                    if (y > y1 - 1)
                        this.y -= vY;
                } while (y > y1 - 1);
                break;
            case 2:
                do {
                    if (x < x2 - 1)
                        this.x += vX;
                    if (y > y2 - 1)
                        this.y -= vY;
                } while (x < x1 - 1 || y > y1 - 1);
                break;
            case 3:
                do {
                    if (x < x1 - 1)
                        this.x += vX;
                    if (y < y1 - 1)
                        this.y += vY;
                } while (x < x1 - 1 || y < y1 - 1);
                break;
            case 4:
                do {
                    if (y < y1 - 1)
                        this.y += vY;
                } while (y < y1 - 1);
                break;
            case 5:
                do {
                    if (x > x1 - 1)
                        this.x -= vX;
                    if (y < y1 - 1)
                        this.y += vY;
                } while (x > x1 - 1 || y < y1 - 1);
                break;
            case 6:
                do {
                    if (x > x1 - 1)
                        this.x -= vX;
                    if (y > y1 - 1)
                        this.y -= vY;
                } while (x > x1 - 1 || y > y1 - 1);
                break;
        }
    }

    public void moveToAdjacentTile(Tile adjacentTile) {
        this.x = adjacentTile.getX();
        this.y = adjacentTile.getY();
        this.movementPoint -= adjacentTile.movementPriceForTile();
    }

    public boolean moveUnitFromTo(Unit selectedUnit, Tile currentTile, Tile destTile) {
        if (!destTile.canBePassed())
            return false;
        Graph graph = new Graph();
        ArrayList<Tile> copyOfMap = new ArrayList<>(GameDatabase.map);
        Tile currentInCopy = null;
        Tile destInCopy = null;
        for (Tile tile : copyOfMap) {
            if (tile.getX() == currentTile.getX() && tile.getY() == currentTile.getY())
                currentInCopy = tile;
            if (tile.getX() == destTile.getX() && tile.getY() == destTile.getY())
                destInCopy = tile;
        }
        ArrayList<Tile> path = new ArrayList<>();
        for (Tile copyTile : graph.route(currentInCopy, destInCopy, copyOfMap)) {
            for (Tile mainTile : GameDatabase.map) {
                if (mainTile.getX() == copyTile.getX() && mainTile.getY() == copyTile.getY()) {
                    path.add(mainTile);
                }
            }
        }
        //Todo.. make each movement round based
        for (int i = 1; i < path.size(); i++) {
            if (movementPoint >= path.get(i).movementPriceForTile()) {
                moveToAdjacentTile(path.get(i));
                path.get(i).addUnit(selectedUnit);
                path.get(i - 1).removeUnit(selectedUnit);
            }
            else {
                break;
            }
        }
        return true;
    }

    public boolean isCombatUnit(){
        return true;
    }

    public void fortify(){
        //TODO...
    }
    public void fortifyHeal(){
        //TODO...
    }

    public void createCity(int xOfTile,int yOfTile){

    }

}
