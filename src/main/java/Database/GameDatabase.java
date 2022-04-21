package Database;

import Model.Civilization;

import java.util.ArrayList;

public class GameDatabase {

    public static ArrayList<Civilization> players;

    public static void setPlayers(ArrayList<Civilization> players) {
        GameDatabase.players = players;
    }

    /**
     *
     * @param civilizationName
     * @return selected civilization
     */
    public static Civilization getCivilizationByName(String civilizationName) {
        for (int i = 0; i < GameDatabase.players.size(); i++) {
            if(GameDatabase.players.get(i).getUsername().equals(civilizationName)) {
                return GameDatabase.players.get(i);
            }
        }
        return null;
    }
}
