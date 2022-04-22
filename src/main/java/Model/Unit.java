package Model;

public class Unit {

    private int X;
    private int Y;
    private int Vx;
    private int Vy;
    private int power;
    private int cost;
    // private Technology technologyRequired;
    // private Resource resourcesRequired;
    private int movementPoint;
    private String unitType;
    private boolean isSleeping;
    private boolean isReady;
    private String era;
    private int HP;
    private Tile isOnTile;

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    public int getVx() {
        return Vx;
    }

    public void setVx(int vx) {
        Vx = vx;
    }

    public int getVy() {
        return Vy;
    }

    public void setVy(int vy) {
        Vy = vy;
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

    public Unit(int x, int y, int Vx, int Vy, int power, int cost, int movementPoint, String unitType, boolean isSleeping, boolean isReady, String era, int HP ){
        this.X = x;
        this.Y = y;
        this.Vx = Vx;
        this.Vy = Vy;
        this.power = power;
        this.movementPoint = movementPoint;
        this.unitType = unitType;
        this.isSleeping = isSleeping;
        this.isReady = isReady;
        this.era = era;
        this.HP = HP;
    }
    public void setIsOnTile(Tile isOnTile) {
        this.isOnTile = isOnTile;
    }
    public Tile getIsOnTile() {
        return isOnTile;
    }
    public void moveOneTile(int direction){
        int x1 = X, y1 = Y - 10; // value is temporary , will be changed
        int x2 = X + 7 , y2 = Y - 7;
        int x3 = X + 7 , y3 = Y + 7;
        int x4 = X, y4 = Y + 10;
        int x5 = X - 7, y5 = Y + 7;
        int x6 = X - 7, y6 = Y - 7;
        switch(direction){
            case 1:
                do {
                    if (Y > y1 - 1)
                        this.Y -= Vy;
                } while (Y > y1 - 1);
                break;
            case 2:
                do {
                    if (X < x2 - 1)
                        this.X += Vx;
                    if (Y > y2 - 1)
                        this.Y -= Vy;
                } while (X < x1 - 1 || Y > y1 - 1);
                break;
            case 3:
                do {
                    if (X < x1 - 1)
                        this.X += Vx;
                    if (Y < y1 - 1)
                        this.Y += Vy;
                } while (X < x1 - 1 || Y < y1 - 1);
                break;
            case 4:
                do {
                    if (Y < y1 - 1)
                        this.Y += Vy;
                } while (Y < y1 - 1);
                break;
            case 5:
                do {
                    if (X > x1 - 1)
                        this.X -= Vx;
                    if (Y < y1 - 1)
                        this.Y += Vy;
                } while (X > x1 - 1 || Y < y1 - 1);
                break;
            case 6:
                do {
                    if (X > x1 - 1)
                        this.X -= Vx;
                    if (Y > y1 - 1)
                        this.Y -= Vy;
                } while (X > x1 - 1 || Y > y1 - 1);
                break;
        }
    }
    public void moveTo(Tile tile){

    }

}
