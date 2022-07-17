package Civilization.Model;

import Civilization.Controller.CombatController;
import Civilization.Controller.GameMenuController;
import Civilization.Database.GameDatabase;
import Civilization.Database.UserDatabase;
import Civilization.View.GameMenu;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;

public class GameModel {

    public static boolean autoSave = false;
    public static boolean isGame = false;

    public void startGame(ArrayList<String> users) {

        ArrayList<Civilization> players = new ArrayList<Civilization>();
        for (String user : users) {
            Civilization civilization = new Civilization(user, Objects.requireNonNull(UserDatabase.getUserByUsername(user)).getNickname());
            players.add(civilization);
        }

        GameDatabase.setPlayers(players);
        GameDatabase.generateMap(users.size());
        GameDatabase.generateRuin();

    }

}
