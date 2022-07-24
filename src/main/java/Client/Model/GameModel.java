package Client.Model;

import Civilization.Database.GameDatabase;
import Server.UserDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class GameModel {

    public static boolean autoSave = false;
    public static boolean isGame = false;

    public void startGame(ArrayList<String> users) throws IOException {

        ArrayList<Civilization> players = new ArrayList<Civilization>();
        for (String user : users) {
            Civilization civilization = new Civilization(user, Objects.requireNonNull(UserDatabase.getUserByUsername(user)).getNickname());
            players.add(civilization);
        }


        for (int i=0;i< users.size();i++) GameDatabase.setPlayers(players,users.get(i));
        System.out.println("worked");

        GameDatabase.generateMap(users.size());
        //GameDatabase.getMapFromServer();
        System.out.println("haha worked");
        GameDatabase.generateRuin();
        System.out.println("haha worked");
    }

}
