package Model;

public class Worker extends Citizen{
    private boolean isAssigned;
    private int roundsTillFinishProject;
    private String typeOfWork;
    public Worker(int x, int y, int Vx, int Vy, int power, int cost, int movementPoint, String unitType, boolean isSleeping, boolean isReady, String era, int HP, int civilizationIndex){
        super(x, y, Vx, Vy, power, cost, movementPoint, unitType, isSleeping, isReady, era, HP, civilizationIndex);
    }
    @Override
    public void nextTurn(){
        roundsTillFinishProject -- ;
        if (roundsTillFinishProject <= 0){
            roundsTillFinishProject = 0;
            isAssigned = false;
            typeOfWork = "";
        }
    }
    public void makeRoad(){
        if ()
        isAssigned = true;
        typeOfWork = "Road";

    }
    public void
}
