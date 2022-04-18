package Controller;

import Model.LoginMenuModel;
import Model.User;
import Database.UserDatabase;

public class LoginMenuController {

    private LoginMenuModel loginMenuModel;
    private UserController userController;

    public LoginMenuController(LoginMenuModel loginMenuModel) {
        this.loginMenuModel = loginMenuModel;
        this.userController = new UserController();
    }

    public boolean isUsernameUnique(String username) {
        User user = UserDatabase.getUserByUsername(username);
        if(user == null) {
            return true;
        }
        return false;
    }

    public boolean isNicknameUnique(String nickname) {
        User user = UserDatabase.getUserByNickname(nickname);
        if(user == null) {
            return true;
        }
        return false;
    }

    public void userCreate(String username, String nickname, String password) {
        User newUser = new User(username, nickname, password);
        this.loginMenuModel.userCreate(newUser);
    }

    public boolean isUserExists(String username) {
        User user = UserDatabase.getUserByUsername(username);
        if(user == null) {
            return false;
        }
        return true;
    }

    public boolean isPasswordCorrect(String username, String password) {
        return this.userController.isPasswordCorrect(username, password);
    }
}
