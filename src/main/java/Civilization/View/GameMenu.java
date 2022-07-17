package Civilization.View;

import Civilization.Controller.CombatController;
import Civilization.Controller.GameMenuController;
import Civilization.Database.GameDatabase;
import Civilization.Database.GlobalVariables;
import Civilization.Model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu extends Menu {

    private GameMenuController gameMenuController;
    private CombatController combatController;
    public int numberOfPlayers;
    public int turn;
    public Unit unitSelected;
    City citySelected;
    int x;
    int y;


    private final int priceOfBuyingTile = 20;
    //Commands
    private static final String MAP_SHOW_POSITION = "map show (?<x>\\d+) (?<y>\\d+)";
    private static final String MAP_SHOW_CITY = "map show (?<cityName>.+)";
    private static final String SELECT_COMBAT = "select combat (?<x>\\d+) (?<y>\\d+)";
    private static final String SELECT_NONCOMBAT = "select noncombat (?<x>\\d+) (?<y>\\d+)";
    private static final String SHOW_TURN = "show turn";
    //unit related
    private static final String UNIT_MOVE_TO = "unit moveto (?<x>\\d+) (?<y>\\d+)";
    private static final String UNIT_SLEEP = "unit sleep";
    private static final String UNIT_ALERT = "unit alert";
    private static final String UNIT_FORTIFY = "unit fortify";
    private static final String UNIT_FORTIFY_HEAL = "unit fortify heal";
    private static final String UNIT_GARRISON = "unit garrison";
    private static final String UNIT_SETUP_RANGE = "unit setup range";
    private static final String UNIT_ATTACK_POSITION = "unit attack (?<x>\\d+) (?<y>\\d+)";
    private static final String UNIT_FOUND_CITY = "unit found city (?<name>\\w+)";
    //private static final String CANCEL_MISSION = "unit cancel mission";
    private static final String UNIT_PILLAGE = "unit pillage tile";
    private static final String UNIT_WAKE = "unit wake";
    private static final String UNIT_DELETE = "unit delete";
    private static final String UNIT_CANCEL_MISSION = "unit cancel mission";
    private static final String CREATE_UNIT = "create (?<unitType>\\w+) (?<x>\\d+) (?<y>\\d+)";

    private static final String MAP_MOVE = "map move (?<direction>\\S+)( (?<c>\\d+))?";
    private static final String SELECT_CITY_BY_POSITION = "select city (?<x>\\d+) (?<y>\\d+)";
    private static final String SELECT_CITY_BY_NAME = "select city (?<cityName>\\S+)";
    private static final String BUY_BUILDING = "building --buy";
    private static final String BUILD_BUILDING = "building --build";
    private static final String BUILD_CITY = "build city (?<cityName>\\S+) (?<x>\\d+) (?<y>\\d+)";
    private static final String SEND_MESSAGE = "to (?<Nickname>\\S+) send (?<Text>.+)";
    private static final String ADD_TILE_TO_CITY = "add tile to city (?<cityName>\\S+) (?<x>\\d+) (?<y>\\d+)";
    //improvement should be PascalCase
    private static final String UNIT_BUILD_IMPROVEMENT = "unit build (?<improvement>\\w+) (?<x>\\d+) (?<y>\\d+)";
    private static final String UNIT_REPAIR = "unit repair (?<x>\\d+) (?<y>\\d+)";
    private static final String UNIT_REMOVE_FEATURE = "unit remove (?<feature>\\S+) (?<x>\\d+) (?<y>\\d+)";
    private static final String REMOVE_CITIZEN_FROM_WORK = "remove citizen from work (?<x>\\d+) (?<y>\\d+)";
    private static final String LOCK_CITIZEN_TO_TILE = "lock citizen to tile (?<x>\\d+) (?<y>\\d+)";
    private static final String GET_UNEMPLOYED_SECTION_BY_COORDINATE = "get unemployed section (?<x>\\d+) (?<y>\\d+)";
    private static final String GET_UNEMPLOYED_SECTION_BY_CITY_NAME = "get unemployed section (?<cityName>[a-zA-Z]+)";
    private static final String SHOW_HAPPINESS_LEVEL = "show happiness level";
    private static final String BUYING_TILE = "buying tile (?<x>\\d+) (?<y>\\d+)";

    //Cheat
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

    //Info
    private static final String INFO_CITY = "info city";
    private static final String VALID_BUILDINGS = "valid buildings";
    private static final String INFO_DEMOGRAPHY = "info demography";
    private static final String INFO_RESEARCH = "info research";
    private static final String INFO_NOTIFICATION = "info notification";
    private static final String INFO_MILITARY = "info military";
    private static final String INFO_UNITS = "info units";
    private static final String INFO_ECONOMY = "info economy";


    public GameMenu(GameMenuController gameMenuController, CombatController combatController) {
        this.gameMenuController = gameMenuController;
        this.combatController = combatController;
        this.turn = 0;
        this.citySelected = null;
        this.unitSelected = null;
        this.x = 0;
        this.y = 0;
    }

    public void run(Scanner scanner) throws IOException {
        System.out.println(GameDatabase.getPlayers().get(0).getNickname());
        String command;
        Info info = Info.getInstance();
        boolean nextTurnIsCalled = false;
        while (true) {
            nextTurnIsCalled = false;
            numberOfPlayers = GameDatabase.players.size();
            Matcher matcher;
            command = scanner.nextLine();
            if (command.equals(CHEAT_WIN)) {
                System.out.println(GameDatabase.getPlayers().get(turn).getNickname() + " is the winner!");
                break;
            } else if ((matcher = getCommandMatcher(command, MENU_SHOW)) != null) {
                System.out.println(menuShow());
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
                    unitSelected.setRoute(this.gameMenuController.selectCombatUnit(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y"))).getRoute());
                    gameMenuController.getMovingUnits().add(this.gameMenuController.selectCombatUnit(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y"))));
                    x = Integer.parseInt(matcher.group("x"));
                    y = Integer.parseInt(matcher.group("y"));
                    System.out.println(unitSelected);
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, SELECT_NONCOMBAT)) != null) {
                String result = selectNonCombat(matcher);
                if (result.startsWith("unit")) {
                    unitSelected = this.gameMenuController.selectNonCombatUnit(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y")));
                    unitSelected.setRoute(this.gameMenuController.selectNonCombatUnit(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y"))).getRoute());
                    //gameMenuController.getMovingUnits().add(this.gameMenuController.selectNonCombatUnit(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y"))));
                    x = Integer.parseInt(matcher.group("x"));
                    y = Integer.parseInt(matcher.group("y"));
                    System.out.println(unitSelected);
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
                    //gameMenuController.getMovingUnits().get(0).setRoute(this.gameMenuController.selectNonCombatUnit(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y"))).getRoute());
                    unitSelected = null;
                    turn = nextTurn();
                    nextTurnIsCalled = true;
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, UNIT_SLEEP)) != null) {
                String result = unitSleep();
                if (result.startsWith("unit")) {
                    unitSelected = null;
                    turn = nextTurn();
                    nextTurnIsCalled = true;
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, UNIT_ALERT)) != null) {
                String result = unitAlert();
                if (result.startsWith("unit")) {
                    unitSelected = null;
                    turn = nextTurn();
                    nextTurnIsCalled = true;
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, UNIT_FORTIFY)) != null) {
                String result = unitFortify();
                if (result.startsWith("unit")) {
                    unitSelected = null;
                    turn = nextTurn();
                    nextTurnIsCalled = true;
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, UNIT_FORTIFY_HEAL)) != null) {
                String result = unitHeal();
                if (result.startsWith("unit")) {
                    unitSelected = null;
                    turn = nextTurn();
                    nextTurnIsCalled = true;
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, UNIT_GARRISON)) != null) {
                String result = unitGarrison();
                if (result.startsWith("unit")) {
                    unitSelected = null;
                    turn = nextTurn();
                    nextTurnIsCalled = true;
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, UNIT_SETUP_RANGE)) != null) {
                String result = unitSetupRange();
                if (result.startsWith("siege")) {
                    unitSelected = null;
                    turn = nextTurn();
                    nextTurnIsCalled = true;
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, UNIT_ATTACK_POSITION)) != null) {
                String result = unitAttack(matcher);
                if (result.startsWith("unit")) {
                    unitSelected = null;
                    turn = nextTurn();
                    nextTurnIsCalled = true;
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, UNIT_FOUND_CITY)) != null) {
                String result = unitFoundCity(matcher);
                if (result.startsWith("unit")) {
                    unitSelected = null;
                    turn = nextTurn();
                    nextTurnIsCalled = true;
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, UNIT_WAKE)) != null) {
                String result = unitWake();
                if (result.startsWith("unit")) {
                    unitSelected = null;
                    turn = nextTurn();
                    nextTurnIsCalled = true;
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, UNIT_DELETE)) != null) {
                String result = unitDelete();
                if (result.startsWith("unit")) {
                    unitSelected = null;
                    turn = nextTurn();
                    nextTurnIsCalled = true;
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, UNIT_BUILD_IMPROVEMENT)) != null) {
                String result = unitBuild(matcher);
                if (result.startsWith("worker")) {
                    unitSelected = null;
                    turn = nextTurn();
                    nextTurnIsCalled = true;
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, UNIT_REMOVE_FEATURE)) != null) {
                String result = unitRemoveFeature(matcher);
                if (result.startsWith("worker")) {
                    unitSelected = null;
                    turn = nextTurn();
                    nextTurnIsCalled = true;
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, UNIT_REPAIR)) != null) {
                String result = unitRepair(matcher);
                if (result.startsWith("worker")) {
                    unitSelected = null;
                    turn = nextTurn();
                    nextTurnIsCalled = true;
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, CREATE_UNIT)) != null) {
                String result = createUnit(matcher);
                if (result.startsWith("unit")) {
                    unitSelected = null;
                    turn = nextTurn();
                    nextTurnIsCalled = true;
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, UNIT_PILLAGE)) != null) {
                String result = unitPillageCurrentTile();
                if (result.startsWith("unit")) {
                    unitSelected = null;
                    turn = nextTurn();
                    nextTurnIsCalled = true;
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, REMOVE_CITIZEN_FROM_WORK)) != null) {
                String result = unitStopWork(matcher);
                if (result.startsWith("project")) {
                    unitSelected = null;
                    turn = nextTurn();
                    nextTurnIsCalled = true;
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, LOCK_CITIZEN_TO_TILE)) != null) {
                String result = lockCitizen(matcher);
                if (result.startsWith("project")) {
                    unitSelected = null;
                    turn = nextTurn();
                    nextTurnIsCalled = true;
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
                    x = citySelected.getX();
                    y = citySelected.getY();
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
                    x = citySelected.getX();
                    y = citySelected.getY();
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
                    nextTurnIsCalled = true;
                } else {
                    System.out.println(result);
                }
            } else if ((matcher = getCommandMatcher(command, INFO_DEMOGRAPHY)) != null) {
                System.out.println(info.infoDemography(turn));
            } else if ((matcher = getCommandMatcher(command, INFO_RESEARCH)) != null) {
                boolean shallNextTurn = info.infoResearch(turn, scanner);
                if(shallNextTurn) {
                    turn = nextTurn();
                }
            } else if ((matcher = getCommandMatcher(command, BUILD_CITY)) != null) {
                String result = buildCity(matcher);
                if (result.startsWith("city")) {
                    x = Integer.parseInt(matcher.group("x"));
                    y = Integer.parseInt(matcher.group("y"));
                    turn = nextTurn();
                    nextTurnIsCalled = true;
                }
                System.out.println(result);
            } else if ((matcher = getCommandMatcher(command, ADD_TILE_TO_CITY)) != null) {
                String result = getAddTileToCity(matcher);
                if (result.startsWith("tile")) {
                    turn = nextTurn();
                    nextTurnIsCalled = true;
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
            } else if ((matcher = getCommandMatcher(command, CHEAT_MAKE_HAPPY)) != null) {
                System.out.println(makeHappy());
            } else if ((matcher = getCommandMatcher(command, CHEAT_ADD_SCIENCE)) != null) {
                System.out.println(addScience(matcher));
            } else if ((matcher = getCommandMatcher(command, SHOW_HAPPINESS_LEVEL)) != null) {
                showHappinessLevel();
            } else if ((matcher = getCommandMatcher(command, CHEAT_ADD_CITY_HIT_POINT)) != null) {
                System.out.println(addHitPointCity(matcher));
            } else if ((matcher = getCommandMatcher(command, CHEAT_ADD_UNIT_HIT_POINT)) != null) {
                System.out.println(addHitPointUnit(matcher));
            } else if ((matcher = getCommandMatcher(command, CHEAT_CHANGE_CAPITAL)) != null) {
                System.out.println(changeCapital(matcher));
            } else if ((matcher = getCommandMatcher(command, CHEAT_ADD_PRODUCTION)) != null) {
                System.out.println(addProduction(matcher));
            } else if ((matcher = getCommandMatcher(command, CHEAT_ADD_SCORE)) != null) {
                System.out.println(addScore(matcher));
            } else if ((matcher = getCommandMatcher(command, CHEAT_DRY_UP)) != null) {
                String result = dryUp(matcher);
                if (result != null) {
                    System.out.println(result);
                } else {
                    x = Integer.parseInt(matcher.group("x"));
                    y = Integer.parseInt(matcher.group("y"));
                    turn = nextTurn();
                    nextTurnIsCalled = true;
                }
            } else if ((matcher = getCommandMatcher(command, BUYING_TILE)) != null) {
                String result = buyTileWithCoordinate(matcher);
                if (result.startsWith("congrats")) {
                    turn = nextTurn();
                    nextTurnIsCalled = true;
                }
                System.out.println(result);
            } else {
                System.out.println("invalid command");
            }
        }
    }

    public String buyTileWithCoordinate(Matcher matcher) throws IOException {
        int y = Integer.parseInt(matcher.group("y"));
        int x = Integer.parseInt(matcher.group("x"));
        Tile tile = GameDatabase.getTileByXAndY(x, y);
        Civilization civilization = GameDatabase.getCivilizationByTurn(turn);
        if (tile == null) return "invalid tile";
        if (gameMenuController.isTileInCivilization(tile, turn)) return "you already have this tile!";
        if (gameMenuController.isTileInAnyCivilization(tile)) return "somebody else has bought this tile";
        if (!gameMenuController.isTileAdjacentToCivilization(tile, civilization)) return "this tile ain't adjacent to your tiles bro";
        if (civilization.getGold() < priceOfBuyingTile) return "bro you dont have enough gold";
        gameMenuController.addTileToCivilization(tile, civilization);
        return "congrats bro you bought it";
    }

    public void showHappinessLevel() {
        String nickname = GameDatabase.players.get(turn).getNickname();
        System.out.println("your civilization is:");
        if (GameDatabase.players.get(turn).isHappy()) {
            System.out.println("\t HAPPY!");
        } else {
            System.out.println("\t UNHAPPY :(");
        }
        System.out.println("your happiness is : " + Integer.toString(GameDatabase.players.get(turn).getHappiness()));
        System.out.println("other civilizations:");
        for (Civilization civilization : GameDatabase.players) {
            if (civilization.getNickname().equals(nickname)) {
                continue;
            }
            System.out.println(civilization.getNickname() + " happiness is " + Integer.toString(civilization.getHappiness()));
        }

    }

    public String changeCapital(Matcher matcher) throws IOException {
        String cityName = matcher.group("cityName");
        if (!this.gameMenuController.isCityValid(cityName)) return "invalid city";
        if (!this.gameMenuController.isCityForThisCivilization(turn, GameDatabase.getCityByName(cityName))) return "selected city is not for your civilization";
        if (this.gameMenuController.isCityCapital(cityName)) return "selected city is already capital of your civilization";
        GameDatabase.getPlayers().get(turn).changeCapital(cityName);
        return "capital changed successfully";
    }

    public String lockCitizen(Matcher matcher) throws IOException {
        int y = Integer.parseInt(matcher.group("y"));
        int x = Integer.parseInt(matcher.group("x"));
        Tile tile = GameDatabase.getTileByXAndY(x, y);
        if (tile == null) return "invalid tile";
        if (!gameMenuController.isTileInCivilization(tile, turn)) return "this tile ain't yours bro";
        Worker worker = tile.getWorker();
        City city = GameDatabase.getCityByXAndY(x, y);
        if (city == null) return "this tile is in no city";
        if (worker == null) worker = gameMenuController.findAvailableWorkerInCity(city);
        if (worker == null) return "no available worker to be locked to this tile";
        worker.lockTheWorker(tile);
        return "worker started locking process successfully!";
    }

    public String unemployedSection(Matcher matcher, boolean isCoordinate) throws IOException {
        City city;
        if (isCoordinate) {
            int x = Integer.parseInt(matcher.group("x"));
            int y = Integer.parseInt(matcher.group("y"));
            city = GameDatabase.getCityByXAndY(x, y);
            Tile tile = GameDatabase.getTileByXAndY(x, y);
            if (!gameMenuController.isTileInCivilization(tile, turn)) return "this tile ain't yours bro\n";
        } else {
            city = GameDatabase.getCityByName(matcher.group("cityName"));
        }
        if (city == null) return "this tile is in no city\n";
        ArrayList<Worker> unemployedWorkers = gameMenuController.getListOfUnemployedWorker(city);
        ArrayList<Settler> unemployedSettlers = gameMenuController.getListOfUnemployedSettler(city);
        return "";
    }

