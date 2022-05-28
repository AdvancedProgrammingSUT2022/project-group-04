package Model;

import Database.UserDatabase;

public class LoginMenuModel {

    public void userCreate(User newUser) {
        UserDatabase.addUser(newUser);
    }
}
