package Civilization.Model;

import Server.UserDatabase;
import Server.User;

public class ProfileMenuModel {

    /**
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
        //System.out.println("in model");
        //System.out.println(loggedInUser);
        String username = loggedInUser.getUsername();
        //System.out.println(loggedInUser);
        //System.out.println(username);
        User user = UserDatabase.getUserByUsername(username);
        //System.out.println(user);
        user.changeNickname(nickname);
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
