package Civilization.View;

import Civilization.Database.GlobalVariables;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Objects;

public class GraphicalBases {

    public static Stage stage;
    public static Scene scene;

    // Cursor images
    public static final Image CURSOR = new Image(Objects.requireNonNull(GraphicalBases.class.getResource("/pics/cursor.png")).toExternalForm());

    // Menus background images
    public static final Image LOGIN_MENU_BACKGROUND = new Image(Objects.requireNonNull(GraphicalBases.class.getResource("/pics/Menus/LoginMenuBackground.png")).toExternalForm());
    public static final Image MAIN_MENU_BACKGROUND_IMAGE = new Image(Objects.requireNonNull(GraphicalBases.class.getResource("/pics/Menus/MainMenuBackground.png")).toExternalForm());
    public static final Image PROFILE_MENU_BACKGROUND_IMAGE = new Image(Objects.requireNonNull(GraphicalBases.class.getResource("/pics/Menus/ProfileMenuBackground.png")).toExternalForm());
    public static final Image BLACK = new Image(Objects.requireNonNull(GraphicalBases.class.getResource("/pics/Menus/black.jpg")).toExternalForm());
    public static final Image KING_DAVID = new Image(Objects.requireNonNull(GraphicalBases.class.getResource("/pics/Menus/KingDavidStatue.png")).toExternalForm());
    public static final Image EZEKIEL = new Image(Objects.requireNonNull(GraphicalBases.class.getResource("/pics/Menus/RaphaelEzekiel'sVision1518.jpg")).toExternalForm());
    public static final Image WIN = new Image(Objects.requireNonNull(GraphicalBases.class.getResource("/pics/Menus/win.jpg")).toExternalForm());
    public static final Image OVERVIEWS = new Image(Objects.requireNonNull(GraphicalBases.class.getResource("/pics/Menus/Overviews.jpg")).toExternalForm());
    public static final Image SASANI = new Image(Objects.requireNonNull(GraphicalBases.class.getResource("/pics/Menus/Sasani.jpg")).toExternalForm());
    public static final Image TREE = new Image(Objects.requireNonNull(GraphicalBases.class.getResource("/pics/Menus/Tree.jpg")).toExternalForm());
    public static final Image INDIA = new Image(Objects.requireNonNull(GraphicalBases.class.getResource("/pics/Menus/india.jpg")).toExternalForm());
    public static final Image WHITE = new Image(Objects.requireNonNull(GraphicalBases.class.getResource("/pics/Menus/white.jpg")).toExternalForm());
    public static final Image WORKER = new Image(Objects.requireNonNull(GraphicalBases.class.getResource("/pics/Menus/worker.jpg")).toExternalForm());
    public static final Image CITY_PANEL = new Image(Objects.requireNonNull(GraphicalBases.class.getResource("/pics/Menus/cityPanel.jpg")).toExternalForm());

    // Avatars
    public static final int NUMBER_OF_AVATARS = 4;
    public static final Image[] AVATARS = new Image[NUMBER_OF_AVATARS];
    public static final Image ADD_AVATAR_ICON = new Image(Objects.requireNonNull(GraphicalBases.class.getResource("/pics/Avatars/addAvatarIcon.jpg")).toExternalForm());

    // Status bar
    public static final Image HAPPY = new Image(Objects.requireNonNull(GraphicalBases.class.getResource("/pics/StatusBar/happy.png")).toExternalForm());
    public static final Image UNHAPPY = new Image(Objects.requireNonNull(GraphicalBases.class.getResource("/pics/StatusBar/unhappy.png")).toExternalForm());
    public static final Image SCIENCE = new Image(Objects.requireNonNull(GraphicalBases.class.getResource("/pics/StatusBar/science.png")).toExternalForm());
    public static final Image COIN = new Image(Objects.requireNonNull(GraphicalBases.class.getResource("/pics/StatusBar/coin.png")).toExternalForm());

    // Info Panel
    public static final Image INFO_PANEL = new Image(Objects.requireNonNull(GraphicalBases.class.getResource("/pics/InfoPanel/infoPanel.png")).toExternalForm());
    public static final Image NULL = new Image(Objects.requireNonNull(GraphicalBases.class.getResource("/pics/Technologies/null.png")).toExternalForm());

