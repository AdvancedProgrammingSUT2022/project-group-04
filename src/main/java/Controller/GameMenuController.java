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
            if((GameDatabase.map.get(i).getX() == x)
                && (GameDatabase.map.get(i).getY() == y)) {
                return true;
            }
        }
        return false;
    }

    public boolean isCityValid(String cityName) {
        City city = GameDatabase.getCityByName(cityName);
        if(city == null) {
            return false;
        }
        return true;
    }

    public boolean isCombatUnitInThisPosition(int turn, int x, int y) {
        for (int i = 0; i < GameDatabase.players.get(turn).getTiles().size(); i++) {
            for (int j = 0; j < GameDatabase.players.get(turn).getTiles().get(i).getUnits().size(); j++) {
                if(GameDatabase.players.get(turn).getTiles().get(i).getUnits().get(j) instanceof Soldier) {
                    if((GameDatabase.players.get(turn).getTiles().get(i).getUnits().get(j).getX() == x)
                        && (GameDatabase.players.get(turn).getTiles().get(i).getUnits().get(j).getY() == y)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Unit selectCombatUnit(int turn, int x, int y) {
        for (int i = 0; i < GameDatabase.players.get(turn).getTiles().size(); i++) {
            for (int j = 0; j < GameDatabase.players.get(turn).getTiles().get(i).getUnits().size(); j++) {
                if(GameDatabase.players.get(turn).getTiles().get(i).getUnits().get(j) instanceof Soldier) {
                    if((GameDatabase.players.get(turn).getTiles().get(i).getUnits().get(j).getX() == x)
                            && (GameDatabase.players.get(turn).getTiles().get(i).getUnits().get(j).getY() == y)) {
                        return GameDatabase.players.get(turn).getTiles().get(i).getUnits().get(j);
                    }
                }
            }
        }
        return null;
    }

    public boolean isNonCombatUnitInThisPosition(int turn, int x, int y) {
        for (int i = 0; i < GameDatabase.players.get(turn).getTiles().size(); i++) {
            for (int j = 0; j < GameDatabase.players.get(turn).getTiles().get(i).getUnits().size(); j++) {
                if(GameDatabase.players.get(turn).getTiles().get(i).getUnits().get(j) instanceof Citizen) {
                    if((GameDatabase.players.get(turn).getTiles().get(i).getUnits().get(j).getX() == x)
                            && (GameDatabase.players.get(turn).getTiles().get(i).getUnits().get(j).getY() == y)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Unit selectNonCombatUnit(int turn, int x, int y) {
        for (int i = 0; i < GameDatabase.players.get(turn).getTiles().size(); i++) {
            for (int j = 0; j < GameDatabase.players.get(turn).getTiles().get(i).getUnits().size(); j++) {
                if(GameDatabase.players.get(turn).getTiles().get(i).getUnits().get(j) instanceof Citizen) {
                    if((GameDatabase.players.get(turn).getTiles().get(i).getUnits().get(j).getX() == x)
                            && (GameDatabase.players.get(turn).getTiles().get(i).getUnits().get(j).getY() == y)) {
                        return GameDatabase.players.get(turn).getTiles().get(i).getUnits().get(j);
                    }
                }
            }
        }
        return null;
    }
}
