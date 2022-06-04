package Civilization.View.FXMLControllers;

import Civilization.Model.User;
import Civilization.View.Components.Account;
import Civilization.View.GraphicalBases;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public class ProfileMenuFXMLController {

    @FXML
    private Rectangle background;

    @FXML
    private VBox avatarVBox;

    private Circle userAvatar;

    @FXML
    public void initialize() {
        setBackground();
        setAvatars();
    }

    private void setAvatars() {
        this.userAvatar = new Circle(75);
        avatarVBox.getChildren().add(userAvatar);
        int index = findAvatarIndex(User.loggedInUser.getAvatarURL()) - 1;
        if(index == -1) {
            loadSelectedAvatar();
        } else {
            loadDefaultAvatar(index);
        }
        setAvatarSettings();
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
}
