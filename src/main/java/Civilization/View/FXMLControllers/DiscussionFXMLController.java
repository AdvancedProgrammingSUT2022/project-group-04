package Civilization.View.FXMLControllers;

import Civilization.Database.GameDatabase;
import Civilization.Model.Civilization;
import Civilization.View.GraphicalBases;
import Civilization.View.Transitions.DiscussionUserChoosingTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class DiscussionFXMLController {

    @FXML
    private AnchorPane mainAnchorPane;

    private String you;
    private String defaultText;
    private Civilization selected;

    private ChoiceBox<String> usersChoiceBox;
    private VBox mainVBox;
    private HBox mainHBox;
    private HBox sendHBox;
    private TextArea messagePart;
    private TextField message;

    @FXML
    public void initialize() {
        you = GameDatabase.getCivilizationByTurn(GameDatabase.getTurn()).getNickname();
        defaultText = you + " Discussions:\n";
        setBackground();
        setListOfUsers();
        setMessagesPart();
        setSendPart();
    }

    public HBox getSendHBox() {
        return sendHBox;
    }

    private void setSendPart() {
        sendHBox = new HBox();
        message = new TextField();
        message.setPromptText("Write your Message: ");
        message.setPrefWidth(650);

        sendHBox.setSpacing(10);
        sendHBox.getChildren().add(message);

        Button button = new Button("SEND");
        button.setStyle("-fx-background-color: #222c41;-fx-border-color: #555564; -fx-text-fill: white;-fx-border-width: 3;");
        button.setDisable(true);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                sendMessage(message.getText());
                writeMessages();
                message.setText("");
            }
        });

        sendHBox.getChildren().add(button);

        message.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                button.setDisable(message.getText().isEmpty());
            }
        });

        mainVBox.getChildren().add(sendHBox);
    }

    private void sendMessage(String text) {
        String messageToYou = "Message to " + selected.getNickname() + ":\n\t" + text;
        GameDatabase.getCivilizationByNickname(you).getMessages().add(messageToYou);

        String messageToCivilization = "Message from " + you + ":\n\t" + text;
        selected.getMessages().add(messageToCivilization);
    }

    private void setMessagesPart() {
        messagePart = new TextArea();
        messagePart.setPrefWidth(300);
        messagePart.setPrefHeight(300);
        messagePart.setText(defaultText);
        messagePart.setEditable(false);
        writeMessages();
        mainVBox.getChildren().add(messagePart);
    }

    private void writeMessages() {
        if(GameDatabase.getCivilizationByNickname(you).getMessages().size() == 0) {
            messagePart.setText(defaultText + "No Messages Yet.");
        } else {
            String text = defaultText;
            for (String message : GameDatabase.getCivilizationByNickname(you).getMessages()) {
                text += message + "\n";
            }
            messagePart.setText(text);
        }
    }

    private void setListOfUsers() {
        mainVBox = new VBox();
        mainHBox = new HBox();

        mainHBox.setSpacing(10);
        mainVBox.setSpacing(10);

        mainVBox.setLayoutX(50);
        mainVBox.setLayoutY(50);

        Text text = new Text("Discussion And Diplomacy Panel");
        text.setStyle("-fx-fill: white; -fx-font-size: 30");

        mainHBox.getChildren().add(text);

        setBackButton();

        usersChoiceBox = new ChoiceBox<>();
        usersChoiceBox.setPrefWidth(100);
        setUsersChoiceBox();

        mainHBox.getChildren().add(usersChoiceBox);

        mainVBox.getChildren().add(mainHBox);
        mainAnchorPane.getChildren().add(mainVBox);

    }

    private void setUsersChoiceBox() {

        DiscussionUserChoosingTransition discussionUserChoosingTransition = new DiscussionUserChoosingTransition(this);
        discussionUserChoosingTransition.play();

        ArrayList<String> users = new ArrayList<>();
        for (Civilization civilization : GameDatabase.players) {
            if(civilization.getNickname().equals(you)) {
                continue;
            }
            users.add(civilization.getNickname());
        }
        ObservableList<String> usersInput = FXCollections.observableArrayList(users);
        usersChoiceBox.setItems(usersInput);

    }

    private void setBackground() {
        Rectangle black = new Rectangle();
        black.setHeight(720);
        black.setWidth(1280);
        black.setX(0);
        black.setY(0);
        black.setFill(new ImagePattern(GraphicalBases.BLACK));
        mainAnchorPane.getChildren().add(black);

        Rectangle ezekiel = new Rectangle();
        ezekiel.setWidth(500);
        ezekiel.setHeight(720);
        ezekiel.setX(1280 - ezekiel.getWidth());
        ezekiel.setY(0);
        ezekiel.setFill(new ImagePattern(GraphicalBases.EZEKIEL));
        mainAnchorPane.getChildren().add(ezekiel);
    }

    private void setBackButton() {
        Button backButton = new Button("BACK");
        backButton.setPrefWidth(100);
        backButton.setStyle("-fx-background-color: #222c41;-fx-border-color: #555564; -fx-text-fill: white;-fx-border-width: 3;");
        backButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GraphicalBases.enterGame("Game");
            }
        });
        mainHBox.getChildren().add(backButton);
    }

    public void handleChoiceBox() {
        if(usersChoiceBox.getValue() != null) {
            String nickname = usersChoiceBox.getValue();
            selected = GameDatabase.getCivilizationByNickname(nickname);
            sendHBox.setVisible(true);
        } else {
            sendHBox.setVisible(false);
        }
    }
}
