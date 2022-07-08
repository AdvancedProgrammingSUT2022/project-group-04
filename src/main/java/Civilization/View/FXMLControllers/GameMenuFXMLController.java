package Civilization.View.FXMLControllers;

import Civilization.Database.UserDatabase;
import Civilization.Model.GameModel;
import Civilization.Model.User;
import Civilization.View.GraphicalBases;
import Civilization.View.Transitions.CursorTransition;
import Civilization.View.Transitions.GameMenuUserChoosingTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameMenuFXMLController {

    @FXML
    private AnchorPane mainAnchorPane;

    private Button OKButton;

    // searching users
    private ChoiceBox<String> users;
    private Set<String> selectedUsers;
    private Text usersInGame;
    private TextField numberOfUsers;
    private int usersCount;

    @FXML
    public void initialize() {

        selectedUsers = new HashSet<>();
        selectedUsers.add(User.loggedInUser.getUsername());

        setBackground();
        setOKButton();
        setBackButton();
        setChoiceBox();
        setNumberOfUsers();
    }

    public Button getOKButton() {
        return OKButton;
    }

    private void setNumberOfUsers() {
        Label usersLabel = new Label("Number of Users: ");
        usersLabel.setStyle("-fx-fill: white");
        usersLabel.setLayoutX(800);
        usersLabel.setLayoutY(200);
        mainAnchorPane.getChildren().add(usersLabel);
        Button userOkButton = new Button("OK");
        userOkButton.setLayoutX(usersLabel.getLayoutX() + 170);
        userOkButton.setLayoutY(usersLabel.getLayoutY() + 20);
        userOkButton.setDisable(true);
        userOkButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String text = numberOfUsers.getText();
                Pattern pattern = Pattern.compile("\\d+");
                Matcher matcher = pattern.matcher(text);
                if(!matcher.matches()) {
                    numberOfUsers.setText("");
                    numberOfUsers.setStyle("-fx-border-color: red");
                } else {
                    int number = Integer.parseInt(text);
                    if(number <= 1 || number > User.users.size()) {
                        numberOfUsers.setText("");
                        numberOfUsers.setStyle("-fx-border-color: red");
                    } else {
                        usersCount = number;
                        numberOfUsers.setEditable(false);
                        users.setDisable(false);
                    }
                }
            }
        });
        mainAnchorPane.getChildren().add(userOkButton);
        numberOfUsers = new TextField();
        numberOfUsers.setLayoutX(usersLabel.getLayoutX());
        numberOfUsers.setLayoutY(usersLabel.getLayoutY() + 20);
        numberOfUsers.setPromptText("Number of Users: ");
        numberOfUsers.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String text = numberOfUsers.getText();
                userOkButton.setDisable(text.length() == 0);
                numberOfUsers.setStyle("-fx-border-color: null");
                Pattern pattern = Pattern.compile("\\d+");
                Matcher matcher = pattern.matcher(text);
                if(!matcher.matches()) {
                    numberOfUsers.setText("");
                }
            }
        });
        mainAnchorPane.getChildren().add(numberOfUsers);
    }

    private void setChoiceBox() {

        GameMenuUserChoosingTransition gameMenuUserChoosingTransition = new GameMenuUserChoosingTransition(this);
        gameMenuUserChoosingTransition.play();

        users = new ChoiceBox<>();
        ArrayList<String> usersNames = new ArrayList<>();
        for (User user : User.users) {
            if(user.getUsername().equals(User.loggedInUser.getUsername())) {
                continue;
            }
            usersNames.add(user.getUsername());
        }
        ObservableList<String> usersInput = FXCollections.observableArrayList(usersNames);
        users.setItems(usersInput);
        users.setDisable(true);

        users.setLayoutX(800);
        users.setLayoutY(300);
        mainAnchorPane.getChildren().add(users);

        usersInGame = new Text(calculateText());
        usersInGame.setLayoutX(users.getLayoutX() + 150);
        usersInGame.setLayoutY(users.getLayoutY());
        usersInGame.setStyle("-fx-fill: white; -fx-font-size: 20");
        mainAnchorPane.getChildren().add(usersInGame);
    }

    public void handleChoiceBox() {
        if(users.getValue() != null) {
            selectedUsers.add(users.getValue());
            usersInGame.setText(calculateText());
        }
    }

    private String calculateText() {
        String text = "Users in Game:";
        for (String user : selectedUsers) {
            text += "\n\t" + user;
        }
        return text;
    }

    private void setBackButton() {
        Button backButton = new Button("BACK");
        backButton.setLayoutX(1000);
        backButton.setLayoutY(620);
        backButton.setPrefWidth(100);
        backButton.setPrefHeight(30);
        backButton.setStyle("-fx-background-color: #222c41;-fx-border-color: #555564; -fx-text-fill: white;-fx-border-width: 3;");
        backButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GraphicalBases.changeMenu("MainMenu");
            }
        });
        mainAnchorPane.getChildren().add(backButton);
    }

    public boolean isOKValid() {
        if(selectedUsers.size() != usersCount) {
            return false;
        }
        return true;
    }

    private void setOKButton() {
        OKButton = new Button("OK");
        OKButton.setLayoutX(1110);
        OKButton.setLayoutY(620);
        OKButton.setPrefWidth(100);
        OKButton.setPrefHeight(30);
        OKButton.setDisable(!isOKValid());
        OKButton.setStyle("-fx-background-color: #222c41;-fx-border-color: #555564; -fx-text-fill: white;-fx-border-width: 3;");
        OKButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                runGame();
                GraphicalBases.enterGame("Game");
            }
        });
        mainAnchorPane.getChildren().add(OKButton);
        CursorTransition cursorTransition = new CursorTransition(OKButton, 1220, 660);
        cursorTransition.play();
    }

    private void runGame() {
        ArrayList<String> users = new ArrayList<>(selectedUsers);
        GameModel gameModel = new GameModel();
        gameModel.startGame(users);
    }


    private void setBackground() {
        Rectangle black = new Rectangle();
        black.setX(0);
        black.setY(0);
        black.setWidth(1280);
        black.setHeight(720);
        black.setFill(new ImagePattern(GraphicalBases.BLACK));
        mainAnchorPane.getChildren().add(black);

        Rectangle kingDavid = new Rectangle();
        kingDavid.setX(0);
        kingDavid.setY(70);
        kingDavid.setWidth(900);
        kingDavid.setHeight(720 - kingDavid.getY());
        kingDavid.setFill(new ImagePattern(GraphicalBases.KING_DAVID));
        mainAnchorPane.getChildren().add(kingDavid);
    }
}
