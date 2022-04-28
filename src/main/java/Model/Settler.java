package Model;

public class Settler extends Citizen{

    public Settler(int x, int y, int Vx, int Vy, int power, int cost, int movementPoint, String unitType, boolean isSleeping, boolean isReady, String era, int HP, int civilizationIndex) {
        super(x, y, Vx, Vy, power, cost, movementPoint, unitType, isSleeping, isReady, era, HP, civilizationIndex);
    }

    public void createCity(Tile tile){

    }

}