//    private void printUnemployedSection(ArrayList<Worker> workers, ArrayList<Settler> settlers) {
//        int i = 0;
//        for (Worker worker : workers) {
//            System.out.println(i + "th unemployed worker: x = " + worker.getX() + ", y = " + worker.getY());
//        }
//        i = 0;
//        for (Settler settler : settlers) {
//            System.out.println(i + "th unemployed worker: x = " + settler.getX() + ", y = " + settler.getY());
//        }
//    }

    public String unitStopWork(Matcher matcher) throws IOException {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        Tile tile = GameDatabase.getTileByXAndY(x, y);
        City city = GameDatabase.getCityByXAndY(x, y);
        if (tile.isRaided()) return "this tile is raided";
        if (tile == null) return "invalid tile";
        if (!gameMenuController.isTileInCivilization(tile, turn)) return "this tile ain't yours bro";
        if (city == null) return "this tile is in no city";
        Worker worker = tile.getActiveWorker();
        if (!tile.getIsGettingWorkedOn() || worker == null) return "this tile isn't getting worked on";
        if (!city.getIsGettingWorkedOn()) return "this city isn't getting worked on";
        gameMenuController.pauseProject(worker, x, y);
        return "project stopped successfully";
    }

    @Override
    public String menuShow() {
        return "Game Menu";
    }

    public String menuEnter(Matcher matcher) {
        return "menu navigation is not possible";
    }

    public String menuExit(Matcher matcher) {
        return "you must finish the game to exit";
    }

    public String mapShowPosition(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        if (!this.gameMenuController.isPositionValid(x, y)) {
            return "position is not valid";
        }
        return null;
    }

    public String mapShowCity(Matcher matcher) throws IOException {
        String cityName = matcher.group("cityName");
        if (!this.gameMenuController.isCityValid(cityName)) {
            return "selected city is not valid";
        }
        return null;
    }

    public String selectCombat(Matcher matcher) throws IOException {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        if (!this.gameMenuController.isPositionValid(x, y)) {
            return "position is not valid";
        }
        if (!this.gameMenuController.isCombatUnitInThisPosition(x, y)) {
            return "no combat unit";
        }
        unitSelected = this.gameMenuController.selectCombatUnit(x, y);
        return "unit selected";
    }

    public String addHitPointUnit(Matcher matcher) throws IOException {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        int amount = Integer.parseInt(matcher.group("amount"));
        if (!this.gameMenuController.isAmountValidForHP(amount)) {
            return "invalid amount";
        }
        if (this.gameMenuController.isAmountALot(amount)) {
            return "please cheat with another amount of HP!";
        }
        if (!this.gameMenuController.isPositionValid(x, y)) {
            return "position is not valid";
        }
        if (!this.gameMenuController.isCombatUnitInThisPosition(x, y)) {
            return "you can't change hit point of non combat units";
        }
        if (!this.gameMenuController.isUnitForThisCivilization(turn, this.gameMenuController.selectCombatUnit(x, y))) {
            return "unit in this position is not for your civilization";
        }
        this.gameMenuController.addHP(x, y, amount);
        return Integer.toString(amount) + " hit point added to unit in position " + Integer.toString(x) + " and " + Integer.toString(y);
    }

    public String selectNonCombat(Matcher matcher) throws IOException {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        if (!this.gameMenuController.isPositionValid(x, y)) {
            return "position is not valid";
        }
        if (!this.gameMenuController.isNonCombatUnitInThisPosition(x, y)) {
            return "no noncombat unit";
        }
        unitSelected = this.gameMenuController.selectNonCombatUnit(x, y);
        gameMenuController.getMovingUnits().add(this.gameMenuController.selectNonCombatUnit(x, y));
        return "unit selected";
    }

    public int getTurn() {
        return this.turn;
    }

    public String changeTurn(Matcher matcher) throws IOException {
        String civilizationName = matcher.group("civilizationName");
        if (!this.gameMenuController.isCivilizationValid(civilizationName)) {
            return "there is no player with nickname " + civilizationName;
        }
        if (!this.gameMenuController.isCheatForTurn(civilizationName, turn)) {
            return "there is already your turn!";
        }
        return "now it's your turn!";
    }

    public String changeTurnByNumber(Matcher matcher) throws IOException {
        int amount = Integer.parseInt(matcher.group("amount"));
        if (!this.gameMenuController.isAmountValidForTurn(amount)) return "invalid turn";
        for (int i = 0; i < amount; i++) turn = nextTurn();
        return "now it's " + GameDatabase.getPlayers().get(turn).getNickname() + " turn!";
    }

    public String addProduction(Matcher matcher) throws IOException {
        int amount = Integer.parseInt(matcher.group("amount"));
        String cityName = matcher.group("cityName");
        if (!this.gameMenuController.isCityValid(cityName)) return "invalid city";
        if (!this.gameMenuController.isAmountValidForProduction(amount)) return "invalid amount";
        if (!this.gameMenuController.isCityForThisCivilization(turn, GameDatabase.getCityByName(cityName))) return "this city is not for your civilization";
        this.gameMenuController.addProduction(cityName, amount);
        return Integer.toString(amount) + " production added to " + cityName;
    }

    private String unitMoveTo(Matcher matcher) throws IOException {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        if (unitSelected == null) {
            return "you must select a unit first";
        } else if (!gameMenuController.isUnitForThisCivilization(turn, unitSelected)) {
            return "this unit is not for you";
        } else if (!this.gameMenuController.isDestinationOkForMove(unitSelected, x, y)) {
            return "there are two units with one type in a tile";
        } else {
            int success = unitSelected.moveUnitFromTo(unitSelected, unitSelected.getTileOfUnit(), GameDatabase.getTileByXAndY(x, y));
            if (success == 0) {
                return "unit moved to " + Integer.toString(x) + " and " + Integer.toString(y);
            } else if (success == -1) {
                return "invalid destination";
            } else {
                return "not enough movementpoints";
            }
        }
    }

    private String createUnit(Matcher matcher) throws IOException {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        String unitType = matcher.group("unitType");
        if (!this.gameMenuController.isPositionValid(x, y)) {
            return "invalid position";
        }
        if(!this.gameMenuController.isUnitTypeValid(unitType)){
            return "invalid unit type";
        }
        if (!this.gameMenuController.isTileValidForCreatingUnit(x, y, turn)) {
            return "you can't create unit on this tile";
        }
        if (!gameMenuController.isTileInCivilization(GameDatabase.getTileByXAndY(x, y), turn % numberOfPlayers)) {
            return "this tile is not for you";
        } else {
            boolean success = gameMenuController.createUnit(unitType, x, y, turn);
            if (success) {
                this.x = x;
                this.y = y;
                return "unit " + unitType + " created";
            } else {
                return "cannot create a unit here";
            }
        }
    }

    public String unitSleep() {
        if (unitSelected == null) { return "you must select a unit first";
        } else if (!gameMenuController.isUnitForThisCivilization(turn % numberOfPlayers, unitSelected)) {return "this unit is not for you";
        } else {
            unitSelected.setSleeping(true);
        }
        return "unit slept";
    }

    public String unitAlert() {
        if (unitSelected == null) {return "you must select a unit first";
        } else if (!gameMenuController.isUnitForThisCivilization(turn % numberOfPlayers, unitSelected)) {return "this unit is not for you";
        } else if (!unitSelected.isCombatUnit()) {return "this is not a combat unit";
        } else {unitSelected.setReady(true);
        }
        return "unit is ready";
    }

    public String unitFortify() {
        if (unitSelected == null) {return "you must select a unit first";
        } else if (!gameMenuController.isUnitForThisCivilization(turn % numberOfPlayers, unitSelected)) {return "this unit is not for you";
        } else if (!unitSelected.isCombatUnit()) {return "this is not a combat unit";
        } else {combatController.fortifyUnit(unitSelected);
        }
        return "unit fortified";
    }

    public String unitHeal() throws IOException {
        if (unitSelected == null) {return "you must select a unit first";
        } else if (!gameMenuController.isUnitForThisCivilization(turn % numberOfPlayers, unitSelected)) {return "this unit is not for you";
        } else if (!unitSelected.isCombatUnit()) {return "this is not a combat unit";
        } else {combatController.healUnit(unitSelected);
        }
        return "unit fortifyHealed";
    }

    public String unitFoundCity(Matcher matcher) {
        if (unitSelected == null) {return "you must select a unit first";
        } else if (!gameMenuController.isUnitForThisCivilization(turn % numberOfPlayers, unitSelected)) {return "this unit is not for you";
        } else if (unitSelected.isCombatUnit()) {return "this is not a settler unit";
        } else {
            if (unitSelected instanceof Settler) {Settler settler = (Settler) unitSelected; String cityName = matcher.group("name"); settler.createNewCity(cityName);}
            else return "this is a worker";
        }
        return "unit found city";

    }

    public String unitPillageCurrentTile() {
        if (unitSelected == null) {return "you must select a unit first";
        } else if (!gameMenuController.isUnitForThisCivilization(turn % numberOfPlayers, unitSelected)) {return "this unit is not for you";
        } else if (!unitSelected.isCombatUnit()) {return "this is not a combat unit";
        } else if (unitSelected.isInItsCivilization()) {return "this is your civilization";
        } else {
            gameMenuController.pillageCurrentTile(unitSelected);
            return "unit pillaged tile";
        }
    }

    public String unitWake() {
        if (unitSelected == null) {return "you must select a unit first";
        } else if (!gameMenuController.isUnitForThisCivilization(turn % numberOfPlayers, unitSelected)) {return "this unit is not for you";
        } else {unitSelected.setSleeping(false);
        }
        return "unit awakened";
    }

    public String unitAttack(Matcher matcher) throws IOException {
        if (unitSelected == null) {return "you must select a unit first";
        } else if (!gameMenuController.isUnitForThisCivilization(turn % numberOfPlayers, unitSelected)) {return "this unit is not for you";
        } else if (!(unitSelected instanceof Soldier)) {return "this unit is not a combat unit";
        } else {
            int x = Integer.parseInt(matcher.group("x"));
            int y = Integer.parseInt(matcher.group("y"));
            Soldier soldier = (Soldier) unitSelected;
            if (soldier.isTileInRangeOfUnit(GameDatabase.getTileByXAndY(x, y))) {
                boolean success = combatController.UnitAttackPosition(soldier, x, y);
                if (success) {
                    if (((Soldier) unitSelected).getCombatType().equals("siege")){
                        ((Soldier) unitSelected).setSiegeReady(0);
                    }
                    return "unit attacked desired position";
                }
                else
                    return "siege unit is not ready";
            } else {
                return "selected position is in not in range of unit";
            }
        }
    }

    public String unitSetupRange(){
        if (unitSelected == null) {return "you must select a unit first";
        } else if (!gameMenuController.isUnitForThisCivilization(turn % numberOfPlayers, unitSelected)) {return "this unit is not for you";
        } else if (!(unitSelected instanceof Soldier)) {return "this unit is not a combat unit";
        } else {
            boolean success = combatController.getSiegeUnitReady((Soldier) unitSelected);
            if (success)return "siege unit is setup";
            else return "this is not a siege unit";

        }
    }

    public String unitGarrison() throws IOException {
        if (unitSelected == null) {return "you must select a unit first";
        } else if (!gameMenuController.isUnitForThisCivilization(turn % numberOfPlayers, unitSelected)) {return "this unit is not for you";
        } else if (!(unitSelected instanceof Soldier)) {return "this unit is not a combat unit";
        } else {
            boolean success = gameMenuController.garrisonUnitToCity(unitSelected);
            if (success) {
                return "unit garrisoned to city";
            } else {
                return "this units tile is not a city";
            }
        }
    }


    public String unitDelete() throws IOException {
        if (unitSelected == null) {return "you must select a unit first";
        } else if (!gameMenuController.isUnitForThisCivilization(turn % numberOfPlayers, unitSelected)) {return "this unit is not for you";
        } else {
            gameMenuController.deleteUnit(unitSelected);
            return "unit deleted";
        }
    }

    public String makeHappy() {
        if (GameDatabase.getPlayers().get(turn).isHappy()) {
            return "you are happy now";
        }
        this.gameMenuController.makeHappy(turn);
        return "now your happiness is 0";
    }

    public String unitBuild(Matcher matcher) throws IOException {
        String improvementName = matcher.group("improvement");
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        Tile tile = GameDatabase.getTileByXAndY(x, y);
        City city = GameDatabase.getCityByXAndY(x,y);
        if (tile == null) return "invalid tile";
        if (city == null) return "no city";
        if (tile.isRaided()) return "this tile is raided";
        if (!improvementName.equals("Road")
                && !improvementName.equals("Railroad")
                && !gameMenuController.isImprovementValid(improvementName)) return "invalid improvement";
        if (!GameDatabase.isTileInCivilization(tile, GameDatabase.getCivilizationByTurn(turn % numberOfPlayers))) return "this tile belongs to another civilization!";
        if (!gameMenuController.isTileInCivilization(tile,turn%numberOfPlayers)) return "this isn't in your civilization";
        if (tile.getIsGettingWorkedOn() || city.getIsGettingWorkedOn()) return "city has an on-going project";
        Worker worker = tile.getAvailableWorker();
        if (worker == null) return "there is no worker in this tile to do the project";
        if (gameMenuController.assignNewProject(worker, improvementName)) return "worker successfully assigned";
        return "you can't do that because either this improvement/(rail)road is already in this tile or " +
                "you don't have the pre-requisite technology";
    }

    public String unitRemoveFeature(Matcher matcher) throws IOException {
        String improvementName = matcher.group("improvement");
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        Tile tile = GameDatabase.getTileByXAndY(x, y);
        if (tile == null) return "invalid tile";
        City city = GameDatabase.getCityByXAndY(x,y);
        if (city == null) return "no city";
        if (!improvementName.equals("Road")
                && !improvementName.equals("Railroad")
                && !improvementName.equals("Jungle")
                && !improvementName.equals("DenseJungle")
                && !improvementName.equals("Prairie")) return "invalid improvement";

        if (!gameMenuController.isTileInCivilization(tile, turn)) return "this tile ain't yours bro";
        if (city.getIsGettingWorkedOn()) return "city has an on-going project";
        Worker worker = tile.getAvailableWorker();
        if (worker == null) return "there is no worker in this tile to do the project";
        if (tile.isRaided()) return "this tile is raided";
        if (gameMenuController.assignNewProject(worker, "remove" + improvementName)) return "worker successfully assigned";
        return "you can't do that because this feature is not in this tile";
    }

    public String unitRepair(Matcher matcher) throws IOException {
        int y = Integer.parseInt(matcher.group("y"));
        int x = Integer.parseInt(matcher.group("x"));
        Tile tile = GameDatabase.getTileByXAndY(x, y);
        if (tile == null ) return "invalid tile";
        City city = GameDatabase.getCityByXAndY(x,y);
        if (city == null) return "no city";
        if (!gameMenuController.isTileInCivilization(tile, turn)) return "this tile ain't yours bro";
        if (city.getIsGettingWorkedOn()) return "there is already a project going on in city";
        Worker worker = tile.getAvailableWorker();
        if (worker == null) return "there is no available worker in this tile";
        if (!tile.isRaided()) return "this tile is not raided";
        gameMenuController.assignNewProject(worker, "repair");
        return "worker successfully assigned";
    }

    public String mapMove(Matcher matcher) {
        String direction = matcher.group("direction");
        int groupCount = matcher.groupCount();
        int c = this.gameMenuController.c;
        if (matcher.group("c") != null) {
            c = Integer.parseInt(matcher.group("c"));
        }
        if (!this.gameMenuController.isDirectionForMapValid(direction)) {
            return "invalid direction";
        }
        int x = this.gameMenuController.getX() + this.gameMenuController.getDirectionX().get(direction) * c;
        int y = this.gameMenuController.getY() + this.gameMenuController.getDirectionY().get(direction) * c;
        if (!this.gameMenuController.isPositionValid(x, y)) {
            return "position is not valid";
        }
        this.gameMenuController.x = x;
        this.gameMenuController.y = y;
        return null;
    }

    public String dryUp(Matcher matcher) throws IOException {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        if (!this.gameMenuController.isPositionValid(x, y)) return "invalid position";
        if (this.gameMenuController.isTileOcean(GameDatabase.getTileByXAndY(x, y))) return "you can not dry up an ocean";
        if (!this.gameMenuController.tileHasRiver(GameDatabase.getTileByXAndY(x, y))) return "no river in this tile";
        this.gameMenuController.dryUp(x, y);
        return null;
    }

    public String addHitPointCity(Matcher matcher) throws IOException {
        String cityName = matcher.group("cityName");
        int amount = Integer.parseInt(matcher.group("amount"));
        if (!this.gameMenuController.isAmountValidForHP(amount)) {
            return "invalid amount";
        }
        if (this.gameMenuController.isAmountALot(amount)) {
            return "please cheat with another amount of HP!";
        }
        if (!this.gameMenuController.isCityValid(cityName)) {
            return "invalid city";
        }
        if (!this.gameMenuController.isCityForThisCivilization(turn, GameDatabase.getCityByName(cityName))) {
            return "city is not for your civilization";
        }
        this.gameMenuController.addHP(cityName, amount);
        return Integer.toString(amount) + " hit point added to city " + cityName;
    }

    public String citySelectByName(Matcher matcher) throws IOException {
        String cityName = matcher.group("cityName");
        if (!this.gameMenuController.isCityValid(cityName)) {
            return "invalid city";
        }
        return null;
    }

    public String citySelectByPosition(Matcher matcher) throws IOException {
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

    public String cheatGold(Matcher matcher) {
        int amount = Integer.parseInt(matcher.group("amount"));
        if (!this.gameMenuController.isAmountValidForGold(amount)) {
            return "invalid amount";
        }
        this.gameMenuController.addGold(turn, amount);
        return "Now you have " + Integer.toString(GameDatabase.getPlayers().get(turn).getGold()) + " golds.";
    }

    public String addScience(Matcher matcher) {
        int science = Integer.parseInt(matcher.group("science"));
        if (!this.gameMenuController.isAmountValidForScience(science)) {
            return "invalid amount";
        }
        this.gameMenuController.addScience(turn, science);
        return "Now you have " + Integer.toString(GameDatabase.getPlayers().get(turn).getScience()) + " science.";
    }

    public String addScore(Matcher matcher) {
        int score = Integer.parseInt(matcher.group("score"));
        if (!this.gameMenuController.isAmountValidForScore(score)) return "invalid amount";
        this.gameMenuController.addScore(turn, score);
        return "Now you have " + Integer.toString(GameDatabase.getPlayers().get(turn).getScore()) + " score.";
    }


    private String validBuildings(Scanner scanner) throws IOException {
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

    public String buildCity(Matcher matcher) throws IOException {
        String cityName = matcher.group("cityName");
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        City city = GameDatabase.getCityByName(cityName);
        if (city != null) return "there is already a city with this name";
        else if (GameDatabase.getCityByXAndY(x, y) != null) return "there is already a city in this tile";
        else {
            Tile tile = GameDatabase.getTileByXAndY(x, y);
            Settler settler = tile.getSettler();
            if (settler == null) return "there is no settler in this tile";
            else if (!gameMenuController.isUnitForThisCivilization(turn % numberOfPlayers, settler)) return "this unit isn't for you!";
            else {
                gameMenuController.createNewCity(settler, cityName);
                return "city created successfully!";
            }
        }
    }

    public String sendMessage(Matcher matcher) throws IOException {
        String nickname = matcher.group("Nickname");
        String text = matcher.group("Text");
        if (!this.gameMenuController.isCivilizationValid(nickname)) return "there is no civilization with this nickname";
        if (GameDatabase.getPlayers().get(turn).getNickname().equals(nickname)) return "you can't send a message for yourself!";
        this.gameMenuController.sendMessage(turn, nickname, text);
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

    private int nextTurn() throws IOException {
        if (gameMenuController.getMovingUnits().size() != 0) {
            System.out.println(gameMenuController.getMovingUnits().get(0) + " kose ammeeee");
            boolean b = gameMenuController.moveUnitAlongPath(gameMenuController.getMovingUnits().get(0));
            if (b){
                System.out.println("salap");
                System.out.println(gameMenuController.getMovingUnits().size());
            }
        }
        printMap(x, y);
        showNextTurn();
        GameDatabase.nextTurn();
        if (turn != this.numberOfPlayers - 1) {
            return turn + 1;
        }
        printUnhappyCivilizations();
        return 0;
    }

    private void showNextTurn() {
        int tempTurn = turn;
        if (tempTurn != this.numberOfPlayers - 1) {
            tempTurn++;
            System.out.println(GameDatabase.players.get(tempTurn).getNickname());
            return;
        }
        System.out.println(GameDatabase.players.get(0).getNickname());
    }

    public String getAddTileToCity(Matcher matcher) throws IOException {
        String cityName = matcher.group("cityName");
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        City city = GameDatabase.getCityByName(cityName);
        if (city == null) return "there is no city with this name";
        if (GameDatabase.getCityByXAndY(x, y) != null) return "there is already a city in this tile";
        Tile tile = GameDatabase.getTileByXAndY(x, y);
        if (tile == null) return "invalid tile";
        if (!gameMenuController.isAdjacent(tile, city)) return "chosen tile isn't adjacent to city";
        else if (!gameMenuController.isTileInCivilization(tile,turn%numberOfPlayers)) return "you haven't bought this tile bro";
        else {
            gameMenuController.addTileToCity(tile, city);
            return "tile added to the city successfully!";
        }
    }

    public void printMap(int mainX, int mainY) throws IOException {
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
                    //set lines of hexagons with format
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
                    String unitNames = "";
                    ArrayList<Unit> units = tile.getUnits();
                    boolean isFirst = true;
                    for (Unit unit : units) {
                        if (!isFirst) unitNames += "-";
                        if (isFirst) isFirst = false;
                        unitNames += unit.getUnitType().substring(0, 1);
                        unitNames += GameDatabase.getCivilizationByTurn(unit.getCivilizationIndex()).getNickname().substring(0, 1);
                    }
                    if (tile.getWorker() != null) {
                        unitNames += "-";
                        unitNames += "W";
                        unitNames += GameDatabase.getCivilizationByTurn(tile.getWorker().getCivilizationIndex()).getNickname().substring(0, 1);
                    }
                    if (tile.getSettler() != null) {
                        unitNames += "-";
                        unitNames += "S";
                        unitNames += GameDatabase.getCivilizationByTurn(tile.getSettler().getCivilizationIndex()).getNickname().substring(0, 1);
                    }
                    linesOfHexagons[i + 2][j + 2][3] = Colors.ANSI_RESET + colorOfHexagon +
                            (unitNames + "                ").substring(0, 12);
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
    // I dedicate this comment to all the good TAs of AP,So I guess you could say that there was no point in writing this
}
