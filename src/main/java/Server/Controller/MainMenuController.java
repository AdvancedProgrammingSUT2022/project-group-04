package Server.Controller;

import Server.UserDatabase;
import Client.Model.MainMenuModel;
import Server.User;

public class MainMenuController {

    private MainMenuModel mainMenuModel;

    public MainMenuController(MainMenuModel mainMenuModel) {
        this.mainMenuModel = mainMenuModel;
    }

    /**
     * @param menuName
     * @return true if the menuName was Profile Menu
     */
    public boolean isMenuValid(String menuName) {
        if (menuName.equals("Profile Menu") || menuName.equals("Game Menu")) {
            return true;
        }
        return false;
    }

    /**
     * @param username
     * @return true if the user was existed
     */
    public boolean isUserExists(String username) {
        User user = UserDatabase.getUserByUsername(username);
        if (user == null) {
            return false;
        }
        return true;
    }
}
