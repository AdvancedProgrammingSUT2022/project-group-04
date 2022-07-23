package Client.View.FXMLControllers;

import Civilization.Controller.ProfileMenuController;
import Civilization.Model.GameModel;
import Civilization.Model.ProfileMenuModel;
import Server.User;
import Client.View.Components.Account;
import Client.View.GraphicalBases;
import Client.View.Transitions.CursorTransition;
import Client.Client;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ProfileMenuFXMLController {

    @FXML
    private Button changePassword;

    @FXML
    private PasswordField currentPasswordTextField;

    @FXML
    private PasswordField newPasswordTextField;

    @FXML
    private Button passwordOKButton;

    @FXML
    private Text passwordError;

    @FXML
    private Label userLabel;

    @FXML
    private Button changeNickname;

    @FXML
    private TextField changeNicknameTextField;

    @FXML
    private Button nicknameOKButton;

    @FXML
    private Text nicknameError;

    @FXML
    private Rectangle background;

    @FXML
    private VBox avatarVBox;

    private Circle userAvatar;
    private ProfileMenuController profileMenuController;

    @FXML
    public void initialize() {
        this.profileMenuController = new ProfileMenuController(new ProfileMenuModel());
        setBackground();
        setCursorTransitions();
        setUsername();
        try {
            setAvatars();
        } catch (Exception e) {

        }
        GameModel.isGame = false;
    }

    private void setCursorTransitions() {
        ArrayList<Button> cursorSets = new ArrayList<>();
        cursorSets.add(nicknameOKButton);
        cursorSets.add(passwordOKButton);
        double[] buttonXs = new double[2];
        buttonXs[0] = 630;
        buttonXs[1] = 1130;
        double[] buttonYs = new double[2];
        buttonYs[0] = 480;
        buttonYs[1] = 520;
        CursorTransition cursorTransition = new CursorTransition(cursorSets, buttonXs, buttonYs);
        cursorTransition.play();
    }

    private void setUsername() {
        this.userLabel.setText(User.loggedInUser.getUsername());
    }

    private void setAvatars() throws IOException {
        this.userAvatar = new Circle(75);
        avatarVBox.getChildren().add(userAvatar);
        String avatarURL = getAvatarURL(User.loggedInUser.getUsername());
        int index = findAvatarIndex(avatarURL) - 1;
        if(index == -1) {
            loadSelectedAvatar();
        } else {
            loadDefaultAvatar(index);
        }
        setAvatarSettings();
    }

    private String getAvatarURL(String username) throws IOException {
        JSONObject input = new JSONObject();
        input.put("menu type","Profile");
        input.put("action","avatarURL");
        input.put("username", username);
        Client.dataOutputStream1.writeUTF(input.toString());
        Client.dataOutputStream1.flush();
        String message = Client.dataInputStream1.readUTF();
        System.out.println(message);
        return message;
    }

    private void setAvatarSettings() {
        userAvatar.setCursor(Cursor.HAND);
        userAvatar.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                userAvatar.setEffect(new DropShadow());
            }
        });
        userAvatar.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                userAvatar.setEffect(null);
            }
        });
        userAvatar.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(avatarVBox.getChildren().get(1).isVisible()) {
                    hideAvatars();
                } else {
                    showAvatars();
                }
            }
        });
        addAnotherAvatars();
        addPlusButton();
    }

    private void addPlusButton() {
        Circle circle = new Circle(30);
        ImagePattern profilePicture = new ImagePattern(GraphicalBases.ADD_AVATAR_ICON);
        circle.setFill(profilePicture);
        circle.setVisible(false);
        circle.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                hideAvatars();
                try {
                    loadAvatar(null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Account.writeAccounts("AccountURLs.json");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        avatarVBox.getChildren().add(circle);
    }

    public void loadAvatar(MouseEvent mouseEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        configureFileChooser(fileChooser);
        File file = fileChooser.showOpenDialog(GraphicalBases.stage);
        if (file != null) {
            User.loggedInUser.setAvatarURL(file.toURI().toURL().toExternalForm());
            ImagePattern profilePicture = new ImagePattern(new Image(User.loggedInUser.getAvatarURL()));
            Circle circle = (Circle) avatarVBox.getChildren().get(0);
            circle.setFill(profilePicture);
        }
    }

    private void configureFileChooser(
            final FileChooser fileChooser) {
        fileChooser.setTitle("View Pictures");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
    }

    private void showAvatars() {
        for (int i = 0; i < GraphicalBases.NUMBER_OF_AVATARS + 1; i++) {
            avatarVBox.getChildren().get(i + 1).setVisible(true);
        }
    }

    private void hideAvatars() {
        for (int i = 0; i < GraphicalBases.NUMBER_OF_AVATARS + 1; i++) {
            avatarVBox.getChildren().get(i + 1).setVisible(false);
        }
    }

    private void addAnotherAvatars() {
        for (int i = 0; i < GraphicalBases.NUMBER_OF_AVATARS; i++) {
            Circle circle = new Circle(30);
            ImagePattern profilePicture = new ImagePattern(GraphicalBases.AVATARS[i]);
            circle.setFill(profilePicture);
            circle.setVisible(false);
            int finalI = i;
            circle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    hideAvatars();
                    ImagePattern profilePicture = new ImagePattern(GraphicalBases.AVATARS[finalI]);
                    Circle circle = (Circle) avatarVBox.getChildren().get(0);
                    circle.setFill(profilePicture);
                    User.loggedInUser.setAvatarURL("/pics/Avatars/" + (finalI + 1) + ".png");
                    try {
                        Account.writeAccounts("AccountURLs.json");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            avatarVBox.getChildren().add(circle);
        }
    }

    private void loadDefaultAvatar(int index) {
        userAvatar.setFill(new ImagePattern(GraphicalBases.AVATARS[index]));
    }

    private void loadSelectedAvatar() {
        userAvatar.setFill(new ImagePattern(new Image(User.loggedInUser.getAvatarURL())));
    }

    private int findAvatarIndex(String avatarURL) {
        String prefix = "/pics/Avatars/";
        String suffix = ".png";
        int index = 0;
        if(avatarURL.matches("\\/pics\\/Avatars\\/\\d+.png")) {
            index = Integer.parseInt(avatarURL.substring(prefix.length(), avatarURL.length() - suffix.length()));
        }
        return index;
    }

    private void setBackground() {
        Image backgroundImage = GraphicalBases.PROFILE_MENU_BACKGROUND_IMAGE;
        ImagePattern backgroundImagePattern = new ImagePattern(backgroundImage);
        this.background.setFill(backgroundImagePattern);
    }

    public void openNicknameTextField(MouseEvent mouseEvent) {
        if(changeNicknameTextField.isVisible()) {
            resetProfileMenu();
            return;
        }
        this.changeNicknameTextField.setVisible(true);
        this.nicknameOKButton.setVisible(true);
    }

    public void openPasswordTextField(MouseEvent mouseEvent) {
        if(currentPasswordTextField.isVisible()) {
            resetProfileMenu();
            return;
        }
        this.currentPasswordTextField.setVisible(true);
        this.newPasswordTextField.setVisible(true);
        this.passwordOKButton.setVisible(true);
    }

    public void visibleOkNickname(KeyEvent mouseEvent) {
        this.nicknameError.setText("");
        this.changeNicknameTextField.setStyle("-fx-border-color: none");
        this.nicknameOKButton.setDisable(false);
    }


    public void visibleOkPassword(KeyEvent keyEvent) {
        if(this.newPasswordTextField.getText().length() > 0
            && this.currentPasswordTextField.getText().length() > 0) {
            this.passwordError.setText("");
            this.currentPasswordTextField.setStyle("-fx-border-color: none");
            this.newPasswordTextField.setStyle("-fx-border-color: none");
            this.passwordOKButton.setDisable(false);
        } else {
            this.passwordOKButton.setDisable(true);
        }
    }

    public void changeNickname(MouseEvent mouseEvent) throws IOException {
        String nickname = this.changeNicknameTextField.getText();
        JSONObject input = new JSONObject();
        input.put("menu type","Profile");
        input.put("action","change nickname");
        input.put("nickname", nickname);
        input.put("username", User.loggedInUser.getUsername());
        //System.out.println("I'm writing");
        Client.dataOutputStream1.writeUTF(input.toString());
        Client.dataOutputStream1.flush();
        //System.out.println("wrote");
        String message = Client.dataInputStream1.readUTF();
        System.out.println(message);
        if(message.equals("Nickname is not unique")) {
            setNicknameError("Nickname is not unique");
            return;
        }
        if(message.equals("Please enter a new nickname")) {
            setNicknameError("Please enter a new nickname");
            return;
        }
        resetProfileMenu();
    }

    public void changePassword(MouseEvent mouseEvent) throws IOException {
        String password = this.currentPasswordTextField.getText();
        String newPassword = this.newPasswordTextField.getText();
        JSONObject input = new JSONObject();
        input.put("menu type","Profile");
        input.put("action","change password");
        input.put("password", password);
        input.put("new password",newPassword);
        input.put("username", User.loggedInUser.getUsername());
        Client.dataOutputStream1.writeUTF(input.toString());
        Client.dataOutputStream1.flush();
        String message = Client.dataInputStream1.readUTF();
        System.out.println(message);
        if(message.equals("current password is invalid")) {
            setPasswordError("current password is invalid");
            return;
        }
        if(message.equals("Please enter a new password")) {
            setPasswordError("Please enter a new password");
            return;
        }
        resetProfileMenu();
    }

    private void setNicknameError(String errorText) {
        this.changeNicknameTextField.setStyle("-fx-border-color: red");
        this.changeNicknameTextField.setText("");
        this.nicknameError.setText(errorText);
        this.nicknameError.setFill(Color.RED);
        this.nicknameOKButton.setDisable(true);
    }

    private void setPasswordError(String errorText) {
        this.currentPasswordTextField.setStyle("-fx-border-color: red");
        this.currentPasswordTextField.setText("");
        this.newPasswordTextField.setStyle("-fx-border-color: red");
        this.newPasswordTextField.setText("");
        this.passwordError.setText(errorText);
        this.passwordError.setFill(Color.RED);
        passwordOKButton.setDisable(true);
    }

    private void resetProfileMenu() {
        this.changeNicknameTextField.setText("");
        this.changeNicknameTextField.setVisible(false);
        this.changeNicknameTextField.setStyle("-fx-border-color: null");
        this.nicknameOKButton.setDisable(true);
        this.nicknameOKButton.setVisible(false);
        this.nicknameError.setText("");
        this.currentPasswordTextField.setText("");
        this.currentPasswordTextField.setVisible(false);
        this.currentPasswordTextField.setStyle("-fx-border-color: null");
        this.newPasswordTextField.setText("");
        this.newPasswordTextField.setVisible(false);
        this.newPasswordTextField.setStyle("-fx-border-color: null");
        this.passwordOKButton.setDisable(true);
        this.passwordOKButton.setVisible(false);
        this.passwordError.setText("");
    }

    public void BackToMainMenu(MouseEvent mouseEvent) {
        GraphicalBases.changeMenu("MainMenu");
    }
}
