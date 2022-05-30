package Civilization.Model;

import Civilization.Database.UserDatabase;

public class LoginMenuModel {

    public void userCreate(User newUser) {
        UserDatabase.addUser(newUser);
    }
}
