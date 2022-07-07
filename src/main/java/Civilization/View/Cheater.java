package Civilization.View;

import Civilization.Controller.GameMenuController;
import Civilization.Database.GameDatabase;
import Civilization.Model.GameModel;
import Civilization.Model.Worker;

import java.util.Objects;
import java.util.regex.Matcher;

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

    private int turn;

    public Cheater(int turn) {
        this.turn = turn;
        gameMenuController = new GameMenuController(new GameModel());
    }

    public String run(String command) {
        Matcher matcher;
        if(command.equals(CHEAT_WIN)) {
            GraphicalBases.userLoggedIn();
            return "Success!";
        } else if ((matcher = getCommandMatcher(command, CHEAT_TURN_BY_NAME)) != null) {
            String result = changeTurn(matcher);
            if (result.startsWith("Success!")) {
                turn = GameDatabase.getCivilizationIndex(matcher.group("civilizationName"));
            }
            return result;
        } else if ((matcher = getCommandMatcher(command, CHEAT_TURN_BY_NUMBER)) != null) {
            return changeTurnByNumber(matcher);
        } else if ((matcher = getCommandMatcher(command, CHEAT_GOLD)) != null) {
            return cheatGold(matcher);
        } else if ((getCommandMatcher(command, CHEAT_MAKE_HAPPY)) != null) {
            return makeHappy();
        } else if ((matcher = getCommandMatcher(command, CHEAT_ADD_SCIENCE)) != null) {
            return addScience(matcher);
        }  else if ((matcher = getCommandMatcher(command, CHEAT_ADD_CITY_HIT_POINT)) != null) {
            return addHitPointCity(matcher);
        } else if ((matcher = getCommandMatcher(command, CHEAT_ADD_UNIT_HIT_POINT)) != null) {
            return addHitPointUnit(matcher);
        } else if ((matcher = getCommandMatcher(command, CHEAT_CHANGE_CAPITAL)) != null) {
            return changeCapital(matcher);
        } else if ((matcher = getCommandMatcher(command, CHEAT_ADD_PRODUCTION)) != null) {
            return addProduction(matcher);
        } else if ((matcher = getCommandMatcher(command, CHEAT_ADD_SCORE)) != null) {
            return addScore(matcher);
        } else if ((matcher = getCommandMatcher(command, CHEAT_DRY_UP)) != null) {
            String result = dryUp(matcher);
            if (!result.startsWith("Error:")) {
                GameDatabase.nextTurn();
            }
            return result;
        } else {
            return "Error: Command \"" + command + "\" is Invalid.";
        }
    }

    public String dryUp(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        if (!this.gameMenuController.isPositionValid(x, y)) {
            return "Error: Invalid Position";
        }
        if (this.gameMenuController.isTileOcean(GameDatabase.getTileByXAndY(x, y))) {
            return "Error: You Can Not Dry Up an Ocean.";
        }
        if (!this.gameMenuController.tileHasRiver(Objects.requireNonNull(GameDatabase.getTileByXAndY(x, y)))) {
            return "Error: No River in This Tile";
        }
        this.gameMenuController.dryUp(x, y);
        return "Success! Now There is No River in Position " + Integer.toString(x) + " And " + Integer.toString(y) + ".";
    }

    public String addScore(Matcher matcher) {
        int score = Integer.parseInt(matcher.group("score"));
        if (!this.gameMenuController.isAmountValidForScore(score)) {
            return "Error: Invalid Amount";
        }
        this.gameMenuController.addScore(turn, score);
        return "Success! Now You Have " + Integer.toString(GameDatabase.getPlayers().get(turn).getScore()) + " Score.";
    }

    public String addProduction(Matcher matcher) {
        int amount = Integer.parseInt(matcher.group("amount"));
        String cityName = matcher.group("cityName");
        if (!this.gameMenuController.isCityValid(cityName)) {
            return "Error: Invalid City";
        }
        if (!this.gameMenuController.isAmountValidForProduction(amount)) {
            return "Error: Invalid Amount";
        }
        if (!this.gameMenuController.isCityForThisCivilization(turn, GameDatabase.getCityByName(cityName))) {
            return "Error: This City is Not For Your Civilization.";
        }
        this.gameMenuController.addProduction(cityName, amount);
        return "Success!" + Integer.toString(amount) + " Production Added to " + cityName + ".";
    }

    public String changeCapital(Matcher matcher) {
        String cityName = matcher.group("cityName");
        if (!this.gameMenuController.isCityValid(cityName)) {
            return "Error: Invalid City";
        }
        if (!this.gameMenuController.isCityForThisCivilization(turn, GameDatabase.getCityByName(cityName))) {
            return "Error: Selected City is Not For Your Civilization.";
        }
        if (this.gameMenuController.isCityCapital(cityName)) {
            return "Error: Selected City is Already Capital of Your Civilization.";
        }
        GameDatabase.getPlayers().get(turn).changeCapital(cityName);
        return "Success! Capital Changed Successfully.";
    }

    public String addHitPointUnit(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        int amount = Integer.parseInt(matcher.group("amount"));
        if (!this.gameMenuController.isAmountValidForHP(amount)) {
            return "Error: Invalid Amount";
        }
        if (this.gameMenuController.isAmountALot(amount)) {
            return "Error: Please Cheat With Another Amount of HP!";
        }
        if (!this.gameMenuController.isPositionValid(x, y)) {
            return "Error: Invalid Position";
        }
        if (!this.gameMenuController.isCombatUnitInThisPosition(x, y)) {
            return "Error: You Can't Change Hit Point of Non Combat Units.";
        }
        if (!this.gameMenuController.isUnitForThisCivilization(turn, this.gameMenuController.selectCombatUnit(x, y))) {
            return "Error: Unit in This Position is Not For Your Civilization.";
        }
        this.gameMenuController.addHP(x, y, amount);
        return "Success! " + Integer.toString(amount) + " Hit Point Added to Unit in Position " + Integer.toString(x) + " And " + Integer.toString(y) + ".";
    }

    public String addHitPointCity(Matcher matcher) {
        String cityName = matcher.group("cityName");
        int amount = Integer.parseInt(matcher.group("amount"));
        if (!this.gameMenuController.isAmountValidForHP(amount)) {
            return "Error: Invalid Amount";
        }
        if (this.gameMenuController.isAmountALot(amount)) {
            return "Error: Please Cheat With Another Amount of HP!";
        }
        if (!this.gameMenuController.isCityValid(cityName)) {
            return "Error: Invalid City";
        }
        if (!this.gameMenuController.isCityForThisCivilization(turn, GameDatabase.getCityByName(cityName))) {
            return "Error: City is Not For Your Civilization.";
        }
        this.gameMenuController.addHP(cityName, amount);
        return "Success! " + Integer.toString(amount) + " Hit Point Added to Your City." + cityName;
    }

    public String addScience(Matcher matcher) {
        int science = Integer.parseInt(matcher.group("science"));
        if (!this.gameMenuController.isAmountValidForScience(science)) {
            return "Error: Invalid Amount";
        }
        this.gameMenuController.addScience(turn, science);
        return "Success! Now You Have " + Integer.toString(GameDatabase.getPlayers().get(turn).getScience()) + " Science.";
    }

    public String makeHappy() {
        if (GameDatabase.getPlayers().get(turn).isHappy()) {
            return "Error: You Are Happy Now.";
        }
        this.gameMenuController.makeHappy(turn);
        return "Success! Now Your Happiness is 0.";
    }

    public String cheatGold(Matcher matcher) {
        int amount = Integer.parseInt(matcher.group("amount"));
        if (!this.gameMenuController.isAmountValidForGold(amount)) {
            return "Error: Invalid Amount";
        }
        this.gameMenuController.addGold(turn, amount);
        return "Success! Now You Have " + Integer.toString(GameDatabase.getPlayers().get(turn).getGold()) + " Golds.";
    }

    public String changeTurn(Matcher matcher) {
        String civilizationName = matcher.group("civilizationName");
        if (!this.gameMenuController.isCivilizationValid(civilizationName)) {
            return "Error: There is No Player With Nickname " + civilizationName + ".";
        }
        if (!this.gameMenuController.isCheatForTurn(civilizationName, turn)) {
            return "Error: There is Already Your Turn!";
        }
        GameDatabase.setTurn(GameDatabase.getCivilizationIndex(civilizationName));
        return "Success! Now it's Your Turn!";
    }

    public String changeTurnByNumber(Matcher matcher) {
        int amount = Integer.parseInt(matcher.group("amount"));
        if (!this.gameMenuController.isAmountValidForTurn(amount)){
            return "Error: Invalid Turn";
        }
        for (int i = 0; i < amount; i++) {
            GameDatabase.nextTurn();
        }
        turn = GameDatabase.getTurn();
        return "Success! Now it's " + GameDatabase.getPlayers().get(turn).getNickname() + " Turn!";
    }
}
