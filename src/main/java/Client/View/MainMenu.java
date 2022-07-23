package Client.View;

import Server.Controller.CombatController;
import Server.Controller.GameMenuController;
import Server.Controller.MainMenuController;
import Server.Controller.ProfileMenuController;
import Civilization.Database.GameDatabase;
import Server.UserDatabase;
import Client.Model.Civilization;
import Client.Model.GameModel;
import Client.Model.ProfileMenuModel;
import Server.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu extends Menu {

    private MainMenuController mainMenuController;
    private static final String PLAY_GAME = "play game(?<command>( --player\\d+ \\S+)+)";
    private static final String SHORT_PLAY_GAME = "play game(?<command>( -p\\d+ \\S+)+)";

    public MainMenu(MainMenuController mainMenuController) {
        this.mainMenuController = mainMenuController;
    }

    public void run(Scanner scanner, User loggedinUser) throws IOException {
        String command;
        while (true) {
            Matcher matcher;
            command = scanner.nextLine();
            if (command.equals("user logout")) {
                System.out.println("user logged out successfully!");
                break;
            } else if ((matcher = getCommandMatcher(command, MENU_SHOW)) != null) {
                System.out.println(menuShow());
            } else if ((matcher = getCommandMatcher(command, MENU_EXIT)) != null) {
                System.out.println(menuExit(matcher));
            } else if ((matcher = getCommandMatcher(command, MENU_ENTER)) != null) {
                String result = menuEnter(matcher);
                if (result != null) {
                    System.out.println(result);
                } else {
                    enterProfileMenu(scanner, loggedinUser);
                }
            } else if (((matcher = getCommandMatcher(command, PLAY_GAME)) != null)
                    || ((matcher = getCommandMatcher(command, SHORT_PLAY_GAME)) != null)) {
                String result = playGame(matcher);
                System.out.println(result);
                if (result.startsWith("game")) {
                    enterGameMenu(scanner, matcher);
                }
            } else {
                System.out.println("invalid command");
            }
        }
    }

    @Override
    public String menuShow() {
        return "Main Menu";
    }

    public String menuEnter(Matcher matcher) {
        String menuName = matcher.group("MenuName");
        if (!this.mainMenuController.isMenuValid(menuName)) {
            return "menu navigation is not possible";
        }
        if (menuName.equals("Game Menu")) {
            return "you must use play game command.";
        }
        return null;
    }

    public String menuExit(Matcher matcher) {
        return "use user logout command";
    }

    /**
     * entered profile menu
     *
     * @param scanner
     * @param loggedInUser
     */
    public void enterProfileMenu(Scanner scanner, User loggedInUser) {
        ProfileMenuModel profileMenuModel = new ProfileMenuModel();
        ProfileMenuController profileMenuController = new ProfileMenuController(profileMenuModel);
        ProfileMenu profileMenu = new ProfileMenu(profileMenuController);
        profileMenu.run(scanner, loggedInUser);
    }

    /**
     * makes a new game
     *
     * @param matcher
     */
    public String playGame(Matcher matcher) {

        String[] splitCommand = matcher.group("command").split(" --player\\d+ ");
        if (splitCommand.length == 1) {
            splitCommand = matcher.group("command").split(" -p\\d+ ");
        }
        for (int i = 1; i < splitCommand.length; i++) {
            if (!this.mainMenuController.isUserExists(splitCommand[i])) {
                return "at least one username dose not exists.";
            }
        }
        if (splitCommand.length - 1 == 1) {
            return "you can't play with yourself!";
        }
        return "game started. good luck!";

    }

    /**
     * enters game menu
     *
     * @param scanner
     * @param matcher
     */
    public void enterGameMenu(Scanner scanner, Matcher matcher) throws IOException {

        String[] splitCommand = matcher.group("command").split(" --player\\d+ ");
        if (splitCommand.length == 1) {
            splitCommand = matcher.group("command").split(" -p\\d+ ");
        }

        ArrayList<Civilization> players = new ArrayList<Civilization>();
        for (int i = 1; i < splitCommand.length; i++) {
            Civilization civilization = new Civilization(splitCommand[i], UserDatabase.getUserByUsername(splitCommand[i]).getNickname());
            players.add(civilization);
        }

        GameModel gameModel = new GameModel();
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        CombatController combatController = new CombatController();
        GameMenu gameMenu = new GameMenu(gameMenuController, combatController);

        for (int i=0;i< players.size();i++) GameDatabase.setPlayers(players,players.get(i).getNickname());
        GameDatabase.generateMap(splitCommand.length - 1);

        for (int i = 0; i < GameDatabase.players.size(); i++) {
            System.out.println(GameDatabase.players.get(i).getNickname());
            for (int j = 0; j < GameDatabase.players.get(i).getTiles().size(); j++) {
                System.out.println(GameDatabase.players.get(i).getTiles().get(j).getX());
                System.out.println(GameDatabase.players.get(i).getTiles().get(j).getY());
            }
        }

        gameMenu.run(scanner);

    }
}
