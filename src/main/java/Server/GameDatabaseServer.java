package Server;

import Civilization.Controller.CopyOfGameDatabase;
import Civilization.Controller.GameMenuController;
import Civilization.Database.GlobalVariables;
import Civilization.Model.*;
import Civilization.View.FXMLControllers.GameFXMLController;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class GameDatabaseServer {

    public static ArrayList<Civilization> players = new ArrayList<Civilization>();
    public static ArrayList<Tile> map = new ArrayList<Tile>();

    public static int length = 50;
    public static int width = 10;

    public static int turn = 0;
    public static int year = 0;
    public static boolean cheated = false;
    public static Civilization cheatedCivilization = null;

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

    public static Civilization getCivilizationByUnit(Unit unit) {
        GameMenuController gameMenuController = new GameMenuController(new GameModel());
        for (Civilization player : players) {
            if (gameMenuController.isUnitForThisCivilization(getCivilizationIndex(player.getNickname()), unit)) {
                return player;
            }
        }
        return null;
    }

    public static void generateRuin() {
        Random random = new Random();
        for (Tile tile : map) {
            if (getCivilizationByTile(tile) == null) {
                int ruin = random.nextInt(500);
                if (ruin == 12) {
                    tile.setRuin(new Ruin());
                }
            }
        }
    }


    public static void setPlayers(ArrayList<Civilization> player) {
        System.out.println("bljjfjfjfj");
        turn = 0;
        year = 0;
        cheated = false;
        cheatedCivilization = null;
        players = player;
        System.out.println(players.size());
        for (Civilization civilization : players) {
            System.out.println("kiriririri");
            civilization.setHappiness(GlobalVariables.firstHappiness * players.size());
            System.out.println("kiriririri1");
            if (civilization.getNickname().equals(User.loggedInUser.getNickname())) {
                civilization.getMessages().add("It's your game, Good luck ;)");
                System.out.println("kiriririri2");
            } else {
                System.out.println("kiriririri3");
                civilization.getMessages().add("You have an invitation from " + User.loggedInUser.getNickname());
            }
            System.out.println("kiriririri4");

        }
        System.out.println("kiri");
    }

    /**
     * @param civilizationName
     * @return selected civilization
     */
    public static Civilization getCivilizationByUsername(String civilizationName) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getUsername().equals(civilizationName)) {
                return players.get(i);
            }
        }
        return null;
    }

    public static Civilization getCivilizationByNickname(String civilizationName) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getNickname().equals(civilizationName)) {
                return players.get(i);
            }
        }
        return null;
    }


    public static City getCityByName(String cityName) {
        for (int i = 0; i < players.size(); i++) {
            for (int j = 0; j < players.get(i).getCities().size(); j++) {
                if (players.get(i).getCities().get(j).getName().equals(cityName)) {
                    return players.get(i).getCities().get(j);
                }
            }
        }
        return null;
    }

    public static City getCityByXAndY(int x, int y) {
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
        return map;
    }


    public static Tile getTileByXAndY(int x, int y) {

        for (Tile tile : map) {
            if (tile.getX() == x && tile.getY() == y) return tile;
        }
        return null;

    }

    public static boolean isTileForACity(Tile tile) {
        for (Civilization civilization : players) {
            for (City city : civilization.getCities()) {
                if (city.isTileForThisCity(tile)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static Civilization getCivilizationByTile(Tile tile) {
        for (int i = 0; i < players.size(); i++) {
            for (int j = 0; j < players.get(i).getTiles().size(); j++) {
                if (players.get(i).getTiles().get(j).equals(tile)) {
                    return players.get(i);
                }
            }
        }
        return null;
    }

    public static int getCivilizationIndex(String civilizationName) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getNickname().equals(civilizationName)) {
                return i;
            }
        }
        return -1;
    }

    public static void generateMap(int numberOfPlayers) throws IOException {
        System.out.println("blah");
        Worker.setHashMap();
        Random random = new Random();
        int[] possibilities = {10, 10, 10, 10, 10, 10, 10, 10};
        int sumOfPossibilities = 0;
        int possibilityOfEdgeBeingRiver = 40;// from 1000
        //initialize and set hashmap
        HashMap<Integer, String> baseTerrains = new HashMap<>();
        baseTerrains.put(0, "Desert");
        baseTerrains.put(1, "Meadow");
        baseTerrains.put(2, "Hill");
        baseTerrains.put(3, "Mountain");
        baseTerrains.put(4, "Ocean");
        baseTerrains.put(5, "Plain");
        baseTerrains.put(6, "Snow");
        baseTerrains.put(7, "Tundra");
        for (int i = 0; i < possibilities.length; i++) {
            sumOfPossibilities += possibilities[i];
        }
        //random initialize tiles of map
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                int randomGenerate = random.nextInt(sumOfPossibilities);
                int counter = 0;
                int flag = -1;
                for (int k = 0; k < possibilities.length; k++) {
                    counter += possibilities[k];
                    if (randomGenerate < counter) {
                        flag = k;
                        break;
                    }
                }
                Tile tile = new Tile("fogOfWar", baseTerrains.get(flag), i, j);
                map.add(tile);
            }
        }
        //random initialize river
        int[] deltaX0 = {-1, 0, 1, 1, 1, 0};
        int[] deltaY0 = {0, 1, 1, 0, -1, -1};
        int[] deltaX1 = {-1, -1, 0, 1, 0, -1};
        int[] deltaY1 = {0, 1, 1, 0, -1, -1};
        int[] numbOfEdge = {3, 4, 5, 0, 1, 2};
        for (int i = 0; i < map.size(); i++) {
            Tile tile = map.get(i);
            for (int j = 0; j < 6; j++) {
                int xOfTile = tile.getX() + deltaX1[j];
                int yOfTile = tile.getY() + deltaY1[j];
                if (tile.getY() % 2 == 0) {
                    xOfTile = tile.getX() + deltaX0[j];
                    yOfTile = tile.getY() + deltaY0[j];
                }
                if ((xOfTile >= length || xOfTile < 0
                        || yOfTile >= width || yOfTile < 0)
                        || (tile.getBaseTerrainType().equals("Ocean")
                        && getTileByXAndY(xOfTile, yOfTile).getBaseTerrainType().equals("Ocean"))) {
                    continue;
                }
                int randomGenerate = random.nextInt(1000);
                if (randomGenerate < possibilityOfEdgeBeingRiver) {
                    Tile tile1 = getTileByXAndY(xOfTile, yOfTile);
                    tile.setRiverByEdgeIndex(j);
                    tile1.setRiverByEdgeIndex(numbOfEdge[j]);
                }
            }
        }
        //random initialize terrainFeature
        for (int i = 0; i < map.size(); i++) {
            BaseTerrain baseTerrain = map.get(i).getBaseTerrain();
            ArrayList<TerrainFeatures> terrainFeatures = baseTerrain.getPossibleFeatures();
            for (int j = 0; j < terrainFeatures.size(); j++) {
                int randomGenerate = random.nextInt(terrainFeatures.size() * 2);
                if (randomGenerate < 3) {//the possibility can be changed
                    String type = terrainFeatures.get(j).getType();
                    TerrainFeatures terrainFeatures1 = randomInitializeFeature(type);
                    map.get(i).getBaseTerrain().setFeature(terrainFeatures1);
                    break;
                }
            }
        }
        //random initialize resources
        for (int i = 0; i < map.size(); i++) {
            BaseTerrain baseTerrain = map.get(i).getBaseTerrain();
            ArrayList<Resources> resources = baseTerrain.getPossibleResources();
            for (int j = 0; j < resources.size(); j++) {
                int randomGenerate = random.nextInt(resources.size() * 2);
                if (randomGenerate < 3) {//the possibility can be changed
                    String name = resources.get(j).getName();
                    Resources resource = new Resources(name);
                    map.get(i).getBaseTerrain().addResource(resource);
                    break;
                }
            }
        }
        //random set beginning tiles for each player
        int counter = 0;
        while (counter < players.size()) {
            int xRandomGenerate = random.nextInt(length);
            int yRandomGenerate = random.nextInt(length);
            int direction = random.nextInt(6);
            int x1 = xRandomGenerate + deltaX0[direction];
            int y1 = yRandomGenerate + deltaY0[direction];
            if (yRandomGenerate % 2 == 1) {
                x1 = xRandomGenerate + deltaX1[direction];
                y1 = yRandomGenerate + deltaY1[direction];
            }
            if (getTileByXAndY(xRandomGenerate, yRandomGenerate) == null
                    || getTileByXAndY(x1, y1) == null
                    || getTileByXAndY(xRandomGenerate, yRandomGenerate).getBaseTerrainType().equals("Ocean")
                    || getTileByXAndY(xRandomGenerate, yRandomGenerate).getBaseTerrainType().equals("Mountain")
                    || getTileByXAndY(x1, y1).getBaseTerrainType().equals("Ocean")
                    || getTileByXAndY(x1, y1).getBaseTerrainType().equals("Mountain")) {

                continue;
            }
            System.out.println(x1 + " " + y1);
            boolean isOccupied = false;
            for (int i = 0; i < counter; i++) {
                if (players.get(i).isTileInCivilization(xRandomGenerate, yRandomGenerate)
                        || players.get(i).isTileInCivilization(x1, y1)) {
                    isOccupied = true;
                    break;
                }
            }

            if (!isOccupied) {
                players.get(counter).addTile(getTileByXAndY(xRandomGenerate, yRandomGenerate));
                players.get(counter).addTile(getTileByXAndY(x1, y1));
                //set non-attacking units in the beginning
                Worker worker = new Worker(xRandomGenerate, yRandomGenerate, counter);
                Settler settler = new Settler(x1, y1, counter);
                getTileByXAndY(xRandomGenerate, yRandomGenerate).addWorker(worker);
                getTileByXAndY(x1, y1).addSettler(settler);
                worker.setTileOfUnit(getTileByXAndY(xRandomGenerate, yRandomGenerate));
                settler.setTileOfUnit(getTileByXAndY(x1, y1));
                counter++;
            }
        }
    }

    public static TerrainFeatures randomInitializeFeature(String type) {
        Random random = new Random();
        TerrainFeatures terrainFeature = new TerrainFeatures(type);
        ArrayList<Resources> resources = terrainFeature.getPossibleResources();
        for (int j = 0; j < resources.size(); j++) {
            int randomGenerate = random.nextInt(resources.size() * 2);
            if (randomGenerate < 3) {//the possibility can be changed
                String name = resources.get(j).getName();
                Resources resource = new Resources(name);
                terrainFeature.addResource(resource);
            }
        }
        return terrainFeature;
    }

    public static Civilization getCivilizationForCity(String cityName) {
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
        setTurn(calculateNextTurn());
        for (Civilization player : players) {
            player.nextTurn();
        }
    }

    private static int calculateNextTurn() {
        if (turn != players.size() - 1) {
            return turn + 1;
        }
        year++;
        return 0;
    }

    public static int getTurn() {
        return turn;
    }

    public static Civilization getCivilizationByTurn(int turn) {
        return players.get(turn);
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
        return players;
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

    public static User getUserForCivilization(String civilizationName) {
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

    public static Civilization getLastCivilization() {
        int turn = getTurn();
        if (turn == 0) {
            return getCivilizationByTurn(players.size() - 1);
        }
        return getCivilizationByTurn(turn - 1);
    }

    public static RequestPlayers readAndCastRequest(String s) throws IOException {
        XStream xStream = new XStream();
        FileWriter fileWriter;
        if (Files.exists(Paths.get("save/array.xml")))
            fileWriter = new FileWriter("save/array.xml");
        else{
            new File("save").mkdir();
            fileWriter = new FileWriter("save/array.xml");
        }
        fileWriter.write(s);
        fileWriter.close();
        ///
        String xml = new String(Files.readAllBytes(Paths.get("save/array.xml")));
        xStream.addPermission(AnyTypePermission.ANY);
        if(xml.length() != 0){
            Object obh = xStream.fromXML(xml);
            RequestPlayers req = (RequestPlayers) obh;
            return req;
        }
        return null;
    }

    public static String processReq(String s) throws IOException {
        RequestPlayers returnValue = new RequestPlayers();
        String response;
        if (s.startsWith("setPlayers")) {
            s = s.substring(10);
            RequestPlayers req = readAndCastRequest(s);
            ArrayList<Civilization> pl = req.players;
            setPlayers(pl);
        }
        else if (s.startsWith("getCivilizationByUnit")){
            s = s.substring("getCivilizationByUnit".length());
            RequestPlayers requestPlayers = readAndCastRequest(s);
            Unit unit = requestPlayers.unit;
            returnValue.civilization = getCivilizationByUnit(unit);
        }
        else if (s.startsWith("generateRuin")){
            generateRuin();
        }
        else if (s.startsWith("getCivilizationByUsername")){
            s = s.substring("getCivilizationByUsername".length());
            RequestPlayers requestPlayers = readAndCastRequest(s);
            returnValue.civilization =  getCivilizationByUsername(requestPlayers.name);
        }
        else if (s.startsWith("getCivilizationByNickname")){
            s = s.substring("getCivilizationByNickname".length());
            RequestPlayers requestPlayers = readAndCastRequest(s);
            returnValue.civilization =  getCivilizationByNickname(requestPlayers.name);
        }
        else if (s.startsWith("getCityByName")){
            s = s.substring("getCityByName".length());
            RequestPlayers requestPlayers = readAndCastRequest(s);
            returnValue.city = getCityByName(requestPlayers.name);
        }
        else if (s.startsWith("getTileByXAndY")){
            s = s.substring("getTileByXAndY".length());
            RequestPlayers requestPlayers = readAndCastRequest(s);
            returnValue.tile = getTileByXAndY(requestPlayers.x, requestPlayers.y);
        }
        else if (s.startsWith("isTileForACity")){
            s = s.substring("isTileForACity".length());
            RequestPlayers requestPlayers = readAndCastRequest(s);
            returnValue.bool = isTileForACity(requestPlayers.tile);
        }
        else if (s.startsWith("getCivilizationByTile")){
            s = s.substring("getCivilizationByTile".length());
            RequestPlayers requestPlayers = readAndCastRequest(s);
            returnValue.civilization =  getCivilizationByTile(requestPlayers.tile);
        }
        else if (s.startsWith("getCivilizationIndex")){
            s = s.substring("getCivilizationIndex".length());
            RequestPlayers requestPlayers = readAndCastRequest(s);
            returnValue.x = getCivilizationIndex(requestPlayers.name);
        }
        else if (s.startsWith("generateMap")){
            s = s.substring("generateMap".length());
            RequestPlayers requestPlayers = readAndCastRequest(s);
            generateMap(requestPlayers.x);
            returnValue.players =  players;
            returnValue.tiles = map;
        }
        else if (s.startsWith("getCivilizationForCity")){
            s = s.substring("getCivilizationForCity".length());
            RequestPlayers requestPlayers = readAndCastRequest(s);
            returnValue.civilization = getCivilizationForCity(requestPlayers.name);
        }
        else if (s.startsWith("nextTurn")){
            nextTurn();
        }
        else if (s.startsWith("calculateNextTurn")){
            returnValue.x = calculateNextTurn();
        }
        else if (s.startsWith("findTileByCitizen")){
            s = s.substring("findTileByCitizen".length());
            RequestPlayers requestPlayers = readAndCastRequest(s);
            returnValue.tile = findTileByCitizen(requestPlayers.citizen);
        }
        else if (s.startsWith("getCivilizationByTurn")){
            s = s.substring("getCivilizationByTurn".length());
            RequestPlayers requestPlayers = readAndCastRequest(s);
            returnValue.civilization =  getCivilizationByTurn(requestPlayers.x);
        }
        else if (s.startsWith("isTileInCivilization")){
            s = s.substring("isTileInCivilization".length());
            RequestPlayers requestPlayers = readAndCastRequest(s);
            returnValue.bool = isTileInCivilization(requestPlayers.tile,requestPlayers.civilization);
        }
        else if (s.startsWith("checkIfWin")){
            returnValue.civilization = checkIfWin();
        }
        else if (s.startsWith("getLastCivilization")){
            returnValue.civilization = getLastCivilization();
        }
        XStream xStream = new XStream();
        response = xStream.toXML(returnValue);
        return response;
    }

    public static JSONObject processReq(JSONObject clientCommandJ) throws IOException {
        JSONObject response = new JSONObject();
        return response;
    }
}
