package View;

import Controller.GameMenuController;
import Database.GameDatabase;
import Database.GlobalVariables;
import Model.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ForkJoinWorkerThread;
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
    private static final String UNIT_BUILD_RAILROAD = "unit build railroad";
    private static final String MAP_MOVE = "map move (?<direction>\\S+)( (?<c>\\d+))?";
    private static final String SELECT_CITY_BY_POSITION = "select city (?<x>\\d+) (?<y>\\d+)";
    private static final String SELECT_CITY_BY_NAME = "select city (?<cityName>\\S+)";
    private static final String BUY_BUILDING = "building --buy";
    private static final String BUILD_BUILDING = "building --build";
    private static final String BUILD_CITY = "build city (?<cityName>\\S+) (?<x>\\d+) (?<y>\\d+)";
    private static final String SEND_MESSAGE = "to (?<Nickname>\\S+) send (?<Text>.+)";
    private static final String ADD_TILE_TO_CITY = "add tile to city (?<cityName>\\S+) (?<x>\\d+) (?<y>\\d+)";
    //improvement should be PascalCase
    private static final String UNIT_BUILD_IMPROVEMENT = "unit build (?<improvement>[a-z]+) (?<x>\\d+) (?<y>\\d+)";
    private static final String UNIT_REPAIR = "unit repair (?<typeOfRepair>\\S+) (?<x>\\d+) (?<y>\\d+)";
    private static final String UNIT_REMOVE_FEATURE = "unit remove (?<feature>\\S+) (?<x>\\d+) (?<y>\\d+)";
    private static final String REMOVE_CITIZEN_FROM_WORK = "remove citizen from work (?<x>\\d+) (?<y>\\d+)";
    private static final String LOCK_CITIZEN_TO_TILE = "lock citizen to tile (?<x>\\d+) (?<y>\\d+)";
    private static final String GET_UNEMPLOYED_SECTION_BY_COORDINATE = "get unemployed section (?<x>\\d+) (?<y>\\d+)";
    private static final String GET_UNEMPLOYED_SECTION_BY_CITY_NAME = "get unemployed section (?<cityName>[a-zA-Z]+)";

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
    private static final String INFO_MILITARY = "info military";
    private static final String INFO_UNITS = "info units";
    private static final String INFO_ECONOMY = "info economy";


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
                if (result.startsWith("unit")) {
                    unitSelected = null;
                    turn = nextTurn();
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, UNIT_ALERT)) != null) {
                String result = unitAlert();
                if (result.startsWith("unit")) {
                    unitSelected = null;
                    turn = nextTurn();
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, UNIT_FORTIFY)) != null) {
                String result = unitFortify();
                if (result.startsWith("unit")) {
                    unitSelected = null;
                    turn = nextTurn();
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, UNIT_FORTIFY_HEAL)) != null) {
                String result = unitFortifyHeal();
                if (result.startsWith("unit")) {
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
                if (result.startsWith("unit")) {
                    unitSelected = null;
                    turn = nextTurn();
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, UNIT_CANCEL_MISSION)) != null) {
                String result = unitCancelMission();
                if (result.startsWith("unit")) {
                    unitSelected = null;
                    turn = nextTurn();
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, UNIT_WAKE)) != null) {
                String result = unitWake();
                if (result.startsWith("unit")) {
                    unitSelected = null;
                    turn = nextTurn();
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, UNIT_DELETE)) != null) {
                String result = unitDelete();
                if (result.startsWith("unit")) {
                    unitSelected = null;
                    turn = nextTurn();
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, UNIT_BUILD_IMPROVEMENT)) != null) {
                String result = unitBuild(matcher);
                if (result.startsWith("worker")) {
                    unitSelected = null;
                    turn = nextTurn();
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, UNIT_REMOVE_FEATURE)) != null) {
                String result = unitRemoveFeature(matcher);
//                if (result.startsWith("unit")) {
//                    unitSelected = null;
//                    turn = nextTurn();
//                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, UNIT_REPAIR)) != null) {
                String result = unitRepair(matcher);
                if (result.startsWith("worker")) {
                    unitSelected = null;
                    turn = nextTurn();
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, REMOVE_CITIZEN_FROM_WORK)) != null) {
                String result = unitStopWork(matcher);
                if (result.startsWith("project")) {
                    unitSelected = null;
                    turn = nextTurn();
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, LOCK_CITIZEN_TO_TILE)) != null) {
                String result = lockCitizen(matcher);
                if (result.startsWith("project")) {
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
            } else if ((matcher = getCommandMatcher(command, GET_UNEMPLOYED_SECTION_BY_COORDINATE)) != null) {
                String result = unemployedSection(matcher, true);
                System.out.print(result);
            } else if ((matcher = getCommandMatcher(command, GET_UNEMPLOYED_SECTION_BY_CITY_NAME)) != null) {
                String result = unemployedSection(matcher, false);
                System.out.print(result);
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
                info.infoCity(scanner, turn);
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
            } else if ((matcher = getCommandMatcher(command, BUILD_CITY)) != null) {
                String result = buildCity(matcher);
                if (result.startsWith("city")) {
                    turn = nextTurn();
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, ADD_TILE_TO_CITY)) != null) {
                String result = getAddTileToCity(matcher);
                if (result.startsWith("tile")) {
                    turn = nextTurn();
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, SEND_MESSAGE)) != null) {
                System.out.println(sendMessage(matcher));
            } else if ((matcher = getCommandMatcher(command, INFO_NOTIFICATION)) != null) {
                info.infoNotification(turn);
            } else if ((matcher = getCommandMatcher(command, INFO_MILITARY)) != null) {
                info.infoMilitary(gameMenuController, turn);
            } else if ((matcher = getCommandMatcher(command, INFO_UNITS)) != null) {
                info.infoUnits(gameMenuController, turn, scanner);
            } else if ((matcher = getCommandMatcher(command, INFO_ECONOMY)) != null) {
                info.infoEconomy(turn, scanner);
            } else {
                System.out.println("invalid command");
            }
        }
    }

    private String lockCitizen(Matcher matcher) {
        int y = Integer.parseInt(matcher.group("y"));
        int x = Integer.parseInt(matcher.group("x"));
        Tile tile = GameDatabase.getTileByXAndY(x, y);
        if (tile == null) return "invalid tile";
        if (!gameMenuController.isTileInCivilization(tile,turn)) return "this tile ain't yours bro";
        Worker worker = tile.getAvailableWorker();
        City city = GameDatabase.getCityByXAndY(x, y);
        if (city == null) return "this tile is in no city";
        if (worker == null) worker = gameMenuController.findAvailableWorkerInCity(city);
        if (worker == null) return "no available worker to be locked to this tile";
        worker.lockTheWorker(tile);
        return "worker started locking process successfully!";
    }

    private String unemployedSection(Matcher matcher, boolean isCoordinate) {
        City city;
        if (isCoordinate) {
            int x = Integer.parseInt(matcher.group("x"));
            int y = Integer.parseInt(matcher.group("y"));
            city = GameDatabase.getCityByXAndY(x, y);
            Tile tile = GameDatabase.getTileByXAndY(x,y);
            if (!gameMenuController.isTileInCivilization(tile,turn)) return "this tile ain't yours bro\n";
        } else {
            city = GameDatabase.getCityByName(matcher.group("cityName"));
        }
        if (city == null) return "this tile is in no city\n";
        ArrayList<Worker> unemployedWorkers = gameMenuController.getListOfUnemployedWorkers(city);
        ArrayList<Settler> unemployedSettlers = gameMenuController.getListOfUnemployedSettlers(city);
        return "";
    }

    private void printUnemployedSection(ArrayList<Worker> workers, ArrayList<Settler> settlers) {
        int i = 0;
        for (Worker worker : workers) {
            System.out.println(i + "th unemployed worker: x = " + worker.getX() + ", y = " + worker.getY());
        }
        i = 0;
        for (Settler settler : settlers) {
            System.out.println(i + "th unemployed worker: x = " + settler.getX() + ", y = " + settler.getY());
        }
    }

    private String unitStopWork(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        Tile tile = GameDatabase.getTileByXAndY(x, y);
        City city = GameDatabase.getCityByXAndY(x, y);
        if (tile == null) return "invalid tile";
        if (!gameMenuController.isTileInCivilization(tile,turn)) return "this tile ain't yours bro";
        if (city == null) return "this tile is in no city";
        Worker worker = tile.getActiveWorker();
        if (!tile.getIsGettingWorkedOn() || worker == null) return "this tile isn't getting worked on";
        gameMenuController.pauseProject(worker,x,y);
        return "project stopped successfully";
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
        return "now it's " + GameDatabase.players.get(turn + amount).getNickname() + " turn!";

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
        } else if (!unitSelected.isCombatUnit()) {
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
        } else if (!unitSelected.isCombatUnit()) {
            return "this is not a combat unit";
        } else {
            unitSelected.fortify();
        }
        return "unit fortified";
    }

    private String unitFortifyHeal() {
        if (unitSelected == null) {
            return "you must select a unit first";
        } else if (!gameMenuController.isUnitForThisCivilization(turn % numberOfPlayers, unitSelected)) {
            return "this unit is not for you";
        } else if (!unitSelected.isCombatUnit()) {
            return "this is not a combat unit";
        } else {
            unitSelected.fortifyHeal();
        }
        return "unit fortifyHealed";
    }

    private String unitFoundCity() {
        if (unitSelected == null) {
            return "you must select a unit first";
        } else if (!gameMenuController.isUnitForThisCivilization(turn % numberOfPlayers, unitSelected)) {
            return "this unit is not for you";
        } else if (unitSelected.isCombatUnit()) {
            return "this is not a settler unit";
        } else {
            unitSelected.createCity(unitSelected.getX(), unitSelected.getY());
        }
        return "unit found city";

    }

    private String unitCancelMission() {
        return null;
    }

    private String unitWake() {
        if (unitSelected == null) {
            return "you must select a unit first";
        } else if (!gameMenuController.isUnitForThisCivilization(turn % numberOfPlayers, unitSelected)) {
            return "this unit is not for you";
        } else {
            unitSelected.setSleeping(false);
        }
        return "unit awakened";
    }

    private String unitAttack() {
        //TODO...
        return null;
    }

    private String unitGarrison() {
        //TODO...
        return null;
    }

    private String unitSetupRanged() {
        //TODO...
        return null;
    }

    private String unitDelete() {
        //TODO...
        return null;
    }

    private String unitBuild(Matcher matcher) {
        String improvementName = matcher.group("improvement");
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        Tile tile = GameDatabase.getTileByXAndY(x, y);
        if (!improvementName.equals("Road")
                && !improvementName.equals("Railroad")
                && !gameMenuController.isImprovementValid(improvementName)) {
            return "invalid improvement";
        }
        if (tile == null) return "invalid tile";
        if (!gameMenuController.isTileInCivilization(tile,turn)) return "this tile ain't yours bro";
        if (tile.getIsGettingWorkedOn()) return "tile has an on-going project";
        Worker worker = tile.getAvailableWorker();
        if (worker == null) return "there is no worker in this tile to do the project";
        if (gameMenuController.assignNewProject(worker,improvementName)) return "worker successfully assigned";
        return "you can't do that because either this improvement/(rail)road is already in this tile or " +
                "you don't have the pre-requisite technology";
    }

    private String unitRemoveFeature(Matcher matcher) {
        String improvementName = matcher.group("improvement");
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        Tile tile = GameDatabase.getTileByXAndY(x, y);
        if (!improvementName.equals("Road")
                && !improvementName.equals("Railroad")
                && !improvementName.equals("Jungle")
                && !improvementName.equals("DenseJungle")
                && !improvementName.equals("Prairie")) {
            return "invalid improvement";
        }
        if (tile == null) return "invalid tile";
        if (!gameMenuController.isTileInCivilization(tile,turn)) return "this tile ain't yours bro";
        if (tile.getIsGettingWorkedOn()) return "tile has an on-going project";
        Worker worker = tile.getAvailableWorker();
        if (worker == null) return "there is no worker in this tile to do the project";
        if (gameMenuController.assignNewProject(worker,"remove" + improvementName)) return "worker successfully assigned";
        return "you can't do that because this feature is not in this tile";
    }

    private String unitRepair(Matcher matcher) {
        String type = matcher.group("typeOfRepair");
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        Tile tile = GameDatabase.getTileByXAndY(x, y);
        if (tile == null) return "invalid tile";
        if (!gameMenuController.isTileInCivilization(tile,turn)) return "this tile ain't yours bro";
        if (tile.getIsGettingWorkedOn()) return "there is already a project going on in this tile";
        Worker worker = tile.getAvailableWorker();
        if (worker == null) return "there is no available worker in this tile";
        if (type.equals("Road") || type.equals("Railroad")) {
            if (gameMenuController.assignNewProject(worker,"repair" + type)) return "worker successfully assigned";
            else return "you can't do this because either tile doesn't have the (rail)road or it isn't broken";
        }
        if (!gameMenuController.isImprovementValid(type)) {
            return "invalid improvement";
        }
        if (gameMenuController.assignNewProject(worker,"repair" + type)) return "worker successfully assigned";
        else return "you can't do this because either tile doesn't have the improvement or it isn't broken";
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
        if (!gameMenuController.isCityForThisCivilization(turn, citySelected)) {
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

    private String buildCity(Matcher matcher) {
        String cityName = matcher.group("cityName");
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        City city = GameDatabase.getCityByName(cityName);
        if (city != null) {
            return "there is already a city with this name";
        } else if (GameDatabase.getCityByXAndY(x, y) != null) {
            return "there is already a city in this tile";
        } else {
            Tile tile = GameDatabase.getTileByXAndY(x, y);
            Settler settler = tile.returnSettler();
            if (settler == null) {
                return "there is no settler in this tile";
            } else {
                gameMenuController.createNewCity(settler, cityName);
                return "city created successfully!";
            }
        }
    }

    private String sendMessage(Matcher matcher) {
        String nickname = matcher.group("Nickname");
        String text = matcher.group("Text");
        if (!this.gameMenuController.isCivilizationValid(nickname)) {
            return "there is no civilization with this nickname";
        }
        if (GameDatabase.players.get(turn).getNickname().equals(nickname)) {
            return "you can't send a message for yourself!";
        }
        Notification notification = new Notification(GameDatabase.players.get(turn).getNickname(), nickname, text);
        Notification.addNotification(notification);
        return "Message sent.";
    }

    private void printUnhappyCivilizations() {
        ArrayList<Civilization> unhappyCivilizations = new ArrayList<Civilization>();
        for (Civilization civilization : GameDatabase.players) {
            if (!civilization.isHappy()) {
                unhappyCivilizations.add(civilization);
            }
        }
        if (unhappyCivilizations.size() == 0) {
            return;
        }
        System.out.println("Attention: These Civilizations are not happy!");
        for (Civilization civilization : unhappyCivilizations) {
            System.out.println(civilization.getNickname() + " - Happiness: " + Integer.toString(civilization.getHappiness()));
        }
    }

    private int nextTurn() {
        if (turn != this.numberOfPlayers - 1) {
            return turn++;
        }
        GameDatabase.nextTurn();
        printUnhappyCivilizations();
        return 0;
    }

    private String getAddTileToCity(Matcher matcher) {
        String cityName = matcher.group("cityName");
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        City city = GameDatabase.getCityByName(cityName);
        if (city == null) {
            return "there is no city with this name";
        }
        if (GameDatabase.getCityByXAndY(x, y) != null) {
            return "there is already a city in this tile";
        }
        Tile tile = GameDatabase.getTileByXAndY(x, y);
        if (!gameMenuController.isAdjacent(tile, city)) {
            return "chosen tile isn't adjacent to city";
        } else if (!gameMenuController.isOperable(tile, city)) {
            return "there is no settler in adjacent tiles in city";
        } else {
            gameMenuController.addTileToCity(tile, city);
            return "tile added to the city successfully!";
        }
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
