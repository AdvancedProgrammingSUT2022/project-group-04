package Model;

import Database.UserDatabase;

public class ProfileMenuModel {

    /**
     *
     * @param loggedInUser
     * @param username
     */
    public void changeUsername(User loggedInUser, String username) {
        loggedInUser.changeUsername(username);
    }

    /**
     * changes nickname
     *
     * @param loggedInUser
     * @param nickname
     */
    public void changeNickname(User loggedInUser, String nickname) {
        String username = loggedInUser.getUsername();
        UserDatabase.getUserByUsername(username).changeNickname(nickname);
    }

    /**
     * changes password
     *
     * @param loggedInUser
     * @param password
     */
    public void changePassword(User loggedInUser, String password) {
        String username = loggedInUser.getUsername();
        UserDatabase.getUserByUsername(username).changePassword(password);
    }
}
