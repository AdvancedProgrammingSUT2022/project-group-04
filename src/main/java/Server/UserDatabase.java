package Server;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import Server.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class UserDatabase {


    /**
     * @param username
     * @return selected user
     */
    public static User getUserByUsername(String username) {
        for (int i = 0; i < User.users.size(); i++) {
            if (User.users.get(i).getUsername().equals(username)) {
                return User.users.get(i);
            }
        }
        return null;
    }

    /**
     * @param nickname
     * @return selected user
     */
    public static User getUserByNickname(String nickname) {
        for (int i = 0; i < User.users.size(); i++) {
            if (User.users.get(i).getNickname().equals(nickname)) {
                return User.users.get(i);
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
        User.users.add(newUser);
    }

    /**
     * @return all of users in database
     */
    public static ArrayList<User> getAllUsers() {
        return User.users;
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
            User.users.add(tempUser);
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
        gsonBuilder.toJson(User.users, writer);
        writer.close();
    }
}
