package Model;

import Database.UserDatabase;

public class ProfileMenuModel {

    public void changeNickname(User loggedinUser, String nickname) {
        String username = loggedinUser.getUsername();
        UserDatabase.getUserByUsername(username).changeNickname(nickname);
    }

    public void changePassword(User loggedinUser, String password) {
        String username = loggedinUser.getUsername();
        UserDatabase.getUserByUsername(username).changePassword(password);
    }
}
