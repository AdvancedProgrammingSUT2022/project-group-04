package Civilization.Database;

import Civilization.Model.*;
import Civilization.View.FXMLControllers.GameFXMLController;
import Civilization.View.Transitions.TransitionDatabase;
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
        XStream xStream = new XStream();
        RequestPlayers requestPlayers = new RequestPlayers();
        requestPlayers.unit = unit;
        Object sth = sendToServer(xStream.toXML(requestPlayers), "getCivilizationByUnit");
        JSONObject serverResponse = sendToServer();
        return (Civilization) serverResponse.get("civilization");
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
        return requestPlayers;
    }

    public static void generateRuin() throws IOException {
        sendToServer(null, "generateRuin");
    }


    public static void setPlayers(ArrayList<Civilization> players, String user) throws IOException {
        TransitionDatabase.restart();
        XStream xStream = new XStream();
        RequestPlayers requestPlayers = new RequestPlayers();
        requestPlayers.players = players;
        requestPlayers.name = user;
        Object sth = sendToServer(xStream.toXML(requestPlayers), "setPlayers");
        GameDatabase.players = players;
    }

    /**
     * @param civilizationName
     * @return selected civilization
     */
    public static Civilization getCivilizationByUsername(String civilizationName) throws IOException {
        XStream xStream = new XStream();
        RequestPlayers requestPlayers = new RequestPlayers();
        requestPlayers.name = civilizationName;
        RequestPlayers sth = sendToServer(xStream.toXML(requestPlayers), "getCivilizationByUsername");
        return sth.civilization;
    }

    public static Civilization getCivilizationByNickname(String civilizationName) throws IOException {
        XStream xStream = new XStream();
        RequestPlayers requestPlayers = new RequestPlayers();
        requestPlayers.name = civilizationName;
        RequestPlayers sth = sendToServer(xStream.toXML(requestPlayers), "getCivilizationByNickname");
        return sth.civilization;
    }


    public static City getCityByName(String cityName) throws IOException {
        XStream xStream = new XStream();
        RequestPlayers requestPlayers = new RequestPlayers();
        requestPlayers.name = cityName;
        RequestPlayers sth = sendToServer(xStream.toXML(requestPlayers), "getCityByName");
        return sth.city;
    }

    public static City getCityByXAndY(int x, int y) throws IOException {
        XStream xStream = new XStream();
        RequestPlayers requestPlayers = new RequestPlayers();
        requestPlayers.x = x;
        requestPlayers.y = y;
        RequestPlayers sth = sendToServer(xStream.toXML(requestPlayers), "getCityByXAndY");
        return sth.city;
    }

    public static ArrayList<Tile> getMap() {
        return GameDatabase.map;
    }


    public static Tile getTileByXAndY(int x, int y) throws IOException {
        XStream xStream = new XStream();
        RequestPlayers requestPlayers = new RequestPlayers();
        requestPlayers.x = x;
        requestPlayers.y = y;
        RequestPlayers sth = sendToServer(xStream.toXML(requestPlayers), "getTileByXAndY");
        return sth.tile;
    }

    public static boolean isTileForACity(Tile tile) throws IOException {
        XStream xStream = new XStream();
        RequestPlayers requestPlayers = new RequestPlayers();
        requestPlayers.tile = tile;
        RequestPlayers sth = sendToServer(xStream.toXML(requestPlayers), "isTileForACity");
        return sth.bool;
    }

    public static Civilization getCivilizationByTile(Tile tile) throws IOException {
        XStream xStream = new XStream();
        RequestPlayers requestPlayers = new RequestPlayers();
        requestPlayers.tile = tile;
        RequestPlayers sth = sendToServer(xStream.toXML(requestPlayers), "getCivilizationByTile");
        return sth.civilization;
    }

    public static int getCivilizationIndex(String civilizationName) throws IOException {
        XStream xStream = new XStream();
        RequestPlayers requestPlayers = new RequestPlayers();
        requestPlayers.name = civilizationName;
        RequestPlayers sth = sendToServer(xStream.toXML(requestPlayers), "getCivilizationIndex");
        return sth.x;
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
        XStream xStream = new XStream();
        RequestPlayers requestPlayers = new RequestPlayers();
        requestPlayers.name = cityName;
        RequestPlayers sth = sendToServer(xStream.toXML(requestPlayers), "generateMap");
        return sth.civilization;
    }

    public static void nextTurn() throws IOException {
        XStream xStream = new XStream();
        RequestPlayers requestPlayers = new RequestPlayers();
        RequestPlayers sth = sendToServer(xStream.toXML(requestPlayers), "nextTurn");
    }

    private static int calculateNextTurn() throws IOException {
        input = new JSONObject();
        input.put("menu type", "Game Database");
        input.put("action", "calculateNextTurn");
        JSONObject serverResponse = sendToServer();
        return (int) serverResponse.get("turn");
    }

    public static int getTurn() {
        return turn;
    }

    public static Civilization getCivilizationByTurn(int turn) throws IOException {
        XStream xStream = new XStream();
        RequestPlayers requestPlayers = new RequestPlayers();
        requestPlayers.x = turn;
        RequestPlayers sth = sendToServer(xStream.toXML(requestPlayers), "getCivilizationByTurn");
        return sth.civilization;
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
        XStream xStream = new XStream();
        RequestPlayers requestPlayers = new RequestPlayers();
        requestPlayers.citizen = citizen;
        RequestPlayers sth = sendToServer(xStream.toXML(requestPlayers), "findTileByCitizen");
        return sth.tile;
    }

    public static ArrayList<Civilization> getPlayers() {
        return GameDatabase.players;
    }

    public static boolean isTileInCivilization(Tile tile, Civilization civilization) throws IOException {
        XStream xStream = new XStream();
        RequestPlayers requestPlayers = new RequestPlayers();
        requestPlayers.tile = tile;
        requestPlayers.civilization = civilization;
        RequestPlayers sth = sendToServer(xStream.toXML(requestPlayers), "isTileInCivilization");
        return sth.bool;
    }

    public static void setTurn(int newTurn) throws IOException {
        turn = newTurn;
        XStream xStream = new XStream();
        RequestPlayers requestPlayers = new RequestPlayers();
        requestPlayers.x = newTurn;
        RequestPlayers sth = sendToServer(xStream.toXML(requestPlayers), "setTurn");
    }

    public static User getUserForCivilization(String civilizationName) throws IOException {
        XStream xStream = new XStream();
        RequestPlayers requestPlayers = new RequestPlayers();
        requestPlayers.name = civilizationName;
        RequestPlayers sth = sendToServer(xStream.toXML(requestPlayers), "getUserForCivilization");
        return sth.user;
    }

    public static Civilization checkIfWin() throws IOException {
        XStream xStream = new XStream();
        RequestPlayers requestPlayers = new RequestPlayers();
        RequestPlayers sth = sendToServer(xStream.toXML(requestPlayers), "checkIfWin");
        return sth.civilization;
    }


    public static void saveGame() {
        for (int i = 0; i < 10; i++) {
            System.err.println("Saving game code has been commented by Sepehr Mizanian. Please uncomment it in GameDatabase.saveGame() function.");
        }
//        CopyOfGameDatabase copy = new CopyOfGameDatabase(players,map,length,width,turn,year,cheated,cheatedCivilization);
//        SavingGame.saveGame(copy);
    }

    public static Civilization getLastCivilization() throws IOException {
        XStream xStream = new XStream();
        RequestPlayers requestPlayers = new RequestPlayers();
        RequestPlayers sth = sendToServer(xStream.toXML(requestPlayers), "getLastCivilization");
        return requestPlayers.civilization;
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
