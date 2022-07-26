package Client.View.FXMLControllers;

import Client.Database.GameDatabase;
import Client.Client;
import Server.Model.Civilization;
import Server.Model.GameModel;
import Client.View.GraphicalBases;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class WinFXMLController {

    @FXML
    private AnchorPane mainAnchorPane;

    private Civilization winner;

    @FXML
    public void initialize(){

        try{
            winner = GameDatabase.checkIfWin();
            setUsersScores(GameDatabase.players);
        } catch (Exception e) {
            //e.printStackTrace();
            System.err.println("Connection lost");
        }

        setBackground();
        setOKButton();
        setWinnerInformation();
        GameModel.isGame = false;
    }

    private void setUsersScores(ArrayList<Civilization> players) throws IOException {
        JSONObject input = new JSONObject();
        input.put("menu type","Win");
        input.put("action","score");
        input.put("users", findUserString(players));
        input.put("scores", findUserScores(players));
        Client.dataOutputStream1.writeUTF(input.toString());
        Client.dataOutputStream1.flush();
        //UserDatabase.setUserScores(GameDatabase.players);
    }

    private String findUserScores(ArrayList<Civilization> players) {
        String result = "";
        for (Civilization player : players) {
            result += Integer.toString(player.getFinalScore()) + " ";
        }
        return result;
    }

    private String findUserString(ArrayList<Civilization> players) {
        String result = "";
        for (Civilization player : players) {
            result += player + " ";
        }
        return result;
    }

    private void setWinnerInformation() {
        VBox vBox = new VBox();
        vBox.setPrefWidth(300);
        vBox.setLayoutX(70);
        vBox.setLayoutY(70);

        Text text = new Text("Winner is: \n" + winner.toString());
        text.setStyle("-fx-fill: #083b4b; -fx-font-size: 35");

        vBox.getChildren().add(text);
        vBox.setSpacing(25);

        setOtherData(vBox);

        mainAnchorPane.getChildren().add(vBox);
    }

    private void setOtherData(VBox vBox) {
        String string = "Others: ";
        for (Civilization civilization : GameDatabase.players) {
            if(civilization.getNickname().equals(winner.getNickname())) {
                continue;
            }
            string += "\n" + civilization.toString();
        }
        TextArea textArea = new TextArea(string);
        vBox.getChildren().add(textArea);
    }

    private void setOKButton() {
        Button button = new Button("OK");
        button.setStyle("-fx-background-color: #222c41;-fx-border-color: #555564; -fx-text-fill: white;-fx-border-width: 3;");
        button.setPrefWidth(150);
        button.setLayoutY(625);
        button.setLayoutX(565);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GraphicalBases.PlayMenuMusic();
                GraphicalBases.userLoggedIn();
            }
        });
        mainAnchorPane.getChildren().add(button);
    }

    private void setBackground() {
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(1280);
        rectangle.setHeight(720);
        rectangle.setY(0);
        rectangle.setX(0);
        rectangle.setFill(new ImagePattern(GraphicalBases.WIN));
        mainAnchorPane.getChildren().add(rectangle);
    }
}
