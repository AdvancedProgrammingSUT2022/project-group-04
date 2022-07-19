package Civilization.View.FXMLControllers;

import Civilization.Database.GameDatabase;
import Civilization.Model.City;
import Civilization.Model.GameModel;
import Civilization.Model.Tile;
import Civilization.View.GraphicalBases;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class CityPanelFXMLController {

    @FXML
    private AnchorPane mainAnchorPane;

    private City city;
    private Tile tile;

    @FXML
    public void initialize() {
        setFirstVariables();
        setBackground();
        setBackButton();
        setCityInformation();
        setBuildBuildingButton();
        GameModel.isGame = true;
    }

    private void setBuildBuildingButton() {
        Button button = new Button("Building menu");
        button.setStyle("-fx-background-color: #222c41;-fx-border-color: #555564; -fx-text-fill: white;-fx-border-width: 3;");
        button.setPrefWidth(150);
        button.setLayoutY(85);
        button.setLayoutX(50);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GraphicalBases.enterGame("creating");
            }
        });
        mainAnchorPane.getChildren().add(button);
    }

    private void setCityInformation() {
        Text text = new Text(city.getName());
        text.setStyle("-fx-font-size: 30");
        text.setX(50);
        text.setY(50);
        mainAnchorPane.getChildren().add(text);
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
                GraphicalBases.enterGame("PanelList");
            }
        });
        mainAnchorPane.getChildren().add(button);
    }

    private void setFirstVariables() {
        tile = GameDatabase.getTileByXAndY(GameDatabase.getCivilizationByTurn(GameDatabase.getTurn()).getSelectedCity().getX(), GameDatabase.getCivilizationByTurn(GameDatabase.getTurn()).getSelectedCity().getY());
        city = GameDatabase.getCivilizationByTurn(GameDatabase.getTurn()).getSelectedCity();
    }

    private void setBackground() {
        Rectangle back = new Rectangle();
        back.setWidth(1280);
        back.setHeight(720);
        back.setX(0);
        back.setY(0);
        back.setFill(new ImagePattern(GraphicalBases.CITY_PANEL));
        mainAnchorPane.getChildren().add(back);

    }
}
