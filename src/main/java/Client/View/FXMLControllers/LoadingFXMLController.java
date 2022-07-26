package Client.View.FXMLControllers;

import Client.Client;
import Server.Model.GameModel;
import Client.View.GraphicalBases;
import Client.View.Transitions.CheckStartGameTransition;
import Server.User;
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

    private Text users;

    private Button refresh;
    private Button goNow;

    private boolean isAdmin;


    @FXML
    public void initialize() {
        usersInGame = new ArrayList<>();
        this.checkStartGameTransition = new CheckStartGameTransition(this);
        this.checkStartGameTransition.play();
        setBackground();
        setText();
        setOkButton();
        setRefreshButton();

        try{
            falseGameModel();
        } catch (Exception e) {

        }

        isAdmin = false;
        try {
            isAdmin();
        } catch (Exception e) {

        }

        setGoToNowButton();

        try{
            updateUsersInGame();
        } catch (Exception e) {

        }
        updateText();
    }

    private void isAdmin() throws IOException {
        JSONObject input = new JSONObject();
        input.put("menu type","Loading");
        input.put("action","isAdmin");
        input.put("username", User.loggedInUser.getUsername());
        Client.dataOutputStream1.writeUTF(input.toString());
        Client.dataOutputStream1.flush();
        isAdmin = Boolean.parseBoolean(Client.dataInputStream1.readUTF());
        System.out.println(isAdmin);
    }

    private void falseGameModel() throws IOException {
        JSONObject input = new JSONObject();
        input.put("menu type","Main");
        input.put("action","false game model");
        Client.dataOutputStream1.writeUTF(input.toString());
        Client.dataOutputStream1.flush();
    }

    private void expireAll() throws IOException {
        JSONObject input = new JSONObject();
        input.put("menu type","invitation");
        input.put("action","expireAll");
        Client.dataOutputStream1.writeUTF(input.toString());
        Client.dataOutputStream1.flush();
    }

    private void setGoToNowButton() {
        goNow = new Button("Go Now");
        goNow.setLayoutX(990);
        goNow.setLayoutY(300);
        goNow.setPrefWidth(200);
        goNow.setStyle("-fx-background-color: #222c41;-fx-border-color: #555564; -fx-text-fill: white;-fx-border-width: 3;");
        goNow.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    expireAll();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        goNow.setVisible(false);
        mainAnchorPane.getChildren().add(goNow);
    }

    private void setRefreshButton() {
        refresh = new Button("Refresh");
        refresh.setLayoutX(990);
        refresh.setLayoutY(250);
        refresh.setPrefWidth(200);
        refresh.setVisible(true);
        refresh.setStyle("-fx-background-color: #222c41;-fx-border-color: #555564; -fx-text-fill: white;-fx-border-width: 3;");
        refresh.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    updateUsersInGame();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                updateText();
            }
        });
        mainAnchorPane.getChildren().add(refresh);
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

        users = new Text("It's Just You!");
        users.setX(50);
        users.setY(50);
        users.setStyle("-fx-fill: white; -fx-font-size: 50");
        mainAnchorPane.getChildren().add(users);

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

    private void updateUsersNames(String[] usersArray) {
        this.usersInGame = new ArrayList<>();
        usersInGame.addAll(Arrays.asList(usersArray));
        if(usersArray.length > 1) {
            users.setText("Players In Game:\n" + userString(usersArray));
            if(isAdmin) {
                goNow.setVisible(true);
            } else {
                goNow.setVisible(false);
            }
        }
    }

    private String userString(String[] usersArray) {
        String result = "";
        for (String name : usersArray) {
            result += name + "\n";
        }
        return result;
    }


    public void shallGoIn() throws IOException {
        if(isGettingUsersValid()) {
            if(usersInGame.size() == usersInvited()) {
                goToGame();
            }
            return;
        }
        System.out.println(this.usersInGame.size());
        if(!isGettingUsersValid() && this.usersInGame.size() <= 1) {
            this.checkStartGameTransition.pause();
            expireGame();
        } else {
            goToGame();
        }
    }

    private int usersInvited() throws IOException {
        JSONObject input = new JSONObject();
        input.put("menu type","Loading");
        input.put("action","number");
        Client.dataOutputStream1.writeUTF(input.toString());
        Client.dataOutputStream1.flush();
        int result = Integer.parseInt(Client.dataInputStream1.readUTF());
        System.out.println(result);
        return result;
    }

    private void goToGame() throws IOException {
        this.checkStartGameTransition.pause();
        runGame();
        GraphicalBases.enterGame("Game");
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
