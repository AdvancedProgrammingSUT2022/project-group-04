package View;

import Controller.GameMenuController;
import Database.GameDatabase;
//import Enums.MapAndGeneralCommandsOfGameMenu;
import Model.Tile;
import Model.Unit;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu extends Menu {

    private GameMenuController gameMenuController;
    int numberOfPlayers = GameDatabase.players.size();
    private static final String MAP_SHOW_POSITION = "map show (?<x>\\d+) (?<y>\\d+)";
    private static final String MAP_SHOW_CITY = "map show (?<cityName>.+)";
    private static final String SELECT_COMBAT = "select combat (?<x>\\d+) (?<y>\\d+)";
    private static final String SELECT_NONCOMBAT = "select noncombat (?<x>\\d+) (?<y>\\d+)";


    public GameMenu(GameMenuController gameMenuController) {
        this.gameMenuController = gameMenuController;
    }

    public void run(Scanner scanner) {
        String command;

        int turn = 1;
        Unit unitSelected = null;

        while (true) {
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
                if(result == null) {
                    printMap(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y")));
                } else {
                    System.out.println(result);
                }
            } else if ((matcher = getCommandMatcher(command, MAP_SHOW_CITY)) != null) {
                String result = mapShowCity(matcher);
                if(result == null) {
                    printMap(GameDatabase.getCityByName(matcher.group("cityName")).getX(), GameDatabase.getCityByName(matcher.group("cityName")).getY());
                } else {
                    System.out.println(result);
                }
            } else if ((matcher = getCommandMatcher(command, SELECT_COMBAT)) != null) {
                System.out.println(selectCombat(matcher, unitSelected, turn));
            } else if ((matcher = getCommandMatcher(command, SELECT_NONCOMBAT)) != null) {
                System.out.println(selectNonCombat(matcher, unitSelected, turn));
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
        if(!this.gameMenuController.isPositionValid(x, y)) {
            return "position is not valid";
        }
        return null;
    }

    private String mapShowCity(Matcher matcher) {
        String cityName = matcher.group("cityName");
        if(!this.gameMenuController.isCityValid(cityName)) {
            return "selected city is not valid";
        }
        return null;
    }

    private String selectCombat(Matcher matcher, Unit unitSelected, int turn) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        if(!this.gameMenuController.isPositionValid(x, y)) {
            return "position is not valid";
        }
        if(!this.gameMenuController.isCombatUnitInThisPosition(turn, x, y)) {
            return "no combat unit";
        }
        unitSelected = this.gameMenuController.selectCombatUnit(turn, x, y);
        return "unit selected";
    }

    private String selectNonCombat(Matcher matcher, Unit unitSelected, int turn) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        if(!this.gameMenuController.isPositionValid(x, y)) {
            return "position is not valid";
        }
        if(!this.gameMenuController.isNonCombatUnitInThisPosition(turn, x, y)) {
            return "no combat unit";
        }
        unitSelected = this.gameMenuController.selectNonCombatUnit(turn, x, y);
        return "unit selected";
    }

    private int nextTurn(int turn) {
        if(turn != this.numberOfPlayers) {
            return turn++;
        } return 1;
    }

    public void printMap(int mainX, int mainY) {
        String[][][] linesOfHexagons = new String[5][6][6];
        for (int i = -2; i < 3; i++) {
            for (int j = -2; j < 4; j++) {
                int x = mainX + i;
                int y = mainY + j;
                Tile tile = GameDatabase.getBlockByXandY(x, y);
                if (tile == null || i == -2 || i == 3) {
                    for (int k = 0; k < 3; k++) {
                        linesOfHexagons[i + 2][j + 2][k] = "";
                        for (int k1 = 0; k1 < 2 * (k + 4); k1++)
                            linesOfHexagons[i + 2][j + 2][k] += " ";
                    }
                    for (int k = 3; k < 6; k++) {
                        linesOfHexagons[i + 2][j + 2][k] = "";
                        for (int k1 = 0; k1 < 2 * (9 - k); k1++)
                            linesOfHexagons[i + 2][j + 2][k] += " ";
                    }
                } else {
                    //TODO set lines of hexagons with format
                    String colorOfHexagon = "";
                    if (tile.getType().equals("Desert") || tile.getType().equals("Meadow")) {
                        colorOfHexagon = Colors.ANSI_YELLOW_BACKGROUND;
                    } else if (tile.getType().equals("Plain") || tile.getType().equals("Hill")) {
                        colorOfHexagon = Colors.ANSI_GREEN_BACKGROUND;
                    } else if (tile.getType().equals("Mountain") || tile.getType().equals("Snow")) {
                        colorOfHexagon = Colors.ANSI_WHITE_BACKGROUND;
                    } else if (tile.getType().equals("Ocean")) {
                        colorOfHexagon = Colors.ANSI_BLUE_BACKGROUND;
                    } else if (tile.getType().equals("Tundra")) {
                        colorOfHexagon = Colors.ANSI_RED_BACKGROUND;
                    }
                    linesOfHexagons[i + 2][j + 2][0] = Colors.ANSI_RESET + colorOfHexagon +
                            (tile.getType().substring(0, 4) + "       ").substring(0, 8);
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
                            ("                ").substring(0,12);//TODO Unit to print
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
                        linesOfHexagons[i + 2][j + 2][5] = Colors.ANSI_RESET + colorOfHexagon + Colors.ANSI_CYAN + "________";
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
                Tile tile = GameDatabase.getBlockByXandY(counterOfHex[j] + mainX - 2, mainY + j - 2);
                if (flag == 1 && tile.isRiverByNumberOfEdge(5)
                        || flag == 0 && tile.isRiverByNumberOfEdge(4)) {//Condition to river
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
