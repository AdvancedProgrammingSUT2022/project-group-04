package Model;

public class Citizen extends Unit {
    protected boolean isHostage;
    protected boolean isAssigned;

    public Citizen(int x, int y, int Vx, int Vy, int power, int cost, int movementPoint, String unitType, boolean isSleeping, boolean isReady, String era, int HP, int civilizationIndex, boolean isAssigned) {
        super(x, y, Vx, Vy, power, cost, movementPoint, unitType, isSleeping, isReady, era, HP, civilizationIndex, 0);
        this.isAssigned = false;
    }

    public boolean isHostage() {
        return isHostage;
    }

    public void setHostage(boolean hostage) {
        isHostage = hostage;
    }

    public boolean isCombatUnit(){
        return false;
    }

    @Override
    public boolean isSettler(){
        return true;
    }

    @Override
    public boolean isWorker(){
        return false;
    }

}
