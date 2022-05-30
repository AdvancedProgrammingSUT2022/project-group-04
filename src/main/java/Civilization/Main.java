package Civilization;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Scanner;

import Civilization.Controller.MainMenuController;
import Civilization.Database.UserDatabase;
import Civilization.Model.MainMenuModel;
import Civilization.Model.User;
import Civilization.View.GraphicalBases;
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

        GraphicalBases.stage = stage;
        GraphicalBases.login();




        Scanner scanner = new Scanner(System.in);

        UserDatabase.readFromFile("UserDatabase.json");

        MainMenuModel mainMenuModel = new MainMenuModel();
        MainMenuController mainMenuController = new MainMenuController(mainMenuModel);
        MainMenu mainMenu = new MainMenu(mainMenuController);

//        while (true) {
//            loggedInUser = loginMenu.run(scanner);
//            if (loggedInUser == null) {
//                break;
//            }
//            mainMenu.run(scanner, loggedInUser);
//        }

        UserDatabase.writeInFile("UserDatabase.json");
    }

}
