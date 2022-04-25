package Model;

import Database.UserDatabase;

public class ProfileMenuModel {

    /**
     * changes nickname
     *
     * @param loggedinUser
     * @param nickname
     */
    public void changeNickname(User loggedinUser, String nickname) {
        String username = loggedinUser.getUsername();
        UserDatabase.getUserByUsername(username).changeNickname(nickname);
    }

    /**
     * changes password
     *
     * @param loggedinUser
     * @param password
     */
    public void changePassword(User loggedinUser, String password) {
        String username = loggedinUser.getUsername();
        UserDatabase.getUserByUsername(username).changePassword(password);
    }
}
