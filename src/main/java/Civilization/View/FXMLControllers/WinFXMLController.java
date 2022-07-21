package Civilization.View.FXMLControllers;

import Civilization.Database.GameDatabase;
import Civilization.Database.UserDatabase;
import Civilization.Model.Civilization;
import Civilization.Model.GameModel;
import Civilization.Model.User;
import Civilization.View.GraphicalBases;
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

public class WinFXMLController {

    @FXML
    private AnchorPane mainAnchorPane;

    private Civilization winner;

    @FXML
    public void initialize() {
        winner = GameDatabase.checkIfWin();
        UserDatabase.setUserScores(GameDatabase.players);
        setBackground();
        setOKButton();
        setWinnerInformation();
        GameModel.isGame = false;
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