    // Game
    public static final HashMap<String, Image> TECHNOLOGIES = new HashMap<>();
    public static final HashMap<String, Image> UNITS = new HashMap<>();
    public static final HashMap<String, Image> BUILDINGS = new HashMap<>();
    public static final HashMap<String, Image> FEATURES = new HashMap<>();
    public static final HashMap<String, Image> RESOURCES = new HashMap<>();
    public static final Image BUILD = new Image(Objects.requireNonNull(GraphicalBases.class.getResource("/pics/Buildings/build.png")).toExternalForm());
    public static final Image RUINS = new Image(Objects.requireNonNull(GraphicalBases.class.getResource("/pics/Ruins.jpg")).toExternalForm());
    public static final Image CITY = new Image(Objects.requireNonNull(GraphicalBases.class.getResource("/pics/City.jpg")).toExternalForm());
    public static final Image FOG_OF_WAR = new Image(Objects.requireNonNull(GraphicalBases.class.getResource("/pics/Map/FogOfWar.png")).toExternalForm());
    public static final Image ROAD = new Image(Objects.requireNonNull(GraphicalBases.class.getResource("/pics/Map/road.jpg")).toExternalForm());
    public static final Image RAIL_ROAD = new Image(Objects.requireNonNull(GraphicalBases.class.getResource("/pics/Map/railroad.png")).toExternalForm());

    // Music
    public static final MediaPlayer MENU = new MediaPlayer(new Media(Objects.requireNonNull(GraphicalBases.class.getResource("/music/Menus.mp3")).toExternalForm()));
    public static final MediaPlayer GAME = new MediaPlayer(new Media(Objects.requireNonNull(GraphicalBases.class.getResource("/music/Game.mp3")).toExternalForm()));

    public static void firstLogin() {
        GraphicalBases.start();
        Parent root = loadFXMLMenus("LoginMenu");
        assert root != null;
        GraphicalBases.scene = new Scene(root);
        GraphicalBases.stage.setScene(GraphicalBases.scene);
        GraphicalBases.stage.show();
    }

    public static void start() {
        for (int i = 0; i < AVATARS.length; i++) {
            AVATARS[i] = new Image(Objects.requireNonNull(GraphicalBases.class.getResource("/pics/Avatars/" + (i + 1) + ".png")).toExternalForm());
        }
        for (String technologyName : GlobalVariables.TECHNOLOGIES) {
            TECHNOLOGIES.put(technologyName, new Image(Objects.requireNonNull(GraphicalBases.class.getResource("/pics/Technologies/" + technologyName + ".png")).toExternalForm()));
        }
        for (String unitName : GlobalVariables.UNITS) {
            UNITS.put(unitName, new Image(Objects.requireNonNull(GraphicalBases.class.getResource("/pics/Units/" + unitName + ".png")).toExternalForm()));
        }
        for (String buildingName : GlobalVariables.BUILDINGS) {
            BUILDINGS.put(buildingName, new Image(Objects.requireNonNull(GraphicalBases.class.getResource("/pics/Buildings/" + buildingName + ".png")).toExternalForm()));
        }
        for (String featureName : GlobalVariables.TERRAIN_FEATURES) {
            FEATURES.put(featureName, new Image(Objects.requireNonNull(GraphicalBases.class.getResource("/pics/Features/" + featureName + ".jpg")).toExternalForm()));
        }
        for (String resourceName : GlobalVariables.RESOURCES) {
            RESOURCES.put(resourceName, new Image(Objects.requireNonNull(GraphicalBases.class.getResource("/pics/Resources/" + resourceName + ".png")).toExternalForm()));
        }
    }

    public static void login() {
        changeMenu("LoginMenu");
    }

    public static void userLoggedIn() {
        changeMenu("MainMenu");
    }

    public static void changeMenu(String name){
        Parent root = loadFXMLMenus(name);
        GraphicalBases.scene.setRoot(root);
        setKeyEventsDefault();
    }

    private static void setKeyEventsDefault() {
        GraphicalBases.scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                // Noting
            }
        });
    }

    private static Pane loadFXMLMenus(String name){
        try {
            URL address = new URL(Objects.requireNonNull(GraphicalBases.class.getResource("/fxml/Menus/" + name + ".fxml")).toString());
            return FXMLLoader.load(address);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static void enterGame(String name) {
        Parent root = loadGame(name);
        GraphicalBases.scene.setRoot(root);
    }

    private static Pane loadGame(String name) {
        try {
            URL address = new URL(Objects.requireNonNull(GraphicalBases.class.getResource("/fxml/Game/" + name + ".fxml")).toString());
            return FXMLLoader.load(address);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static void PlayMenuMusic() {
        GAME.pause();
        MENU.play();
    }

    public static void playGameMusic() {
        MENU.pause();
        GAME.play();
    }
}
