package Civilization.View.FXMLControllers;

import Civilization.Database.UserDatabase;
import Civilization.Model.GameModel;
import Civilization.Model.User;
import Civilization.View.Components.SwitchButton;
import Civilization.View.GraphicalBases;
import Civilization.View.Transitions.CursorTransition;
import Civilization.View.Transitions.GameMenuUserChoosingTransition;
import Civilization.View.Transitions.SwitchButtonTransition;
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

    // map and saving information
    private TextField numberOfTiles;
    private int tileCount;
    private TextField autoSaveRate;
    private int autoSave;
    private SwitchButton autoSaveSwitchButton;
    private Label autoSaveRateLabel;
    private Button autoSaveOKButton;

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
        tileCount = 0;

        setBackground();
        setOKButton();
        setBackButton();
        setChoiceBox();
        setNumberOfUsers();
        setNumberOfTiles();
        setAutoSaveInformation();
    }

    private void setAutoSaveInformation() {
        Label autoSaveLabel = new Label("Auto Saving: ");
        autoSaveLabel.setStyle("-fx-fill: white");
        autoSaveLabel.setLayoutX(800);
        autoSaveLabel.setLayoutY(110);
        mainAnchorPane.getChildren().add(autoSaveLabel);

        autoSaveSwitchButton = new SwitchButton();
        autoSaveSwitchButton.setLayoutX(870);
        autoSaveSwitchButton.setLayoutY(110);
        mainAnchorPane.getChildren().add(autoSaveSwitchButton);
        SwitchButtonTransition switchButtonTransition = new SwitchButtonTransition(autoSaveSwitchButton, this);
        switchButtonTransition.play();

        autoSaveRateLabel = new Label("Auto Saving Rate: ");
        autoSaveRateLabel.setStyle("-fx-fill: white");
        autoSaveRateLabel.setLayoutX(900);
        autoSaveRateLabel.setLayoutY(110);
        autoSaveRateLabel.setVisible(false);
        mainAnchorPane.getChildren().add(autoSaveRateLabel);

        autoSaveRate = new TextField();
        autoSaveRate.setPromptText("Auto Saving Rate: ");
        autoSaveRate.setLayoutX(970);
        autoSaveRate.setLayoutY(110);
        autoSaveRate.setVisible(false);

        autoSaveOKButton = new Button("OK");
        autoSaveOKButton.setLayoutX(1130);
        autoSaveOKButton.setLayoutY(110);
        autoSaveOKButton.setDisable(true);
        autoSaveOKButton.setVisible(false);
        autoSaveOKButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String text = autoSaveRate.getText();
                Pattern pattern = Pattern.compile("\\d+");
                Matcher matcher = pattern.matcher(text);
                if(!matcher.matches()) {
                    autoSaveRate.setText("");
                    autoSaveRate.setStyle("-fx-border-color: red");
                } else {
                    int number = Integer.parseInt(text);
                    if(number <= 0 || number > 10) {
                        autoSaveRate.setText("");
                        autoSaveRate.setStyle("-fx-border-color: red");
                    } else {
                        autoSave = number;
                        autoSaveRate.setEditable(false);
                        autoSaveSwitchButton.setDisable(true);
                    }
                }
            }
        });
        mainAnchorPane.getChildren().add(autoSaveOKButton);

        autoSaveRate.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String text = autoSaveRate.getText();
                autoSaveOKButton.setDisable(text.length() == 0);
                autoSaveRate.setStyle("-fx-border-color: null");
                Pattern pattern = Pattern.compile("\\d+");
                Matcher matcher = pattern.matcher(text);
                if(!matcher.matches()) {
                    autoSaveRate.setText("");
                }
            }
        });
        mainAnchorPane.getChildren().add(autoSaveRate);

    }

    public void handleSwitchButton() {
        autoSaveRateLabel.setVisible(autoSaveSwitchButton.getState());
        autoSaveRate.setVisible(autoSaveRateLabel.isVisible());
        autoSaveOKButton.setVisible(autoSaveRateLabel.isVisible());
    }

    private void setNumberOfTiles() {

        Label label = new Label("Number of Tiles in a Row: ");
        label.setStyle("-fx-fill: white");
        label.setLayoutX(800);
        label.setLayoutY(130);
        mainAnchorPane.getChildren().add(label);


        numberOfTiles = new TextField();
        numberOfTiles.setPromptText("Number of Tiles in a Row: ");
        numberOfTiles.setLayoutX(label.getLayoutX());
        numberOfTiles.setLayoutY(label.getLayoutY() + numberOfTiles.getHeight() + 20);


        Button tileOkButton = new Button("OK");
        tileOkButton.setLayoutX(numberOfTiles.getLayoutX() + numberOfTiles.getWidth() + 170);
        tileOkButton.setLayoutY(numberOfTiles.getLayoutY());
        tileOkButton.setDisable(true);
        tileOkButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String text = numberOfTiles.getText();
                Pattern pattern = Pattern.compile("\\d+");
                Matcher matcher = pattern.matcher(text);
                if(!matcher.matches()) {
                    numberOfTiles.setText("");
                    numberOfTiles.setStyle("-fx-border-color: red");
                } else {
                    int number = Integer.parseInt(text);
                    if(number <= User.users.size() || number > User.users.size() + 50) {
                        numberOfTiles.setText("");
                        numberOfTiles.setStyle("-fx-border-color: red");
                    } else {
                        tileCount = number;
                        numberOfTiles.setEditable(false);
                    }
                }
            }
        });
        mainAnchorPane.getChildren().add(tileOkButton);


        numberOfTiles.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String text = numberOfTiles.getText();
                tileOkButton.setDisable(text.length() == 0);
                numberOfTiles.setStyle("-fx-border-color: null");
                Pattern pattern = Pattern.compile("\\d+");
                Matcher matcher = pattern.matcher(text);
                if(!matcher.matches()) {
                    numberOfTiles.setText("");
                }
            }
        });
        mainAnchorPane.getChildren().add(numberOfTiles);
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
            if(selectedUsers.size() == usersCount) {
                users.setDisable(true);
            }
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
        } else if (tileCount == 0) {
            return false;
        } else if (autoSaveSwitchButton.getState() && autoSaveRate.getText().length() == 0) {
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
