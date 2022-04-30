package Controller;

import Database.GameDatabase;
import Model.*;

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

    public boolean isUnitSoldier(Unit unitSelected){
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
        if(!GameDatabase.getCivilizationByTile(tile).isTechnologyForThisCivilization(improvement.getRequiredTechnology()));
        for (String terrainName : improvement.getBaseTerrainThatCanBeBuilt()) {
            if(tile.getBaseTerrain().getType().equals(terrainName)) {
                return true;
            }
        }
        for (String featureName : improvement.getTerrainFeaturesThatCanBeBuilt()) {
            if(tile.getBaseTerrain().getFeature().getType().equals(featureName)) {
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
            return true;
        } else {
            for (Unit unit1 : GameDatabase.getTileByXAndY(x, y).getUnits()) {
                if (isUnitCivilian(unit1)) {
                    return false;
                }
            }
            return true;
        }
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

    public boolean isAmountValidForTurn(int amount) {
        if (amount < 1) {
            return false;
        }
        return true;
    }

    public boolean isCityForThisCivilization(int turn, City citySelected) {
        for (City city : GameDatabase.players.get(turn).getCities()) {
            if(city.getName().equals(citySelected.getName())) {
                return true;
            }
        }
        return false;
    }

    public boolean isCityNameUnique(String cityName) {
        for (Civilization player : GameDatabase.players) {
            for (City city : player.getCities()) {
                if(city.getName().equals(cityName)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isAmountValidForGold(int amount) {
        return isAmountValidForTurn(amount);
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
}
