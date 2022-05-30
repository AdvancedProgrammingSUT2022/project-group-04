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

    public static final Image CURSOR = new Image(GraphicalBases.class.getResource("/pics/cursor.png").toExternalForm());

    public static void login() {
        Parent root = loadFXMLMenus("LoginMenu");
        GraphicalBases.scene = new Scene(root);
        GraphicalBases.stage.setScene(GraphicalBases.scene);
        GraphicalBases.stage.show();
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
