package Model;

import Database.GameDatabase;

import java.util.ArrayList;

public class Unit {

    private int x;
    private int y;
    private int vX;
    private int vY;
    private int power;
    private int cost;
    private Technology technologyRequired;
    private Resources resourcesRequired;
    private int movementPoint;
    private String unitType;
    private boolean isSleeping;
    private boolean isReady;
    private String era;
    private int HP;
    private Tile tileOfUnit;

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

    public void setHP(int HP) {
        this.HP = HP;
    }

    public Unit(int x, int y, int vX, int vY, int power, int cost, int movementPoint, String unitType, boolean isSleeping, boolean isReady, String era, int HP ){
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
    }
    public void setIsOnTile(Tile isOnTile) {
        this.tileOfUnit = isOnTile;
    }
    public Tile getIsOnTile() {
        return tileOfUnit;
    }
    public void moveOneTile(int direction){
        int x1 = x, y1 = y - 10; // value is temporary , will be changed
        int x2 = x + 7 , y2 = y - 7;
        int x3 = x + 7 , y3 = y + 7;
        int x4 = x, y4 = y + 10;
        int x5 = x - 7, y5 = y + 7;
        int x6 = x - 7, y6 = y - 7;
        switch(direction){
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
    public void moveTo(Tile tile){

    }
    public int DistanceMetric(Tile currentTile, Tile destTile){
        return 0;
    }
    public void findPath(Tile currentTile, Tile destTile){

    }
    public void findNextStep(Tile currentTile, Tile destTile, ArrayList<Tile>listOfCheckedTiles){

    }
    public boolean isImpossibleToMove(Tile currentTile, ArrayList<Tile>listOfCheckedTiles){
        Tile adjacentTile0 = GameDatabase.getTileByXandY(currentTile.getX(), currentTile.getY());
        if (listOfCheckedTiles.contains(adjacentTile0)){

        }
        return true;


    }


}
