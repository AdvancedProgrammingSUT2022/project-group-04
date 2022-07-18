package Civilization.View.FXMLControllers;

import Civilization.Controller.GameMenuController;
import Civilization.Database.GameDatabase;
import Civilization.Database.GlobalVariables;
import Civilization.Model.*;
import Civilization.View.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;

public class GameFXMLController {

    @FXML
    private AnchorPane mainAnchorPane;

    // Status bar
    private Rectangle statusBar;
    private HBox statusBarHBox;
    private Rectangle showHappiness;
    private Text coinText;
    private Text scienceText;
    private Text happinessText;
    private Text showHappinessText;
    private Text civilizationName;

    // Info Panel
    private Rectangle infoPanel;
    private VBox infoPanelVBox;
    private Rectangle technologyUnderSearch;
    private Rectangle unitSelected;

    // Terminal
    private TextArea terminal;
    private String terminalDefaultStart;
    private String terminalDefaultEnd;
    private String terminalDefault;
    private boolean isTerminalOn;

    // Game
    AnchorPane mapPane = new AnchorPane();
    ArrayList<TileFX> tileFXES = new ArrayList<>();
    public static int turn = 0;
    private Button nextTurn;
    boolean isClickedOnce =false;
    boolean isClickedTwice = false;
    ObservableList<String> Units = FXCollections.observableArrayList(GlobalVariables.UNITS);
    TileFX selectedTile = null;
    public ArrayList<Unit> movingUnits = new ArrayList<>();

    // Map
    private ScrollPane map;

    int NUMBER_OF_TILES_IN_COLUMN = 25;
    @FXML
    public void initialize() {
        turn = GameDatabase.getTurn();
        setMap();
        setStatusBar();
        setNextTurnButton();
        setBackButton();
        setStopButton();
        setInfoPanel();
        setCheatCodesTerminal();
        setTerminal();
        GameModel.isGame = true;

    }

    class TileFX extends Polygon {
        int x, y;
        public TileFX(int x, int y){
            this.x = x;
            this.y = y;
            tileFXES.add(this);
        }
        Polygon polygon;
        ArrayList<Polygon> sides = new ArrayList<>(6);
        // Tile info ::::::::::::::::;
        Text informationText = new Text();
        ChoiceBox<String> soldiers = new ChoiceBox<>(Units);
        Button createUnit = new Button("create unit");
        HBox hBox = new HBox(new Text("Choose unit :"),soldiers, createUnit);
        VBox vBox = new VBox(informationText, hBox);
        Pane informationOfTile = new Pane(vBox);
        Text nameOfOwner = new Text();



        //Soldiers :::::::::::::::::::;
        Circle combatUnit;
        Circle nonCombatUnit;

       //Features and resources :::::::::::::::::::;

        Polygon feature = new Polygon();
        Polygon resource = new Polygon();

    }


    private void setMap(){
        map = new ScrollPane();
        map.setLayoutX(150);
        map.setLayoutY(40);
        map.setPrefHeight(720 - 40);
        map.setPrefWidth(1280-150);
        map.setPannable(true);
        mapPane.setPrefHeight(2000);
        mapPane.setPrefWidth(2000);

        updateFirstMap();

        map.setContent(mapPane);
        mainAnchorPane.getChildren().add(map);
    }


    public Tile GetTileInReal(TileFX tileFX){
        for (Tile tileMap:GameDatabase.map){
            if (tileMap.getX() == tileFX.x && tileMap.getY() == tileFX.y){
                return tileMap;
            }
        }
        return null;
    }


    public TileFX GetGraphicalTile(Tile tile){
        for(TileFX tileFX : tileFXES){
            if (tileFX.x == tile.getX() && tileFX.y == tile.getY()){
                return tileFX;
            }
        }
        return null;
    }

