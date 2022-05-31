package Civilization.View.FXMLControllers;

import Civilization.Model.User;
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
    }

    public void goToProfileMenu(MouseEvent mouseEvent) {
    }

    public void goToGameMenu(MouseEvent mouseEvent) {
    }

    public void goToScoreboard(MouseEvent mouseEvent) {
    }

    public void goToChatroom(MouseEvent mouseEvent) {
    }
}
