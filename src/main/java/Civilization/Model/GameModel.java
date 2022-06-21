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

    public void startGame(ArrayList<String> users) {

        ArrayList<Civilization> players = new ArrayList<Civilization>();
        for (int i = 1; i < users.size(); i++) {
            Civilization civilization = new Civilization(users.get(i), Objects.requireNonNull(UserDatabase.getUserByUsername(users.get(i))).getNickname());
            players.add(civilization);
        }

        GameDatabase.setPlayers(players);
        GameDatabase.generateMap(users.size());

    }

}
