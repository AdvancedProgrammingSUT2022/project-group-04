package Civilization.View.Components;

import Civilization.Model.User;
import Civilization.View.GraphicalBases;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

public class Account {

    public static ArrayList<Account> accounts = new ArrayList<Account>();

    private User user;
    private String avatarURL;

    public User getUser() {
        return user;
    }

    public String getAvatarURL() {
        return toURL(avatarURL);
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = toJson(avatarURL);
    }

    public Account(User user) {
        this.user = user;
        this.avatarURL = assignAvatar();
    }

    private String assignAvatar() {
        Random random = new Random();
        int avatarIndex = random.nextInt(GraphicalBases.NUMBER_OF_AVATARS) + 1;
        return toJson("/pics/Avatars/" + avatarIndex + ".png");
    }

    private String toJson(String avatarURL) {
        String tempAvatarURL = avatarURL.replace("/", "*");
        return tempAvatarURL.replace(":", "^");
    }

    private String toURL(String avatarURL) {
        String tempAvatarURL = avatarURL.replace("*", "/");
        return tempAvatarURL.replace("^", ":");
    }

    public static void readAccounts(String fileName) throws IOException {
        Gson gson = new Gson();
        Path userPath = Paths.get(fileName);
        if (!userPath.toFile().isFile()) {
            return;
        }
        Reader reader = Files.newBufferedReader(userPath);
        ArrayList<?> jsonStringUserDatabase = gson.fromJson(reader, ArrayList.class);
        reader.close();

        for (int i = 0; i < jsonStringUserDatabase.size(); i++) {
            Account tempAccount = gson.fromJson(jsonStringUserDatabase.get(i).toString(), Account.class);
            Account.accounts.add(tempAccount);
        }
    }

    public static void writeAccounts(String fileName) throws IOException {
        Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
        Path userPath = Paths.get(fileName);
        Writer writer = Files.newBufferedWriter(userPath);
        gsonBuilder.toJson(Account.accounts, writer);
        writer.close();
    }
}
