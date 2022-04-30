package View;

import Controller.GameMenuController;
import Database.GameDatabase;
import Database.GlobalVariables;
import Model.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu extends Menu {

    private GameMenuController gameMenuController;
    int numberOfPlayers;
    int turn;
    Unit unitSelected;
    City citySelected;

    //Commands
    private static final String MAP_SHOW_POSITION = "map show (?<x>\\d+) (?<y>\\d+)";
    private static final String MAP_SHOW_CITY = "map show (?<cityName>.+)";
    private static final String SELECT_COMBAT = "select combat (?<x>\\d+) (?<y>\\d+)";
    private static final String SELECT_NONCOMBAT = "select noncombat (?<x>\\d+) (?<y>\\d+)";
    private static final String SHOW_TURN = "show turn";
    private static final String UNIT_MOVE_TO = "unit moveto (?<x>\\d+) (?<y>\\d+)";
    private static final String UNIT_SLEEP = "unit sleep";
    private static final String UNIT_ALERT = "unit alert";
    private static final String UNIT_FORTIFY = "unit fortify";
    private static final String UNIT_FORTIFY_HEAL = "unit fortify heal";
    private static final String UNIT_GARRISON = "unit garrison";
    private static final String UNIT_SETUP_RANGE = "unit setup range";
    private static final String UNIT_ATTACK_POSITION = "unit attack (?<x>\\d+) (?<y>\\d+)";
    private static final String UNIT_FOUND_CITY = "unit found city";
    private static final String CANCEL_MISSION = "unit cancel mission";
    private static final String UNIT_WAKE = "unit wake";
    private static final String UNIT_DELETE = "unit delete";
    private static final String UNIT_CANCEL_MISSION = "unit cancel mission";
    private static final String UNIT_BUILD_ROAD = "unit build road";
    private static final String UNIT_BUILD_RAILROAD = "unit build railraod";
    private static final String UNIT_BUILD_FARM = "unit build farm";
    private static final String UNIT_BUILD_MINE = "unit build mine";
    private static final String UNIT_BUILD_TRADINGPOST = "unit build tradingpost";
    private static final String UNIT_BUILD_LUMBERMILL = "unit build lumbermill";
    private static final String UNIT_BUILD_PASTURE = "unit build pasture";
    private static final String UNIT_BUILD_CAMP = "unit build camp";
    private static final String UNIT_BUILD_PLANTATION = "unit build plantation";
    private static final String UNIT_BUILD_QUARRY = "unit build quarry";
    private static final String UNIT_REMOVE_JUNGLE = "unit remove jungle";
    private static final String UNIT_REMOVE_ROUTE = "unit remove route";
    private static final String UNIT_REPAIR = "unit repair";
    private static final String MAP_MOVE = "map move (?<direction>\\S+)( (?<c>\\d+))?";
    private static final String SELECT_CITY_BY_POSITION = "select city (?<x>\\d+) (?<y>\\d+)";
    private static final String SELECT_CITY_BY_NAME = "select city (?<cityName>\\S+)";
    private static final String BUY_BUILDING = "building --buy";
    private static final String BUILD_BUILDING = "building --build";
    private static final String SEND_MESSAGE = "to (?<Nickname>\\S+) send (?<Text>.+)";

    //Cheat
    private static final String CHEAT_TURN_BY_NAME = "turn change (?<civilizationName>\\S+)";
    private static final String CHEAT_TURN_BY_NUMBER = "turn increase (?<amount>-?\\d+)";
    private static final String CHEAT_GOLD = "gold increase (?<amount>-?\\d+)";

    //Info
    private static final String INFO_CITY = "info city";
    private static final String VALID_BUILDINGS = "valid buildings";
    private static final String INFO_DEMOGRAPHY = "info demography";
    private static final String INFO_RESEARCH = "info research";
    private static final String INFO_NOTIFICATION = "info notification";


    public GameMenu(GameMenuController gameMenuController) {
        this.gameMenuController = gameMenuController;
        this.turn = 0;
        this.citySelected = null;
        this.unitSelected = null;
    }

    public void run(Scanner scanner) {
        String command;
        Info info = Info.getInstance();
        while (true) {
            numberOfPlayers = GameDatabase.players.size();
            Matcher matcher;
            command = scanner.nextLine();
            if (command.equals("menu exit")) {
                break;
            } else if ((matcher = getCommandMatcher(command, MENU_SHOW)) != null) {
                System.out.println(menuShow(matcher));
            } else if ((matcher = getCommandMatcher(command, MENU_ENTER)) != null) {
                System.out.println(menuEnter(matcher));
            } else if ((matcher = getCommandMatcher(command, MENU_EXIT)) != null) {
                System.out.println(menuExit(matcher));
            } else if ((matcher = getCommandMatcher(command, MAP_SHOW_POSITION)) != null) {
                String result = mapShowPosition(matcher);
                if (result == null) {
                    printMap(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y")));
                    this.gameMenuController.x = Integer.parseInt(matcher.group("x"));
                    this.gameMenuController.y = Integer.parseInt(matcher.group("y"));
                } else {
                    System.out.println(result);
                }
            } else if ((matcher = getCommandMatcher(command, MAP_SHOW_CITY)) != null) {
                String result = mapShowCity(matcher);
                if (result == null) {
                    printMap(GameDatabase.getCityByName(matcher.group("cityName")).getX(), GameDatabase.getCityByName(matcher.group("cityName")).getY());
                    this.gameMenuController.x = GameDatabase.getCityByName(matcher.group("cityName")).getX();
                    this.gameMenuController.y = GameDatabase.getCityByName(matcher.group("cityName")).getY();
                } else {
                    System.out.println(result);
                }
            } else if ((matcher = getCommandMatcher(command, SELECT_COMBAT)) != null) {
                String result = selectCombat(matcher);
                if (result.startsWith("unit")) {
                    unitSelected = this.gameMenuController.selectCombatUnit(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y")));
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, SELECT_NONCOMBAT)) != null) {
                String result = selectNonCombat(matcher);
                if (result.startsWith("unit")) {
                    unitSelected = this.gameMenuController.selectCombatUnit(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y")));
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, CHEAT_TURN_BY_NAME)) != null) {
                String result = changeTurn(matcher);
                if (result.startsWith("now")) {
                    turn = GameDatabase.getCivilizationIndex(matcher.group("civilizationName"));
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, CHEAT_TURN_BY_NUMBER)) != null) {
                String result = changeTurnByNumber(matcher);
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, SHOW_TURN)) != null) {
                System.out.println(GameDatabase.players.get(turn).getNickname());
            } else if ((matcher = getCommandMatcher(command, UNIT_MOVE_TO)) != null) {
                String result = unitMoveTo(matcher);
                if (result.startsWith("unit")) {
                    unitSelected = null;
                    turn = nextTurn();
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, UNIT_SLEEP)) != null) {
                String result = unitSleep();
                if (result.startsWith("unit")){
                    unitSelected = null;
                    turn = nextTurn();
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, UNIT_ALERT)) != null) {
                String result = unitAlert();
                if (result.startsWith("unit")){
                    unitSelected = null;
                    turn = nextTurn();
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, UNIT_FORTIFY)) != null) {
                String result = unitFortify();
                if (result.startsWith("unit")){
                    unitSelected = null;
                    turn = nextTurn();
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, UNIT_FORTIFY_HEAL)) != null) {
                String result = unitFortifyHeal();
                if (result.startsWith("unit")){
                    unitSelected = null;
                    turn = nextTurn();
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, UNIT_GARRISON)) != null) {
                //TODO...
            } else if ((matcher = getCommandMatcher(command, UNIT_SETUP_RANGE)) != null) {
                //TODO...
            } else if ((matcher = getCommandMatcher(command, UNIT_ATTACK_POSITION)) != null) {
                //TODO...
            } else if ((matcher = getCommandMatcher(command, UNIT_FOUND_CITY)) != null) {
                String result = unitFoundCity();
                if (result.startsWith("unit")){
                    unitSelected = null;
                    turn = nextTurn();
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, UNIT_CANCEL_MISSION)) != null) {
                String result = unitCancelMission();
                if (result.startsWith("unit")){
                    unitSelected = null;
                    turn = nextTurn();
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, UNIT_WAKE)) != null) {
                String result = unitWake();
                if (result.startsWith("unit")){
                    unitSelected = null;
                    turn = nextTurn();
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, UNIT_DELETE)) != null) {
                String result = unitDelete();
                if (result.startsWith("unit")){
                    unitSelected = null;
                    turn = nextTurn();
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, UNIT_BUILD_ROAD)) != null) {
                Improvement Road = new Improvement("Road");
                String result = unitBuild(Road);
                if (result.startsWith("unit")){
                    unitSelected = null;
                    turn = nextTurn();
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, UNIT_BUILD_RAILROAD)) != null) {
                Improvement RailRoad = new Improvement("RailRoad");
                String result = unitBuild(RailRoad);
                if (result.startsWith("unit")){
                    unitSelected = null;
                    turn = nextTurn();
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, UNIT_BUILD_MINE)) != null) {
                Improvement Mine = new Improvement("Mine");
                String result = unitBuild(Mine);
                if (result.startsWith("unit")){
                    unitSelected = null;
                    turn = nextTurn();
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, UNIT_BUILD_TRADINGPOST)) != null) {
                Improvement TradingPost = new Improvement("TradingPost");
                String result = unitBuild(TradingPost);
                if (result.startsWith("unit")){
                    unitSelected = null;
                    turn = nextTurn();
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, UNIT_BUILD_LUMBERMILL)) != null) {
                Improvement LumberMill = new Improvement("LumberMill");
                String result = unitBuild(LumberMill);
                if (result.startsWith("unit")){
                    unitSelected = null;
                    turn = nextTurn();
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, UNIT_BUILD_PASTURE)) != null) {
                Improvement Pasture = new Improvement("Pasture");
                String result = unitBuild(Pasture);
                if (result.startsWith("unit")){
                    unitSelected = null;
                    turn = nextTurn();
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, UNIT_BUILD_CAMP)) != null) {
                Improvement Camp = new Improvement("Camp");
                String result = unitBuild(Camp);
                if (result.startsWith("unit")){
                    unitSelected = null;
                    turn = nextTurn();
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, UNIT_BUILD_PLANTATION)) != null) {
                Improvement Plantation = new Improvement("Plantation");
                String result = unitBuild(Plantation);
                if (result.startsWith("unit")){
                    unitSelected = null;
                    turn = nextTurn();
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, UNIT_BUILD_QUARRY)) != null) {
                Improvement Quarry = new Improvement("Quarry");
                String result = unitBuild(Quarry);
                if (result.startsWith("unit")){
                    unitSelected = null;
                    turn = nextTurn();
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, UNIT_REMOVE_JUNGLE)) != null) {
                String result = unitRemoveJungle();
                if (result.startsWith("unit")){
                    unitSelected = null;
                    turn = nextTurn();
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, UNIT_REMOVE_ROUTE)) != null) {
                String result = unitRemoveRoute();
                if (result.startsWith("unit")){
                    unitSelected = null;
                    turn = nextTurn();
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, UNIT_REPAIR)) != null) {
                String result = unitRepair();
                if (result.startsWith("unit")){
                    unitSelected = null;
                    turn = nextTurn();
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, MAP_MOVE)) != null) {
                String result = mapMove(matcher);
                if (result == null) {
                    printMap(this.gameMenuController.x, this.gameMenuController.y);
                } else {
                    System.out.println(result);
                }
            } else if ((matcher = getCommandMatcher(command, SELECT_CITY_BY_NAME)) != null) {
                String result = citySelectByName(matcher);
                if (result == null) {
                    citySelected = GameDatabase.getCityByName(matcher.group("cityName"));
                    System.out.println(citySelected);
                } else {
                    System.out.println(result);
                }
            } else if ((matcher = getCommandMatcher(command, SELECT_CITY_BY_POSITION)) != null) {
                String result = citySelectByPosition(matcher);
                if (result == null) {
                    citySelected = GameDatabase.getCityByXAndY(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y")));
                    System.out.println(citySelected);
                } else {
                    System.out.println(result);
                }
            } else if ((matcher = getCommandMatcher(command, CHEAT_GOLD)) != null) {
                System.out.println(cheatGold(matcher));

            } else if ((matcher = getCommandMatcher(command, INFO_CITY)) != null) {
                info.infoCity(turn);
            } else if ((matcher = getCommandMatcher(command, VALID_BUILDINGS)) != null) {
                String result = validBuildings(scanner);
                if (result == null) {
                    turn = nextTurn();
                } else {
                    System.out.println(result);
                }

            } else if ((matcher = getCommandMatcher(command, INFO_DEMOGRAPHY)) != null) {
                System.out.println(info.infoDemography(turn));
            } else if ((matcher = getCommandMatcher(command, INFO_RESEARCH)) != null) {
                info.infoResearch(turn, scanner);
            } else if ((matcher = getCommandMatcher(command, SEND_MESSAGE)) != null) {
                System.out.println(sendMessage(matcher));
            } else if ((matcher = getCommandMatcher(command, INFO_NOTIFICATION)) != null) {
                info.infoNotification(turn);
            } else {
                System.out.println("invalid command");
            }
        }
    }

    @Override
    public String menuShow(Matcher matcher) {
        return "Game Menu";
    }

    private String menuEnter(Matcher matcher) {
        return "menu navigation is not possible";
    }

    private String menuExit(Matcher matcher) {
        return "you must finish the game to exit";
    }

    private String mapShowPosition(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        if (!this.gameMenuController.isPositionValid(x, y)) {
            return "position is not valid";
        }
        return null;
    }

    private String mapShowCity(Matcher matcher) {
        String cityName = matcher.group("cityName");
        if (!this.gameMenuController.isCityValid(cityName)) {
            return "selected city is not valid";
        }
        return null;
    }

    private String selectCombat(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        if (!this.gameMenuController.isPositionValid(x, y)) {
            return "position is not valid";
        }
        if (!this.gameMenuController.isCombatUnitInThisPosition(x, y)) {
            return "no combat unit";
        }
        unitSelected = this.gameMenuController.selectCombatUnit(x, y);
        System.out.println(unitSelected);
        return "unit selected";
    }

    private String selectNonCombat(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        if (!this.gameMenuController.isPositionValid(x, y)) {
            return "position is not valid";
        }
        if (!this.gameMenuController.isNonCombatUnitInThisPosition(x, y)) {
            return "no combat unit";
        }
        unitSelected = this.gameMenuController.selectNonCombatUnit(x, y);
        System.out.println(unitSelected);
        return "unit selected";
    }

    private String changeTurn(Matcher matcher) {
        String civilizationName = matcher.group("civilizationName");
        if (!this.gameMenuController.isCivilizationValid(civilizationName)) {
            return "there is no player with nickname " + civilizationName;
        }
        if (!this.gameMenuController.isCheatForTurn(civilizationName, turn)) {
            return "there is already your turn!";
        }
        return "now it's your turn!";
    }

    private String changeTurnByNumber(Matcher matcher) {
        int amount = Integer.parseInt(matcher.group("amount"));
        if (!this.gameMenuController.isAmountValidForTurn(amount)) {
            return "invalid turn";
        }
        for (int i = 0; i < amount; i++) {
            turn = nextTurn();
        }
        return "now it's " + GameDatabase.players.get(turn+amount).getNickname() + " turn!";

    }

    private String unitMoveTo(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        if (unitSelected == null) {
            return "you must select a unit first";
        } else if (!gameMenuController.isUnitForThisCivilization(turn % numberOfPlayers, unitSelected)) {
            return "this unit is not for you";
        } else if (!this.gameMenuController.isDestinationOkForMove(unitSelected, x, y)) {
            return "there are two units with one type in a tile";
        } else if (!unitSelected.moveUnitFromTo(unitSelected, unitSelected.getTileOfUnit(), GameDatabase.getTileByXAndY(x, y))) {
            return "invalid to move";
        } else {
            unitSelected.moveUnitFromTo(unitSelected, unitSelected.getTileOfUnit(), GameDatabase.getTileByXAndY(x, y));
        }
        return "unit moved to " + Integer.toString(x) + " and " + Integer.toString(y);
    }

    private String unitSleep() {
        if (unitSelected == null) {
            return "you must select a unit first";
        } else if (!gameMenuController.isUnitForThisCivilization(turn % numberOfPlayers, unitSelected)) {
            return "this unit is not for you";
        } else {
            unitSelected.setSleeping(true);
        }
        return "unit slept";
    }

    private String unitAlert() {
        if (unitSelected == null) {
            return "you must select a unit first";
        } else if (!gameMenuController.isUnitForThisCivilization(turn % numberOfPlayers, unitSelected)) {
            return "this unit is not for you";
        } else if(!unitSelected.isCombatUnit()) {
            return "this is not a combat unit";
        } else {
            unitSelected.setReady(true);
        }
        return "unit is ready";
    }

    private String unitFortify() {
        if (unitSelected == null) {
            return "you must select a unit first";
        } else if (!gameMenuController.isUnitForThisCivilization(turn % numberOfPlayers, unitSelected)) {
            return "this unit is not for you";
        } else if(!unitSelected.isCombatUnit()) {
            return "this is not a combat unit";
        } else {
            unitSelected.fortify();
        }
        return "unit fortified";
    }

    private String unitFortifyHeal(){
        if (unitSelected == null) {
            return "you must select a unit first";
        } else if (!gameMenuController.isUnitForThisCivilization(turn % numberOfPlayers, unitSelected)) {
            return "this unit is not for you";
        } else if(!unitSelected.isCombatUnit()) {
            return "this is not a combat unit";
        } else {
            unitSelected.fortifyHeal();
        }
        return "unit fortifyHealed";
    }

    private String unitFoundCity(){
        if (unitSelected == null) {
            return "you must select a unit first";
        } else if (!gameMenuController.isUnitForThisCivilization(turn % numberOfPlayers, unitSelected)) {
            return "this unit is not for you";
        } else if(unitSelected.isCombatUnit()) {
            return "this is not a settler unit";
        } else {
            unitSelected.createCity(unitSelected.getX(), unitSelected.getY());
        }
        return "unit found city";

    }

    private String unitCancelMission(){
        return null;
    }
    private String unitWake(){
        if (unitSelected == null) {
            return "you must select a unit first";
        } else if (!gameMenuController.isUnitForThisCivilization(turn % numberOfPlayers, unitSelected)) {
            return "this unit is not for you";
        } else {
            unitSelected.setSleeping(false);
        }
        return "unit awakened";
    }
    private String unitAttack(){
        //TODO...
        return null;
    }
    private String unitGarrison(){
        //TODO...
        return null;
    }
    private String unitSetupRanged(){
        //TODO...
        return null;
    }
    private String unitDelete(){
        //TODO...
        return null;
    }
    private String unitBuild(Improvement improvement){
        //TODO...
        return null;
    }
    private String unitRemoveJungle(){
        //TODO...
        return null;
    }
    private String unitRemoveRoute(){
        //TODO...
        return null;
    }
    private String unitRepair(){
        //TODO...
        return null;
    }

    private String mapMove(Matcher matcher) {
        String direction = matcher.group("direction");
        int groupCount = matcher.groupCount();
        int c = this.gameMenuController.c;
        if (matcher.group("c") != null) {
            c = Integer.parseInt(matcher.group("c"));
        }
        if (!this.gameMenuController.isDirectionForMapValid(direction)) {
            return "invalid direction";
        }
        int x = this.gameMenuController.x + this.gameMenuController.directionX.get(direction) * c;
        int y = this.gameMenuController.y + this.gameMenuController.directionY.get(direction) * c;
        if (!this.gameMenuController.isPositionValid(x, y)) {
            return "position is not valid";
        }
        this.gameMenuController.x = x;
        this.gameMenuController.y = y;
        return null;

    }

    private String citySelectByName(Matcher matcher) {
        String cityName = matcher.group("cityName");
        if (!this.gameMenuController.isCityValid(cityName)) {
            return "invalid city";
        }
        return null;
    }

    private String citySelectByPosition(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        if (!this.gameMenuController.isPositionValid(x, y)) {
            return "invalid position";
        }
        if (!this.gameMenuController.isCityPositionValid(x, y)) {
            return "no city in position " + Integer.toString(x) + " and " + Integer.toString(y);
        }
        return null;
    }

    private String cheatGold(Matcher matcher) {
        int amount = Integer.parseInt(matcher.group("amount"));
        if (!this.gameMenuController.isAmountValidForGold(amount)) {
            return "invalid amount";
        }
        GameDatabase.players.get(turn).addGold(amount);
        return "Now you have " + Integer.toString(GameDatabase.players.get(turn).getGold()) + " golds.";
    }


    private String validBuildings(Scanner scanner) {
        if (citySelected == null) {
            return "you must select a city first";
        }
        if (!gameMenuController.isCityForThisCivilization(turn % numberOfPlayers, citySelected)) {
            return "this city is not for you";
        }
        ArrayList<Building> validBuildings = new ArrayList<Building>();
        int counter = 1;
        for (String buildingName : GlobalVariables.BUILDINGS) {
            Building building = new Building(buildingName);
            if (building.isBuildingValidForCivilization(GameDatabase.players.get(turn), citySelected)) {
                validBuildings.add(building);
                System.out.println(Integer.toString(counter) + "- " + building.getName() + " | cost: " + Integer.toString(building.getCost()));
                counter++;
            }
        }
        if (validBuildings.size() == 0) {
            return "poor civilization! you don't have any valid buildings!!!";
        }
        String input;
        int index;
        while (true) {
            input = scanner.nextLine();
            if (input.equals("EXIT")) {
                return "EXIT building menu";
            }
            if (!input.matches("\\d+")) {
                System.out.println("please inter a number");
            } else {
                if (Integer.parseInt(input) > 0 && Integer.parseInt(input) <= counter) {
                    index = Integer.parseInt(input);
                    break;
                }
                System.out.println("invalid number");
            }
        }
        index--;
        System.out.println("do you want build building " + validBuildings.get(index).getName() + " or buy?");
        boolean build = true;
        while (true) {
            input = scanner.nextLine();
            if (input.equals("EXIT")) {
                return "EXIT building menu";
            }
            if (input.equals(BUY_BUILDING)) {
                build = false;
                break;
            } else if (!input.equals(BUILD_BUILDING)) {
                System.out.println("buy or build?");
            } else {
                break;
            }
        }
        System.out.println("Great!");
        citySelected.buildBuilding(validBuildings.get(index), build);
        return null;
    }

    private String sendMessage(Matcher matcher) {
        String nickname = matcher.group("Nickname");
        String text = matcher.group("Text");
        if(!this.gameMenuController.isCivilizationValid(nickname)) {
            return "there is no civilization with this nickname";
        }
        if(GameDatabase.players.get(turn).getNickname().equals(nickname)) {
            return "you can't send a message for yourself!";
        }
        Notification notification = new Notification(GameDatabase.players.get(turn).getNickname(), nickname, text);
        Notification.addNotification(notification);
        return "Message sent.";
    }

    private int nextTurn() {
        GameDatabase.nextTurn();
        if (turn != this.numberOfPlayers - 1) {
            return turn++;
        }
        return 0;
    }

    public void printMap(int mainX, int mainY) {
        String[][][] linesOfHexagons = new String[5][6][6];
        for (int i = -2; i < 3; i++) {
            for (int j = -2; j < 4; j++) {
                int x = mainX + i;
                int y = mainY + j;
                Tile tile = GameDatabase.getTileByXAndY(x, y);
                if (tile == null || (i == 2 && (mainY % 2 == 1 || y % 2 == mainY % 2)) || i == -2
                        || (i == -1 && mainY % 2 == 0 && y % 2 != mainY % 2)) {
                    for (int k = 0; k < 3; k++) {
                        linesOfHexagons[i + 2][j + 2][k] = Colors.ANSI_RESET;
                        for (int k1 = 0; k1 < 2 * (k + 4); k1++)
                            linesOfHexagons[i + 2][j + 2][k] += " ";
                    }
                    for (int k = 3; k < 6; k++) {
                        linesOfHexagons[i + 2][j + 2][k] = Colors.ANSI_RESET;
                        for (int k1 = 0; k1 < 2 * (9 - k); k1++)
                            linesOfHexagons[i + 2][j + 2][k] += " ";
                    }
                } else {
                    //TODO set lines of hexagons with format
                    String colorOfHexagon = "";
                    if (tile.getBaseTerrainType().equals("Desert") || tile.getBaseTerrainType().equals("Meadow")) {
                        colorOfHexagon = Colors.ANSI_YELLOW_BACKGROUND;
                    } else if (tile.getBaseTerrainType().equals("Plain") || tile.getBaseTerrainType().equals("Hill")) {
                        colorOfHexagon = Colors.ANSI_GREEN_BACKGROUND;
                    } else if (tile.getBaseTerrainType().equals("Mountain") || tile.getBaseTerrainType().equals("Snow")) {
                        colorOfHexagon = Colors.ANSI_WHITE_BACKGROUND;
                    } else if (tile.getBaseTerrainType().equals("Ocean")) {
                        colorOfHexagon = Colors.ANSI_BLUE_BACKGROUND;
                    } else if (tile.getBaseTerrainType().equals("Tundra")) {
                        colorOfHexagon = Colors.ANSI_RED_BACKGROUND;
                    }
                    linesOfHexagons[i + 2][j + 2][0] = Colors.ANSI_RESET + colorOfHexagon +
                            (tile.getBaseTerrainType().substring(0, 4) + "       ").substring(0, 8);
                    //finding the civilization of tile
                    int flag = -1;
                    for (int k = 0; k < GameDatabase.players.size(); k++) {
                        if (GameDatabase.players.get(k).isTileInCivilization(x, y)) {
                            flag = k;
                            break;
                        }
                    }
                    if (flag == -1) {
                        linesOfHexagons[i + 2][j + 2][1] = Colors.ANSI_RESET + colorOfHexagon +
                                ("                              ").substring(0, 10);
                    } else {
                        linesOfHexagons[i + 2][j + 2][1] = Colors.ANSI_RESET + colorOfHexagon
                                + (GameDatabase.players.get(flag).getNickname() + "             ").substring(0, 10);
                    }
                    //
                    linesOfHexagons[i + 2][j + 2][2] = Colors.ANSI_RESET + colorOfHexagon +
                            ("    " + x + "," + y + "        ").substring(0, 12);//coordinates
                    //
                    linesOfHexagons[i + 2][j + 2][3] = Colors.ANSI_RESET + colorOfHexagon +
                            ("                ").substring(0, 12);//TODO Unit to print
                    //showing Features
                    String allFeatures = " ";
                    for (int k = 0; k < tile.getBaseTerrain().getPossibleFeatures().size(); k++) {
                        allFeatures += tile.getBaseTerrain().getPossibleFeatures().get(k).getType().substring(0, 2) + "-";
                    }
                    linesOfHexagons[i + 2][j + 2][4] = Colors.ANSI_RESET + colorOfHexagon +
                            (allFeatures + "           ").substring(0, 10);
                    //
                    linesOfHexagons[i + 2][j + 2][5] = Colors.ANSI_RESET + colorOfHexagon + "________";
                    if (tile.isRiverByNumberOfEdge(3)) {
                        linesOfHexagons[i + 2][j + 2][5] = Colors.ANSI_RESET + Colors.ANSI_CYAN_BACKGROUND + "________";
                    }
                }
            }
        }
        String[] lines = new String[30];
        int[] counterLine = {0, 3, 0, 3, 0, 3};
        int[] counterOfHex = new int[6];
        counterOfHex[0] = 1;
        counterOfHex[2] = 1;
        counterOfHex[4] = 1;
        if (mainY % 2 == 1) {
            counterOfHex[1] = 0;
            counterOfHex[3] = 0;
            counterOfHex[5] = 0;
        } else {
            counterOfHex[1] = 1;
            counterOfHex[3] = 1;
            counterOfHex[5] = 1;
        }
        for (int i = 1; i < 22; i++) {
            int numberOfSpace = (i % 6);
            String[] marz = new String[2];
            marz[0] = "\\";
            marz[1] = "/";
            int flag;
            if (numberOfSpace < 4 && numberOfSpace > 0) {
                flag = 1;
            } else {
                flag = 0;
            }
            if (numberOfSpace == 0 || numberOfSpace == 1) numberOfSpace = 3;
            else if (numberOfSpace == 2 || numberOfSpace == 5) numberOfSpace = 2;
            else if (numberOfSpace == 4 || numberOfSpace == 3) numberOfSpace = 1;
            lines[i] = Colors.ANSI_RESET;
            for (int j = 0; j < numberOfSpace; j++) {
                lines[i] += " ";
            }
            //set the i-th line
            for (int j = 0; j < 6; j++) {
                Tile tile = GameDatabase.getTileByXAndY(counterOfHex[j] + mainX - 2, mainY + j - 2);
                if ((tile != null)
                        && (flag == 1 && tile.isRiverByNumberOfEdge(5)
                        || flag == 0 && tile.isRiverByNumberOfEdge(4))) {//Condition to river
                    lines[i] += Colors.ANSI_RESET + Colors.ANSI_CYAN_BACKGROUND;
                } else {
                    lines[i] += Colors.ANSI_RESET;
                }
                lines[i] += marz[flag];
                lines[i] += linesOfHexagons[counterOfHex[j]][j][counterLine[j]];
                flag = 1 - flag;
            }
            lines[i] += Colors.ANSI_RESET + marz[flag] + "\n";
            //set arrays for next cycle
            for (int u = 0; u < 6; u++) {
                counterLine[u] += 1;
                if (counterLine[u] == 6) {
                    counterOfHex[u] += 1;
                    counterLine[u] = 0;
                }
            }
        }
        lines[0] = "";
        lines[22] = "";
        for (int i = 0; i < 23; i++) {
            System.out.print(lines[i]);
        }
    }

    // this is a comment for retards
    // this a comment for women
}
