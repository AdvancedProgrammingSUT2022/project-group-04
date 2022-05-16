package Controller;

import Database.GameDatabase;
import Database.GlobalVariables;
import Model.*;
import com.sun.jdi.ArrayReference;
import org.mockito.internal.stubbing.defaultanswers.GloballyConfiguredAnswer;

import java.lang.reflect.Array;
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

    public ArrayList<Unit> movingUnits = new ArrayList<>();

    public ArrayList<Unit> getMovingUnits() {
        return movingUnits;
    }

    public void setMovingUnits(ArrayList<Unit> movingUnits) {
        this.movingUnits = movingUnits;
    }

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
        for (int i = 0; i < GameDatabase.getMap().size(); i++) {
            if ((GameDatabase.getMap().get(i).getX() == x)
                    && (GameDatabase.getMap().get(i).getY() == y)) {
                return true;
            }
        }
        return false;
    }

    public boolean isTileOcean(Tile tile) {
        if (tile == null) {
            return false;
        }
        return tile.getBaseTerrain().getType().equals("Ocean");
    }

    public boolean tileHasRiver(Tile tile) {
        boolean[] rivers = tile.getIsRiver();
        for (int i = 0; i < 6; i++) {
            if (rivers[i]) {
                return true;
            }
        }
        return false;
    }

    public boolean isUnitSoldier(Unit unitSelected) {
        return !unitSelected.getUnitType().equals("Settler")
                && !unitSelected.getUnitType().equals("worker")
                && !unitSelected.getUnitType().equals("Citizen");
    }

    public boolean isUnitCivilian(Unit unitSelected) {
        return !isUnitSoldier(unitSelected);
    }

    public boolean isCityValid(String cityName) {
        City city = GameDatabase.getCityByName(cityName);
        return city != null;
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
        if (direction.equals("UP")
                || direction.equals("DOWN")
                || direction.equals("RIGHT")
                || direction.equals("LEFT")) {
            return true;
        }
        return false;
    }

    public boolean isNonCombatUnitInThisPosition(int x, int y) {
        Tile tile = GameDatabase.getTileByXAndY(x, y);
        if (tile.getSettler() != null || tile.getWorker() != null)
            return true;
        return false;
    }

    public Unit selectNonCombatUnit(int x, int y) {
        Tile tile = GameDatabase.getTileByXAndY(x, y);
        if (tile.getSettler() != null)
            return tile.getSettler();
        if (tile.getWorker() != null)
            return tile.getWorker();
        return null;
    }

    public boolean isUnitForThisCivilization(int turn, Unit unitSelected) {
        if (unitSelected.getCivilizationIndex() == turn) {
            return true;
        }
        return false;
    }

    public void addHP(int x, int y, int amount) {
        this.selectCombatUnit(x, y).addHP(amount);
    }

    public void addHP(String cityName, int amount) {
        GameDatabase.getCityByName(cityName).addHP(amount);
    }

    public void makeHappy(int turn) {
        GameDatabase.getPlayers().get(turn).happy();
    }

    public void dryUp(int x, int y) {
        GameDatabase.getTileByXAndY(x, y).dryUp();
    }

    public void addGold(int turn, int amount) {
        GameDatabase.getPlayers().get(turn).addGold(amount);
    }

    public void addScience(int turn, int science) {
        GameDatabase.getPlayers().get(turn).addScience(science);
    }

    public void addScore(int turn, int score) {
        GameDatabase.getPlayers().get(turn).addScore(score);
    }

    public void sendMessage(int turn, String nickname, String text) {
        Notification notification = new Notification(GameDatabase.getPlayers().get(turn).getNickname(), nickname, text);
        Notification.addNotification(notification);
    }

    public boolean isCityPositionValid(int x, int y) {
        City city = GameDatabase.getCityByXAndY(x, y);
        return city != null;
    }

    public boolean isDestinationOkForMove(Unit unit, int x, int y) {
        if (!isPositionValid(x, y)) {
            return false;
        }
        if (isUnitSoldier(unit)) {
            for (int i = 0; i < GameDatabase.getTileByXAndY(x, y).getUnits().size(); i++) {
                if (isUnitSoldier(GameDatabase.getTileByXAndY(x, y).getUnits().get(i))) {
                    return false;
                }
            }
        } else if (GameDatabase.getTileByXAndY(x, y).getSettler() != null
                || GameDatabase.getTileByXAndY(x, y).getWorker() != null) {
            return false;
        }
        return true;
    }

    public boolean isCityCapital(String cityName) {
        return GameDatabase.getCityByName(cityName).isCapital();
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
        if (index == -1 || index == turn) {
            return false;
        }
        return true;
    }

    public boolean isAmountValidForHP(int amount) {
        return isAmountValid(amount);
    }

    public boolean isAmountALot(int amount) {
        return amount > 10;
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
        for (int i = 0; i < GameDatabase.getPlayers().get(turn).getCities().size(); i++) {
            if (GameDatabase.getPlayers().get(turn).getCities().get(i).getName().equals(citySelected.getName())) {
                return true;
            }
        }
        return false;
    }

    public boolean isAmountValidForGold(int amount) {
        return isAmountValid(amount);
    }

    public void createNewCity(Settler settler, String cityName) {
        settler.createNewCity(cityName);
    }

    public boolean isAdjacent(Tile tile, City city) {
        for (int i = 0; i < city.getTiles().size(); i++) {
            if (tile.getAdjacentTiles().contains(city.getTiles().get(i))) {
                return true;
            }
        }
        return false;
    }

    public void addTileToCity(Tile tile, City city) {
        city.addTile(tile);
        Civilization civilization = GameDatabase.getCivilizationForCity(city.getName());
        addTileToCivilization(tile, civilization);
    }

    public boolean isOperable(Tile tile, City city) {
        for (int i = 0; i < city.getTiles().size(); i++) {
            if (tile.getAdjacentTiles().contains(city.getTiles().get(i))
                    && city.getTiles().get(i).getSettler() != null) {
                return true;
            }
        }
        return false;
    }

    public boolean isAmountValidForProduction(int amount) {
        return isAmountValid(amount);
    }

    public void addProduction(String cityName, int amount) {
        GameDatabase.getCityByName(cityName).addProduction(amount);
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

    public ArrayList<Citizen> getListOfUnemployedCitizens(City city) {
        ArrayList<Citizen> citizenArrayList = new ArrayList<>();
        for (Citizen citizen : city.getCitizens()) {
            citizenArrayList.add(citizen);
        }
        return citizenArrayList;
    }

    public void pauseProject(Worker worker, int x, int y) {
        Tile tile = GameDatabase.getTileByXAndY(x, y);
        if (worker.isAssigned() && !worker.isMoving() && worker.isLocked() && tile != null) {
            worker.setIsAssigned(false);
            tile.setIsGettingWorkedOn(false);
        }
    }

    public boolean assignNewProject(Worker worker, String type) {
        Tile tile = GameDatabase.getTileByXAndY(worker.getX(), worker.getY());
        City city = GameDatabase.getCityByXAndY(worker.getX(), worker.getY());
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
                        isPossible = removeFeature(worker);
                        break;
                    case 13:
                        isPossible = removeRoad(worker);
                        break;
                    case 14:
                        isPossible = removeRailroad(worker);
                        break;
                    case 15:
                        isPossible = makeRepair(worker);
                        break;
                }
            }
            if (isPossible) {
                tile.setIsGettingWorkedOn(true);
                city.setIsGettingWorkedOn(true);
                return true;
            }
        }
        return false;
    }

    public boolean removeFeature(Worker worker) {
        Tile tile = GameDatabase.getTileByXAndY(worker.getX(), worker.getY());
        if (tile.getBaseTerrain().getFeature() != null
                && (tile.getBaseTerrain().getFeature().equals("DenseJungle")
                || tile.getBaseTerrain().getFeature().equals("Prairie")
                || tile.getBaseTerrain().getFeature().equals("Jungle"))) {
            return true;
        }
        worker.setIndexOfProject(-1);
        worker.setIsAssigned(false);
        worker.setTypeOfWork("");
        return false;
    }

    public boolean removeRailroad(Worker worker) {
        Tile tile = GameDatabase.getTileByXAndY(worker.getX(), worker.getY());
        if (tile.hasRailroad()) {
            return true;
        }
        worker.setIndexOfProject(-1);
        worker.setIsAssigned(false);
        worker.setTypeOfWork("");
        return false;
    }

    public boolean makeRailRoad(Worker worker) {
        Tile tile = GameDatabase.getTileByXAndY(worker.getX(), worker.getY());
        Civilization civilization = GameDatabase.getCivilizationByTile(tile);
        if (civilization.isTechnologyInCivilization("SteamPower")
                && !tile.hasRailroad()
                && !tile.getBaseTerrainType().equals("Ice")
                && !tile.getBaseTerrainType().equals("Ocean")
                && !tile.getBaseTerrainType().equals("Mountain")) {
            worker.setIndexOfProject(1);
            worker.setIsAssigned(true);
            worker.setTypeOfWork("Railroad");
            return true;
        }
        worker.setIndexOfProject(-1);
        worker.setIsAssigned(false);
        worker.setTypeOfWork("");
        return false;
    }

    public boolean removeRoad(Worker worker) {
        Tile tile = GameDatabase.getTileByXAndY(worker.getX(), worker.getY());
        if (tile.hasRoad()) {
            return true;
        }
        worker.setIndexOfProject(-1);
        worker.setIsAssigned(false);
        worker.setTypeOfWork("");
        return false;
    }

    public boolean makeImprovement(Worker worker) {
        Tile tile = GameDatabase.getTileByXAndY(worker.getX(), worker.getY());
        Civilization civilization = GameDatabase.getCivilizationByTile(tile);
        Improvement improvement = new Improvement(worker.getTypeOfWork());
        boolean isImprovementInTile = tile.isImprovementForThisTile(worker.getTypeOfWork());
        if (civilization == null
                || !civilization.isTechnologyInCivilization(improvement.getRequiredTechnology().getName())
                || !isImprovementInTile) {
            worker.setIndexOfProject(-1);
            worker.setIsAssigned(false);
            worker.setTypeOfWork("");
            return false;
        }
        return true;
    }

    public boolean makeRepair(Worker worker) {
        Tile tile = GameDatabase.getTileByXAndY(worker.getX(), worker.getY());
        if (worker.getIndexOfProject() == 15 && tile.isRaided()) {
            return true;
        }
        worker.setIndexOfProject(-1);
        worker.setIsAssigned(false);
        worker.setTypeOfWork("");
        return false;
    }

    public boolean makeFarm(Worker worker) {
        Tile tile = GameDatabase.getTileByXAndY(worker.getX(), worker.getY());
        Civilization civilization = GameDatabase.getCivilizationByTile(tile);
        if (civilization.isTechnologyInCivilization("Agriculture")
                && !tile.getBaseTerrainType().equals("Ice")
                && tile.getBaseTerrain().getFeature() != null
                && (tile.getBaseTerrain().getFeature().getType().equals("Jungle") && civilization.isTechnologyInCivilization("Mining")
                || tile.getBaseTerrain().getFeature().getType().equals("DenseJungle") && civilization.isTechnologyInCivilization("BronzeWorking")
                || tile.getBaseTerrain().getFeature().getType().equals("Prairie") && civilization.isTechnologyInCivilization("Masonry"))) {
            worker.setIndexOfProject(Worker.workToIndex.get("Farm"));
            worker.setIsAssigned(true);
            worker.setTypeOfWork("Farm");
            return true;
        }
        worker.setIndexOfProject(-1);
        worker.setIsAssigned(false);
        worker.setTypeOfWork("");
        return false;
    }

    public boolean makeMine(Worker worker) {
        Tile tile = GameDatabase.getTileByXAndY(worker.getX(), worker.getY());
        Civilization civilization = GameDatabase.getCivilizationByTile(tile);
        if (civilization.isTechnologyInCivilization("Mining")
                && !tile.getBaseTerrainType().equals("Ice")
                && (tile.getBaseTerrainType().equals("Hill"))
                && tile.getBaseTerrain().getFeature() != null
                && (tile.getBaseTerrain().getFeature().getType().equals("Jungle") && civilization.isTechnologyInCivilization("Mining")
                || tile.getBaseTerrain().getFeature().getType().equals("DenseJungle") && civilization.isTechnologyInCivilization("BronzeWorking")
                || tile.getBaseTerrain().getFeature().getType().equals("Prairie") && civilization.isTechnologyInCivilization("Masonry"))) {
            worker.setIndexOfProject(Worker.workToIndex.get("Mine"));
            worker.setIsAssigned(true);
            worker.setTypeOfWork("Mine");
            return true;
        }
        worker.setIndexOfProject(-1);
        worker.setIsAssigned(false);
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
            worker.setIsAssigned(true);
            worker.setTypeOfWork("Road");
            return true;
        }
        worker.setIndexOfProject(-1);
        worker.setIsAssigned(false);
        worker.setTypeOfWork("");
        return false;
    }

    public boolean isTileInCivilization(Tile tile, int turn) {
        Civilization civilization = GameDatabase.getCivilizationByTurn(turn);
        return civilization.isTileInCivilization(tile.getX(), tile.getY());
    }

    public void deleteUnit(Unit unit) {
        int amount = (int) ((double) 1 / 10 * unit.getCost());
        GameDatabase.getCivilizationByTile(unit.getTileOfUnit()).addGold(amount);
        unit.getTileOfUnit().removeUnit(unit);
    }


    public boolean createUnit(String unitType, int x, int y, int civilizationIndex) {
        Tile tile = GameDatabase.getTileByXAndY(x, y);
        if (GameDatabase.getCivilizationByTurn(civilizationIndex).getClearTiles().contains(tile)) {
            if (unitType.equals("Settler")
                    || unitType.equals("worker")) {
                return createNonCombatUnit(unitType, x, y, civilizationIndex);
            } else {
                createCombatUnit(unitType, x, y, civilizationIndex);
                return true;
            }
        } else {
            return false;
        }
    }

    public boolean isTileValidForCreatingUnit(int x, int y, int turn) {
        return GameDatabase.getCivilizationByTurn(turn).getClearTiles().contains(GameDatabase.getTileByXAndY(x, y));
    }

    public void createCombatUnit(String unitType, int x, int y, int civilizationIndex) {
        Soldier soldier = new Soldier(x, y, unitType, civilizationIndex);
        GameDatabase.getTileByXAndY(x, y).addUnit(soldier);
        soldier.setTileOfUnit(GameDatabase.getTileByXAndY(x, y));
    }

    public boolean isAmountValidForScore(int amount) {
        return isAmountValid(amount);
    }

    public boolean createNonCombatUnit(String unitType, int x, int y, int civilizationIndex) {
        if (GameDatabase.getCityByXAndY(x, y) != null) {
            if (unitType.equals("Settler")) {
                GameDatabase.getCityByXAndY(x, y).createSettler(x, y);
                return true;
            } else if (unitType.equals("worker")) {
                GameDatabase.getCityByXAndY(x, y).createWorker(x, y);
                return true;
            } else
                return false;
        } else {
            return false;
        }
    }

    public boolean isTileAdjacentToCivilization(Tile tile, Civilization civilization) {
        for (Tile civilizationTile : civilization.getTiles()) {
            if (civilizationTile.getAdjacentTiles().contains(tile)) {
                return true;
            }
        }
        return false;
    }

    public void addTileToCivilization(Tile tile, Civilization civilization) {
        civilization.addTile(tile);
    }

    public ArrayList<Worker> getListOfUnemployedWorker(City city) {
        ArrayList<Worker> workers = new ArrayList<>();
        if (city.getWorker() != null) workers.add(city.getWorker());
        return workers;
    }


    public ArrayList<Settler> getListOfUnemployedSettler(City city) {
        ArrayList<Settler> settlers = new ArrayList<>();
        if (city.getSettler() != null) settlers.add(city.getSettler());
        return settlers;
    }

    public boolean garrisonUnitToCity(Unit unit) {
        City city = GameDatabase.getCityByXAndY(unit.getTileOfUnit().getX(), unit.getTileOfUnit().getY());
        if (city != null) {
            city.setGarrison(unit);
            city.setPower(city.getPower() + 5);
            city.setGarrisoned(true);
            return true;
        } else {
            return false;
        }

    }

    public void pillageCurrentTile(Unit unit) {
        for (Improvement improvement : unit.getTileOfUnit().getImprovements()) {
            improvement.breakImprovement();
        }
        unit.getTileOfUnit().setRaidedModel(true);
        if (unit.getTileOfUnit().hasRoad()) {
            unit.getTileOfUnit().setRoadBroken(true);
            unit.getTileOfUnit().setRailroadBroken(true);
        }
    }

    public boolean isUnitTypeValid(String unitType) {
        for (String unit : GlobalVariables.UNITS) {
            if (unitType.equalsIgnoreCase(unit)) {
                return true;
            }
        }
        return false;
    }


    public boolean moveUnitAlongPath(Unit selectedUnit) {
        int index = 0;
        for (int i = 0; i < selectedUnit.route.size(); i++) {
            if (selectedUnit.getTileOfUnit().equals(selectedUnit.route.get(i))) {
                index = i;
                selectedUnit.moveToAdjacentTile(selectedUnit.route.get(i + 1));
                if (selectedUnit instanceof Worker) {
                    selectedUnit.route.get(i + 1).addWorker((Worker) selectedUnit);
                    selectedUnit.route.get(i).removeWorker((Worker) selectedUnit);
                } else if (selectedUnit instanceof Settler) {
                    System.out.println("this is working");
                    selectedUnit.route.get(i + 1).addSettler((Settler) selectedUnit);
                    selectedUnit.route.get(i).removeSettler((Settler) selectedUnit);
                    selectedUnit.setTileOfUnit(selectedUnit.route.get(i + 1));
                    System.out.println(i);
                } else {
                    selectedUnit.route.get(i + 1).addUnit(selectedUnit);
                }
                break;
            }

        }
        if (index + 1 == selectedUnit.route.size()) {
            this.getMovingUnits().remove(selectedUnit);
            return true;
        }
        return false;
    }


    public boolean isTileInAnyCivilization(Tile tile) {
        for (int i = 0; i < GameDatabase.players.size(); i++) {
            if (isTileInCivilization(tile, i)) {
                return true;
            }
        }
        return false;
    }

}
