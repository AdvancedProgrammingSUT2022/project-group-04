package Civilization.View.FXMLControllers;

import Civilization.Controller.GameMenuController;
import Civilization.Database.GameDatabase;
import Civilization.Model.City;
import Civilization.Model.GameModel;
import Civilization.Model.Unit;
import Civilization.View.GraphicalBases;
import Civilization.View.Info;
import Civilization.View.Transitions.CityPanelChoosingTransition;
import Civilization.View.Transitions.UnitPanelChoosingTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PanelListFXMLController {

    @FXML
    private AnchorPane mainAnchorPane;

    private ChoiceBox<String> cities;
    private TextArea cityInformation;

    private ChoiceBox<String> units;
    private TextArea unitInformation;

    @FXML
    public void initialize() {
        setBackground();
        setBackButton();

        setNoCitiesText();
        setCityInformation();
        setCitiesList();

        setNoUnitText();
        setUnitInformation();
        setUnitsList();
    }

    private void setUnitsList() {
        cities = new ChoiceBox<>();

        UnitPanelChoosingTransition unitPanelChoosingTransition = new UnitPanelChoosingTransition(this);
        unitPanelChoosingTransition.play();

        units = new ChoiceBox<>();
        ArrayList<String> soldierNames = new ArrayList<>();
        for (Unit soldier : Info.getInstance().getSoldiers(new GameMenuController(new GameModel()), GameDatabase.getTurn())) {
            soldierNames.add(soldier.getUnitType() + " in X: " + Integer.toString(soldier.getX()) + " and Y: " + Integer.toString(soldier.getY()));
        }
        ObservableList<String> usersInput = FXCollections.observableArrayList(soldierNames);
        units.setItems(usersInput);
        if(Info.getInstance().getSoldiers(new GameMenuController(new GameModel()), GameDatabase.getTurn()).size() == 0) {
            units.setVisible(false);
        }

        units.setLayoutX(800);
        units.setLayoutY(100);
        mainAnchorPane.getChildren().add(units);
    }

    private void setUnitInformation() {
        unitInformation = new TextArea();
        unitInformation.setPrefWidth(100);
        unitInformation.setPrefWidth(100);
        unitInformation.setLayoutX(1020);
        unitInformation.setLayoutY(100);
        unitInformation.setEditable(false);
        mainAnchorPane.getChildren().add(unitInformation);
    }

    private void setNoUnitText() {
        Text text = new Text("No Units yet");
        text.setStyle("-fx-fill: white; -fx-font-size: 70");
        text.setX(770);
        text.setY(300);
        if(Info.getInstance().getSoldiers(new GameMenuController(new GameModel()), GameDatabase.getTurn()).size() != 0) {
            text.setVisible(false);
        }
        mainAnchorPane.getChildren().add(text);
    }

    private void setCityInformation() {
        cityInformation = new TextArea();
        cityInformation.setPrefWidth(100);
        cityInformation.setPrefWidth(100);
        cityInformation.setLayoutX(220);
        cityInformation.setLayoutY(100);
        cityInformation.setEditable(false);
        mainAnchorPane.getChildren().add(cityInformation);
    }

    private void setNoCitiesText() {
        Text text = new Text("No Cities yet");
        text.setStyle("-fx-fill: white; -fx-font-size: 70");
        text.setX(70);
        text.setY(300);
        if(GameDatabase.getCivilizationByTurn(GameDatabase.getTurn()).getCities().size() != 0) {
            text.setVisible(false);
        }
        mainAnchorPane.getChildren().add(text);
    }

    private void setCitiesList() {
        cities = new ChoiceBox<>();

        CityPanelChoosingTransition cityPanelChoosingTransition = new CityPanelChoosingTransition(this);
        cityPanelChoosingTransition.play();

        cities = new ChoiceBox<>();
        ArrayList<String> cityNames = new ArrayList<>();
        for (City city : GameDatabase.getCivilizationByTurn(GameDatabase.getTurn()).getCities()) {
            cityNames.add(city.getName());
        }
        ObservableList<String> usersInput = FXCollections.observableArrayList(cityNames);
        cities.setItems(usersInput);
        if(GameDatabase.getCivilizationByTurn(GameDatabase.getTurn()).getCities().size() == 0) {
            cities.setVisible(false);
        }

        cities.setLayoutX(100);
        cities.setLayoutY(100);
        mainAnchorPane.getChildren().add(cities);
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
        Rectangle black = new Rectangle();
        black.setX(0);
        black.setY(0);;
        black.setWidth(1280);
        black.setHeight(720);
        black.setFill(new ImagePattern(GraphicalBases.BLACK));
        mainAnchorPane.getChildren().add(black);

        Rectangle sasani = new Rectangle();
        sasani.setX(310);
        sasani.setY(100);
        sasani.setWidth(1280 - 2*sasani.getX());
        sasani.setHeight(500);
        sasani.setFill(new ImagePattern(GraphicalBases.SASANI));
        mainAnchorPane.getChildren().add(sasani);
    }

    public void handleCityChoiceBox() {
        if(cities.getValue() != null) {
            cityInformation.setText(GameDatabase.getCityByName(cities.getValue()).toString());
            cityInformation.setVisible(true);
            selectCity(cities.getValue());
        } else {
            cityInformation.setVisible(false);
        }
    }

    private void selectCity(String value) {
        GameDatabase.getCivilizationByTurn(GameDatabase.getTurn()).setSelectedCity(GameDatabase.getCityByName(value));
    }

    public void handleUnitChoiceBox() {
        if(units.getValue() != null) {
            unitInformation.setText(GameDatabase.getCityByName(units.getValue()).toString());
            unitInformation.setVisible(true);
            selectUnit(units.getValue());
        } else {
            unitInformation.setVisible(false);
        }
    }

    private void selectUnit(String value) {
        Pattern pattern = Pattern.compile("(?<name>\\S+) in X: (?<x>\\d+) and Y: (?<y>\\d+)");
        Matcher matcher = pattern.matcher(value);
        GameMenuController gameMenuController = new GameMenuController(new GameModel());
        for (Unit unit : GameDatabase.getTileByXAndY(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y"))).getUnits()) {
            if(unit.getUnitType().equals(matcher.group("name"))
                && gameMenuController.isUnitForThisCivilization(GameDatabase.getTurn(), unit)) {
                GameDatabase.getCivilizationByTurn(GameDatabase.getTurn()).setSelectedUnit(unit);
            }
        }
    }
}
