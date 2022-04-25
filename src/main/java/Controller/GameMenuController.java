package Controller;

import Database.GameDatabase;
import Model.*;

public class GameMenuController {

    private GameModel gameModel;

    public GameMenuController(GameModel gameModel) {
        this.gameModel = gameModel;
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
        if (city == null) {
            return false;
        }
        return true;
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
}
