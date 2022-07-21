package Civilization.View.FXMLControllers;

import Civilization.Model.GameModel;
import Server.User;
import Civilization.View.GraphicalBases;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

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

    public void logout(MouseEvent mouseEvent) {
        GraphicalBases.login();
        //TODO send req to server
    }

    public void goToProfileMenu(MouseEvent mouseEvent) {
        GraphicalBases.changeMenu("ProfileMenu");
    }

    public void goToGameMenu(MouseEvent mouseEvent) {
        GraphicalBases.changeMenu("GameMenu");
        //GraphicalBases.enterGame("Game");
    }

    public void goToScoreboard(MouseEvent mouseEvent) {
        GraphicalBases.changeMenu("LeaderBoard");
    }

    public void goToChatroom(MouseEvent mouseEvent) {
        GraphicalBases.changeMenu("Chatroom");
    }
}
