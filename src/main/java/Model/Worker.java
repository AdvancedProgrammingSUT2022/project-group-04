package Model;

import Database.GameDatabase;

public class Worker extends Citizen{
    private boolean isAssigned;
    private int indexOfProject;
    private String typeOfWork;
    public Worker(int x, int y, int Vx, int Vy, int power, int cost, int movementPoint, String unitType, boolean isSleeping, boolean isReady, String era, int HP, int civilizationIndex){
        super(x, y, Vx, Vy, power, cost, movementPoint, unitType, isSleeping, isReady, era, HP, civilizationIndex);
    }
    //@Override
    public void nextTurn(){
        Tile tile = GameDatabase.getTileByXAndY(this.x,this.y);
        int roundsTillFinishProject = tile.getRoundsTillFinishProjectByIndex(indexOfProject);
        tile.reduceRoundsByIndex(indexOfProject);
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
        if (civilization.isTechnologyInCivilization("Wheel")
                && !tile.hasRoad
                && !tile.getBaseTerrain().equals("Ice")
                && !tile.getBaseTerrain().equals("Ocean")
                && !tile.getBaseTerrain().equals("Mountain")) {
            indexOfProject = 0;
            isAssigned = true;
            typeOfWork = "Road";
        }
    }
    public void finishWork(){
        if (typeOfWork.equals("Road")){
            Tile tile = GameDatabase.getTileByXAndY(this.x,this.y);
            tile.hasRoad = true;
        }
//        else if (typeOfWork.equals("")){
//
//        }
    }
}
