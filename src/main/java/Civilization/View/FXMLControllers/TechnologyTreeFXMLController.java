package Civilization.View.FXMLControllers;

import Civilization.Database.GameDatabase;
import Civilization.Database.GlobalVariables;
import Civilization.Model.GameModel;
import Civilization.Model.Technology;
import Civilization.View.GraphicalBases;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.Objects;

public class TechnologyTreeFXMLController {

    @FXML
    private AnchorPane mainAnchorPane;

    private ScrollPane mainScrollPane;
    private TextArea information;
    private boolean hasCity;

    @FXML
    public void initialize() {

        hasCity = GameDatabase.getCivilizationByTurn(GameDatabase.getTurn()).getCities().size() != 0;

        setBackground();
        setBackButton();
        setTextArea();
        setTree();
        GameModel.isGame = true;
    }

    private void setTextArea() {
        information = new TextArea();
        information.setLayoutX(1100);
        information.setLayoutY(50);
        information.setPrefWidth(130);
        information.setPrefHeight(580);
        mainAnchorPane.getChildren().add(information);
    }

    private void setTree() {
        HBox hBox = drawTree();
        Rectangle rectangle = new Rectangle();
        rectangle.setFill(new ImagePattern(GraphicalBases.TREE));
        rectangle.setWidth(6195);
        rectangle.setHeight(564);
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(rectangle);
        anchorPane.getChildren().add(hBox);
        mainScrollPane = new ScrollPane(anchorPane);
        mainScrollPane.setLayoutX(50);
        mainScrollPane.setLayoutY(50);
        mainScrollPane.setPrefWidth(1000);
        mainScrollPane.setPrefHeight(580);
        mainAnchorPane.getChildren().add(mainScrollPane);
    }

    private HBox drawTree() {
        int x = 60;
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        for (int i = 0; i < GlobalVariables.TECHNOLOGIES.length; i++) {
            x += 120 + 10;
            boolean disable = false;
            VBox vBox = new VBox();
            vBox.setSpacing(10);
            Circle circle = new Circle(60);
            circle.setFill(new ImagePattern(GraphicalBases.TECHNOLOGIES.get(GlobalVariables.TECHNOLOGIES[i])));
            Technology technology = new Technology(GlobalVariables.TECHNOLOGIES[i]);
            int finalI = i;
            if(!hasCity || !technology.isTechnologyValidForCivilization(GameDatabase.getCivilizationByTurn(GameDatabase.getTurn()))
                || GameDatabase.getCivilizationByTurn(GameDatabase.getTurn()).isTechnologyForThisCivilization(technology)) {
                disable = true;
            }
            circle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    GameDatabase.getCivilizationByTurn(GameDatabase.getTurn()).addTechnology(technology);
                }
            });
            circle.setDisable(disable);
            if(disable) {
                circle.setEffect(new DropShadow());
                circle.setStroke(Color.RED);
                if(GameDatabase.getCivilizationByTurn(GameDatabase.getTurn()).isTechnologyForThisCivilization(technology)) {
                    circle.setStroke(Color.BLUE);
                }
            }
            Text text = new Text(technology.getName());

            text.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    information.setText(technology.getInformation());
                }
            });

            text.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    information.setText("");
                }
            });

            text.setStyle("-fx-fill: Red; -fx-font-size: 15");
            vBox.getChildren().add(text);
            vBox.getChildren().add(circle);
            for (Technology leadingTechnology : technology.getLeadingTechnologies()) {
                Circle leadingCircle = new Circle(30);
                leadingCircle.setStroke(circle.getStroke());
                leadingCircle.setEffect(circle.getEffect());
                leadingCircle.setFill(new ImagePattern(GraphicalBases.TECHNOLOGIES.get(leadingTechnology.getName())));
                vBox.getChildren().add(leadingCircle);
            }
            hBox.getChildren().add(vBox);
        }
        return hBox;
    }

    private void setBackground() {
        Rectangle black = new Rectangle();
        black.setX(0);
        black.setY(0);;
        black.setWidth(1280);
        black.setHeight(720);
        black.setFill(new ImagePattern(GraphicalBases.BLACK));
        mainAnchorPane.getChildren().add(black);
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
}
