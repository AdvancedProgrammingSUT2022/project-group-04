package Database;

import Model.Civilization;

import java.util.ArrayList;

public class GameDatabase {

    public static ArrayList<Civilization> players;

    public static void setPlayers(ArrayList<Civilization> players) {
        GameDatabase.players = players;
    }
}
