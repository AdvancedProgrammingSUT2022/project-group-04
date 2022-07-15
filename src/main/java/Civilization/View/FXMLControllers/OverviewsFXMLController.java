package Civilization.View.FXMLControllers;

import Civilization.Database.GameDatabase;
import Civilization.View.GraphicalBases;
import Civilization.View.Info;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class OverviewsFXMLController {

    @FXML
    private AnchorPane mainAnchorPane;

    private HBox mainHBox;

    @FXML
    public void initialize() {
        mainHBox = new HBox();
        mainHBox.setSpacing(30);
        mainHBox.setLayoutX(80);
        mainHBox.setLayoutY(80);
        mainHBox.setPrefHeight(560);
        setBackground();
        setBackButton();
        setEconomyOverview();
        setMilitaryOverview();
        setDemographyOverview();
        mainAnchorPane.getChildren().add(mainHBox);

    }

    private void setDemographyOverview() {
        TextArea textArea = new TextArea();
        textArea.setText("Demography overview of " + Info.getInstance().infoDemography(GameDatabase.getTurn()).toString());
        textArea.setEditable(false);
        textArea.setPrefWidth(353);
        mainHBox.getChildren().add(textArea);
    }

    private void setMilitaryOverview() {
        TextArea textArea = new TextArea();
        textArea.setText("Military overview of " + Info.getInstance().infoMilitary(GameDatabase.getTurn()));
        textArea.setEditable(false);
        textArea.setPrefWidth(353);
        mainHBox.getChildren().add(textArea);
    }

    private void setEconomyOverview() {
        TextArea textArea = new TextArea();
        textArea.setText("Economy overview of " + Info.getInstance().infoEconomy(GameDatabase.getTurn()));
        textArea.setEditable(false);
        textArea.setPrefWidth(353);
        mainHBox.getChildren().add(textArea);
    }

    private void setBackButton() {
        Button button = new Button("BACK");
        button.setPrefWidth(200);
        button.setLayoutX(540);
        button.setLayoutY(650);
        button.setStyle("-fx-background-color: #222c41;-fx-border-color: #555564; -fx-text-fill: white;-fx-border-width: 3;");
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GraphicalBases.enterGame("Game");
            }
        });
        mainAnchorPane.getChildren().add(button);
    }

    private void setBackground() {
        Rectangle rectangle = new Rectangle();
        rectangle.setX(0);
        rectangle.setY(0);
        rectangle.setHeight(720);
        rectangle.setWidth(1280);
        rectangle.setFill(new ImagePattern(GraphicalBases.OVERVIEWS));
        mainAnchorPane.getChildren().add(rectangle);
    }
}
