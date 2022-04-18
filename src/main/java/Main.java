import java.util.Scanner;

import Controller.LoginMenuController;
import Model.LoginMenuModel;
import Model.User;
import View.LoginMenu;

public class Main{


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        LoginMenuModel loginMenuModel = new LoginMenuModel();
        LoginMenuController loginMenuController = new LoginMenuController(loginMenuModel);
        LoginMenu loginMenu = new LoginMenu(loginMenuController);

        User loggedinUser;

        while(true) {
            loggedinUser = loginMenu.run(scanner);
            if(loggedinUser == null) {
                break;
            }
        }


    }
}
