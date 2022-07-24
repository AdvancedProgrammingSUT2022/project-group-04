package Client.View.FXMLControllers;

import Client.Model.GameModel;
import Client.Client;
import Server.User;
import Client.View.GraphicalBases;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import org.json.JSONObject;

import java.io.IOException;

public class MainMenuFXMLController {

    @FXML
    private Label userLabel;

    @FXML
    private VBox mainVBox;

    @FXML
    private Rectangle background;

    @FXML
    public void initialize() {
        setBackground();
        setNameLabel();
        GameModel.isGame = false;
    }

    private void setNameLabel() {
        this.userLabel.setText("Welcome\n" + User.loggedInUser.getUsername() + "!");
    }

    private void setBackground() {
        Image backgroundImage = GraphicalBases.MAIN_MENU_BACKGROUND_IMAGE;
        ImagePattern backgroundImagePattern = new ImagePattern(backgroundImage);
        this.background.setFill(backgroundImagePattern);
    }

    public void logout(MouseEvent mouseEvent) throws IOException {
        JSONObject input = new JSONObject();
        input.put("menu type","Main");
        input.put("action","logout");
        Client.dataOutputStream1.writeUTF(input.toString());
        Client.dataOutputStream1.flush();
        GraphicalBases.login();
    }

    public void goToProfileMenu(MouseEvent mouseEvent) throws IOException {
        JSONObject input = new JSONObject();
        input.put("menu type","Main");
        input.put("action","profile");
        Client.dataOutputStream1.writeUTF(input.toString());
        Client.dataOutputStream1.flush();
        GraphicalBases.changeMenu("ProfileMenu");
    }

    public void goToGameMenu(MouseEvent mouseEvent) {
        try{
            if(shallGoToGameMenu()) {
                GraphicalBases.changeMenu("GameMenu");
            } else if (haveNotAcceptedInvitation() || theyDoNotInviteYou()) {
                GraphicalBases.changeMenu("invitation");
            } else {
                GraphicalBases.changeMenu("Loading");
            }
        } catch (Exception e) {

        }
    }

    private boolean theyDoNotInviteYou() throws IOException {
        JSONObject input = new JSONObject();
        input.put("menu type","invitation");
        input.put("action","isAGame");
        input.put("username", User.loggedInUser.getUsername());
        Client.dataOutputStream1.writeUTF(input.toString());
        Client.dataOutputStream1.flush();
        Boolean bool = Boolean.parseBoolean(Client.dataInputStream1.readUTF());
        System.out.println(bool);
        return bool;
    }

    private boolean haveNotAcceptedInvitation() throws IOException {
        JSONObject input = new JSONObject();
        input.put("menu type","invitation");
        input.put("action","haveNotAcceptedInvitation");
        input.put("username", User.loggedInUser.getUsername());
        Client.dataOutputStream1.writeUTF(input.toString());
        Client.dataOutputStream1.flush();
        Boolean bool = Boolean.parseBoolean(Client.dataInputStream1.readUTF());
        System.out.println(bool);
        return bool;
    }

    private boolean shallGoToGameMenu() throws IOException {
        JSONObject input = new JSONObject();
        input.put("menu type","invitation");
        input.put("action","getAllInvitations");
        Client.dataOutputStream1.writeUTF(input.toString());
        Client.dataOutputStream1.flush();
        Boolean bool = Boolean.parseBoolean(Client.dataInputStream1.readUTF());
        System.out.println(bool);
        return bool;
    }

    public void goToScoreboard(MouseEvent mouseEvent) {
        GraphicalBases.changeMenu("LeaderBoard");
    }

    public void goToChatroom(MouseEvent mouseEvent) {
        GraphicalBases.changeMenu("Chatroom");
    }
}
