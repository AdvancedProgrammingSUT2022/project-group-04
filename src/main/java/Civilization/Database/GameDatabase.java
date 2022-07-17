package Civilization.Database;

import Civilization.Controller.CopyOfGameDatabase;
import Civilization.Controller.SavingGame;
import Civilization.Controller.GameMenuController;
import Civilization.Model.*;
import Civilization.View.FXMLControllers.GameFXMLController;
import Civilization.View.Transitions.TransitionDatabase;
import Client.Client;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.*;
import java.lang.reflect.Array;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameDatabase {

    public static ArrayList<Civilization> players = new ArrayList<Civilization>();
    public static ArrayList<Tile> map = new ArrayList<Tile>();

    public static int length = 50;
    public static int width = 10;

    public static int turn = 0;
    public static int year = 0;
    public static boolean cheated = false;
    public static Civilization cheatedCivilization = null;

    public static Socket socket1;
    public static DataInputStream dataInputStream1;
    public static DataOutputStream dataOutputStream1;

    public static JSONObject input;

    public void setSocket(Socket socket, DataOutputStream dataOutputStream, DataInputStream dataInputStream) {
        socket1 = socket;
        dataInputStream1 = dataInputStream;
        dataOutputStream1 = dataOutputStream;
    }

    public static void setStaticFields(ArrayList<Civilization> civilizations, ArrayList<Tile> tiles, int tool, int arz, int nobat
            , int sal, boolean taghalobKarde, Civilization civilizationtaghalob) {
        players = civilizations;
        map = tiles;
        length = tool;
        width = arz;
        year = sal;
        turn = nobat;
        cheated = taghalobKarde;
        cheatedCivilization = civilizationtaghalob;
    }

    public static Civilization getCivilizationByUnit(Unit unit) throws IOException {
        input = new JSONObject();
        input.put("menu type", "Game Database");
        input.put("action", "getCivilizationByUnit");
        input.put("unit", unit);
        JSONObject serverResponse = sendToServer();
        return (Civilization) serverResponse.get("civilization");
    }

    private static JSONObject sendToServer() throws IOException {
        dataOutputStream1.writeUTF(input.toString());
        dataOutputStream1.flush();
        String message = Client.dataInputStream1.readUTF();
        return new JSONObject(message);
        // TODO update data here based on message

    }

    public static void generateRuin() throws IOException {
        Random random = new Random();
        for (Tile tile : GameDatabase.map) {
            if (getCivilizationByTile(tile) == null) {
                int ruin = random.nextInt(500);
                if (ruin == 12) {
                    tile.setRuin(new Ruin());
                }
            }
        }
    }


    public static void setPlayers(ArrayList<Civilization> players) throws IOException {
        input = new JSONObject();
        input.put("menu type", "Game Database");
        input.put("action", "setPlayers");
        input.put("players", players);
        JSONObject serverResponse = sendToServer();
        //TODO SYSout??
        return;
    }

    /**
     * @param civilizationName
     * @return selected civilization
     */
    public static Civilization getCivilizationByUsername(String civilizationName) throws IOException {
        input = new JSONObject();
        input.put("menu type", "Game Database");
        input.put("action", "getCivilizationByUsername");
        input.put("civilization name",civilizationName);
        JSONObject serverResponse = sendToServer();
        return (Civilization) serverResponse.get("civilization");
    }

    public static Civilization getCivilizationByNickname(String civilizationName) throws IOException {
        input = new JSONObject();
        input.put("menu type", "Game Database");
        input.put("action", "getCivilizationByNickname");
        input.put("civilization name",civilizationName);
        JSONObject serverResponse = sendToServer();
        return (Civilization) serverResponse.get("civilization");
    }


    public static City getCityByName(String cityName) throws IOException {
        input = new JSONObject();
        input.put("menu type", "Game Database");
        input.put("action", "getCityByName");
        input.put("city name",cityName);
        JSONObject serverResponse = sendToServer();
        return (City) serverResponse.get("city");
    }

    public static City getCityByXAndY(int x, int y) throws IOException {
        input = new JSONObject();
        input.put("menu type", "Game Database");
        input.put("action", "getCityByXAndY");
        input.put("x",x);
        input.put("y",y);
        JSONObject serverResponse = sendToServer();
        return (City) serverResponse.get("city");
    }

    public static ArrayList<Tile> getMap() {
        return GameDatabase.map;
    }


    public static Tile getTileByXAndY(int x, int y) throws IOException {
        input = new JSONObject();
        input.put("menu type", "Game Database");
        input.put("action", "getTileByXAndY");
        input.put("x",x);
        input.put("y",y);
        JSONObject serverResponse = sendToServer();
        return (Tile) serverResponse.get("tile");
    }

    public static boolean isTileForACity(Tile tile) throws IOException {
        input = new JSONObject();
        input.put("menu type", "Game Database");
        input.put("action", "isTileForACity");
        input.put("Tile",tile);
        JSONObject serverResponse = sendToServer();
        return (boolean) serverResponse.get("isIt?");
    }

    public static Civilization getCivilizationByTile(Tile tile) throws IOException {
        input = new JSONObject();
        input.put("menu type", "Game Database");
        input.put("action", "getCivilizationByTile");
        input.put("Tile",tile);
        JSONObject serverResponse = sendToServer();
        return (Civilization) serverResponse.get("civilization");
    }

    public static int getCivilizationIndex(String civilizationName) throws IOException {
        input = new JSONObject();
        input.put("menu type", "Game Database");
        input.put("action", "getCivilizationIndex");
        input.put("civilization name",civilizationName);
        JSONObject serverResponse = sendToServer();
        return (int) serverResponse.get("index");
    }

    public static void generateMap(int numberOfPlayers) throws IOException {
        input = new JSONObject();
        input.put("menu type", "Game Database");
        input.put("action", "generate map");
        input.put("number of players",numberOfPlayers);
        JSONObject serverResponse = sendToServer();
        players = (ArrayList<Civilization>) serverResponse.get("players");
        map = (ArrayList<Tile>) serverResponse.get("tiles");
    }

    public static Civilization getCivilizationForCity(String cityName) throws IOException {
        input = new JSONObject();
        input.put("menu type", "Game Database");
        input.put("action", "getCivilizationIndex");
        input.put("city name",cityName);
        JSONObject serverResponse = sendToServer();
        return (Civilization) serverResponse.get("civilization");
    }

    public static void nextTurn() throws IOException {
        setTurn(calculateNextTurn());
        for (Civilization player : GameDatabase.players) {
            player.nextTurn();
        }
    }

    private static int calculateNextTurn() {
        if (turn != GameDatabase.players.size() - 1) {
            return turn + 1;
        }
        year++;
        return 0;
    }

    public static int getTurn() {
        return turn;
    }

    public static Civilization getCivilizationByTurn(int turn) {
        return GameDatabase.players.get(turn);
    }

    public static Tile findTileBySettler(Settler settler) {
        for (Tile tile : map) {
            ArrayList<Unit> soldiers = tile.getUnits();
            for (Unit soldier : soldiers) {
                if (soldier.getUnitType().equals("Settler")
                        && settler == soldier) {
                    return tile;
                }
            }
        }
        return null;
    }

    public static Tile findTileByWorker(Worker worker) {
        for (Tile tile : map) {
            ArrayList<Unit> soldiers = tile.getUnits();
            for (Unit soldier : soldiers) {
                if (soldier.getUnitType().equals("worker")
                        && worker == soldier) {
                    return tile;
                }
            }
        }
        return null;
    }

    public static Tile findTileByCitizen(Citizen citizen) {
        for (Tile tile : map) {
            ArrayList<Citizen> citizens = tile.getCitizens();
            if (citizens.contains(citizen)) return tile;
        }
        return null;
    }

    public static ArrayList<Civilization> getPlayers() {
        return GameDatabase.players;
    }

    public static boolean isTileInCivilization(Tile tile, Civilization civilization) {
        if (civilization.isTileInCivilization(tile.getX(), tile.getY())) {
            return true;
        }
        return false;
    }

    public static void setTurn(int newTurn) {
        turn = newTurn;
        GameFXMLController.turn = turn;
    }

    public static User getUserForCivilization(String civilizationName) throws IOException {
        String username = Objects.requireNonNull(getCivilizationByNickname(civilizationName)).getUsername();
        return UserDatabase.getUserByUsername(username);
    }

//    public static void saveGame() throws IOException {
//        SavingData savingData = new SavingData(length, width, turn, year, players, map);
//
//        // saving information;
//        Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
//        Path userPath = Paths.get("savedMap.json");
//        Writer writer = Files.newBufferedWriter(userPath);
//        gsonBuilder.toJson(savingData, writer);
//        writer.close();
//    }

    public static Civilization checkIfWin() {
        if (GameDatabase.year >= 2050) {
            return GameDatabase.getCivilizationByTurn(GameDatabase.getTurn());
        }
        if (GameDatabase.cheated && GameDatabase.cheatedCivilization != null) {
            return cheatedCivilization;
        }
        if (players.size() == 1) {
            return players.get(0);
        } else {
            for (Civilization civilization : players) {
                if (civilization.getTechnologies().size() == GlobalVariables.TECHNOLOGIES.length) {
                    return civilization;
                }
            }
        }
        return null;
    }


    public static void saveGame() {
        for (int i = 0; i < 10; i++) {
            System.err.println("Saving game code has been commented by Sepehr Mizanian. Please uncomment it in GameDatabase.saveGame() function.");
        }
//        CopyOfGameDatabase copy = new CopyOfGameDatabase(players,map,length,width,turn,year,cheated,cheatedCivilization);
//        SavingGame.saveGame(copy);
    }

    public static Civilization getLastCivilization() {
        int turn = GameDatabase.getTurn();
        if (turn == 0) {
            return GameDatabase.getCivilizationByTurn(GameDatabase.players.size() - 1);
        }
        return GameDatabase.getCivilizationByTurn(turn - 1);
    }
}
