import java.util.Scanner;

import Controller.LoginMenuController;
import Controller.MainMenuController;
import Model.LoginMenuModel;
import Model.MainMenuModel;
import Model.User;
import View.LoginMenu;
import View.MainMenu;

public class Main{


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        LoginMenuModel loginMenuModel = new LoginMenuModel();
        LoginMenuController loginMenuController = new LoginMenuController(loginMenuModel);
        LoginMenu loginMenu = new LoginMenu(loginMenuController);

        MainMenuModel mainMenuModel = new MainMenuModel();
        MainMenuController mainMenuController = new MainMenuController(mainMenuModel);
        MainMenu mainMenu = new MainMenu(mainMenuController);

        User loggedinUser;

        while(true) {
            loggedinUser = loginMenu.run(scanner);
            if(loggedinUser == null) {
                break;
            }
            mainMenu.run(scanner, loggedinUser);
        }


    }
}
