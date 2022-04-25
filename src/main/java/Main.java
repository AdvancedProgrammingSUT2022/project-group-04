import java.io.IOException;
import java.util.Scanner;

import Controller.LoginMenuController;
import Controller.MainMenuController;
import Database.UserDatabase;
import Model.LoginMenuModel;
import Model.MainMenuModel;
import Model.User;
import View.LoginMenu;
import View.MainMenu;

public class Main {


    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        UserDatabase.readFromFile("UserDatabase.json");

        LoginMenuModel loginMenuModel = new LoginMenuModel();
        LoginMenuController loginMenuController = new LoginMenuController(loginMenuModel);
        LoginMenu loginMenu = new LoginMenu(loginMenuController);

        MainMenuModel mainMenuModel = new MainMenuModel();
        MainMenuController mainMenuController = new MainMenuController(mainMenuModel);
        MainMenu mainMenu = new MainMenu(mainMenuController);

        User loggedinUser;

        while (true) {
            loggedinUser = loginMenu.run(scanner);
            if (loggedinUser == null) {
                break;
            }
            mainMenu.run(scanner, loggedinUser);
        }

        UserDatabase.writeInFile("UserDatabase.json");


    }
}
