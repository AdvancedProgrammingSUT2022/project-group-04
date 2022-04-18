package View;

import Controller.MainMenuController;
import Controller.ProfileMenuController;
import Enums.LoginMenuCommands;
import Enums.MainMenuCommands;
import Model.ProfileMenuModel;
import Model.User;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu extends Menu{

    private MainMenuController mainMenuController;

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
            } else if((matcher = MainMenuCommands.getCommandMatcher(command, MainMenuCommands.MENU_SHOW)) != null) {
                System.out.println(menuShow(matcher));
            } else if((matcher = MainMenuCommands.getCommandMatcher(command, MainMenuCommands.MENU_ENTER)) != null) {
                String result = menuEnter(matcher);
                if(result != null) {
                    System.out.println(result);
                } else {
                    enterProfileMenu(scanner, loggedinUser);
                }
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

    private void enterProfileMenu(Scanner scanner, User loggedinUser) {
        ProfileMenuModel profileMenuModel = new ProfileMenuModel();
        ProfileMenuController profileMenuController = new ProfileMenuController(profileMenuModel);
        ProfileMenu profileMenu = new ProfileMenu(profileMenuController);

        profileMenu.run(scanner, loggedinUser);
    }
}
