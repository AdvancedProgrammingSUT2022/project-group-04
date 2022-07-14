package Civilization.View;

import Civilization.Database.GlobalVariables;
import Civilization.Model.Unit;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
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
}
