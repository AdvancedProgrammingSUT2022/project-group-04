package Database;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import Model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class UserDatabase {

    private static ArrayList<User> users = new ArrayList<>();

    /**
     * @param username
     * @return selected user
     */
    public static User getUserByUsername(String username) {
        for (int i = 0; i < UserDatabase.users.size(); i++) {
            if (UserDatabase.users.get(i).getUsername().equals(username)) {
                return UserDatabase.users.get(i);
            }
        }
        return null;
    }

    /**
     * @param nickname
     * @return selected user
     */
    public static User getUserByNickname(String nickname) {
        for (int i = 0; i < UserDatabase.users.size(); i++) {
            if (UserDatabase.users.get(i).getNickname().equals(nickname)) {
                return UserDatabase.users.get(i);
            }
        }
        return null;
    }

    /**
     * adds a new user to users database
     *
     * @param newUser
     */
    public static void addUser(User newUser) {
        UserDatabase.users.add(newUser);
    }

    /**
     * @return all of users in database
     */
    public static ArrayList<User> getAllUsers() {
        return UserDatabase.users;
    }

    /**
     * reads users from file
     *
     * @param fileName
     */
    public static void readFromFile(String fileName) throws IOException {
        Gson gson = new Gson();
        Path userPath = Paths.get(fileName);
        if (!userPath.toFile().isFile()) {
            return;
        }
        Reader reader = Files.newBufferedReader(userPath);
        ArrayList<?> jsonStringUserDatabase = gson.fromJson(reader, ArrayList.class);
        reader.close();

        for (int i = 0; i < jsonStringUserDatabase.size(); i++) {
            User tempUser = gson.fromJson(jsonStringUserDatabase.get(i).toString(), User.class);
            UserDatabase.users.add(tempUser);
        }
    }

    /**
     * writes in file
     *
     * @param fileName
     * @throws IOException
     */
    public static void writeInFile(String fileName) throws IOException {
        Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
        Path userPath = Paths.get(fileName);
        Writer writer = Files.newBufferedWriter(userPath);
        gsonBuilder.toJson(UserDatabase.users, writer);
        writer.close();
    }
}
