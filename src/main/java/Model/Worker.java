package Model;

import Database.GameDatabase;

import java.util.HashMap;

public class Worker extends Citizen {
    private boolean isAssigned;
    private int indexOfProject;
    private String typeOfWork;
    private static HashMap<String, Integer> workToIndex;

    public static void setHashMap() {
        workToIndex.put("Road", 0);
        workToIndex.put("Railroad", 1);
        workToIndex.put("Farm", 2);
        workToIndex.put("Mine", 3);
        workToIndex.put("TradingPost", 4);
        workToIndex.put("LumberMill", 5);
        workToIndex.put("Pasture", 6);
        workToIndex.put("Camp", 7);
        workToIndex.put("Field", 8);
        workToIndex.put("quarry", 9);//TODO might change
        workToIndex.put("removeJungle", 10);//forest in the doc
        workToIndex.put("removeDenseJungle", 11);
        workToIndex.put("removePrairie", 12);
        workToIndex.put("removeRoad", 13);
        workToIndex.put("removeRailroad", 14);
        workToIndex.put("repair", 15);
    }

    public Worker(int x, int y, int Vx, int Vy, int power, int cost, int movementPoint, String unitType, boolean isSleeping, boolean isReady, String era, int HP, int civilizationIndex, boolean isAssigned) {
        super(x, y, Vx, Vy, power, cost, movementPoint, unitType, isSleeping, isReady, era, HP, civilizationIndex, isAssigned);
        isAssigned = false;
        indexOfProject = -1;
        typeOfWork = "";
    }

    //@Override
    public void nextTurn() {
        if (isAssigned) {
            Tile tile = GameDatabase.getTileByXAndY(this.x, this.y);
            int roundsTillFinishProject = tile.getRoundsTillFinishProjectByIndex(indexOfProject);
            tile.reduceRoundsByIndex(indexOfProject);
            roundsTillFinishProject--;
            if (roundsTillFinishProject == 0) {
                finishWork();
                isAssigned = false;
                typeOfWork = "";
            }
        }
    }

    public void pauseProject() {
        if (isAssigned) this.isAssigned = false;
    }

    public void assignNewProject(String type) {
        if (typeOfWork.equals(type)) {
            isAssigned = true;
        } else {
            Tile tile = GameDatabase.getTileByXAndY(this.x, this.y);
            if (tile.getRoundsTillFinishProjectByIndex(indexOfProject) != 0)
                tile.initializeRoundsTillFinish(indexOfProject);
            indexOfProject = workToIndex.get(type);
            typeOfWork = type;
            isAssigned = true;
        }
    }

    public void makeFarm() {
        Tile tile = GameDatabase.getTileByXAndY(this.x, this.y);
        Civilization civilization = GameDatabase.getCivilizationByTile(tile);
        if (civilization.isTechnologyInCivilization("Agriculture")
                && !tile.getBaseTerrain().getType().equals("Ice")
                && (tile.getBaseTerrain().getFeature().equals("Jungle") && civilization.isTechnologyInCivilization("Mining")
                || tile.getBaseTerrain().getFeature().equals("DenseJungle") && civilization.isTechnologyInCivilization("BronzeWorking")
                || tile.getBaseTerrain().getFeature().equals("Prairie") && civilization.isTechnologyInCivilization("Masonry"))) {
            indexOfProject = workToIndex.get("Farm");
            typeOfWork = "Farm";
            isAssigned = true;
        }
    }

    public void makeMine() {
        Tile tile = GameDatabase.getTileByXAndY(this.x, this.y);
        Civilization civilization = GameDatabase.getCivilizationByTile(tile);
        if (civilization.isTechnologyInCivilization("")
                && !tile.getBaseTerrain().getType().equals("Ice")
                && (tile.getBaseTerrainType().equals("Hill"))
                && (tile.getBaseTerrain().getFeature().equals("Jungle") && civilization.isTechnologyInCivilization("Mining")
                || tile.getBaseTerrain().getFeature().equals("DenseJungle") && civilization.isTechnologyInCivilization("BronzeWorking")
                || tile.getBaseTerrain().getFeature().equals("Prairie") && civilization.isTechnologyInCivilization("Masonry"))) {
            indexOfProject = workToIndex.get("Mine");
            typeOfWork = "Mine";
            isAssigned = true;
        }
    }

    public void makeRoad() {
        Tile tile = GameDatabase.getTileByXAndY(this.x, this.y);
        Civilization civilization = GameDatabase.getCivilizationByTile(tile);
        if (civilization.isTechnologyInCivilization("Wheel")
                && !tile.hasRoad
                && !tile.getBaseTerrainType().equals("Ice")
                && !tile.getBaseTerrainType().equals("Ocean")
                && !tile.getBaseTerrainType().equals("Mountain")) {
            indexOfProject = 0;
            isAssigned = true;
            typeOfWork = "Road";
        }
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
        switch (indexOfProject) {
            case 0:
                tile.hasRoad = true;
                break;
            case 1://TODO railroad
                tile.hasRailroad = true;
                break;
            case 2:
                //TODO edit when feature and resources are fixed
                //tile.baseTerrain.removeFeature();
//                tile.baseTerrain = null;
                Improvement improvement = new Improvement("Farm");
                tile.improvements.add(improvement);
                typeOfWork = "remove" + tile.getBaseTerrainType();
                indexOfProject = workToIndex.get(typeOfWork);
                break;
            case 3:
                improvement = new Improvement("Mine");
                tile.improvements.add(improvement);
                typeOfWork = "remove" + tile.getBaseTerrainType();
                indexOfProject = workToIndex.get(typeOfWork);
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                improvement = new Improvement(typeOfWork);
                tile.improvements.add(improvement);
                typeOfWork = "";
                indexOfProject = -1;
                isAssigned = false;
                break;
            case 9://TODO others
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

        }
        isAssigned = false;
    }
}
