package Database;

import java.util.ArrayList;

import Model.User;

public class UserDatabase {

    private static ArrayList<User> users = new ArrayList<>();

    public static User getUserByUsername(String username) {
        for(int i=0; i<UserDatabase.users.size(); i++) {
            if(UserDatabase.users.get(i).getUsername().equals(username)) {
                return UserDatabase.users.get(i);
            }
        }
        return null;
    }

    public static User getUserByNickname(String nickname) {
        for(int i=0; i<UserDatabase.users.size(); i++) {
            if(UserDatabase.users.get(i).getNickname().equals(nickname)) {
                return UserDatabase.users.get(i);
            }
        }
        return null;
    }

    public static void addUser(User newUser) {
        UserDatabase.users.add(newUser);
    }

    public static ArrayList<User> getAllUsers() {
        return UserDatabase.users;
    }
}