    private void updateFirstMap() {
        for (int i = 0; i < GameDatabase.length; i++){
            for (int j = 0; j < GameDatabase.width; j++) {
                double a = j * 200;
                double b = i * 200;
                TileFX tile;
                if ( i % 2 == 0) {
                    tile = new TileFX(i, j);
                    tile.polygon = new Polygon(b + 100.0, a + 100, b + 250.0, a + 100, b + 300.0, a + 200.0, b + 250.0, a + 300.0, b + 100.0, a + 300.0, b + 50.0, a + 200.0);
                    Polygon side1 = new Polygon(b + 100, a + 100, b + 250 , a + 100, b + 250 , a + 105, b + 100, a + 105);
                    Polygon side2 = new Polygon(b + 250, a + 100, b + 300, a + 200, b + 300, a + 205, b + 250, a + 105);
                    Polygon side3 = new Polygon(b + 300, a + 200, b + 250, a + 300,b + 250, a + 295, b + 300, a + 195);
                    Polygon side4 = new Polygon(b + 250, a + 300, b + 100, a + 300, b + 100, a + 295, b + 250, a + 295);
                    Polygon side5 = new Polygon(b + 100, a + 300, b + 50, a + 200, b + 50 , a + 195, b + 100, a + 295);
                    Polygon side6 = new Polygon(b + 50, a + 200, b + 100, a + 100, b + 100, a + 105, b + 50 , a + 205);
                    tile.sides.add(side1);
                    tile.sides.add(side2);
                    tile.sides.add(side3);
                    tile.sides.add(side4);
                    tile.sides.add(side5);
                    tile.sides.add(side6);
                } else {
                    tile = new TileFX(i, j);
                    tile.polygon = new Polygon(b + 100.0, a, b + 250.0, a , b + 300.0, a + 100.0, b + 250.0, a + 200.0, b + 100.0, a + 200.0, b + 50.0, a + 100.0);
                    Polygon side1 = new Polygon(b + 100, a , b + 250 , a , b + 250 , a + 5, b + 100, a + 5);
                    Polygon side2 = new Polygon(b + 250, a , b + 300, a + 100, b + 300, a + 105, b + 250, a + 5);
                    Polygon side3 = new Polygon(b + 300, a + 100, b + 250, a + 200,b + 250, a + 195, b + 300, a + 95);
                    Polygon side4 = new Polygon(b + 250, a + 200, b + 100, a + 200, b + 100, a + 195, b + 250, a + 195);
                    Polygon side5 = new Polygon(b + 100, a + 200, b + 50, a + 100, b + 50 , a + 95, b + 100, a + 195);
                    Polygon side6 = new Polygon(b + 50, a + 100, b + 100, a , b + 100, a + 5, b + 50 , a + 105);
                    tile.sides.add(side1);
                    tile.sides.add(side2);
                    tile.sides.add(side3);
                    tile.sides.add(side4);
                    tile.sides.add(side5);
                    tile.sides.add(side6);
                }

                if(GameDatabase.getTileByXAndY(tile.x, tile.y).getNonCombatUnit() != null) {
                    showNonCombatByUnit(GameDatabase.getTileByXAndY(tile.x, tile.y).getNonCombatUnit(), tile);
                    //System.out.println(GameDatabase.getTileByXAndY(tile.x, tile.y).getNonCombatUnit());


                }
                if(GameDatabase.getTileByXAndY(tile.x, tile.y).getCombatUnit() != null) {
                    showCombatByUnit(GameDatabase.getTileByXAndY(tile.x, tile.y).getCombatUnit(), tile);
                    //System.out.println(GameDatabase.getTileByXAndY(tile.x, tile.y).getCombatUnit());


                }

                updateMapForOneTile(tile);
                mapPane.getChildren().add(tile.polygon);
                mapPane.getChildren().add(tile.nameOfOwner);
                mapPane.getChildren().add(tile.feature);
                mapPane.getChildren().add(tile.resource);
                for (Polygon side : tile.sides) {
                    mapPane.getChildren().add(side);
                }
                mainAnchorPane.getChildren().add(tile.informationOfTile);
                tile.informationOfTile.toFront();
                tile.nameOfOwner.toFront();
                tile.feature.toFront();
                tile.resource.toFront();
                if(tile.nonCombatUnit != null) {
                    tile.nonCombatUnit.toFront();
                }
                if(tile.combatUnit != null) {
                    tile.combatUnit.toFront();
                }

            }
        }
    }

