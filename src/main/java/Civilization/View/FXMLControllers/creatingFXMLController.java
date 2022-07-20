package Civilization.View.FXMLControllers;

import Civilization.Database.GameDatabase;
import Civilization.Database.GlobalVariables;
import Civilization.Model.Building;
import Civilization.Model.GameModel;
import Civilization.View.GraphicalBases;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class creatingFXMLController {

    @FXML
    private AnchorPane mainAnchorPane;

    private ScrollPane cityScrollPane;

    private Building selectedBuilding;
    private boolean buildBool;

    @FXML
    public void initialize() {
        setBackground();
        setValidBuildings();
        GameModel.isGame = true;
    }

    private void setValidBuildings() {

        Text totalText = new Text("Build/Buy buildings. number of valid buildings = " + Integer.toString(GameDatabase.getCivilizationByTurn(GameDatabase.getTurn()).getValidBuildings().size()));
        totalText.setX(50);
        totalText.setY(230);
        totalText.setStyle("-fx-font-size: 30");
        mainAnchorPane.getChildren().add(totalText);
        HBox hBox = new HBox();
        hBox.setSpacing(10);

        int i = 0;

        for (String building : GlobalVariables.BUILDINGS) {
            boolean buyingValid = true;
            if((new Building(building)).getCost() > GameDatabase.getCivilizationByTurn(GameDatabase.getTurn()).getGold()) {
                buyingValid = false;
            }
            VBox vBox = new VBox();
            vBox.setSpacing(10);
            Circle circle = new Circle(80);
            circle.setFill(new ImagePattern(GraphicalBases.BUILDINGS.get(building)));
            HBox subHBox = new HBox();
            subHBox.setSpacing(10);
            Rectangle buy = new Rectangle();
            buy.setWidth(40);
            buy.setHeight(40);
            buy.setFill(new ImagePattern(GraphicalBases.COIN));
            buy.setVisible(false);
            Rectangle build = new Rectangle();
            build.setWidth(40);
            build.setHeight(40);
            build.setFill(new ImagePattern(GraphicalBases.BUILD));
            build.setVisible(false);

            Text text = new Text(new Building(building).getInformation());

            if(GameDatabase.getCivilizationByTurn(GameDatabase.getTurn()).getCities().size() == 0
                || GameDatabase.getCivilizationByTurn(GameDatabase.getTurn()).getSelectedCity() == null
                || !(new Building(building).isBuildingValidForCivilization(GameDatabase.getCivilizationByTurn(GameDatabase.getTurn()), GameDatabase.getCivilizationByTurn(GameDatabase.getTurn()).getSelectedCity()))
                || GameDatabase.getCivilizationByTurn(GameDatabase.getTurn()).getSelectedCity().isBuildingInCity(building)) {
                circle.setDisable(true);
                circle.setEffect(new DropShadow());
                circle.setStroke(Color.RED);
                if(GameDatabase.getCivilizationByTurn(GameDatabase.getTurn()).getSelectedCity() != null
                    && GameDatabase.getCivilizationByTurn(GameDatabase.getTurn()).getSelectedCity().isBuildingInCity(building)) {
                    vBox.setVisible(true);
                } else {
                    vBox.setVisible(false);
                }
            }

            int finalI = i;
            boolean finalBuyingValid = buyingValid;
            circle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    selectedBuilding = new Building(building);
                    setDisableAll(hBox, finalI);
                    build.setVisible(true);
                    buy.setVisible(finalBuyingValid);
                }
            });

            build.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    System.out.println("build");
                    buildBool = true;
                    GameDatabase.getCivilizationByTurn(GameDatabase.getTurn()).getSelectedCity().buildBuilding(selectedBuilding, buildBool);
                }
            });
            buy.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    System.out.println("buy");
                    buildBool = false;
                    GameDatabase.getCivilizationByTurn(GameDatabase.getTurn()).getSelectedCity().buildBuilding(selectedBuilding, buildBool);
                }
            });

            subHBox.getChildren().add(build);
            subHBox.getChildren().add(buy);
            vBox.getChildren().add(circle);
            vBox.getChildren().add(text);
            vBox.getChildren().add(subHBox);
            hBox.getChildren().add(vBox);

            i++;
        }

        cityScrollPane = new ScrollPane(hBox);
        cityScrollPane.setLayoutX(75);
        cityScrollPane.setLayoutY(275);
        cityScrollPane.setPrefWidth(600);
        cityScrollPane.setPrefHeight(300);
        mainAnchorPane.getChildren().add(cityScrollPane);
    }

    private void setDisableAll(HBox hBox, int finalI) {
        int i = 0;
        for (Node child : hBox.getChildren()) {
            if(i == finalI) {
                i++;
                continue;
            }
            child.setDisable(true);
            i++;
        }
    }

    private void setBackground() {
        Rectangle rectangle = new Rectangle();
        rectangle.setX(0);
        rectangle.setY(0);
        rectangle.setHeight(720);
        rectangle.setWidth(1280);
        rectangle.setFill(new ImagePattern(GraphicalBases.WHITE));
        mainAnchorPane.getChildren().add(rectangle);

        Rectangle india = new Rectangle();
        india.setX(700);
        india.setY(0);
        india.setHeight(720);
        india.setWidth(1280 - india.getX());
        india.setFill(new ImagePattern(GraphicalBases.INDIA));
        mainAnchorPane.getChildren().add(india);

        setBackButton(india);
    }

    private void setBackButton(Rectangle india) {
        Button button = new Button("BACK");
        button.setPrefWidth(200);
        button.setLayoutX(india.getX() + (india.getWidth() - 200)/2);
        button.setLayoutY(650);
        button.setStyle("-fx-background-color: #222c41;-fx-border-color: #555564; -fx-text-fill: white;-fx-border-width: 3;");
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GraphicalBases.enterGame("CityPanel");
            }
        });
        mainAnchorPane.getChildren().add(button);
    }
}
