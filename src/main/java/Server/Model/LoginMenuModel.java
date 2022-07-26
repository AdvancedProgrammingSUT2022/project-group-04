package Server.Model;

import Server.UserDatabase;
import Server.User;

public class LoginMenuModel {

    public void userCreate(User newUser) {
        UserDatabase.addUser(newUser);
    }
}
