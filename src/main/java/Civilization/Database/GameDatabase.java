package Civilization.Database;

import Client.Model.*;
import Server.Controller.GameMenuController;
import Client.View.Transitions.TransitionDatabase;
import Server.RequestPlayers;
import Server.User;
import Server.UserDatabase;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import org.json.JSONObject;


import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class GameDatabase {

    public static ArrayList<Civilization> players = new ArrayList<Civilization>();
    public static ArrayList<Tile> map = new ArrayList<Tile>();

    public static int length = 12;
    public static int width = 12;

    public static User you;

    public static int turn = 0;
    public static int year = 0;
    public static boolean cheated = false;
    public static Civilization cheatedCivilization = null;

    public static Socket socket1;
    public static DataInputStream dataInputStream1;
    public static DataOutputStream dataOutputStream1;

    public static boolean isYourTurn = false;

    public static JSONObject input;
    public static boolean mapTransferStarted = false;

    public static void setSocket(Socket socket, DataOutputStream dataOutputStream, DataInputStream dataInputStream) {
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
//        XStream xStream = new XStream(); TODO change the protocol
//        RequestPlayers requestPlayers = new RequestPlayers();
//        requestPlayers.unit = unit;
//        Object sth = sendToServer(xStream.toXML(requestPlayers), "getCivilizationByUnit");
//        JSONObject serverResponse = sendToServer();
//        return (Civilization) serverResponse.get("civilization");
        GameMenuController gameMenuController = new GameMenuController(new GameModel());
        for (Civilization player : players) {
            if (gameMenuController.isUnitForThisCivilization(getCivilizationIndex(player.getNickname()), unit)) {
                return player;
            }
        }
        return null;
    }

    private static JSONObject sendToServer() throws IOException {
        dataOutputStream1.writeUTF(input.toString());
        dataOutputStream1.flush();
        String message = dataInputStream1.readUTF();
        return new JSONObject(message);
        // TODO update data here based on message
    }

    private static RequestPlayers readAndCastResponse(String s) throws IOException {
        XStream xStream = new XStream();
        FileWriter fileWriter;
        System.out.println("wot wot?");
        if (Files.exists(Paths.get("clientResponse/response.xml")))
            fileWriter = new FileWriter("clientResponse/response.xml");
        else {
            new File("clientResponse").mkdir();
            fileWriter = new FileWriter("clientResponse/response.xml");
        }
        fileWriter.write(s);
        fileWriter.close();
        ///
        String xml = new String(Files.readAllBytes(Paths.get("clientResponse/response.xml")));
        xStream.addPermission(AnyTypePermission.ANY);
        if (xml.length() != 0) {
            Object obh = xStream.fromXML(xml);
            RequestPlayers req = (RequestPlayers) obh;
            return req;
        }
        return null;
    }

    private static RequestPlayers sendToServer(String s, String functionName) throws IOException {
        dataOutputStream1.writeUTF("!!!" + functionName + s);
        System.out.println(4321);
        dataOutputStream1.flush();
        System.out.println(43210);
        byte[] requestToByte = new byte[dataInputStream1.readInt()];
        dataInputStream1.readFully(requestToByte);
        String response = new String(requestToByte, StandardCharsets.UTF_8);
        System.out.println(response);
        System.out.println(432);
        RequestPlayers requestPlayers = readAndCastResponse(response);
        System.out.println(48885);
        GameDatabase.players = requestPlayers.players;
        System.out.println(485);
        GameDatabase.map = requestPlayers.tiles;
        System.out.println(469);
        return null;
    }

    public static void generateRuin() throws IOException {
        sendToServer(null, "generateRuin");
        GameDatabase.mapTransferStarted = true;
//        Random random = new Random();
//        for (Tile tile : map) {
//            if (getCivilizationByTile(tile) == null) {
//                int ruin = random.nextInt(500);
//                if (ruin == 12) {
//                    tile.setRuin(new Ruin());
//                }
//            }
//        }
//        sendMapToServer();
    }

    private static void sendMapToServer() throws IOException {
        System.out.println("entered sent Map to server");
        RequestPlayers requestPlayers = new RequestPlayers();
        requestPlayers.tiles = GameDatabase.map;
        requestPlayers.players = GameDatabase.players;
        requestPlayers.x = GameDatabase.turn;
        XStream xStream = new XStream();
        byte[] requestToBytes = xStream.toXML(requestPlayers).getBytes(StandardCharsets.UTF_8);
        //System.out.println(Arrays.toString(requestToBytes));
        dataOutputStream1.writeInt(requestToBytes.length);
        dataOutputStream1.flush();
        dataOutputStream1.write(requestToBytes);
        dataOutputStream1.flush();
        GameDatabase.mapTransferStarted = true;
    }

    public static void getMapFromServer() throws IOException {
        GameDatabase.mapTransferStarted = false;
        System.out.println("entered get Map from server");
        byte[] requestToByte = new byte[dataInputStream1.readInt()];
        System.out.println(12);
        dataInputStream1.readFully(requestToByte);
        System.out.println(13);
        String response = new String(requestToByte, StandardCharsets.UTF_8);
        RequestPlayers requestPlayers = readAndCastResponse(response);
        GameDatabase.map = requestPlayers.tiles;
        if (requestPlayers.civilization.getNickname().equals(GameDatabase.you.getNickname())) isYourTurn = true;
        else isYourTurn = false;
    }

    public static void setYou() {
        you = User.loggedInUser;
    }


    public static void setPlayers(ArrayList<Civilization> players, String user) throws IOException {
        TransitionDatabase.restart();
        XStream xStream = new XStream();
        RequestPlayers requestPlayers = new RequestPlayers();
        requestPlayers.players = players;
        requestPlayers.name = user;
        sendToServer(xStream.toXML(requestPlayers), "setPlayers");
        //GameDatabase.getMapFromServer();
    }

    /**
     * @param civilizationName
     * @return selected civilization
     */
//    public static Civilization getCivilizationByUsername(String civilizationName) throws IOException {
//        XStream xStream = new XStream();
//        RequestPlayers requestPlayers = new RequestPlayers();
//        requestPlayers.name = civilizationName;
//        RequestPlayers sth = sendToServer(xStream.toXML(requestPlayers), "getCivilizationByUsername");
//        return sth.civilization;
//    }
    public static Civilization getCivilizationByNickname(String civilizationName) throws IOException {
//        XStream xStream = new XStream();
//        RequestPlayers requestPlayers = new RequestPlayers();
//        requestPlayers.name = civilizationName;
//        RequestPlayers sth = sendToServer(xStream.toXML(requestPlayers), "getCivilizationByNickname");
//        return sth.civilization;
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getNickname().equals(civilizationName)) {
                return players.get(i);
            }
        }
        return null;
    }


    public static City getCityByName(String cityName) throws IOException {
//        XStream xStream = new XStream();
//        RequestPlayers requestPlayers = new RequestPlayers();
//        requestPlayers.name = cityName;
//        RequestPlayers sth = sendToServer(xStream.toXML(requestPlayers), "getCityByName");
//        return sth.city;
        for (int i = 0; i < players.size(); i++) {
            for (int j = 0; j < players.get(i).getCities().size(); j++) {
                if (players.get(i).getCities().get(j).getName().equals(cityName)) {
                    return players.get(i).getCities().get(j);
                }
            }
        }
        return null;
    }

    public static City getCityByXAndY(int x, int y) throws IOException {
//        XStream xStream = new XStream();
//        RequestPlayers requestPlayers = new RequestPlayers();
//        requestPlayers.x = x;
//        requestPlayers.y = y;
//        RequestPlayers sth = sendToServer(xStream.toXML(requestPlayers), "getCityByXAndY");
//        return sth.city;
        for (Civilization player : players) {
            for (City city : player.getCities()) {
                if (city.getX() == x
                        && city.getY() == y) {
                    return city;
                }
            }
        }
        return null;
    }

    public static ArrayList<Tile> getMap() {
        return GameDatabase.map;
    }


    public static Tile getTileByXAndY(int x, int y) throws IOException {
//        XStream xStream = new XStream();
//        RequestPlayers requestPlayers = new RequestPlayers();
//        requestPlayers.x = x;
//        requestPlayers.y = y;
//        RequestPlayers sth = sendToServer(xStream.toXML(requestPlayers), "getTileByXAndY");
//        return sth.tile;
        for (Tile tile : map) {
            if (tile.getX() == x && tile.getY() == y) return tile;
        }
        return null;
    }

    public static boolean isTileForACity(Tile tile) throws IOException {
//        XStream xStream = new XStream();
//        RequestPlayers requestPlayers = new RequestPlayers();
//        requestPlayers.tile = tile;
//        RequestPlayers sth = sendToServer(xStream.toXML(requestPlayers), "isTileForACity");
//        return sth.bool;
        for (Civilization civilization : players) {
            for (City city : civilization.getCities()) {
                if (city.isTileForThisCity(tile)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static Civilization getCivilizationByTile(Tile tile) throws IOException {
//        XStream xStream = new XStream();
//        RequestPlayers requestPlayers = new RequestPlayers();
//        requestPlayers.tile = tile;
//        RequestPlayers sth = sendToServer(xStream.toXML(requestPlayers), "getCivilizationByTile");
//        return sth.civilization;
        for (int i = 0; i < players.size(); i++) {
            for (int j = 0; j < players.get(i).getTiles().size(); j++) {
                if (players.get(i).getTiles().get(j).equals(tile)) {
                    return players.get(i);
                }
            }
        }
        return null;
    }

    public static int getCivilizationIndex(String civilizationName) throws IOException {
//        XStream xStream = new XStream();
//        RequestPlayers requestPlayers = new RequestPlayers();
//        requestPlayers.name = civilizationName;
//        RequestPlayers sth = sendToServer(xStream.toXML(requestPlayers), "getCivilizationIndex");
//        return sth.x;
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getNickname().equals(civilizationName)) {
                return i;
            }
        }
        return -1;
    }

    public static void generateMap(int numberOfPlayers) throws IOException {
        XStream xStream = new XStream();
        RequestPlayers requestPlayers = new RequestPlayers();
        requestPlayers.x = numberOfPlayers;
        System.out.println("blahhhh");
        RequestPlayers sth = sendToServer(xStream.toXML(requestPlayers), "generateMap");
        System.out.println("please work");

    }

    public static Civilization getCivilizationForCity(String cityName) throws IOException {
//        XStream xStream = new XStream();
//        RequestPlayers requestPlayers = new RequestPlayers();
//        requestPlayers.name = cityName;
//        RequestPlayers sth = sendToServer(xStream.toXML(requestPlayers), "generateMap");
//        return sth.civilization;
        for (Civilization player : players) {
            for (City city : player.getCities()) {
                if (city.getName().equals(cityName)) {
                    return player;
                }
            }
        }
        return null;
    }

    public static void nextTurn() throws IOException {
        System.out.println("sar");
        setTurn(calculateNextTurn());
        for (Civilization player : players) {
            player.nextTurn();
        }
//        XStream xStream = new XStream();
////        RequestPlayers requestPlayers = new RequestPlayers();
////        RequestPlayers sth = sendToServer(xStream.toXML(requestPlayers), "nextTurn");
//        //send data to database!
//        RequestPlayers requestPlayers = new RequestPlayers();
//        requestPlayers.tiles = GameDatabase.map;
//        requestPlayers.players = GameDatabase.players;
//        requestPlayers.x = GameDatabase.turn;
        sendMapToServer();
        System.out.println("tah");
    }

    private static int calculateNextTurn() throws IOException {
//        input = new JSONObject();
//        input.put("menu type", "Game Database");
//        input.put("action", "calculateNextTurn");
//        JSONObject serverResponse = sendToServer();
//        return (int) serverResponse.get("turn");
        if (turn != players.size() - 1) {
            return turn + 1;
        }
        year++;
        return 0;
    }

    public static int getTurn() {
        return turn;
    }

    public static Civilization getCivilizationByTurn(int turn) throws IOException {
//        XStream xStream = new XStream();
//        RequestPlayers requestPlayers = new RequestPlayers();
//        requestPlayers.x = turn;
//        RequestPlayers sth = sendToServer(xStream.toXML(requestPlayers), "getCivilizationByTurn");
//        return sth.civilization;
        return players.get(turn);
    }

//    public static Tile findTileBySettler(Settler settler) {
//        for (Tile tile : map) {
//            ArrayList<Unit> soldiers = tile.getUnits();
//            for (Unit soldier : soldiers) {
//                if (soldier.getUnitType().equals("Settler")
//                        && settler == soldier) {
//                    return tile;
//                }
//            }
//        }
//        return null;
//    }
//
//    public static Tile findTileByWorker(Worker worker) {
//        for (Tile tile : map) {
//            ArrayList<Unit> soldiers = tile.getUnits();
//            for (Unit soldier : soldiers) {
//                if (soldier.getUnitType().equals("worker")
//                        && worker == soldier) {
//                    return tile;
//                }
//            }
//        }
//        return null;
//    }

    public static Tile findTileByCitizen(Citizen citizen) throws IOException {
//        XStream xStream = new XStream();
//        RequestPlayers requestPlayers = new RequestPlayers();
//        requestPlayers.citizen = citizen;
//        RequestPlayers sth = sendToServer(xStream.toXML(requestPlayers), "findTileByCitizen");
//        return sth.tile;
        for (Tile tile : map) {
            ArrayList<Citizen> citizens = tile.getCitizens();
            if (citizens.contains(citizen)) return tile;
        }
        return null;
    }

    public static ArrayList<Civilization> getPlayers() {
        return GameDatabase.players;
    }

    public static boolean isTileInCivilization(Tile tile, Civilization civilization) throws IOException {
//        XStream xStream = new XStream();
//        RequestPlayers requestPlayers = new RequestPlayers();
//        requestPlayers.tile = tile;
//        requestPlayers.civilization = civilization;
//        RequestPlayers sth = sendToServer(xStream.toXML(requestPlayers), "isTileInCivilization");
//        return sth.bool;
        if (civilization.isTileInCivilization(tile.getX(), tile.getY())) {
            return true;
        }
        return false;
    }

    public static void setTurn(int newTurn) throws IOException {
//        turn = newTurn;
//        XStream xStream = new XStream();
//        RequestPlayers requestPlayers = new RequestPlayers();
//        requestPlayers.x = newTurn;
//        RequestPlayers sth = sendToServer(xStream.toXML(requestPlayers), "setTurn");

    }

    public static User getUserForCivilization(String civilizationName) throws IOException {
//        XStream xStream = new XStream();
//        RequestPlayers requestPlayers = new RequestPlayers();
//        requestPlayers.name = civilizationName;
//        RequestPlayers sth = sendToServer(xStream.toXML(requestPlayers), "getUserForCivilization");
//        return sth.user;
        String username = Objects.requireNonNull(getCivilizationByNickname(civilizationName)).getUsername();
        return UserDatabase.getUserByUsername(username);
    }

    public static Civilization checkIfWin() throws IOException {
//        XStream xStream = new XStream();
//        RequestPlayers requestPlayers = new RequestPlayers();
//        RequestPlayers sth = sendToServer(xStream.toXML(requestPlayers), "checkIfWin");
//        return sth.civilization;
        if (year >= 2050) {
            return getCivilizationByTurn(getTurn());
        }
        if (cheated && cheatedCivilization != null) {
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

    public static Civilization getLastCivilization() throws IOException {
//        XStream xStream = new XStream();
//        RequestPlayers requestPlayers = new RequestPlayers();
//        RequestPlayers sth = sendToServer(xStream.toXML(requestPlayers), "getLastCivilization");
//        return requestPlayers.civilization;
        int turn = getTurn();
        if (turn == 0) {
            return getCivilizationByTurn(players.size() - 1);
        }
        return getCivilizationByTurn(turn - 1);
    }

    public static void setWidth(int number) {
        GameDatabase.width = number;
    }

    public static void setLength(int number) {
        GameDatabase.length = number;
    }

    public static int getLength() {
        return GameDatabase.length;
    }

    public static int getWidth() {
        return GameDatabase.width;
    }

    public static int getYear() {
        return GameDatabase.year;
    }
}
