package Controller;

import Model.User;
import Database.UserDatabase;

public class UserController {

    public boolean isPasswordCorrect(String username, String password) {
        User user = UserDatabase.getUserByUsername(username);
        if(user == null) {
            return false;
        }
        if(user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }
}
