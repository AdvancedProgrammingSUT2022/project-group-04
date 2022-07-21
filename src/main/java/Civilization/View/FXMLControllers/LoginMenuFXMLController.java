package Civilization.View.FXMLControllers;

import Civilization.Controller.LoginMenuController;
import Civilization.Database.UserDatabase;
import Civilization.Model.GameModel;
import Civilization.Model.LoginMenuModel;
import Civilization.Model.User;
import Civilization.View.Components.Account;
import Civilization.View.GraphicalBases;
import Civilization.View.Transitions.CursorTransition;
import Client.Client;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDateTime;

public class LoginMenuFXMLController {

    @FXML
    private VBox mainVBox;

    @FXML
    private Label nicknameLabel;

    @FXML
    private TextField nickname;

    @FXML
    private Button loginOrRegister;

    @FXML
    private Rectangle background;

    @FXML
    private Text error;

    @FXML
    private  Button register;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    private boolean userLogin;

    private static final LoginMenuController loginMenuController = new LoginMenuController(new LoginMenuModel());


    @FXML
    public void initialize() {
        setBackground();
        setRegister();
        setCursor();
        GraphicalBases.PlayMenuMusic();
        GameModel.isGame = false;
    }

    public void setCursor() {
        CursorTransition cursorTransition = new CursorTransition(register, 640, 580);
        cursorTransition.play();
    }

    private void setBackground() {
        Image backgroundImage = GraphicalBases.LOGIN_MENU_BACKGROUND;
        ImagePattern backgroundImagePattern = new ImagePattern(backgroundImage);
        this.background.setFill(backgroundImagePattern);
    }

    public void setColorTransparent(KeyEvent keyEvent) {
        this.username.setStyle("-fx-border-color: none");
        this.password.setStyle("-fx-border-color: none");
        if(!userLogin) {
            this.nickname.setStyle("-fx-border-color: none");
        }
        checkAbilityToClickForButtons();
    }

    private void checkAbilityToClickForButtons() {
        this.error.setText("");
        if (this.username.getText().length() != 0
                && this.password.getText().length() != 0) {
            if (!userLogin) {
                if (this.nickname.getText().length() != 0) {
                    this.register.setDisable(false);
                }
            } else {
                this.register.setDisable(false);
            }
        } else {
            if (!userLogin) {
                if (this.nickname.getText().length() == 0) {
                    this.register.setDisable(true);
                }
            } else {
                this.register.setDisable(true);
            }
        }
    }

    public void registerButton(MouseEvent mouseEvent) throws IOException {
        String username = this.username.getText();
        String password = this.password.getText();
        String nickname = this.nickname.getText();
        JSONObject input = new JSONObject();
        input.put("menu type","Login");
        input.put("action","Register");
        input.put("username", username);
        input.put("password", password);
        input.put("nickname", nickname);
        Client.dataOutputStream1.writeUTF(input.toString());
        Client.dataOutputStream1.flush();
        String message = Client.dataInputStream1.readUTF();
        System.out.println(message);
        if (message.equals("Username is not unique")){
            System.out.println("bus");
            setError("Username is not unique");
            return;
        } else if (message.equals("Nickname is not unique")){
            setError("Nickname is not unique");
            return;
        }
        //=======================
        this.error.setText("Registered successfully");
        this.error.setFill(Color.BLUE);
        this.username.setText("");
        this.password.setText("");
        this.nickname.setText("");
        checkAbilityToClickForButtons();
        UserDatabase.writeInFile("UserDatabase.json");
    }

    private void createAccount(String username) throws IOException {
        Account account = new Account(username);
        Account.accounts.add(account);
        Account.writeAccounts("AccountURLs.json");
    }

    private void setError(String errorText) {
        this.error.setText(errorText);
        this.username.setStyle("-fx-border-color: red");
        this.username.setText("");
        if(!userLogin) {
            this.nickname.setStyle("-fx-border-color: red");
            this.nickname.setText("");
        }
        this.password.setStyle("-fx-border-color: red");
        this.password.setText("");
        this.error.setFill(Color.RED);
    }

    public void Exit(MouseEvent mouseEvent) throws IOException {
        UserDatabase.writeInFile("UserDatabase.json");
        System.exit(0);
    }

    public void ChangeRegisterOrLogin(MouseEvent mouseEvent) {
        if(userLogin) {
            setRegister();
        } else {
            setLogin();
        }
    }

    public void loginButton(MouseEvent mouseEvent) throws IOException {
        String username = this.username.getText();
        String password = this.password.getText();
        JSONObject input = new JSONObject();
        input.put("menu type","Login");
        input.put("action","Login");
        input.put("username", username);
        input.put("password", password);
        Client.dataOutputStream1.writeUTF(input.toString());
        Client.dataOutputStream1.flush();
        String message = Client.dataInputStream1.readUTF();
        if (message.equals("Username and password didn't match")){
            setError("Username and password didn't match");
            return;
        }
        User.loggedInUser = UserDatabase.getUserByUsername(username);
        login();
    }

    private void login() {
        User.loggedInUser.setLastLoginTime(LocalDateTime.now());
        GraphicalBases.userLoggedIn();
    }

    private void setLogin() {
        userLogin = true;
        this.username.setText("");
        this.nickname.setText("");
        this.password.setText("");
        this.nickname.setVisible(false);
        this.nicknameLabel.setVisible(false);
        this.register.setText("Login");
        this.register.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    loginButton(mouseEvent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        this.loginOrRegister.setText("Don't have an account?");
    }

    private void setRegister() {
        userLogin = false;
        this.username.setText("");
        this.nickname.setText("");
        this.password.setText("");
        this.nickname.setVisible(true);
        this.nicknameLabel.setVisible(true);
        register.setText("Register");
        register.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    registerButton(mouseEvent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        this.loginOrRegister.setText("Already have an account?");
    }
}
