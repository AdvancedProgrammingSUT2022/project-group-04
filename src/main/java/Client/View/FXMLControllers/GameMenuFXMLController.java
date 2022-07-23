package Client.View.FXMLControllers;

import Civilization.Controller.SavingGame;
import Civilization.Database.GameDatabase;
import Civilization.Model.GameModel;
import Server.User;
import Client.View.Components.SwitchButton;
import Client.View.GraphicalBases;
import Client.View.Transitions.CursorTransition;
import Client.View.Transitions.GameMenuUserChoosingTransition;
import Client.View.Transitions.SwitchButtonTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private Button saveButton;

    // game speed
    private SwitchButton speed;

    // searching users
    private ChoiceBox<String> users;
    private Set<String> selectedUsers;
    private Text usersInGame;
    private TextField numberOfUsers;
    private int usersCount;

    // information
    private Text autoSaving;
    private boolean isAutoSavingOn;
    private Text autoSavingRate;
    private boolean isAutoSavingRateOn;
    private Text numberOfTilesInformation;
    private boolean isNumberOfTilesOn;
    private Text numberOfUsersInformation;
    private boolean isNumberOfUsersOn;
    private Text choosingUsers;
    private boolean isChoosingUsersOn;
    private Text listOfUsers;
    private boolean isListOfUsersOn;
    private Text savingGame;
    private boolean isSavingGameOn;
    private Text speedInformation;
    private boolean isSpeedOn;

    @FXML
    public void initialize() {

        selectedUsers = new HashSet<>();
        selectedUsers.add(User.loggedInUser.getUsername());
        tileCount = 0;
        GameModel.isGame = false;

        setBackground();
        setOKButton();
        setBackButton();
        setChoiceBox();
        setNumberOfUsers();
        setNumberOfTiles();
        setAutoSaveInformation();
        setPlayingSavedGame();
        setSpeed();
        setInformation();
    }

    private void setSpeed() {
        speed = new SwitchButton();
        Label label = new Label("X2 Speed: ");
        label.setStyle("-fx-fill: white");
        label.setLayoutX(800);
        label.setLayoutY(40);
        speed.setLayoutX(860);
        speed.setLayoutY(40);

        mainAnchorPane.getChildren().add(label);
        mainAnchorPane.getChildren().add(speed);
    }

    private void setPlayingSavedGame() {
        saveButton = new Button("Playing Saved Game");
        saveButton.setPrefWidth(200);
        saveButton.setLayoutX(800);
        saveButton.setLayoutY(70);
        saveButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                SavingGame.readGame();
                GraphicalBases.enterGame("Game");

            }
        });

        saveButton.setVisible(isFileValid("savedMap.json"));

        mainAnchorPane.getChildren().add(saveButton);
    }

    private boolean isFileValid(String fileName) {
        Path userPath = Paths.get(fileName);
        return userPath.toFile().isFile();
    }

    private void setInformation() {
        setAutoSavingInformation();
        setAutoSavingRateInformation();
        setNumberOfTilesInformation();
        setNumberOfUsersInformation();
        setChoosingUsersInformation();
        setListOfUsersInformation();
        setSavingGameInformation();
        setSpeedInformation();
    }

    private void setSpeedInformation() {
        speedInformation = new Text("Speed of game");
        speedInformation.setStyle("-fx-fill: white; -fx-font-size: 20");
        speedInformation.setLayoutY(speed.getLayoutY() - 10);
        speedInformation.setLayoutX(speed.getLayoutX());
        speedInformation.setVisible(false);
        isSpeedOn = false;
        mainAnchorPane.getChildren().add(speedInformation);
        speed.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!isSpeedOn) {
                    isSpeedOn= true;
                    speedInformation.setVisible(true);
                } else {
                    isSpeedOn = false;
                    speedInformation.setVisible(false);
                }
            }
        });
        speed.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!isSpeedOn) {
                    isSpeedOn= true;
                    speedInformation.setVisible(true);
                } else {
                    isSpeedOn = false;
                    speedInformation.setVisible(false);
                }
            }
        });
    }

    private void setSavingGameInformation() {
        savingGame = new Text("Playing a saved Game");
        savingGame.setStyle("-fx-fill: white; -fx-font-size: 20");
        savingGame.setLayoutY(saveButton.getLayoutY() - 10);
        savingGame.setLayoutX(saveButton.getLayoutX());
        savingGame.setVisible(false);
        isSavingGameOn = false;
        mainAnchorPane.getChildren().add(savingGame);
        saveButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!isSavingGameOn) {
                    isSavingGameOn= true;
                    savingGame.setVisible(true);
                } else {
                    isSavingGameOn = false;
                    savingGame.setVisible(false);
                }
            }
        });
        saveButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!isSavingGameOn) {
                    isSavingGameOn= true;
                    savingGame.setVisible(true);
                } else {
                    isSavingGameOn = false;
                    savingGame.setVisible(false);
                }
            }
        });
    }

    private void setListOfUsersInformation() {
        listOfUsers = new Text("List of Users in Game");
        listOfUsers.setStyle("-fx-fill: white; -fx-font-size: 20");
        listOfUsers.setLayoutY(usersInGame.getLayoutY() - 20);
        listOfUsers.setLayoutX(usersInGame.getLayoutX() + 100);
        listOfUsers.setVisible(false);
        isListOfUsersOn = false;
        mainAnchorPane.getChildren().add(listOfUsers);
        usersInGame.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!isListOfUsersOn) {
                    isListOfUsersOn = true;
                    listOfUsers.setVisible(true);
                } else {
                    isListOfUsersOn = false;
                    listOfUsers.setVisible(false);
                }
            }
        });
        usersInGame.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!isListOfUsersOn) {
                    isListOfUsersOn = true;
                    listOfUsers.setVisible(true);
                } else {
                    isListOfUsersOn = false;
                    listOfUsers.setVisible(false);
                }
            }
        });
    }

    private void setChoosingUsersInformation() {
        choosingUsers = new Text("List of Valid Users");
        choosingUsers.setStyle("-fx-fill: white; -fx-font-size: 20");
        choosingUsers.setLayoutY(users.getLayoutY());
        choosingUsers.setLayoutX(users.getLayoutX() - 100);
        choosingUsers.setVisible(false);
        isChoosingUsersOn = false;
        mainAnchorPane.getChildren().add(choosingUsers);
        users.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!isChoosingUsersOn) {
                    isChoosingUsersOn = true;
                    choosingUsers.setVisible(true);
                } else {
                    isChoosingUsersOn = false;
                    choosingUsers.setVisible(false);
                }
            }
        });
        users.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!isChoosingUsersOn) {
                    isChoosingUsersOn = true;
                    choosingUsers.setVisible(true);
                } else {
                    isChoosingUsersOn = false;
                    choosingUsers.setVisible(false);
                }
            }
        });
    }

    private void setNumberOfUsersInformation() {
        numberOfUsersInformation = new Text("Number of Users in Game");
        numberOfUsersInformation.setStyle("-fx-fill: white; -fx-font-size: 20");
        numberOfUsersInformation.setLayoutY(numberOfUsers.getLayoutY());
        numberOfUsersInformation.setLayoutX(numberOfUsers.getLayoutX() - 100);
        numberOfUsersInformation.setVisible(false);
        isNumberOfUsersOn = false;
        mainAnchorPane.getChildren().add(numberOfUsersInformation);
        numberOfUsers.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!isNumberOfUsersOn) {
                    isNumberOfUsersOn = true;
                    numberOfUsersInformation.setVisible(true);
                } else {
                    isNumberOfUsersOn = false;
                    numberOfUsersInformation.setVisible(false);
                }
            }
        });
        numberOfUsers.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!isNumberOfUsersOn) {
                    isNumberOfUsersOn = true;
                    numberOfUsersInformation.setVisible(true);
                } else {
                    isNumberOfUsersOn = false;
                    numberOfUsersInformation.setVisible(false);
                }
            }
        });
    }


    private void setNumberOfTilesInformation() {
        numberOfTilesInformation = new Text("Number of Tiles in a Row");
        numberOfTilesInformation.setStyle("-fx-fill: white; -fx-font-size: 20");
        numberOfTilesInformation.setLayoutY(numberOfTiles.getLayoutY());
        numberOfTilesInformation.setLayoutX(numberOfTiles.getLayoutX() - 100);
        numberOfTilesInformation.setVisible(false);
        isNumberOfTilesOn = false;
        mainAnchorPane.getChildren().add(numberOfTilesInformation);
        numberOfTiles.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!isNumberOfTilesOn) {
                    isNumberOfTilesOn = true;
                    numberOfTilesInformation.setVisible(true);
                } else {
                    isNumberOfTilesOn = false;
                    numberOfTilesInformation.setVisible(false);
                }
            }
        });
        numberOfTiles.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!isNumberOfTilesOn) {
                    isNumberOfTilesOn = true;
                    numberOfTilesInformation.setVisible(true);
                } else {
                    isNumberOfTilesOn = false;
                    numberOfTilesInformation.setVisible(false);
                }
            }
        });
    }

    private void setAutoSavingRateInformation() {
        autoSavingRate = new Text("AutoSaving Rate");
        autoSavingRate.setStyle("-fx-fill: white; -fx-font-size: 20");
        autoSavingRate.setLayoutY(autoSaveRate.getLayoutY());
        autoSavingRate.setLayoutX(autoSaveRate.getLayoutX() - 100);
        autoSavingRate.setVisible(false);
        isAutoSavingRateOn = false;
        mainAnchorPane.getChildren().add(autoSavingRate);
        autoSaveRate.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!isAutoSavingRateOn) {
                    isAutoSavingRateOn = true;
                    autoSavingRate.setVisible(true);
                } else {
                    isAutoSavingRateOn = false;
                    autoSavingRate.setVisible(false);
                }
            }
        });
        autoSaveRate.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!isAutoSavingRateOn) {
                    isAutoSavingRateOn = true;
                    autoSavingRate.setVisible(true);
                } else {
                    isAutoSavingRateOn = false;
                    autoSavingRate.setVisible(false);
                }
            }
        });
    }

    private void setAutoSavingInformation() {
        autoSaving = new Text("AutoSaving");
        autoSaving.setStyle("-fx-fill: white; -fx-font-size: 20");
        autoSaving.setLayoutY(autoSaveSwitchButton.getLayoutY());
        autoSaving.setLayoutX(autoSaveSwitchButton.getLayoutX() - 100);
        autoSaving.setVisible(false);
        isAutoSavingOn = false;
        mainAnchorPane.getChildren().add(autoSaving);
        autoSaveSwitchButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!isAutoSavingOn) {
                    isAutoSavingOn = true;
                    autoSaving.setVisible(true);
                } else {
                    isAutoSavingOn = false;
                    autoSaving.setVisible(false);
                }
            }
        });
        autoSaveSwitchButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!isAutoSavingOn) {
                    isAutoSavingOn = true;
                    autoSaving.setVisible(true);
                } else {
                    isAutoSavingOn = false;
                    autoSaving.setVisible(false);
                }
            }
        });
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
        GameModel.autoSave = autoSaveSwitchButton.getState();
    }

    private void setNumberOfTiles() {

        Label label = new Label("Number of Tiles in a Column: ");
        label.setStyle("-fx-fill: white");
        label.setLayoutX(800);
        label.setLayoutY(130);
        mainAnchorPane.getChildren().add(label);


        numberOfTiles = new TextField();
        numberOfTiles.setPromptText("Number of Tiles in a Column: ");
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
                    if(number <= 10 || number > 20) {
                        numberOfTiles.setText("");
                        numberOfTiles.setStyle("-fx-border-color: red");
                    } else {
                        tileCount = number;
                        numberOfTiles.setEditable(false);
                        GameDatabase.setWidth(number);
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
                        users.setVisible(true);
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
        users.setVisible(false);

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
//        } else if (autoSaveSwitchButton.getState() && autoSaveRate.getText().length() == 0) {
//            return false;
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
                try {
                    runGame();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                GraphicalBases.enterGame("Game");
            }
        });
        mainAnchorPane.getChildren().add(OKButton);
        CursorTransition cursorTransition = new CursorTransition(OKButton, 1220, 660);
        cursorTransition.play();
    }

    private void runGame() throws IOException {
        GameDatabase.setYou();
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
