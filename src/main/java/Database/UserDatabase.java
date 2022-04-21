package Database;

import java.util.ArrayList;

import Model.User;

public class UserDatabase {

    private static ArrayList<User> users = new ArrayList<>();

    /**
     *
     * @param username
     * @return selected user
     */
    public static User getUserByUsername(String username) {
        for(int i=0; i<UserDatabase.users.size(); i++) {
            if(UserDatabase.users.get(i).getUsername().equals(username)) {
                return UserDatabase.users.get(i);
            }
        }
        return null;
    }

    /**
     *
     * @param nickname
     * @return selected user
     */
    public static User getUserByNickname(String nickname) {
        for(int i=0; i<UserDatabase.users.size(); i++) {
            if(UserDatabase.users.get(i).getNickname().equals(nickname)) {
                return UserDatabase.users.get(i);
            }
        }
        return null;
    }

    /**
     * adds a new user to users database
     * @param newUser
     */
    public static void addUser(User newUser) {
        UserDatabase.users.add(newUser);
    }

    /**
     *
     * @return all of users in database
     */
    public static ArrayList<User> getAllUsers() {
        return UserDatabase.users;
    }
}
