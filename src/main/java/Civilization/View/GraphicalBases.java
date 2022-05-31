package Civilization.View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class GraphicalBases {

    public static Stage stage;
    public static Scene scene;

    // Cursor images
    public static final Image CURSOR = new Image(GraphicalBases.class.getResource("/pics/cursor.png").toExternalForm());

    // Menus background images
    public static final Image LOGIN_MENU_BACKGROUND = new Image(GraphicalBases.class.getResource("/pics/Menus/LoginMenuBackground.png").toExternalForm());
    public static final Image MAIN_MENU_BACKGROUND_IMAGE = new Image(GraphicalBases.class.getResource("/pics/Menus/MainMenuBackground.png").toExternalForm());

    public static void firstLogin() {
        Parent root = loadFXMLMenus("LoginMenu");
        GraphicalBases.scene = new Scene(root);
        GraphicalBases.stage.setScene(GraphicalBases.scene);
        GraphicalBases.stage.show();
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
}
