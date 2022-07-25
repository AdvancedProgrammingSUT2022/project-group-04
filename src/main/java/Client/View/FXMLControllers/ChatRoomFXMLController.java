package Client.View.FXMLControllers;

import Client.Client;
import Server.UserDatabase;
import Client.Model.GameModel;
import Server.User;
import Client.View.GraphicalBases;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ChatRoomFXMLController {
    @FXML
    ChoiceBox<String> choiceBox;
    @FXML
    TextField messageBox;
    @FXML
    VBox chatBox;
    @FXML
    AnchorPane chatBoxPane;
    @FXML
    ChoiceBox<String> users;

    @FXML
    public void initialize(){
        GameModel.isGame = false;
        setChoices();
    }

    private void setChoices() {
        ObservableList<String> availableChoices = FXCollections.observableArrayList("public", "private", "rooms");
        choiceBox.setItems(availableChoices);
        ArrayList<String> names = new ArrayList<>();
        for (User user: UserDatabase.getAllUsers()){
            names.add(user.getNickname());
        }
        ObservableList<String> availableUsers = FXCollections.observableArrayList(names);
        users.setItems(availableUsers);
    }

    public void sendMessage(){
        //Todo
        if (!messageBox.getText().isBlank()) {
            String message = messageBox.getText();
            messageBox.clear();
            Text name = new Text(User.loggedInUser.getNickname());
            Label label = new Label();
            label.setText(" " + message + " ");
            Text time = new Text();
            time.setText(LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute() + ":" + LocalDateTime.now().getSecond());

//            try{
//                sendMessageInReal(User.loggedInUser.getNickname(), users.getValue(), message, choiceBox.getValue());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

            Image avatarImage = new Image(GraphicalBases.class.getResource(User.loggedInUser.getAvatarURL()).toString());
            ImageView avatar = new ImageView(avatarImage);
            avatar.setFitHeight(37);
            avatar.setFitWidth(37);
            HBox line = new HBox(avatar, name, label, time);
            name.setStyle("-fx-fill: white; -fx-font-size: 10");
            line.setStyle("-fx-alignment: center-left; -fx-spacing: 10; -fx-padding: 10");
            label.setStyle("-fx-font-size: 15;" +
                    "    -fx-background-color: black;" +
                    "    -fx-border-color: #fffde9;" +
                    "    -fx-text-fill: white;" +
                    "    -fx-border-width: 1;" +
                    "    -fx-fill: white;" +
                    "    -fx-text-alignment: center;" +
                    "    -fx-tile-alignment: center;" +
                    "    -fx-border-radius: 2;" +
                    "-fx-start-margin: 20");
            time.setStyle("-fx-font-size: 10; -fx-fill: white; -fx-text-fill: white;");
            chatBox.getChildren().add(line);
            chatBoxPane.setPrefHeight(chatBoxPane.getPrefHeight() + 50);
            chatBox.setPrefHeight(chatBox.getPrefHeight() + 50);
        }
    }

    private void sendMessageInReal(String nickname, String value, String message, String value1) throws IOException {
        if(value1.equals("public")) {
            value = "ALL USERS";
        } else if (value1.equals("rooms")) {
            value = "ALL IN ROOM";
        }
        JSONObject input = new JSONObject();
        input.put("menu type","Chatroom");
        input.put("action","send message");
        input.put("from", nickname);
        input.put("to", value);
        input.put("text", message);
        input.put("room", value1);
        Client.dataOutputStream1.writeUTF(input.toString());
        Client.dataOutputStream1.flush();
    }

    public void backToMain() {
        GraphicalBases.changeMenu("MainMenu");
    }

    public void changeChatType() {
        if (choiceBox.getValue().equals("private")){
            users.setVisible(true);
        } else {
            users.setVisible(false);
        }
    }
}
