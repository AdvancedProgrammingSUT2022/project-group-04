package Controller;

import Database.UserDatabase;
import Model.MainMenuModel;
import Model.User;

public class MainMenuController {

    private MainMenuModel mainMenuModel;

    public MainMenuController(MainMenuModel mainMenuModel) {
        this.mainMenuModel = mainMenuModel;
    }

    public boolean isMenuValid(String menuName) {
        if(menuName.equals("Profile Menu") || menuName.equals("Game Menu")) {
            return true;
        }
        return false;
    }

    public boolean isUserExists(String username) {
        User user = UserDatabase.getUserByUsername(username);
        if(user == null) {
            return false;
        }
        return true;
    }
}
