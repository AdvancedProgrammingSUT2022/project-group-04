package Civilization.View.FXMLControllers;

import Civilization.Database.GameDatabase;
import Civilization.Model.Citizen;
import Civilization.Model.City;
import Civilization.Model.GameModel;
import Civilization.Model.Tile;
import Civilization.View.GraphicalBases;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CityPanelFXMLController {

    @FXML
    private AnchorPane mainAnchorPane;

    private City city;
    private Tile tile;

    private Text unemployedCitizen;

    private ChoiceBox<String> validCitizensForLocking;
    private ChoiceBox<String> tileForLocking;
    private ChoiceBox<String> workingCitizens;

    @FXML
    public void initialize() {
        setFirstVariables();
        setBackground();
        setBackButton();
        setCityInformation();
        setBuildBuildingButton();
        setValidCitizensForLocking();
        setWorkingCivilization();
        setUnemployedCitizenSection();
        GameModel.isGame = true;
    }

    private void setWorkingCivilization() {
        workingCitizens = new ChoiceBox<>();
        updateWorkingChoiceBox();
        workingCitizens.setLayoutX(50);
        workingCitizens.setLayoutY(200);
        mainAnchorPane.getChildren().add(workingCitizens);

        Button ok = new Button("Ok");
        ok.setLayoutX(150);
        ok.setLayoutY(200);
        ok.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(workingCitizens.getValue() == null) {
                    return;
                }
                Citizen citizen = findCitizen(workingCitizens.getValue());
                if(citizen == null) {
                    return;
                }
                citizen.removeFromWork();
                updateLockingChoiceBox();
                updateTilesChoiceBox();
                updateUnemployedCitizensSection();
                updateWorkingChoiceBox();
            }
        });
        mainAnchorPane.getChildren().add(ok);
    }

    private void updateWorkingChoiceBox() {
        ArrayList<String> citizens = new ArrayList<>();
        for (Citizen citizen : city.getCitizens()) {
            if(citizen.isAssigned()) {
                citizens.add(citizen.toString());
            }
        }
        ObservableList<String> validCitizens = FXCollections.observableArrayList(citizens);
        workingCitizens.setItems(validCitizens);
    }

    private void setValidCitizensForLocking() {
        validCitizensForLocking = new ChoiceBox<>();
        updateLockingChoiceBox();
        validCitizensForLocking.setLayoutX(50);
        validCitizensForLocking.setLayoutY(150);
        mainAnchorPane.getChildren().add(validCitizensForLocking);

        final String[] result = {""};

        tileForLocking = new ChoiceBox<>();
        updateTilesChoiceBox();
        tileForLocking.setLayoutX(270);
        tileForLocking.setLayoutY(150);
        tileForLocking.setVisible(false);
        mainAnchorPane.getChildren().add(tileForLocking);

        Button ok = new Button("Ok");
        ok.setLayoutX(310);
        ok.setLayoutY(150);
        ok.setVisible(false);
        ok.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(result[0].equals("")) {
                    return;
                }
                Citizen citizen = findCitizen(result[0]);
                if(citizen == null) {
                    return;
                }
                citizen.assign();
                updateLockingChoiceBox();
                updateTilesChoiceBox();
                updateUnemployedCitizensSection();
                updateWorkingChoiceBox();
            }
        });
        mainAnchorPane.getChildren().add(ok);

        Button button = new Button(" Lock to tile ");
        button.setPrefWidth(100);
        button.setLayoutX(150);
        button.setLayoutY(150);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(validCitizensForLocking.getValue() == null) {
                    tileForLocking.setVisible(false);
                    validCitizensForLocking.setDisable(false);
                    ok.setVisible(false);
                    result[0] = "";
                } else {
                    tileForLocking.setVisible(true);
                    validCitizensForLocking.setDisable(true);
                    result[0] = validCitizensForLocking.getValue();
                    ok.setVisible(true);
                }
            }
        });
        mainAnchorPane.getChildren().add(button);


    }

    private void updateTilesChoiceBox() {
        ArrayList<String> tiles = new ArrayList<>();
        for (Tile cityTile : city.getTiles()) {
            tiles.add("Tile in X: " + cityTile.getX() + " Y: " + cityTile.getY());
        }
        ObservableList<String> validTiles = FXCollections.observableArrayList(tiles);
        tileForLocking.setItems(validTiles);
    }

    private void updateLockingChoiceBox() {
        ArrayList<String> citizens = new ArrayList<>();
        for (Citizen citizen : city.getCitizens()) {
            if(!citizen.isAssigned()) {
                citizens.add(citizen.toString());
            }
        }
        ObservableList<String> validCitizens = FXCollections.observableArrayList(citizens);
        validCitizensForLocking.setItems(validCitizens);
    }

    private void setUnemployedCitizenSection() {
        unemployedCitizen = new Text();
        unemployedCitizen.setX(50);
        unemployedCitizen.setY(250);
        unemployedCitizen.setStyle("-fx-font-size: 30");
        updateUnemployedCitizensSection();
        mainAnchorPane.getChildren().add(unemployedCitizen);
    }

    private void updateUnemployedCitizensSection() {
        String text = "Unemployed Citizens Section: ";
        for (Citizen citizen : city.getCitizens()) {
            if(!citizen.isAssigned()) {
                text += "\n\t" + citizen.toString();
            }
        }
        unemployedCitizen.setText(text);
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

    private Citizen findCitizen(String input) {
        Pattern pattern = Pattern.compile("Citizen on X: (?<x>\\d+) Y: (?<y>\\d+)");
        Matcher matcher = pattern.matcher(input);
        if(!matcher.matches()) {
            return null;
        }
        return GameDatabase.getTileByXAndY(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("x"))).getCitizen();
    }
}
