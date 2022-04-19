package View;

import Controller.GameMenuController;
import Enums.MapAndGeneralCommandsOfGameMenu;

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
            } else if((matcher = MapAndGeneralCommandsOfGameMenu.getCommandMatcher(command, MapAndGeneralCommandsOfGameMenu.MENU_SHOW)) != null) {
                System.out.println(menuShow(matcher));
            } else if((matcher = MapAndGeneralCommandsOfGameMenu.getCommandMatcher(command, MapAndGeneralCommandsOfGameMenu.MENU_ENTER)) != null) {
                System.out.println(menuEnter(matcher));
            } else if((matcher = MapAndGeneralCommandsOfGameMenu.getCommandMatcher(command, MapAndGeneralCommandsOfGameMenu.MENU_EXIT)) != null) {
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
}
