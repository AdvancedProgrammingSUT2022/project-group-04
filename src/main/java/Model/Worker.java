package Model;

import Database.GameDatabase;

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
            finishWork();
            roundsTillFinishProject = 0;
            isAssigned = false;
            typeOfWork = "";
        }
    }
    public void makeRoad(){
        Tile tile = GameDatabase.getTileByXAndY(this.x,this.y);
        Civilization civilization = GameDatabase.getCivilizationByTile(tile);
        if ()
        if (civilization.isTechnologyInCivilization("Wheel")
                && !tile.hasRoad
                && !tile.getBaseTerrain().equals("Ice")
                && !tile.getBaseTerrain().equals("Ocean")
                && !tile.getBaseTerrain().equals("Mountain") {
            roundsTillFinishProject = 3;
            isAssigned = true;
            typeOfWork = "Road";
        }
    }
    public void finishWork(){
        if (typeOfWork.equals("Road")){
            Tile tile = GameDatabase.getTileByXAndY(this.x,this.y);
            tile.hasRoad = true;
        }
        else if (typeOfWork.equals("")){

        }
    }
}