    private void updateMapForOneTile(TileFX tile) {
        for (Tile tileMap : GameDatabase.map){
            if (tileMap.getX() == tile.x && tileMap.getY() == tile.y){
                if (tileMap.getBaseTerrainType().equals("Desert")){
                    tile.polygon.setFill(Color.TAN);
                } else if (tileMap.getBaseTerrainType().equals("Meadow")){
                    tile.polygon.setFill(Color.YELLOW);
                } else if (tileMap.getBaseTerrainType().equals("Hill")){
                    tile.polygon.setFill(Color.GREEN);
                } else if (tileMap.getBaseTerrainType().equals("Mountain")){
                    tile.polygon.setFill(Color.BROWN);
                } else if (tileMap.getBaseTerrainType().equals("Ocean")){
                    tile.polygon.setFill(Color.BLUE);
                } else if (tileMap.getBaseTerrainType().equals("Plain")){
                    tile.polygon.setFill(Color.LIGHTGREEN);
                } else if (tileMap.getBaseTerrainType().equals("Snow")){
                    tile.polygon.setFill(Color.GRAY);
                } else if (tileMap.getBaseTerrainType().equals("Tundra")){
                    tile.polygon.setFill(Color.RED);
                }

                if (tileMap.isRiverByNumberOfEdge(0)){
                    tile.sides.get(0).setFill(Color.BLUE);
                } else if (tileMap.isRiverByNumberOfEdge(1)){
                    tile.sides.get(1).setFill(Color.BLUE);
                } else if (tileMap.isRiverByNumberOfEdge(2)){
                    tile.sides.get(2).setFill(Color.BLUE);
                } else if (tileMap.isRiverByNumberOfEdge(3)){
                    tile.sides.get(3).setFill(Color.BLUE);
                } else if (tileMap.isRiverByNumberOfEdge(4)){
                    tile.sides.get(4).setFill(Color.BLUE);
                } else if (tileMap.isRiverByNumberOfEdge(5)){
                    tile.sides.get(5).setFill(Color.BLUE);
                }
                Civilization player = GameDatabase.getCivilizationByTile(tileMap);
                if (player != null) {
                    tile.nameOfOwner.setText(player.getNickname());
                    tile.nameOfOwner.setLayoutX(tile.polygon.getPoints().get(0) + 10);
                    tile.nameOfOwner.setLayoutY(tile.polygon.getPoints().get(1) + 20);
                    tile.nameOfOwner.prefWidth(100);
                    tile.nameOfOwner.setFill(Color.BLACK);
                }
            }

            if(GameDatabase.getTileByXAndY(tile.x, tile.y).getBaseTerrain().getFeature() != null) {
                tile.feature.setFill(new ImagePattern(GraphicalBases.FEATURES.get(GameDatabase.getTileByXAndY(tile.x, tile.y).getBaseTerrain().getFeature().getType())));
            } else {
                //tile.feature.setFill(Color.BLACK);
                tile.feature.setVisible(false);
            }

            if(GameDatabase.getTileByXAndY(tile.x, tile.y).getBaseTerrain().getResources() != null
                   &&  GameDatabase.getTileByXAndY(tile.x, tile.y).getBaseTerrain().getResources().isResourceVisibleForThisCivilization(GameDatabase.getCivilizationByTurn(GameDatabase.getTurn()))) {
                tile.resource.setFill(new ImagePattern(GraphicalBases.RESOURCES.get(GameDatabase.getTileByXAndY(tile.x, tile.y).getBaseTerrain().getResources().getName())));
            } else {
                //tile.resource.setFill(Color.BLACK);
                tile.resource.setVisible(false);
            }

            tile.feature.getPoints().add(tile.polygon.getPoints().get(0) + 10);
            tile.feature.getPoints().add(tile.polygon.getPoints().get(1) + 40);
            tile.feature.getPoints().add(tile.polygon.getPoints().get(0) + 80);
            tile.feature.getPoints().add(tile.polygon.getPoints().get(1) + 40);
            tile.feature.getPoints().add(tile.polygon.getPoints().get(0) + 80);
            tile.feature.getPoints().add(tile.polygon.getPoints().get(1) + 80);
            tile.feature.getPoints().add(tile.polygon.getPoints().get(0) + 10);
            tile.feature.getPoints().add(tile.polygon.getPoints().get(1) + 80);

            tile.resource.getPoints().add(tile.polygon.getPoints().get(0) + 10);
            tile.resource.getPoints().add(tile.polygon.getPoints().get(1) + 80);
            tile.resource.getPoints().add(tile.polygon.getPoints().get(0) + 80);
            tile.resource.getPoints().add(tile.polygon.getPoints().get(1) + 80);
            tile.resource.getPoints().add(tile.polygon.getPoints().get(0) + 80);
            tile.resource.getPoints().add(tile.polygon.getPoints().get(1) + 120);
            tile.resource.getPoints().add(tile.polygon.getPoints().get(0) + 10);
            tile.resource.getPoints().add(tile.polygon.getPoints().get(1) + 120);


            // SEPEHR INJA BEZAN :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            tile.informationOfTile.setStyle("-fx-background-color: #222c41;-fx-border-color: #555564; -fx-text-fill: white;-fx-border-width: 3; -fx-padding: 50; -fx-start-margin: 10");
            tile.informationOfTile.setLayoutX(800);
            tile.informationOfTile.setLayoutY(0);
            tile.informationOfTile.setPrefHeight(300);
            tile.informationOfTile.setPrefWidth(300);
            tile.informationOfTile.setVisible(false);
            tile.vBox.setAlignment(Pos.CENTER_LEFT);
            tile.hBox.setAlignment(Pos.CENTER);
            tile.hBox.setSpacing(10);
            Text text = (Text) tile.hBox.getChildren().get(0);
            text.setFill(Color.WHITE);
            tile.informationOfTile.setBackground(Background.fill(new ImagePattern(GraphicalBases.BLACK)));
            tile.informationText.setText(GameDatabase.getTileByXAndY(tile.x, tile.y).getInformation());
            tile.informationText.setFill(Color.WHITE);
            tile.informationText.setStyle("-fx-padding: 20");
            isClickedOnce = false;
            isClickedTwice = false;
            tile.polygon.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    Unit selectedUnit = null;
                    if (!isClickedOnce) {
                        isClickedOnce = true;
                        selectedTile = tile;
                        selectedUnit = GameDatabase.getTileByXAndY(tile.x, tile.y).getCombatUnit();
                        GameDatabase.getCivilizationByTurn(GameDatabase.getTurn()).setSelectedUnit(selectedUnit);
                    } else if (isClickedOnce){
                        if ( (selectedUnit = GameDatabase.getCivilizationByTurn(GameDatabase.getTurn()).getSelectedUnit()) != null) {
                            if (tile.x == selectedUnit.getTileOfUnit().getX() && tile.y == selectedUnit.getTileOfUnit().getY()) {
                                //isClickedTwice = true;
                                isClickedOnce = false;
                                selectedTile = null;
                                System.out.println("you selected the same tile the unit is in");
                            } else {
                                System.out.println(tile.x + " " + tile.y);
                                int b = selectedUnit.moveUnitFromTo(selectedUnit, selectedUnit.getTileOfUnit(), GameDatabase.getTileByXAndY(tile.x, tile.y));
                                if (b == -1 || b == -2 ){
                                    selectedTile.informationText.setText(GetTileInReal(selectedTile).getInformation());
                                    selectedTile.informationText.setText(selectedTile.informationText.getText() + "\n stacking limitation or dest tile can't be passed");
                                } else {
                                    for (TileFX tileFX : tileFXES){
                                        if (selectedTile.x == tileFX.x && selectedTile.y == tileFX.y){
                                            if (selectedUnit instanceof Soldier){
                                                System.out.println("removing form " + tileFX.x + " " +tileFX.y);
                                                tileFX.combatUnit.setVisible(false);
                                                tileFX.combatUnit = null;
                                            } else {
                                                tileFX.nonCombatUnit = null;
                                            }
                                        }
                                    }
                                    moveUnitAlongPath(selectedUnit);
                                    //move combat unit to next tile graphic
                                    tile.combatUnit = new Circle(30, Color.BLACK);
                                    tile.combatUnit.setFill(new ImagePattern(GraphicalBases.UNITS.get(selectedUnit.getUnitType())));
                                    tile.combatUnit.setLayoutX(tile.polygon.getPoints().get(6) - 40);
                                    tile.combatUnit.setLayoutY(tile.polygon.getPoints().get(7) - 40);
                                    tile.combatUnit.prefHeight(100);
                                    tile.combatUnit.prefWidth(100);
                                    mapPane.getChildren().add(tile.combatUnit);
                                    tile.combatUnit.toFront();
                                    addToTileInReal(GameDatabase.getTileByXAndY(tile.x, tile.y), selectedUnit.getUnitType());
                                    isClickedOnce = false;
                                    System.out.println(selectedUnit.getTileOfUnit().getX() + " " + selectedUnit.getTileOfUnit().getY());
                                    selectedTile = null;
                                }


                            }
                        } else {
                            System.out.println("no unit was selected");
                            //isClickedTwice = true;
                            isClickedOnce = false;
                            selectedTile = null;
                        }
                    }

                }
            });
            tile.polygon.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (!isClickedOnce) {
                        tile.informationOfTile.toFront();
                        if (!tile.informationOfTile.isVisible()) {
                            tile.informationOfTile.setVisible(true);
                        }
                    }
                }
            });
            tile.polygon.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (!isClickedOnce) {
                        tile.informationOfTile.toFront();
                        if (tile.informationOfTile.isVisible())
                            tile.informationOfTile.setVisible(false);
                    }
                }
            });

            tile.createUnit.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    createUnit();
                }
            });


            if(GameDatabase.getTileByXAndY(tile.x, tile.y).ruin != null) {
                tile.polygon.setFill(new ImagePattern(GraphicalBases.RUINS));
            }

            if(GameDatabase.getCivilizationByTurn(GameDatabase.getTurn()).isThisTileFogOfWar(GameDatabase.getTileByXAndY(tile.x, tile.y))) {
                tile.polygon.setFill(new ImagePattern(GraphicalBases.FOG_OF_WAR));
                tile.informationText.setText("This tile is Fog of War\nX = " + tile.x + " Y = " + tile.y);
                tile.nameOfOwner.setVisible(false);
                tile.hBox.setVisible(false);
                if(tile.combatUnit != null) {
                    tile.combatUnit.setVisible(false);
                }
                if(tile.nonCombatUnit != null) {
                    tile.nonCombatUnit.setVisible(false);
                }
                if(tile.feature != null) {
                    tile.feature.setVisible(false);
                }
                if(tile.resource != null) {
                    tile.resource.setVisible(false);
                }
            } else {
                tile.nameOfOwner.setVisible(true);
                tile.hBox.setVisible(true);
                tile.informationOfTile.setStyle("-fx-background-color: #222c41;-fx-border-color: #555564; -fx-text-fill: white;-fx-border-width: 3; -fx-padding: 50; -fx-start-margin: 10");
                if(tile.combatUnit != null) {
                    tile.combatUnit.setVisible(true);
                }
                if(tile.nonCombatUnit != null) {
                    tile.nonCombatUnit.setVisible(true);
                }
                if(tile.feature != null && !tile.feature.getFill().equals(Color.BLACK)) {
                    tile.feature.setVisible(true);
                }
                if(tile.resource != null && !tile.resource.getFill().equals(Color.BLACK)) {
                    tile.resource.setVisible(true);
                }
            }

        }

    }

    private void updateMap() {
        for (TileFX tileFX : tileFXES) {
            if(needToUpdate(tileFX)) {
                updateMapForOneTile(tileFX);
                tileFX.informationOfTile.setStyle("-fx-background-color: #222c41;-fx-border-color: #555564; -fx-text-fill: white;-fx-border-width: 3; -fx-padding: 50; -fx-start-margin: 10");
            }
        }
    }

    private boolean needToUpdate(TileFX tileFX) {
        Civilization civilization = GameDatabase.getLastCivilization();
        if(civilization == null) {
            return true;
        }
        if(civilization.isThisTileFogOfWar(GameDatabase.getTileByXAndY(tileFX.x, tileFX.y)) &&
            GameDatabase.getCivilizationByTurn(GameDatabase.getTurn()).isThisTileFogOfWar(GameDatabase.getTileByXAndY(tileFX.x, tileFX.y))){
            return false;
        }
        return civilization.isThisTileFogOfWar(GameDatabase.getTileByXAndY(tileFX.x, tileFX.y)) ||
                GameDatabase.getCivilizationByTurn(GameDatabase.getTurn()).isThisTileFogOfWar(GameDatabase.getTileByXAndY(tileFX.x, tileFX.y));
    }

    public boolean moveUnitAlongPath(Unit selectedUnit) {
        int index = 0;
        for (int i = 0; i < selectedUnit.getRoute().size(); i++) {
            if (selectedUnit.getTileOfUnit().equals(selectedUnit.getRoute().get(i))) {
                index = i;
                if (i + 1 != selectedUnit.getRoute().size()) {
                    selectedUnit.moveToAdjacentTile(selectedUnit.getRoute().get(i + 1));
                    if (selectedUnit instanceof Worker) {
                        selectedUnit.getRoute().get(i + 1).addWorker((Worker) selectedUnit);
                        selectedUnit.getRoute().get(i).removeWorker((Worker) selectedUnit);
                        selectedUnit.setTileOfUnit(selectedUnit.getRoute().get(i + 1));
                    } else if (selectedUnit instanceof Settler) {
                        selectedUnit.getRoute().get(i + 1).addSettler((Settler) selectedUnit);
                        selectedUnit.getRoute().get(i).removeSettler((Settler) selectedUnit);
                        selectedUnit.setTileOfUnit(selectedUnit.getRoute().get(i + 1));
                    } else {
                        selectedUnit.getRoute().get(i + 1).getUnits().add(selectedUnit);
                        selectedUnit.getRoute().get(i).getUnits().remove(selectedUnit);
                        selectedUnit.setTileOfUnit(selectedUnit.getRoute().get(i + 1));
                    }
                    break;
                }
            }

        }
        if (index + 1 == selectedUnit.getRoute().size() - 1){
            selectedUnit.setSpeed(selectedUnit.getOriginialspeed());
            this.movingUnits.remove(selectedUnit);
            return true;
        }
        return false;
    }

    private void createUnit(){
        if(selectedTile == null) {
            return;
        }
        if(selectedTile.soldiers.getValue() == null) {
            return;
        }
        //System.out.println(selectedTile.soldiers.getValue().toString());
        if(selectedTile.soldiers.getValue().equals("Settler")
            || selectedTile.soldiers.getValue().equals("worker")) {
            createNonCombat();
        } else {
            createCombat();
        }

    }
    private void createUnitInTile(TileFX tileFX, Unit selectedUnit){
        if(tileFX == null) {
            return;
        }

        if (selectedUnit instanceof Soldier){
            createCombatInTile(tileFX, selectedUnit);
        } else {
            createNonCombatInTile(tileFX, selectedUnit);
        }

    }

    private void createNonCombat() {
        if(!addToTileInReal(GameDatabase.getTileByXAndY(selectedTile.x, selectedTile.y), selectedTile.soldiers.getValue())) {
            return;
        }
        selectedTile.nonCombatUnit = new Circle(30, Color.BLACK);
        selectedTile.nonCombatUnit.setFill(new ImagePattern(GraphicalBases.UNITS.get(selectedTile.soldiers.getValue().toString())));
        selectedTile.nonCombatUnit.setLayoutX(selectedTile.polygon.getPoints().get(6) - 120);
        selectedTile.nonCombatUnit.setLayoutY(selectedTile.polygon.getPoints().get(7) - 40);
        selectedTile.nonCombatUnit.prefHeight(100);
        selectedTile.nonCombatUnit.prefWidth(100);

        selectedTile.nonCombatUnit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(selectedTile != null && GameDatabase.getTileByXAndY(selectedTile.x, selectedTile.y).getNonCombatUnit() != null
                        && GameDatabase.getTileByXAndY(selectedTile.x, selectedTile.y).getNonCombatUnit().getCivilizationIndex() == GameDatabase.getTurn()) {
                    GameDatabase.getCivilizationByTurn(GameDatabase.getTurn()).setSelectedUnit(GameDatabase.getTileByXAndY(selectedTile.x, selectedTile.y).getNonCombatUnit());
                    updateInfoPanel();
                }
            }
        });

        mapPane.getChildren().add(selectedTile.nonCombatUnit);
        selectedTile.nonCombatUnit.toFront();
        addToTileInReal(GameDatabase.getTileByXAndY(selectedTile.x, selectedTile.y), selectedTile.soldiers.getValue());

    }

    private void createNonCombatInTile(TileFX tileFX, Unit selectedUnit) {
        if(!addToTileInReal(GameDatabase.getTileByXAndY(tileFX.x, tileFX.y), selectedUnit.getUnitType())) {
            return;
        }
        tileFX.nonCombatUnit = new Circle(30, Color.BLACK);
        tileFX.nonCombatUnit.setFill(new ImagePattern(GraphicalBases.UNITS.get(tileFX.soldiers.getValue().toString())));
        tileFX.nonCombatUnit.setLayoutX(tileFX.polygon.getPoints().get(6) - 120);
        tileFX.nonCombatUnit.setLayoutY(tileFX.polygon.getPoints().get(7) - 40);
        tileFX.nonCombatUnit.prefHeight(100);
        tileFX.nonCombatUnit.prefWidth(100);

        tileFX.nonCombatUnit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(tileFX != null && GameDatabase.getTileByXAndY(tileFX.x, tileFX.y).getNonCombatUnit() != null
                        && GameDatabase.getTileByXAndY(tileFX.x, tileFX.y).getNonCombatUnit().getCivilizationIndex() == GameDatabase.getTurn()) {
                    GameDatabase.getCivilizationByTurn(GameDatabase.getTurn()).setSelectedUnit(GameDatabase.getTileByXAndY(tileFX.x, tileFX.y).getNonCombatUnit());
                    updateInfoPanel();
                }
            }
        });

        mapPane.getChildren().add(tileFX.nonCombatUnit);
        tileFX.nonCombatUnit.toFront();
        addToTileInReal(GameDatabase.getTileByXAndY(tileFX.x, tileFX.y), tileFX.soldiers.getValue());

    }

    private void showNonCombatByUnit(Unit unit, TileFX tileFX) {
        tileFX.nonCombatUnit = new Circle(30, Color.BLACK);
        tileFX.nonCombatUnit.setFill(new ImagePattern(GraphicalBases.UNITS.get(unit.getUnitType())));
        tileFX.nonCombatUnit.setLayoutX(tileFX.polygon.getPoints().get(6) - 120);
        tileFX.nonCombatUnit.setLayoutY(tileFX.polygon.getPoints().get(7) - 40);
        tileFX.nonCombatUnit.prefHeight(100);
        tileFX.nonCombatUnit.prefWidth(100);

        tileFX.nonCombatUnit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(GameDatabase.getTileByXAndY(tileFX.x, tileFX.y).getNonCombatUnit().getCivilizationIndex() == GameDatabase.getTurn()) {
                    GameDatabase.getCivilizationByTurn(GameDatabase.getTurn()).setSelectedUnit(GameDatabase.getTileByXAndY(tileFX.x, tileFX.y).getNonCombatUnit());
                    updateInfoPanel();
                }
            }
        });

        mapPane.getChildren().add(tileFX.nonCombatUnit);
        tileFX.nonCombatUnit.toFront();
    }

    private void showCombatByUnit(Unit unit, TileFX tileFX) {
        tileFX.combatUnit = new Circle(30, Color.BLACK);
        tileFX.combatUnit.setFill(new ImagePattern(GraphicalBases.UNITS.get(unit.getUnitType())));
        tileFX.combatUnit.setLayoutX(tileFX.polygon.getPoints().get(6) - 40);
        tileFX.combatUnit.setLayoutY(tileFX.polygon.getPoints().get(7) - 40);
        tileFX.combatUnit.prefHeight(100);
        tileFX.combatUnit.prefWidth(100);

        tileFX.combatUnit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(GameDatabase.getTileByXAndY(tileFX.x, tileFX.y).getCombatUnit().getCivilizationIndex() == GameDatabase.getTurn()) {
                    GameDatabase.getCivilizationByTurn(GameDatabase.getTurn()).setSelectedUnit(GameDatabase.getTileByXAndY(tileFX.x, tileFX.y).getCombatUnit());
                    updateInfoPanel();
                }
            }
        });

        mapPane.getChildren().add(tileFX.combatUnit);
        tileFX.combatUnit.toFront();
    }

    private void createCombat() {
        if(!addToTileInReal(GameDatabase.getTileByXAndY(selectedTile.x, selectedTile.y), selectedTile.soldiers.getValue())) {
            return;
        }
        selectedTile.combatUnit = new Circle(30, Color.BLACK);
        selectedTile.combatUnit.setFill(new ImagePattern(GraphicalBases.UNITS.get(selectedTile.soldiers.getValue().toString())));
        selectedTile.combatUnit.setLayoutX(selectedTile.polygon.getPoints().get(6) - 40);
        selectedTile.combatUnit.setLayoutY(selectedTile.polygon.getPoints().get(7) - 40);
        selectedTile.combatUnit.prefHeight(100);
        selectedTile.combatUnit.prefWidth(100);

        selectedTile.combatUnit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(selectedTile != null && GameDatabase.getTileByXAndY(selectedTile.x, selectedTile.y).getCombatUnit() != null
                    && GameDatabase.getTileByXAndY(selectedTile.x, selectedTile.y).getCombatUnit().getCivilizationIndex() == GameDatabase.getTurn()) {
                    GameDatabase.getCivilizationByTurn(GameDatabase.getTurn()).setSelectedUnit(GameDatabase.getTileByXAndY(selectedTile.x, selectedTile.y).getCombatUnit());
                    updateInfoPanel();
                }
            }
        });



        mapPane.getChildren().add(selectedTile.combatUnit);
        selectedTile.combatUnit.toFront();
        addToTileInReal(GameDatabase.getTileByXAndY(selectedTile.x, selectedTile.y), selectedTile.soldiers.getValue());
    }

    private void createCombatInTile(TileFX tileFX, Unit selectedUnit) {
        if(!addToTileInReal(GameDatabase.getTileByXAndY(tileFX.x, tileFX.y), selectedUnit.getUnitType())) {
            System.out.println("gaygay gay");
            return;
        }
        tileFX.combatUnit = new Circle(30, Color.BLACK);
        tileFX.combatUnit.setFill(new ImagePattern(GraphicalBases.UNITS.get(tileFX.soldiers.getValue().toString())));
        tileFX.combatUnit.setLayoutX(tileFX.polygon.getPoints().get(6) - 40);
        tileFX.combatUnit.setLayoutY(tileFX.polygon.getPoints().get(7) - 40);
        tileFX.combatUnit.prefHeight(100);
        tileFX.combatUnit.prefWidth(100);

        tileFX.combatUnit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(tileFX != null && GameDatabase.getTileByXAndY(tileFX.x, tileFX.y).getCombatUnit() != null
                        && GameDatabase.getTileByXAndY(tileFX.x, tileFX.y).getCombatUnit().getCivilizationIndex() == GameDatabase.getTurn()) {
                    GameDatabase.getCivilizationByTurn(GameDatabase.getTurn()).setSelectedUnit(GameDatabase.getTileByXAndY(tileFX.x, tileFX.y).getCombatUnit());
                    updateInfoPanel();
                }
            }
        });



        mapPane.getChildren().add(tileFX.combatUnit);
        tileFX.combatUnit.toFront();
        addToTileInReal(GameDatabase.getTileByXAndY(tileFX.x, tileFX.y), tileFX.soldiers.getValue());
    }
    private boolean addToTileInReal(Tile tileByXAndY, String value) {
        if(tileByXAndY == null) {
            return false;
        }
        boolean create = new GameMenuController(new GameModel()).createUnit(value, tileByXAndY.getX(), tileByXAndY.getY(), GameDatabase.getTurn());
        if(!create) {
            selectedTile.informationText.setText(selectedTile.informationText.getText() + "\n Creating unit is invalid.");
        } else {
            //GameDatabase.getCivilizationByTurn(GameDatabase.getTurn()).setSelectedUnit(GameDatabase.getTileByXAndY(selectedTile.x, selectedTile.y).getCombatUnit());
            updateInfoPanel();
            //System.out.println(GameDatabase.getTileByXAndY(selectedTile.x, selectedTile.y).getCombatUnit());
        }
        return create;
    }

    private void setStopButton() {
        Button stopButton = new Button("STOP");
        stopButton.setLayoutY(0);
        stopButton.setPrefHeight(40);
        stopButton.setLayoutX(nextTurn.getLayoutX() - 100);
        stopButton.setStyle(nextTurn.getStyle());
        stopButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GameDatabase.saveGame();
                GraphicalBases.changeMenu("GameMenu");
            }
        });
        mainAnchorPane.getChildren().add(stopButton);
    }

    private void setTechnologyTree() {
        Button technologyTree = new Button("Technology Tree");
        technologyTree.setPrefWidth(infoPanel.getWidth());
        technologyTree.setStyle("-fx-background-color: #222c41;-fx-border-color: #555564; -fx-text-fill: white;-fx-border-width: 3;");
        technologyTree.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GraphicalBases.enterGame("TechnologyTree");
            }
        });
        infoPanelVBox.getChildren().add(technologyTree);
    }

    private void setGameDiscussion() {
        Button discussion = new Button("Game Discussion");
        discussion.setPrefWidth(infoPanel.getWidth());
        discussion.setStyle("-fx-background-color: #222c41;-fx-border-color: #555564; -fx-text-fill: white;-fx-border-width: 3;");
        discussion.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GraphicalBases.enterGame("Discussion");
            }
        });
        infoPanelVBox.getChildren().add(discussion);
    }

    private void setBackButton() {
        Button backButton = new Button("BACK");
        backButton.setLayoutY(0);
        backButton.setPrefHeight(40);
        backButton.setLayoutX(nextTurn.getLayoutX() - 50);
        backButton.setStyle(nextTurn.getStyle());
        backButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GameDatabase.saveGame();
                GraphicalBases.userLoggedIn();
            }
        });
        mainAnchorPane.getChildren().add(backButton);
    }

    private void setNextTurnButton() {
        nextTurn = new Button("NEXT TURN");
        nextTurn.setLayoutX(1200);
        nextTurn.setLayoutY(0);
        nextTurn.setPrefHeight(40);
        nextTurn.setStyle("-fx-background-color: #222c41;-fx-border-color: #555564; -fx-text-fill: white;-fx-border-width: 3;");
        nextTurn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                checkIfWin();
                if(GameModel.autoSave && GameDatabase.getTurn()%50 == 49) {
                    try {
                        autoSave();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                GameDatabase.nextTurn();
                if(isTerminalOn) {
                    endTerminal();
                }
                updateStatusBar();
                updateInfoPanel();
                updateMap();
            }
        });
        mainAnchorPane.getChildren().add(nextTurn);
    }

    private void autoSave() throws IOException {
        GameDatabase.saveGame();
    }

    private void checkIfWin() {
        if(GameDatabase.checkIfWin() == null) {
            return;
        }
        GraphicalBases.enterGame("Win");

    }

    private void setInfoPanel() {
        infoPanel = new Rectangle();
        infoPanel.setX(0);
        infoPanel.setY(0);
        infoPanel.setWidth(150);
        infoPanel.setHeight(720);
        infoPanel.setFill(new ImagePattern(GraphicalBases.INFO_PANEL));

        infoPanelVBox = new VBox();
        infoPanelVBox.setSpacing(10);
        technologyUnderSearch = new Rectangle();
        technologyUnderSearch.setWidth(150);
        technologyUnderSearch.setHeight(technologyUnderSearch.getWidth());
        infoPanelVBox.getChildren().add(technologyUnderSearch);

        setTechnologyTree();
        setGameDiscussion();
        setOverviews();
        setPanelLists();
        //setUnitCreating();

        unitSelected = new Rectangle();
        unitSelected.setWidth(150);
        unitSelected.setHeight(unitSelected.getWidth());
        infoPanelVBox.getChildren().add(unitSelected);

        mainAnchorPane.getChildren().add(infoPanel);
        mainAnchorPane.getChildren().add(infoPanelVBox);

        updateInfoPanel();

    }

    private void setUnitCreating() {
        Button button = new Button("Creating Units\nand Buildings");
        button.setPrefWidth(infoPanel.getWidth());
        button.setStyle("-fx-background-color: #222c41;-fx-border-color: #555564; -fx-text-fill: white;-fx-border-width: 3;");
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GraphicalBases.enterGame("creating");
            }
        });
        infoPanelVBox.getChildren().add(button);
    }

    private void setPanelLists() {
        Button button = new Button("Panel lists");
        button.setPrefWidth(infoPanel.getWidth());
        button.setStyle("-fx-background-color: #222c41;-fx-border-color: #555564; -fx-text-fill: white;-fx-border-width: 3;");
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GraphicalBases.enterGame("PanelList");
            }
        });
        infoPanelVBox.getChildren().add(button);
    }

    private void setOverviews() {
        Button button = new Button("Overviews");
        button.setPrefWidth(infoPanel.getWidth());
        button.setStyle("-fx-background-color: #222c41;-fx-border-color: #555564; -fx-text-fill: white;-fx-border-width: 3;");
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GraphicalBases.enterGame("Overviews");
            }
        });
        infoPanelVBox.getChildren().add(button);
    }

    private void updateInfoPanel() {
        Technology technology = GameDatabase.getCivilizationByTurn(turn).getTechnologyUnderResearch();
        if(technology == null) {
            technologyUnderSearch.setFill(new ImagePattern(GraphicalBases.NULL));
        } else {
            technologyUnderSearch.setFill(new ImagePattern(GraphicalBases.TECHNOLOGIES.get(technology.getName())));
        }

        Unit unit = GameDatabase.getCivilizationByTurn(turn).getSelectedUnit();
        if(unit == null) {
            unitSelected.setFill(new ImagePattern(GraphicalBases.NULL));
        } else {
            unitSelected.setFill(new ImagePattern(GraphicalBases.UNITS.get(unit.getUnitType())));
        }
    }

    private void setStatusBar() {
        statusBar = new Rectangle();
        statusBar.setX(0);
        statusBar.setY(0);
        statusBar.setHeight(40);
        statusBar.setWidth(1280);
        statusBar.setStyle("-fx-fill: #222c41");
        mainAnchorPane.getChildren().add(statusBar);

        statusBarHBox = new HBox();
        statusBarHBox.setLayoutX(200);
        statusBarHBox.setLayoutY(5);
        fillStatusBar();
        mainAnchorPane.getChildren().add(statusBarHBox);

        updateStatusBar();
    }

    private void fillStatusBar() {
        // name
        civilizationName = new Text("");
        civilizationName.setStyle("-fx-font-size: 30; -fx-fill: white");
        statusBarHBox.getChildren().add(civilizationName);


        // coin
        coinText = new Text("  ");
        coinText.setStyle("-fx-font-size: 30; -fx-fill: white");
        statusBarHBox.getChildren().add(coinText);
        Rectangle coin = new Rectangle(30, 30);
        coin.setFill(new ImagePattern(GraphicalBases.COIN));
        statusBarHBox.getChildren().add(coin);

        // science
        scienceText = new Text("  ");
        scienceText.setStyle("-fx-font-size: 30; -fx-fill: white");
        statusBarHBox.getChildren().add(scienceText);
        Rectangle science = new Rectangle(30, 30);
        science.setFill(new ImagePattern(GraphicalBases.SCIENCE));
        statusBarHBox.getChildren().add(science);

        // happiness
        happinessText = new Text("  ");
        happinessText.setStyle("-fx-font-size: 30; -fx-fill: white");
        statusBarHBox.getChildren().add(happinessText);
        VBox happinessVBox = new VBox();
        Rectangle happiness = new Rectangle(30, 30);
        happiness.setFill(new ImagePattern(GraphicalBases.HAPPY));
        showHappinessText = new Text("");
        showHappinessText.setVisible(false);
        showHappinessText.setStyle("-fx-font-size: 10");
        showHappiness = new Rectangle();
        showHappiness.setWidth(happiness.getWidth());
        showHappiness.setHeight(happiness.getHeight());
        showHappiness.setX(150);
        showHappiness.setY(happiness.getHeight() + 20);
        showHappiness.setVisible(false);
        happiness.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(GameDatabase.getCivilizationByTurn(turn).isHappy()) {
                    showHappiness.setFill(new ImagePattern(GraphicalBases.HAPPY));
                } else {
                    showHappiness.setFill(new ImagePattern(GraphicalBases.UNHAPPY));
                }
                showHappinessText.setText("Happiness: " + GameDatabase.getCivilizationByTurn(turn).getHappiness() +
                        "\n Unhappiness: " + (GameDatabase.getCivilizationByTurn(turn).getHappiness()*-1));
                showHappinessText.setVisible(true);
                showHappiness.setVisible(true);
            }
        });
        happiness.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                showHappinessText.setVisible(false);
                showHappiness.setVisible(false);
            }
        });
        happinessVBox.getChildren().add(happiness);
        happinessVBox.getChildren().add(showHappiness);
        happinessVBox.getChildren().add(showHappinessText);
        statusBarHBox.getChildren().add(happinessVBox);
    }

    private void updateStatusBar() {
        coinText.setText("   " + Integer.toString(GameDatabase.getPlayers().get(turn).getGold()) + "   ");
        happinessText.setText("   " + Integer.toString(GameDatabase.getPlayers().get(turn).getHappiness()) + "   ");
        scienceText.setText("   " + Integer.toString(GameDatabase.players.get(turn).getScience()) + "   ");
        civilizationName.setText(GameDatabase.getCivilizationByTurn(turn).getNickname());
    }

    private void setTerminal() {
        terminal = new TextArea();
        terminal.setPrefWidth(1250);
        terminal.setPrefHeight(50);
        terminal.setLayoutX(15);
        terminal.setLayoutY(655);
        terminalDefaultStart = "AP: group04>CivilizationV>Terminal>";
        terminalDefaultEnd = ">";
    }

    private void setCheatCodesTerminal() {
        isTerminalOn = false;
        GraphicalBases.scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                boolean shallStartTerminal = keyEvent.getText().equals("c") && keyEvent.isShiftDown() && keyEvent.isControlDown();
                if(shallStartTerminal && !isTerminalOn) {
                    startTerminal();
                } else {
                    boolean shallEndTerminal = keyEvent.getText().equals("e") && keyEvent.isShiftDown() && keyEvent.isControlDown();
                    if(shallEndTerminal && isTerminalOn) {
                        endTerminal();
                    } else {
                        boolean shallRestartTerminal = keyEvent.getText().equals("r") && keyEvent.isShiftDown() && keyEvent.isControlDown();
                        if(shallRestartTerminal && isTerminalOn) {
                            restartTerminal();
                        }
                    }
                }
            }
        });
    }

    private void endTerminal() {
        isTerminalOn = false;
        mainAnchorPane.getChildren().remove(terminal);
    }

    private void startTerminal() {
        isTerminalOn = true;
        terminal.setEditable(true);
        terminalDefault = terminalDefaultStart + GameDatabase.getCivilizationByTurn(turn).getNickname() + terminalDefaultEnd;
        terminal.setText(terminalDefault);
        terminal.positionCaret(terminalDefault.length());
        terminal.setStyle("-fx-control-inner-background:#000000; " +
                "-fx-font-family: Consolas; " +
                "-fx-highlight-fill: #00ff00; " +
                "-fx-highlight-text-fill: #000000; " +
                "-fx-text-fill: #00ff00;");
        terminal.setOnKeyTyped(new EventHandler<KeyEvent>() {

            public boolean isResult = false;
            Cheater cheater;

            @Override
            public void handle(KeyEvent keyEvent) {
                if(terminal.getText().length() < terminalDefault.length()
                    || !terminal.getText().startsWith(terminalDefault)) {
                    terminal.setText(terminalDefault);
                    terminal.positionCaret(terminalDefault.length());
                } else if(isCharEnter(keyEvent)) {
                    if(shallRestart()) {
                        restartTerminal();
                    } else {
                        isResult = true;
                        String command = commandFounder();
                        cheater = new Cheater(turn);
                        addResult(cheater.run(command));
                        updateStatusBar();
                    }
                }
            }

            private void addResult(String result) {
                if(result.startsWith("Error:")) {
                    terminal.setStyle("-fx-control-inner-background:#000000; " +
                            "-fx-font-family: Consolas; " +
                            "-fx-highlight-fill: #00ff00; " +
                            "-fx-highlight-text-fill: #000000; " +
                            "-fx-text-fill: #ff0000;");
                }
                result += " Press Ctrl+Shift+R to Restart the Terminal.";
                terminal.setText(terminal.getText() + result);
                terminal.setEditable(false);
            }

            private String commandFounder() {
                String text = terminal.getText().substring(terminalDefault.length());
                String[] textSplit = text.split("\n");
                text = textSplit[0];
                return text;
            }

            private boolean shallRestart() {
                return terminal.getText().substring(terminalDefault.length()).equals("\n") || isResult;
            }

            private boolean isCharEnter(KeyEvent keyEvent) {
                return keyEvent.getCharacter().equals("\r");
            }
        });
        mainAnchorPane.getChildren().add(terminal);
    }

    private void restartTerminal() {
        endTerminal();
        startTerminal();
    }

   
}
