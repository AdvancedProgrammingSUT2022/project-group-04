package Model;

import Database.GameDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Worker extends Citizen {
    protected boolean isAssigned;
    protected int indexOfProject;
    private String typeOfWork;
    private boolean isMoving;
    private Tile desTile;
    private boolean isLocked;
    public static HashMap<String, Integer> workToIndex;
    //TODO implement railroad

    public static void setHashMap() {
        workToIndex = new HashMap<>();
        workToIndex.put("Road", 0);
        workToIndex.put("Railroad", 1);
        workToIndex.put("Farm", 2);
        workToIndex.put("Mine", 3);
        workToIndex.put("TradingPost", 4);
        workToIndex.put("LumberMill", 5);
        workToIndex.put("Pasture", 6);
        workToIndex.put("Camp", 7);
        workToIndex.put("Plantation", 8);
        workToIndex.put("Quarry", 9);
        workToIndex.put("removeJungle", 10);//forest in the doc
        workToIndex.put("removeDenseJungle", 11);
        workToIndex.put("removePrairie", 12);
        workToIndex.put("removeRoad", 13);
        workToIndex.put("removeRailroad", 14);
        workToIndex.put("repairRoad", 15);
        workToIndex.put("repairRailroad", 16);
        workToIndex.put("repairFarm", 17);
        workToIndex.put("repairMine", 18);
        workToIndex.put("repairTradingPost", 19);
        workToIndex.put("repairLumberMill", 20);
        workToIndex.put("repairPasture", 21);
        workToIndex.put("repairCamp", 22);
        workToIndex.put("repairPlantation", 23);
        workToIndex.put("repairQuarry", 24);
    }

    public Worker(int x, int y, int Vx, int Vy, int power, int movementPoint, String era, int HP, int civilizationIndex, boolean isAssigned) {
        super(x, y, Vx, Vy, power, 70, movementPoint, "Civilian Worker", true, true, era, HP, civilizationIndex, isAssigned);
        isAssigned = false;
        indexOfProject = -1;
        typeOfWork = "";
        isLocked = false;
        isMoving = false;
    }

    public boolean isAssigned(){
        return isAssigned;
    }

    @Override
    public boolean isWorker() {
        return true;
    }

    @Override
    public boolean isSettler() {
        return false;
    }

    public void nextTurn() {
        Tile tile = GameDatabase.getTileByXAndY(this.x, this.y);
        if (isAssigned && !isMoving && isLocked) {
            int roundsTillFinishProject = tile.getRoundsTillFinishProjectByIndex(indexOfProject);
            tile.reduceRoundsByIndex(indexOfProject);
            roundsTillFinishProject--;
            if (roundsTillFinishProject == 0) {
                finishWork();
                isAssigned = false;
                typeOfWork = "";
            }
        }
        if (tile == desTile){
            isMoving = false;
            isLocked = true;
        }
    }

    public void lockTheWorker(Tile tile){
        isMoving = true;
        moveUnitFromTo(this,GameDatabase.getTileByXAndY(this.x,this.y),tile);
        this.desTile = tile;
    }

//    public void removeRoad(){//TODO change if more than 1 turn is needed for removal
//        Tile tile = GameDatabase.getTileByXAndY(this.x,this.y);
//        tile.hasRoad = false;
//    }
//
//    public void removeFeature(){
//        Tile tile = GameDatabase.getTileByXAndY(this.x,this.y);
//        tile.baseTerrain.removeFeature();
//    }

    public void finishWork() {
        Tile tile = GameDatabase.getTileByXAndY(this.x, this.y);
        tile.setIsGettingWorkedOn(false);
        switch (indexOfProject) {
            case 0:
                tile.hasRoad = true;
                tile.isRoadBroken = false;
                typeOfWork = "";
                indexOfProject = -1;
                isAssigned = false;
                break;
            case 1:
                tile.hasRailroad = true;
                tile.isRailroadBroken = false;
                typeOfWork = "";
                indexOfProject = -1;
                isAssigned = false;
                break;
            case 2:
                //TODO edit when feature and resources are fixed
                //tile.baseTerrain.removeFeature();
//                tile.baseTerrain = null;
                Improvement improvement = new Improvement("Farm");
                improvement.setTurnsNeed(GameDatabase.getTileByXAndY(this.x, this.y));
                tile.improvements.add(improvement);
                typeOfWork = "remove" + tile.baseTerrain.getFeature().getType();
                indexOfProject = workToIndex.get(typeOfWork);
                break;
            case 3:
                improvement = new Improvement("Mine");
                improvement.setTurnsNeed(GameDatabase.getTileByXAndY(this.x, this.y));
                tile.improvements.add(improvement);
                typeOfWork = "remove" + tile.baseTerrain.getFeature().getType();
                indexOfProject = workToIndex.get(typeOfWork);
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                improvement = new Improvement(typeOfWork);
                tile.improvements.add(improvement);
                typeOfWork = "";
                indexOfProject = -1;
                isAssigned = false;
                break;
            case 10:
            case 11:
            case 12:
                tile.baseTerrain.removeFeature();
                typeOfWork = "";
                indexOfProject = -1;
                isAssigned = false;
                break;
            case 13:
                tile.hasRoad = false;
                typeOfWork = "";
                indexOfProject = -1;
                isAssigned = false;
                break;
            case 14:
                tile.hasRailroad = false;
                typeOfWork = "";
                indexOfProject = -1;
                isAssigned = false;
                break;
            case 15:
                tile.isRoadBroken = false;
                typeOfWork = "";
                indexOfProject = -1;
                isAssigned = false;
                break;
            case 16:
                tile.isRailroadBroken = false;
                typeOfWork = "";
                indexOfProject = -1;
                isAssigned = false;
                break;
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
                String nameOfImprovement = typeOfWork.substring(6);
                tile.fixImprovementByName(nameOfImprovement);
                typeOfWork = "";
                indexOfProject = -1;
                isAssigned = false;
                break;
        }
        isAssigned = false;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public String getTypeOfWork() {
        return typeOfWork;
    }

    public void setIsAssigned(boolean isAssigned) {
        this.isAssigned = isAssigned;
    }

    public int getIndexOfProject() {
        return this.indexOfProject;
    }

    public void setIndexOfProject(int newIndexOfProject) {
        this.indexOfProject = newIndexOfProject;
    }


    public void setTypeOfWork(String type) {
        this.typeOfWork = type;
    }

    public boolean isMoving() {
        return isMoving;
    }
}
