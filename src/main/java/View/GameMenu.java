package View;

import Controller.GameMenuController;
import Database.GameDatabase;
//import Enums.MapAndGeneralCommandsOfGameMenu;
import Model.Tile;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu extends Menu{

    private GameMenuController gameMenuController;

    public GameMenu(GameMenuController gameMenuController) {
        this.gameMenuController = gameMenuController;
    }

    public void run(Scanner scanner) {
        String command;
        while(true) {
            Matcher matcher;
            command = scanner.nextLine();
            if(command.equals("menu exit")) {
                break;
            } else if((matcher = getCommandMatcher(command, MENU_SHOW)) != null) {
                System.out.println(menuShow(matcher));
            } else if((matcher = getCommandMatcher(command, MENU_ENTER)) != null) {
                System.out.println(menuEnter(matcher));
            } else if((matcher = getCommandMatcher(command, MENU_EXIT)) != null) {
                System.out.println(menuExit(matcher));
            } else {
                System.out.println("invalid command");
            }
        }
    }

    @Override
    public String menuShow(Matcher matcher) {
        return "Main Menu";
    }

    private String menuEnter(Matcher matcher) {
        return "menu navigation is not possible";
    }

    private String menuExit(Matcher matcher) {
        return "you must finish the game to exit";
    }

    public void printMap(Matcher matcher){
        int mainX = Integer.parseInt(matcher.group("x"));
        int mainY = Integer.parseInt(matcher.group("y"));
        String[][][] linesOfHexagons = new String[5][6][6];
        for (int i=-2;i<3;i++){
            for (int j=-2;j<4;j++){
                int x = mainX+i;
                int y = mainY+j;
                Tile tile = GameDatabase.getBlockByXandY(x,y);
                if (tile == null || i==-2 || i==3){
                    for (int k = 0; k < 3; k++) {
                        for (int k1 = 0; k1 < 2*(k+4);k1++)
                            linesOfHexagons[i + 2][j + 2][k] +=" ";
                    }
                    for (int k = 4; k < 6; k++) {
                        for (int k1 = 0; k1 < 2*(10-k);k1++)
                            linesOfHexagons[i + 2][j + 2][k] +=" ";
                    }
                }
                else {
                    //TODO set lines of hexagons with format
                    String colorOfHexagon;
                    if (tile.getType().equals("Desert") || tile.getType().equals("Meadow")){
                        colorOfHexagon = Colors.ANSI_YELLOW_BACKGROUND;
                    }
                    else if (tile.getType().equals("Plain") || tile.getType().equals("Hill")){
                        colorOfHexagon = Colors.ANSI_GREEN_BACKGROUND;
                    }
                    else if (tile.getType().equals("Mountain") || tile.getType().equals("Snow")){
                        colorOfHexagon = Colors.ANSI_WHITE_BACKGROUND;
                    }
                    else if (tile.getType().equals("Ocean")){
                        colorOfHexagon = Colors.ANSI_BLUE_BACKGROUND;
                    }
                    else if (tile.getType().equals("Tundra")){
                        colorOfHexagon = Colors.ANSI_RED_BACKGROUND;
                    }
                    linesOfHexagons[i + 2][j + 2][0] = colorOfHexagon+;
                    linesOfHexagons[i + 2][j + 2][1] = colorOfHexagon+;
                    linesOfHexagons[i + 2][j + 2][2] = colorOfHexagon+;
                    linesOfHexagons[i + 2][j + 2][3] = colorOfHexagon+;
                    linesOfHexagons[i + 2][j + 2][4] = colorOfHexagon+;
                    linesOfHexagons[i + 2][j + 2][5] = colorOfHexagon+"--------";
                }
            }
        }
        String[] lines = new String[30];
        int[] counterLine ={0,3,0,3,0,3};
        int[] counterOfHex = {1,0,1,0,1,0};
        for (int i=1;i<22;i++){
            int numberOfSpace = (i%6);
            String[] marz = new String[2];
            if (numberOfSpace<4 && numberOfSpace>0){
                marz[0]="\\";
                marz[1]="/";
            }
            else{
                marz[1]="\\";
                marz[0]="/";
            }
            if (numberOfSpace==0 || numberOfSpace==1) numberOfSpace=3;
            else if (numberOfSpace==2 || numberOfSpace==5) numberOfSpace=2;
            else if (numberOfSpace==4 || numberOfSpace==3) numberOfSpace=1;
            for (int j=0;j<numberOfSpace;j++) {
                lines[i] +=" ";
            }
            //set the i-th line
            int flag = 1;
            for (int j=0;j<6;j++) {
                lines[i] += marz[flag];
                lines[i] += linesOfHexagons[counterOfHex[j]][j][counterLine[j]];
                flag = 1 - flag;
            }
            //set arrays for next cycle
            for (int u=0;u<6;u++){
                counterLine[u] += 1;
                if (counterLine[u]==6) {
                    counterOfHex[u]+=1;
                    counterLine[u] = 0;
                }
            }
        }
        for (int i=0;i<23;i++){
            System.out.println(lines[i]);
        }
    }
}
