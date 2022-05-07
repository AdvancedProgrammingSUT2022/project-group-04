package Controller;

import Database.GameDatabase;
import Model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;

public class GameMenuController {

    private GameModel gameModel;
    public HashMap<String, Integer> directionX;
    public HashMap<String, Integer> directionY;
    public int x;
    public int y;
    public int c;

    public GameMenuController(GameModel gameModel) {
        this.gameModel = gameModel;
        this.directionX = new HashMap<String, Integer>();
        {
            this.directionX.put("UP", -1);
            this.directionX.put("DOWN", 1);
            this.directionX.put("RIGHT", 0);
            this.directionX.put("LEFT", 0);
        }
        this.directionY = new HashMap<String, Integer>();
        {
            this.directionY.put("UP", 0);
            this.directionY.put("DOWN", 0);
            this.directionY.put("RIGHT", 1);
            this.directionY.put("LEFT", -1);
        }
        this.x = 0;
        this.y = 0;
        this.c = 1;
    }

    public boolean isPositionValid(int x, int y) {
        for (int i = 0; i < GameDatabase.map.size(); i++) {
            if ((GameDatabase.map.get(i).getX() == x)
                    && (GameDatabase.map.get(i).getY() == y)) {
                return true;
            }
        }
        return false;
    }

    public boolean isUnitSoldier(Unit unitSelected) {
        return !unitSelected.getUnitType().startsWith("Civilian");
    }

    public boolean isUnitCivilian(Unit unitSelected) {
        return !isUnitSoldier(unitSelected);
    }

    public boolean isUnitWorker(Unit unitSelected) {
        return unitSelected.getUnitType().equals("Civilian Worker");
    }

    public boolean isUnitSettler(Unit unitSelected) {
        return unitSelected.getUnitType().equals("Civilian Settler");
    }

    public boolean isCityValid(String cityName) {
        City city = GameDatabase.getCityByName(cityName);
        return city != null;
    }

    public boolean isJungleInThisTile(Tile tile) {
        return tile.getBaseTerrain().getFeature().getType().endsWith("Jungle");
    }

    public boolean isRouteInThisTile(Tile tile) {
        return tile.hasRoad() || tile.hasRailroad();
    }

    public boolean isImprovementValidForThisTile(Tile tile, String improvementName) {
        Improvement improvement = new Improvement(improvementName);
        if (!GameDatabase.getCivilizationByTile(tile).isTechnologyForThisCivilization(improvement.getRequiredTechnology()))
            ;
        for (String terrainName : improvement.getBaseTerrainThatCanBeBuilt()) {
            if (tile.getBaseTerrain().getType().equals(terrainName)) {
                return true;
            }
        }
        for (String featureName : improvement.getTerrainFeaturesThatCanBeBuilt()) {
            if (tile.getBaseTerrain().getFeature().getType().equals(featureName)) {
                return true;
            }
        }
        return false;
    }

    public boolean isCombatUnitInThisPosition(int x, int y) {
        Tile tile = GameDatabase.getTileByXAndY(x, y);
        for (int i = 0; i < tile.getUnits().size(); i++) {
            if (isUnitSoldier(tile.getUnits().get(i))) {
                return true;
            }
        }
        return false;
    }

    public Unit selectCombatUnit(int x, int y) {
        Tile tile = GameDatabase.getTileByXAndY(x, y);
        for (int i = 0; i < tile.getUnits().size(); i++) {
            if (isUnitSoldier(tile.getUnits().get(i))) {
                return tile.getUnits().get(i);
            }
        }
        return null;
    }

    public boolean isDirectionForMapValid(String direction) {
        if (direction.equals("UP")) {
            return true;
        }
        if (direction.equals("DOWN")) {
            return true;
        }
        if (direction.equals("RIGHT")) {
            return true;
        }
        return direction.equals("LEFT");
    }

    public boolean isNonCombatUnitInThisPosition(int x, int y) {
        Tile tile = GameDatabase.getTileByXAndY(x, y);
        for (int i = 0; i < tile.getUnits().size(); i++) {
            if (isUnitCivilian(tile.getUnits().get(i))) {
                return true;
            }
        }
        return false;
    }

