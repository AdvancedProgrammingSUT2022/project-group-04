package Civilization.View.FXMLControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class LoginMenuFXMLController {

    @FXML
    private TextField nickname;

    @FXML
    private Button loginOrRegister;

    @FXML
    private Rectangle background;

    @FXML
    private Text error;

    @FXML
    private Button exit;

    @FXML
    private Button register;

    @FXML
    private Button login;

    @FXML
    private TextField password;

    @FXML
    private TextField username;

    private boolean userLogin;

    @FXML
    public void initialize() {
        userLogin = true;
        setBackground();
    }

    private void setBackground() {
        Image backgroundImage = new Image(getClass().getResource("/pics/Menus/LoginMenuBackground.png").toExternalForm());
        ImagePattern backgroundImagePattern = new ImagePattern(backgroundImage);
        this.background.setFill(backgroundImagePattern);
    }

    public void setColorTransparent(KeyEvent keyEvent) {
    }

    public void LoginButton(MouseEvent mouseEvent) {
    }

    public void RegisterButton(MouseEvent mouseEvent) {
    }

    public void Exit(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void ChangeRegisterOrLogin(MouseEvent mouseEvent) {
        if(userLogin) {
            userLogin = false;
        } else {
            userLogin = true;
        }
    }
}
