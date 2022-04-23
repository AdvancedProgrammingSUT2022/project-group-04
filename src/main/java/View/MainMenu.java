package View;

import Controller.GameMenuController;
import Controller.MainMenuController;
import Controller.ProfileMenuController;
import Database.GameDatabase;
import Database.UserDatabase;
import Model.Civilization;
import Model.GameModel;
import Model.ProfileMenuModel;
import Model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu extends Menu{

    private MainMenuController mainMenuController;
    private static final String PLAY_GAME = "play game(?<command>( --player\\d+ \\S+)+)";
    private static final String SHORT_PLAY_GAME = "play game(?<command>( -p\\d+ \\S+)+)";

    public MainMenu(MainMenuController mainMenuController) {
        this.mainMenuController = mainMenuController;
    }

    public void run(Scanner scanner, User loggedinUser) {
        String command;
        while(true) {
            Matcher matcher;
            command = scanner.nextLine();
            if(command.equals("user logout")) {
                System.out.println("user logged out successfully!");
                break;
            } else if((matcher = getCommandMatcher(command, MENU_SHOW)) != null) {
                System.out.println(menuShow(matcher));
            } else if((matcher = getCommandMatcher(command, MENU_ENTER)) != null) {
                String result = menuEnter(matcher);
                if(result != null) {
                    System.out.println(result);
                } else {
                    enterProfileMenu(scanner, loggedinUser);
                }
            } else if(((matcher = getCommandMatcher(command, PLAY_GAME)) != null)
                        || ((matcher = getCommandMatcher(command, SHORT_PLAY_GAME)) != null)) {
                String result = playGame(matcher);
                System.out.println(result);
                if(result.startsWith("game")) {
                    enterGameMenu(scanner, matcher);
                }
            } else {
                System.out.println(command);
                System.out.println("invalid command");
            }
        }
    }

    @Override
    public String menuShow(Matcher matcher) {
        return "Main Menu";
    }

    private String menuEnter(Matcher matcher) {
        String menuName = matcher.group("MenuName");
        if(!this.mainMenuController.isMenuValid(menuName)) {
            return "menu navigation is not possible";
        }
        if(menuName.equals("Game Menu")) {
            return "you must use play game command.";
        }
        return null;
    }

    /**
     * entered profile menu
     * @param scanner
     * @param loggedinUser
     */
    private void enterProfileMenu(Scanner scanner, User loggedinUser) {
        ProfileMenuModel profileMenuModel = new ProfileMenuModel();
        ProfileMenuController profileMenuController = new ProfileMenuController(profileMenuModel);
        ProfileMenu profileMenu = new ProfileMenu(profileMenuController);

        profileMenu.run(scanner, loggedinUser);
    }

    /**
     * makes a new game
     * @param matcher
     */
    private String playGame(Matcher matcher) {

        String[] splitCommand = matcher.group("command").split(" --player\\d+ ");
        for (int i = 1; i < splitCommand.length; i++) {
            if(!this.mainMenuController.isUserExists(splitCommand[i])) {
                return "at least one username dose not exists.";
            }
        }
        return "game started. good luck!";

    }

    /**
     * enters game menu
     * @param scanner
     * @param matcher
     */
    private void enterGameMenu(Scanner scanner, Matcher matcher) {

        String[] splitCommand = matcher.group("command").split(" --player\\d+ ");
        GameDatabase.generateMap(splitCommand.length - 1);
        ArrayList<Civilization> players = new ArrayList<Civilization>();

        for (int i = 1; i < splitCommand.length; i++) {
            Civilization civilization = new Civilization(splitCommand[i], UserDatabase.getUserByUsername(splitCommand[i]).getNickname());
            players.add(civilization);
        }

        GameModel gameModel = new GameModel();
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        GameMenu gameMenu = new GameMenu(gameMenuController);

        GameDatabase.setPlayers(players);
        gameMenu.run(scanner);

    }
}
