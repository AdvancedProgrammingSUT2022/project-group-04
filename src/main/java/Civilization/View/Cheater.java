package Civilization.View;

import Civilization.Controller.GameMenuController;
import Civilization.Model.GameModel;
import Civilization.Model.Worker;

public class Cheater extends Menu{

    private static final String CHEAT_TURN_BY_NAME = "turn change (?<civilizationName>\\S+)";
    private static final String CHEAT_TURN_BY_NUMBER = "turn increase (?<amount>-?\\d+)";
    private static final String CHEAT_GOLD = "gold increase (?<amount>-?\\d+)";
    private static final String CHEAT_MAKE_HAPPY = "make happy";
    private static final String CHEAT_ADD_SCIENCE = "add science (?<science>-?\\d+)";
    private static final String CHEAT_WIN = "win";
    private static final String CHEAT_ADD_CITY_HIT_POINT = "add hit point (?<amount>-?\\d+) city (?<cityName>\\S+)";
    private static final String CHEAT_ADD_UNIT_HIT_POINT = "add hit point (?<amount>-?\\d+) position (?<x>\\d+) (?<y>\\d+)";
    private static final String CHEAT_DRY_UP = "dry up (?<x>\\d+) (?<y>\\d+)";
    private static final String CHEAT_CHANGE_CAPITAL = "change capital (?<cityName>\\S+)";
    private static final String CHEAT_ADD_PRODUCTION = "add production (?<amount>-?\\d+) city (?<cityName>\\S+)";
    private static final String CHEAT_ADD_SCORE = "add score (?<amount>-?\\d+)";

    private GameMenuController gameMenuController;

    public Cheater() {
        GameMenuController gameMenuController = new GameMenuController(new GameModel());
    }

    public String run(String command) {
        if(command.equals(CHEAT_WIN)) {
            GraphicalBases.userLoggedIn();
            return "Success!";
        } else {
            return "Error: Command \"" + command + "\" is Invalid. Press Ctrl+Shift+R to Restart the Terminal.";
        }
    }
}
