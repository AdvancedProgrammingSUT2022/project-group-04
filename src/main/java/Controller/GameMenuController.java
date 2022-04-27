package Controller;

import Database.GameDatabase;
import Model.*;

import java.util.HashMap;

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

    public boolean isCityValid(String cityName) {
        City city = GameDatabase.getCityByName(cityName);
        return city != null;
    }

    public boolean isCombatUnitInThisPosition(int x, int y) {
        Tile tile = GameDatabase.getTileByXAndY(x, y);
        for (int i = 0; i < tile.getUnits().size(); i++) {
            if (tile.getUnits().get(i) instanceof Soldier) {
                return true;
            }
        }
        return false;
    }

    public Unit selectCombatUnit(int x, int y) {
        Tile tile = GameDatabase.getTileByXAndY(x, y);
        for (int i = 0; i < tile.getUnits().size(); i++) {
            if (tile.getUnits().get(i) instanceof Soldier) {
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
            if (tile.getUnits().get(i) instanceof Citizen) {
                return true;
            }
        }
        return false;
    }

    public Unit selectNonCombatUnit(int x, int y) {
        Tile tile = GameDatabase.getTileByXAndY(x, y);
        for (int i = 0; i < tile.getUnits().size(); i++) {
            if (tile.getUnits().get(i) instanceof Citizen) {
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
        if (unit instanceof Soldier) {
            for (Unit unit1 : GameDatabase.getTileByXAndY(x, y).getUnits()) {
                if (unit1 instanceof Soldier) {
                    return false;
                }
            }
            return true;
        } else {
            for (Unit unit1 : GameDatabase.getTileByXAndY(x, y).getUnits()) {
                if (unit1 instanceof Citizen) {
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

    public boolean isAmountValidForGold(int amount) {
        return isAmountValidForTurn(amount);
    }
}
