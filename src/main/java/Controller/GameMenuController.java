package Controller;

import Database.GameDatabase;
import Model.City;
import Model.GameModel;

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
}
