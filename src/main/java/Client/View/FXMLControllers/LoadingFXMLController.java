package Client.View.FXMLControllers;

import Civilization.Model.GameModel;
import Client.Client;
import Client.View.GraphicalBases;
import Client.View.Transitions.CheckStartGameTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class LoadingFXMLController {

    @FXML
    private AnchorPane mainAnchorPane;

    private ArrayList<String> usersInGame;

    private CheckStartGameTransition checkStartGameTransition;

    private Text text;
    private Button button;

    @FXML
    public void initialize() {
        usersInGame = new ArrayList<>();
        this.checkStartGameTransition = new CheckStartGameTransition(this);
        this.checkStartGameTransition.play();
        setBackground();
        setText();
        setOkButton();
    }

    private void setOkButton() {
        button = new Button("OK");
        button.setLayoutX(590);
        button.setLayoutY(650);
        button.setPrefWidth(100);
        button.setVisible(false);
        button.setStyle("-fx-background-color: #222c41;-fx-border-color: #555564; -fx-text-fill: white;-fx-border-width: 3;");
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GraphicalBases.userLoggedIn();
            }
        });
        mainAnchorPane.getChildren().add(button);
    }

    private void setText() {
        text = new Text("");
        text.setX(500);
        text.setY(500);
        text.setStyle("-fx-fill: white; -fx-font-size: 50");
        mainAnchorPane.getChildren().add(text);
    }

    private void setBackground() {
        Rectangle rectangle = new Rectangle();
        rectangle.setX(0);
        rectangle.setY(0);
        rectangle.setHeight(720);
        rectangle.setWidth(1280);
        rectangle.setFill(new ImagePattern(GraphicalBases.LOADING_MENU));
        mainAnchorPane.getChildren().add(rectangle);
    }

    public boolean isGettingUsersValid() throws IOException {
        JSONObject input = new JSONObject();
        input.put("menu type","Loading");
        input.put("action","isGettingUserValid");
        Client.dataOutputStream1.writeUTF(input.toString());
        Client.dataOutputStream1.flush();
        Boolean message = Boolean.parseBoolean(Client.dataInputStream1.readUTF());
        System.out.println(message);
        return message;
    }

    public void updateUsersInGame() throws IOException {
        JSONObject input = new JSONObject();
        input.put("menu type","Loading");
        input.put("action","getUsers");
        Client.dataOutputStream1.writeUTF(input.toString());
        Client.dataOutputStream1.flush();
        String[] users = Client.dataInputStream1.readUTF().split(" ");
        System.out.println(users.length);
        updateUsersNames(users);
        updateText();
    }

    private void updateText() {
        this.text.setText(this.usersInGame.size() + " Players in Game.");
    }

    private void updateUsersNames(String[] users) {
        this.usersInGame = new ArrayList<>();
        usersInGame.addAll(Arrays.asList(users));
    }


    public void shallGoIn() throws IOException {
        if(isGettingUsersValid()) {
            return;
        }
        System.out.println(this.usersInGame.size());
        if(!isGettingUsersValid() && this.usersInGame.size() <= 1) {
            this.checkStartGameTransition.pause();
            expireGame();
        } else {
            this.checkStartGameTransition.pause();
            runGame();
            GraphicalBases.enterGame("Game");
        }
    }

    private void expireGame() {
        text.setText("Game Expired");
        button.setVisible(true);
    }

    private void runGame() throws IOException {
        GameModel gameModel = new GameModel();
        gameModel.startGame(usersInGame);
    }
}
