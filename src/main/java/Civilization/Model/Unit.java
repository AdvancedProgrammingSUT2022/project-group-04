package Civilization.Model;

import Civilization.Database.GameDatabase;

import java.util.ArrayList;

public class Unit {

    protected int x;
    protected int y;
    protected int speed;
    protected int originialspeed;
    protected int rangedCombatStrength;
    protected int combatStrength;
    protected int cost;
    protected Technology technologyRequired;
    protected StrategicsResources resourcesRequired;
    protected int movementPoint;
    protected String unitType;
    protected boolean isSleeping;
    protected boolean isReady;
    protected String era;
    protected int HP;
    protected int XP;
    protected Tile tileOfUnit;
    protected int civilizationIndex;
    protected int maintenance;

    public ArrayList<Tile> route = null;
    public Technology getTechnologyRequired() {
        return technologyRequired;
    }

    public StrategicsResources getResourcesRequired() {
        return resourcesRequired;
    }

    public int getX() {
        return x;
    }

    public int getMaintenance() {
        return this.maintenance;
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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getOriginialspeed() {
        return originialspeed;
    }

    public void setOriginialspeed(int originialspeed) {
        this.originialspeed = originialspeed;
    }

    public int getRangedCombatStrength() {
        return rangedCombatStrength;
    }

    public void setRangedCombatStrength(int rangedCombatStrength) {
        this.rangedCombatStrength = rangedCombatStrength;
    }

    public int getCombatStrength() {
        return combatStrength;
    }

    public void setCombatStrength(int combatStrength) {
        this.combatStrength = combatStrength;
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

    public void sleep() {
        this.isSleeping = true;
    }

    public void wakeUp() {
        this.isSleeping = false;
    }

    public void setSleeping(boolean isSleeping) {
        this.isSleeping = isSleeping;
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

    public void reduceHP(int amount) {
        this.HP -= amount;
    }

    public void regainHP(int amount) {
        this.HP += amount;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public void addHP(int amount) {
        this.HP += amount;
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

    public ArrayList<Tile> getRoute() {
        return route;
    }

    public void setRoute(ArrayList<Tile> route) {
        this.route = route;
    }

    public Unit(int x, int y, String unitType, int HP, int civilizationIndex, int maintenance) {
        this.x = x;
        this.y = y;
        this.unitType = unitType;
        this.HP = HP;
        this.civilizationIndex = civilizationIndex;
        this.maintenance = maintenance;
    }

    public void setTileOfUnit(Tile isOnTile) {
        this.tileOfUnit = isOnTile;
        if(isOnTile.getRuin() != null) {
            isOnTile.arriveRuin(this);
        }
        isOnTile.discoverResource();
    }

    public Tile getTileOfUnit() {
        return tileOfUnit;
    }

    /*public void moveOneTile(int direction) { //this function is to be used with graphics
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
    }*/

    public void moveToAdjacentTile(Tile adjacentTile) {
        this.x = adjacentTile.getX();
        this.y = adjacentTile.getY();
        this.movementPoint -= adjacentTile.movementPriceForTile();
    }

    @Override
    public String toString() {
        String result = this.unitType;
        result += "\nX = " + Integer.toString(this.x) + " Y = " + Integer.toString(this.y) + "\n";
        result += "Hit point = " + Integer.toString(this.HP) + "\n" + "Power = " + Integer.toString(this.combatStrength);
        return result;
    }

    public int moveUnitFromTo(Unit selectedUnit, Tile currentTile, Tile destTile) {
        if (!destTile.canBePassed()) {
            return -1;
        }
        if (selectedUnit instanceof Settler || selectedUnit instanceof Worker){
            if (destTile.getSettler() != null || destTile.getWorker() != null){
                return -3;
            }
        }
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
        ArrayList<Tile> temp = graph.route(currentInCopy, destInCopy, copyOfMap);
        for (Tile copyTile : temp) {
            for (Tile mainTile : GameDatabase.map) {
                if (mainTile.getX() == copyTile.getX() && mainTile.getY() == copyTile.getY()) {
                    path.add(mainTile);
                }
            }
        }
        selectedUnit.route = path;

        int movementPriceForPath = 0;
        outer: for (int i = 1; i < path.size(); i++) {
            if (path.get(i).hasRoad() || path.get(i).hasRailroad()){
                movementPriceForPath += path.get(i).movementPriceForTile() - 1;
            }
            else {
                movementPriceForPath += path.get(i).movementPriceForTile();
            }
            for (Civilization player : GameDatabase.getPlayers()){
                if (!player.equals(GameDatabase.getCivilizationByTurn(selectedUnit.getCivilizationIndex()))) {
                    for (Unit unit : player.getAllUnitsOfCivilization()) {
                        if (unit instanceof Soldier) {
                            Soldier soldier = (Soldier) unit;
                            if (soldier.isTileInRangeOfUnit(path.get(i))) {
                                movementPriceForPath = selectedUnit.getSpeed();
                                break outer;
                            }
                        }
                    }
                }
            }
        }
        if (selectedUnit.getSpeed() < movementPriceForPath){
            selectedUnit.route = null;
            return -2;
        }
        else {
            selectedUnit.setSpeed(selectedUnit.getSpeed() - movementPriceForPath);
        }
        return 0;
    }

    public boolean isCombatUnit() {
        return true;
    }

    public boolean isSettler() {
        return false;
    }

    public boolean isWorker() {
        return false;
    }

    public boolean isInItsCivilization() {
        if (GameDatabase.getCivilizationByTurn(civilizationIndex) == GameDatabase.getCivilizationByTile(this.tileOfUnit)) {
            return true;
        }
        return false;

    }

}
