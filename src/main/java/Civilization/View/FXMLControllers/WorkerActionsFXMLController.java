package Civilization.View.FXMLControllers;

import Civilization.Database.GameDatabase;
import Civilization.Model.GameModel;
import Civilization.Model.Tile;
import Civilization.Model.Worker;
import Civilization.View.GraphicalBases;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class WorkerActionsFXMLController {

    @FXML
    private AnchorPane mainAnchorPane;

    private Worker worker;
    private Tile tile;

    @FXML
    public void initialize() {
        setFirstVariables();
        setBackground();
        setBackButton();
        GameModel.isGame = true;
    }

    private void setBackButton() {
        Button button = new Button("Back");
        button.setStyle("-fx-background-color: #222c41;-fx-border-color: #555564; -fx-text-fill: white;-fx-border-width: 3;");
        button.setPrefWidth(150);
        button.setLayoutY(625);
        button.setLayoutX(565);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GraphicalBases.enterGame("Game");
            }
        });
        mainAnchorPane.getChildren().add(button);
    }

    private void setFirstVariables() {
        tile = GameDatabase.getTileByXAndY(GameDatabase.getCivilizationByTurn(GameDatabase.getTurn()).getSelectedUnit().getX(), GameDatabase.getCivilizationByTurn(GameDatabase.getTurn()).getSelectedUnit().getY());
        worker = tile.getWorker();
    }

    private void setBackground() {
        Rectangle back = new Rectangle();
        back.setWidth(1280);
        back.setHeight(720);
        back.setX(0);
        back.setY(0);
        back.setFill(new ImagePattern(GraphicalBases.WHITE));
        mainAnchorPane.getChildren().add(back);

        Rectangle worker = new Rectangle();
        worker.setWidth(500);
        worker.setHeight(500);
        worker.setX(390);
        worker.setY(220);
        worker.setFill(new ImagePattern(GraphicalBases.WORKER));
        mainAnchorPane.getChildren().add(worker);
    }
}
