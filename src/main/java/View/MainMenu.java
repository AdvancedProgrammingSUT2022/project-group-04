package View;

import Controller.GameMenuController;
import Controller.MainMenuController;
import Controller.ProfileMenuController;
import Database.GameDatabase;
import Model.Civilization;
import Model.GameModel;
import Model.ProfileMenuModel;
import Model.User;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu extends Menu{

    private MainMenuController mainMenuController;
    private static final String PLAY_GAME = "play game";

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
            } else if((matcher = getCommandMatcher(command, PLAY_GAME)) != null) {
                playGame(matcher, scanner);
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
     * @param scanner
     */
    private void playGame(Matcher matcher, Scanner scanner) {

        ArrayList<Civilization> players = new ArrayList<Civilization>();
        // find Users;
        // create Civilizations
        enterGameMenu(players, scanner);

    }

    /**
     * enters game menu
     * @param players
     * @param scanner
     */
    private void enterGameMenu(ArrayList<Civilization> players, Scanner scanner) {
        GameModel gameModel = new GameModel();
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        GameMenu gameMenu = new GameMenu(gameMenuController);

        GameDatabase.setPlayers(players);
        gameMenu.run(scanner);

    }
}
