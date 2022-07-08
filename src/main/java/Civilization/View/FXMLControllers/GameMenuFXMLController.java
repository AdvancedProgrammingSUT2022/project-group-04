package Civilization.View.FXMLControllers;

import Civilization.View.GraphicalBases;
import Civilization.View.Transitions.CursorTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class GameMenuFXMLController {

    @FXML
    private AnchorPane mainAnchorPane;

    private Button OKButton;

    @FXML
    public void initialize() {
        setBackground();
        setOKButton();
        setBackButton();
    }

    private void setBackButton() {
        Button backButton = new Button("BACK");
        backButton.setLayoutX(1000);
        backButton.setLayoutY(620);
        backButton.setPrefWidth(100);
        backButton.setPrefHeight(30);
        backButton.setStyle("-fx-background-color: #222c41;-fx-border-color: #555564; -fx-text-fill: white;-fx-border-width: 3;");
        backButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GraphicalBases.changeMenu("MainMenu");
            }
        });
        mainAnchorPane.getChildren().add(backButton);
    }

    private boolean isOKValid() {
        return true;
    }

    private void setOKButton() {
        OKButton = new Button("OK");
        OKButton.setLayoutX(1110);
        OKButton.setLayoutY(620);
        OKButton.setPrefWidth(100);
        OKButton.setPrefHeight(30);
        OKButton.setDisable(!isOKValid());
        OKButton.setStyle("-fx-background-color: #222c41;-fx-border-color: #555564; -fx-text-fill: white;-fx-border-width: 3;");
        OKButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GraphicalBases.enterGame("Game");
            }
        });
        mainAnchorPane.getChildren().add(OKButton);
        CursorTransition cursorTransition = new CursorTransition(OKButton, 1220, 660);
        cursorTransition.play();
    }


    private void setBackground() {
        Rectangle black = new Rectangle();
        black.setX(0);
        black.setY(0);
        black.setWidth(1280);
        black.setHeight(720);
        black.setFill(new ImagePattern(GraphicalBases.BLACK));
        mainAnchorPane.getChildren().add(black);

        Rectangle kingDavid = new Rectangle();
        kingDavid.setX(0);
        kingDavid.setY(70);
        kingDavid.setWidth(900);
        kingDavid.setHeight(720 - kingDavid.getY());
        kingDavid.setFill(new ImagePattern(GraphicalBases.KING_DAVID));
        mainAnchorPane.getChildren().add(kingDavid);
    }
}
