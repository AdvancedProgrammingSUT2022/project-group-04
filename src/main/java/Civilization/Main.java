package Civilization;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Scanner;

import Civilization.Controller.LoginMenuController;
import Civilization.Controller.MainMenuController;
import Civilization.Database.UserDatabase;
import Civilization.Model.LoginMenuModel;
import Civilization.Model.MainMenuModel;
import Civilization.Model.User;
import Civilization.View.LoginMenu;
import Civilization.View.MainMenu;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    public static void main(String[] args) throws IOException {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        URL address = new URL(Objects.requireNonNull(getClass().getResource("/test.fxml")).toString());
        Parent root = FXMLLoader.load(address);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();




        Scanner scanner = new Scanner(System.in);

        UserDatabase.readFromFile("UserDatabase.json");

        LoginMenuModel loginMenuModel = new LoginMenuModel();
        LoginMenuController loginMenuController = new LoginMenuController(loginMenuModel);
        LoginMenu loginMenu = new LoginMenu(loginMenuController);

        MainMenuModel mainMenuModel = new MainMenuModel();
        MainMenuController mainMenuController = new MainMenuController(mainMenuModel);
        MainMenu mainMenu = new MainMenu(mainMenuController);

        User loggedInUser;

        while (true) {
            loggedInUser = loginMenu.run(scanner);
            if (loggedInUser == null) {
                break;
            }
            mainMenu.run(scanner, loggedInUser);
        }

        UserDatabase.writeInFile("UserDatabase.json");
    }
}