    public Unit selectNonCombatUnit(int x, int y) {
        Tile tile = GameDatabase.getTileByXAndY(x, y);
        for (int i = 0; i < tile.getUnits().size(); i++) {
            if (isUnitCivilian(tile.getUnits().get(i))) {
                return tile.getUnits().get(i);
            }
        }
        return null;
    }

    public boolean isUnitForThisCivilization(int turn, Unit unitSelected) {
        if (unitSelected.getCivilizationIndex() == turn) {
            return true;
        }
        return false;
    }

    public boolean isCityPositionValid(int x, int y) {
        City city = GameDatabase.getCityByXAndY(x, y);
        return city != null;
    }

    public boolean isDestinationOkForMove(Unit unit, int x, int y) {
        if (isUnitSoldier(unit)) {
            for (Unit unit1 : GameDatabase.getTileByXAndY(x, y).getUnits()) {
                if (isUnitSoldier(unit1)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isAmountValidForScience(int amount) {
        return isAmountValid(amount);
    }

    public boolean isCivilizationValid(String civilizationName) {
        Civilization civilization = GameDatabase.getCivilizationByNickname(civilizationName);
        if (civilization == null) {
            return false;
        }
        return true;
    }

    public boolean isCheatForTurn(String civilizationName, int turn) {
        int index = GameDatabase.getCivilizationIndex(civilizationName);
        if (index == -1) {
            return false;
        }
        if (index == turn) {
            return false;
        }
        return true;
    }

    public boolean isAmountValid(int amount) {
        if (amount < 1) {
            return false;
        }
        return true;
    }

    public boolean isAmountValidForTurn(int amount) {
        return isAmountValid(amount);
    }

    public boolean isCityForThisCivilization(int turn, City citySelected) {
        for (City city : GameDatabase.players.get(turn).getCities()) {
            if (city.getName().equals(citySelected.getName())) {
                return true;
            }
        }
        return false;
    }

    public boolean isCityNameUnique(String cityName) {
        for (Civilization player : GameDatabase.players) {
            for (City city : player.getCities()) {
                if (city.getName().equals(cityName)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isAmountValidForGold(int amount) {
        return isAmountValid(amount);
    }

    public String buildCity(Matcher matcher) {
        int xOfCity = Integer.parseInt(matcher.group("x"));
        int yOfCity = Integer.parseInt(matcher.group("y"));
        //TODO when commands are released
//        if (){
//
//        }
        return "";
    }

    public void createNewCity(Settler settler, String cityName) {
        settler.createNewCity(cityName);
    }

    public boolean isAdjacent(Tile tile, City city) {
        ArrayList<Tile> neighbours = tile.getAdjacentTiles();
        ArrayList<Tile> tilesOfCity = city.getTiles();
        for (Tile tile1 : tilesOfCity) {
            if (neighbours.contains(tile1)) return true;
        }
        return false;
    }

    public void addTileToCity(Tile tile, City city) {
        city.addTile(tile);
    }

    public boolean isOperable(Tile tile, City city) {
        ArrayList<Tile> neighbours = tile.getAdjacentTiles();
        ArrayList<Tile> tilesOfCity = city.getTiles();
        for (Tile tile1 : tilesOfCity) {
            if (neighbours.contains(tile1)
                    && tile1.returnSettler() != null) return true;
        }
        return false;
    }

    public boolean isImprovementValid(String name) {
        switch (name) {
            case "Camp":
            case "Farm":
            case "LumberMill":
            case "Mine":
            case "Pasture":
            case "Plantation":
            case "StoneMine":
            case "TradingPost":
            case "Quarry":
                return true;
            default:
                return false;
        }
    }

    public Worker findAvailableWorkerInCity(City city) {
        for (Tile tile : city.getTiles()) {
            if (tile.getAvailableWorker() != null) return tile.getAvailableWorker();
        }
        return null;
    }

    public ArrayList<Worker> getListOfUnemployedWorkers(City city) {
        ArrayList<Worker> workerArrayList = new ArrayList<>();
        for (Worker worker : city.getWorkers()) {
            if (!worker.isAssigned()) workerArrayList.add(worker);
        }
        return workerArrayList;
    }

    public ArrayList<Settler> getListOfUnemployedSettlers(City city) {
        ArrayList<Settler> settlerArrayList = new ArrayList<>();
        for (Settler settler : city.getSettlers()) {
            if (!settler.isAssigned()) settlerArrayList.add(settler);
        }
        return settlerArrayList;
    }

    public void pauseProject(Worker worker,int x,int y) {
        if (worker.isAssigned() && !worker.isMoving() && worker.isLocked()) {
            Tile tile = GameDatabase.getTileByXAndY(x, y);
            worker.setIsAssigned(false);
            tile.setIsGettingWorkedOn(false);
        }
    }

    public boolean assignNewProject(Worker worker, String type) {
        Tile tile = GameDatabase.getTileByXAndY(worker.getX(), worker.getY());
        if (!worker.isAssigned() && !tile.getIsGettingWorkedOn()) {
            boolean isPossible = true;
            if (worker.getTypeOfWork().equals(type)) {
                worker.setIsAssigned(true);
            } else {
                if (tile.getRoundsTillFinishProjectByIndex(worker.getIndexOfProject()) != 0)
                    tile.initializeRoundsTillFinish(worker.getIndexOfProject());
                worker.setIndexOfProject(Worker.workToIndex.get(type));
                worker.setTypeOfWork(type);
                worker.setIsAssigned(true);
                //if repair then initiate the index of array again
                if (worker.getIndexOfProject() > 12) tile.initializeRoundsTillFinish(worker.getIndexOfProject());
                switch (worker.getIndexOfProject()) {
                    case 0:
                        isPossible = makeRoad(worker);
                        break;
                    case 1:
                        isPossible = makeRailRoad(worker);
                        break;
                    case 2:
                        isPossible = makeFarm(worker);
                        break;
                    case 3:
                        isPossible = makeMine(worker);
                        break;
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                        isPossible = makeImprovement(worker);
                        break;
                    case 10:
                    case 11:
                    case 12:
                        break;
                    case 13:
                        isPossible = removeRoad(worker);
                        break;
                    case 14:
                        //isPossible = removeRailroad();
                        break;
                    case 15:
                    case 16:
                    case 17:
                    case 18:
                    case 19:
                    case 20:
                    case 21:
                    case 22:
                    case 23:
                    case 24:
                        isPossible = makeRepair(worker);
                        break;
                }
            }
            if (isPossible) {
                tile.setIsGettingWorkedOn(true);
                return true;
            }
        }
        return false;
    }
    private boolean makeRailRoad(Worker worker) {
        Tile tile = GameDatabase.getTileByXAndY(worker.getX(), worker.getY());
        Civilization civilization = GameDatabase.getCivilizationByTile(tile);
        if (civilization.isTechnologyInCivilization("SteamPower")
                && !tile.hasRailroad()
                && !tile.getBaseTerrainType().equals("Ice")
                && !tile.getBaseTerrainType().equals("Ocean")
                && !tile.getBaseTerrainType().equals("Mountain")) {
            worker.setIndexOfProject(1);
            worker.setIsAssigned (true);
            worker.setTypeOfWork("Railroad");
            return true;
        }
        worker.setIndexOfProject(-1);
        worker.setIsAssigned (false);
        worker.setTypeOfWork("");
        return false;
    }

    private boolean removeRoad(Worker worker) {
        Tile tile = GameDatabase.getTileByXAndY(worker.getX(), worker.getY());
        if (tile.hasRoad()) {
            return true;
        }
        worker.setIndexOfProject(-1);
        worker.setIsAssigned (false);
        worker.setTypeOfWork("");
        return false;
    }

    private boolean makeImprovement(Worker worker) {
        Tile tile = GameDatabase.getTileByXAndY(worker.getX(), worker.getY());
        Civilization civilization = GameDatabase.getCivilizationByTile(tile);
        Improvement improvement = new Improvement(worker.getTypeOfWork());
        boolean isImprovementInTile = tile.isImprovementForThisTile(worker.getTypeOfWork());
        if (civilization == null
                || !civilization.isTechnologyInCivilization(improvement.getRequiredTechnology().getName())
                || isImprovementInTile) {
            worker.setIndexOfProject(-1);
            worker.setIsAssigned (false);
            worker.setTypeOfWork("");
            return false;
        }
        return true;
    }

    private boolean makeRepair(Worker worker) {
        Tile tile = GameDatabase.getTileByXAndY(worker.getX(), worker.getY());
        if (worker.getIndexOfProject() == 15 && tile.hasRoad() && tile.isRoadBroken()) {
            return true;
        }
        if (worker.getIndexOfProject() == 16 && tile.hasRailroad() && tile.isRailroadBroken()) {
            return true;
        }
        String improvementName = worker.getTypeOfWork().substring(6);
        if (worker.getIndexOfProject() >= 17 && tile.isImprovementBroken(improvementName)) {
            return true;
        }
        worker.setIndexOfProject(-1);
        worker.setIsAssigned (false);
        worker.setTypeOfWork("");
        return false;
    }

    public boolean makeFarm(Worker worker) {
        Tile tile = GameDatabase.getTileByXAndY(worker.getX(), worker.getY());
        Civilization civilization = GameDatabase.getCivilizationByTile(tile);
        if (civilization.isTechnologyInCivilization("Agriculture")
                && !tile.getBaseTerrain().getType().equals("Ice")
                && (tile.getBaseTerrain().getFeature().equals("Jungle") && civilization.isTechnologyInCivilization("Mining")
                || tile.getBaseTerrain().getFeature().equals("DenseJungle") && civilization.isTechnologyInCivilization("BronzeWorking")
                || tile.getBaseTerrain().getFeature().equals("Prairie") && civilization.isTechnologyInCivilization("Masonry"))) {
            worker.setIndexOfProject(Worker.workToIndex.get("Farm"));
            worker.setIsAssigned (true);
            worker.setTypeOfWork("Farm");
            return true;
        }
        worker.setIndexOfProject(-1);
        worker.setIsAssigned (false);
        worker.setTypeOfWork("");
        return false;
    }

    public boolean makeMine(Worker worker) {
        Tile tile = GameDatabase.getTileByXAndY(worker.getX(), worker.getY());
        Civilization civilization = GameDatabase.getCivilizationByTile(tile);
        if (civilization.isTechnologyInCivilization("")
                && !tile.getBaseTerrain().getType().equals("Ice")
                && (tile.getBaseTerrainType().equals("Hill"))
                && (tile.getBaseTerrain().getFeature().equals("Jungle") && civilization.isTechnologyInCivilization("Mining")
                || tile.getBaseTerrain().getFeature().equals("DenseJungle") && civilization.isTechnologyInCivilization("BronzeWorking")
                || tile.getBaseTerrain().getFeature().equals("Prairie") && civilization.isTechnologyInCivilization("Masonry"))) {
            worker.setIndexOfProject(Worker.workToIndex.get("Mine"));
            worker.setIsAssigned (true);
            worker.setTypeOfWork("Mine");
            return true;
        }
        worker.setIndexOfProject(-1);
        worker.setIsAssigned (false);
        worker.setTypeOfWork("");
        return false;
    }

    public boolean makeRoad(Worker worker) {
        Tile tile = GameDatabase.getTileByXAndY(worker.getX(), worker.getY());
        Civilization civilization = GameDatabase.getCivilizationByTile(tile);
        if (civilization.isTechnologyInCivilization("Wheel")
                && !tile.hasRoad()
                && !tile.getBaseTerrainType().equals("Ice")
                && !tile.getBaseTerrainType().equals("Ocean")
                && !tile.getBaseTerrainType().equals("Mountain")) {
            worker.setIndexOfProject(Worker.workToIndex.get("Road"));
            worker.setIsAssigned (true);
            worker.setTypeOfWork("Road");
            return true;
        }
        worker.setIndexOfProject(-1);
        worker.setIsAssigned (false);
        worker.setTypeOfWork("");
        return false;
    }
    public boolean isTileInCivilization(Tile tile, int turn) {
        Civilization civilization = GameDatabase.getCivilizationByTurn(turn);
        if (civilization == null
                && !civilization.isTileInCivilization(tile.getX(), tile.getX())) {
            return false;
        }
        return true;
    }

    public void deleteUnit(Unit unit){
        int amount = (int)((double)1 / 10 * unit.getCost());
        GameDatabase.getCivilizationByTile(unit.getTileOfUnit()).addGold(amount);
        unit.getTileOfUnit().removeUnit(unit);
    }



}
