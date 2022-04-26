package View;

import Controller.GameMenuController;
import Database.GameDatabase;
import Model.City;
import Model.Tile;
import Model.Unit;

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
    private static final String MAP_MOVE = "map move (?<direction>\\S+)( (?<c>\\d+))?";
    private static final String SELECT_CITY_BY_POSITION = "select city (?<x>\\d+) (?<y>\\d+)";
    private static final String SELECT_CITY_BY_NAME = "select city (?<cityName>\\S+)";

    //Cheat
    private static final String CHEAT_TURN_BY_NAME = "turn change (?<civilizationName>\\S+)";
    private static final String CHEAT_TURN_BY_NUMBER = "turn increase (?<amount>-?\\d+)";
    private static final String CHEAT_GOLD = "gold increase (?<amount>-?\\d+)";


    public GameMenu(GameMenuController gameMenuController) {
        this.gameMenuController = gameMenuController;
        this.turn = 0;
        this.citySelected = null;
        this.unitSelected = null;
    }

    public void run(Scanner scanner) {
        String command;


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
                if (result.startsWith("now")) {
                    turn += Integer.parseInt(matcher.group("amount"));
                    turn %= numberOfPlayers;

                }
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
                } else {
                    System.out.println(result);
                }
            } else if ((matcher = getCommandMatcher(command, SELECT_CITY_BY_POSITION)) != null) {
                String result = citySelectByPosition(matcher);
                if (result == null) {
                    citySelected = GameDatabase.getCityByXAndY(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y")));
                } else {
                    System.out.println(result);
                }
            } else if ((matcher = getCommandMatcher(command, CHEAT_GOLD)) != null) {
                System.out.println(cheatGold(matcher));

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
        turn += amount;
        turn %= numberOfPlayers;
        return "now it's " + GameDatabase.players.get(turn).getNickname() + " turn!";

    }

    private String unitMoveTo(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        if (unitSelected == null) {
            return "you must select a unit first";
        }
        if (!gameMenuController.isUnitForThisCivilization(turn % numberOfPlayers, unitSelected)) {
            return "this unit is not for you";
        }
        if(!this.gameMenuController.isDestinationOkForMove(unitSelected, x, y)) {
            return "there are two units with one type in a tile";
        }
        if(!unitSelected.moveUnitFromTo(unitSelected, unitSelected.getTileOfUnit(), GameDatabase.getTileByXAndY(x, y))) {
            return "invalid to move";

        }
        return "unit moved to " + Integer.toString(x) + " and " + Integer.toString(y);
    }

    private String mapMove(Matcher matcher) {
        String direction = matcher.group("direction");
        int groupCount = matcher.groupCount();
        int c = this.gameMenuController.c;
        if(matcher.group("c") != null) {
            c = Integer.parseInt(matcher.group("c"));
        }
        if(!this.gameMenuController.isDirectionForMapValid(direction)) {
            return "invalid direction";
        }
        int x = this.gameMenuController.x + this.gameMenuController.directionX.get(direction)*c;
        int y = this.gameMenuController.y + this.gameMenuController.directionY.get(direction)*c;
        if (!this.gameMenuController.isPositionValid(x, y)) {
            return "position is not valid";
        }
        this.gameMenuController.x = x;
        this.gameMenuController.y = y;
        return null;

    }

    private String citySelectByName(Matcher matcher) {
        String cityName = matcher.group("cityName");
        if(!this.gameMenuController.isCityValid(cityName)) {
            return "invalid city";
        }
        return null;
    }

    private String citySelectByPosition(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        if(!this.gameMenuController.isPositionValid(x, y)) {
            return "invalid position";
        }
        if(!this.gameMenuController.isCityPositionValid(x, y)) {
            return "no city in position " + Integer.toString(x) + " and " + Integer.toString(y);
        }
        return null;
    }

    private String cheatGold(Matcher matcher) {
        int amount = Integer.parseInt(matcher.group("amount"));
        if(!this.gameMenuController.isAmountValidForGold(amount)) {
            return "invalid amount";
        }
        GameDatabase.players.get(turn).addGold(amount);
        return "Now you have " + Integer.toString(GameDatabase.players.get(turn).getGold()) + " golds.";
    }

    private int nextTurn() {
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
                if (tile == null || i == -2 || i == 2) {
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
        int[] counterOfHex = {1, 0, 1, 0, 1, 0};
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
            lines[i] += Colors.ANSI_RESET + "\n";
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
}
