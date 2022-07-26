package Client.View.FXMLControllers;

import Server.Controller.GameMenuController;
import Client.Database.GameDatabase;
import Client.Database.GlobalVariables;
import Server.Model.GameModel;
import Server.Model.TerrainFeatures;
import Server.Model.Tile;
import Server.Model.Worker;
import Client.View.GraphicalBases;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class WorkerActionsFXMLController {

    @FXML
    private AnchorPane mainAnchorPane;

    private Worker worker;
    private Tile tile;

    private VBox mainWorkerVBox;
    private ChoiceBox<String> improvementsCoiceBox;

    @FXML
    public void initialize() throws IOException {
        setFirstVariables();
        setBackground();
        setBackButton();
        setStopWorkButton();
        setPauseProjectButton();
        setWorkerVBox();
        setWorks();
        GameModel.isGame = true;
    }

    private void setWorks() {
        Text text = new Text("Worker Actions:\n\tBuilding Road and RailRoad\n\tClearing Tile Features\n\tImplementing Improvements\n\tRepair Tile");
        text.setStyle("-fx-font-size: 25");
        text.setX(500);
        text.setY(50);
        mainAnchorPane.getChildren().add(text);
    }

    private void setWorkerVBox() throws IOException {
        mainWorkerVBox = new VBox();
        mainWorkerVBox.setLayoutX(1000);
        mainWorkerVBox.setLayoutY(50);
        mainWorkerVBox.setSpacing(10);

        setCreateRoadButton();
        setCreateRailRoadButton();
        setRepairTileButton();
        setRemoveFeature();
        setRemoveRoad();
        setRemoveRailRoad();
        setImprovements();

        if(!worker.isAssigned()) {
            mainWorkerVBox.setVisible(true);
        } else {
            mainWorkerVBox.setVisible(false);
        }
        mainAnchorPane.getChildren().add(mainWorkerVBox);
    }

    private void setImprovements() {
        HBox hBox = new HBox();
        hBox.setSpacing(10);

        improvementsCoiceBox = new ChoiceBox<>();
        updatesImprovements();
        hBox.getChildren().add(improvementsCoiceBox);

        Button button = new Button("Create");
        button.setStyle("-fx-background-color: #222c41;-fx-border-color: #555564; -fx-text-fill: white;-fx-border-width: 3;");
        button.setPrefWidth(150);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(improvementsCoiceBox.getValue() == null) {
                    return;
                }
                String improvementName = improvementsCoiceBox.getValue();
                boolean bool = false;
                try {
                    bool = new GameMenuController(new GameModel()).assignNewProject(worker, improvementName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(!bool) {
                    button.setStyle("-fx-border-color: RED");
                }
            }
        });
        hBox.getChildren().add(button);


        mainWorkerVBox.getChildren().add(hBox);

    }

    private void updatesImprovements() {
        ArrayList<String> improvements = new ArrayList<>(Arrays.asList(GlobalVariables.IMPROVEMENTS));
        ObservableList<String> validTiles = FXCollections.observableArrayList(improvements);
        improvementsCoiceBox.setItems(validTiles);
    }

    private void setRemoveRailRoad() throws IOException {
        Button button = new Button("Remove Railroad");
        button.setStyle("-fx-background-color: #222c41;-fx-border-color: #555564; -fx-text-fill: white;-fx-border-width: 3;");
        button.setPrefWidth(150);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                boolean bool = false;
                try {
                    bool = new GameMenuController(new GameModel()).assignNewProject(worker, "removeRailroad");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(!bool) {
                    button.setStyle("-fx-border-color: RED");
                }
            }
        });
        if(GameDatabase.getTileByXAndY(worker.getX(), worker.getY()).hasRailroad()) {
            button.setVisible(true);
        } else {
            button.setVisible(false);
        }
        mainWorkerVBox.getChildren().add(button);
    }

    private void setRemoveRoad() throws IOException {
        Button button = new Button("Remove Road");
        button.setStyle("-fx-background-color: #222c41;-fx-border-color: #555564; -fx-text-fill: white;-fx-border-width: 3;");
        button.setPrefWidth(150);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                boolean bool = false;
                try {
                    bool = new GameMenuController(new GameModel()).assignNewProject(worker, "removeRoad");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(!bool) {
                    button.setStyle("-fx-border-color: RED");
                }
            }
        });
        if(GameDatabase.getTileByXAndY(worker.getX(), worker.getY()).hasRoad()) {
            button.setVisible(true);
        } else {
            button.setVisible(false);
        }
        mainWorkerVBox.getChildren().add(button);
    }

    private void setRemoveFeature() throws IOException {
        TerrainFeatures features = GameDatabase.getTileByXAndY(worker.getX(), worker.getY()).getBaseTerrain().getFeature();
        final String[] featureType = {""};
        if(features != null) {
            featureType[0] = features.getType();
        }
        Button button = new Button("Remove Feature");
        button.setStyle("-fx-background-color: #222c41;-fx-border-color: #555564; -fx-text-fill: white;-fx-border-width: 3;");
        button.setPrefWidth(150);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(features == null) {
                    return;
                } else {
                    featureType[0] = "remove" + featureType[0];
                }
                if(featureType[0].equals("removeJungle")) {
                    boolean bool = false;
                    try {
                        bool = new GameMenuController(new GameModel()).assignNewProject(worker, "removeJungle");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(!bool) {
                        button.setStyle("-fx-border-color: RED");
                    }
                } else if(featureType[0].equals("removeDense_Jungle")) {
                    boolean bool = false;
                    try {
                        bool = new GameMenuController(new GameModel()).assignNewProject(worker, "removeDense_Jungle");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(!bool) {
                        button.setStyle("-fx-border-color: RED");
                    }
                } else if(featureType[0].equals("removePrairie")) {
                    boolean bool = false;
                    try {
                        bool = new GameMenuController(new GameModel()).assignNewProject(worker, "removePrairie");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(!bool) {
                        button.setStyle("-fx-border-color: RED");
                    }
                } else {
                    button.setStyle("-fx-border-color: RED");
                }

            }
        });
        if(GameDatabase.getTileByXAndY(worker.getX(), worker.getY()).getBaseTerrain().getFeature() != null) {
            String featureName = GameDatabase.getTileByXAndY(worker.getX(), worker.getY()).getBaseTerrain().getFeature().getType();
            if(featureName.equals("Jungle")
            || featureName.equals("Dense_Jungle")
            || featureName.equals("Prairie")) {
                button.setVisible(true);
            } else {
                button.setVisible(false);
            }
        } else {
            button.setVisible(false);
        }
        mainWorkerVBox.getChildren().add(button);

    }


    private void setRepairTileButton() throws IOException {
        Button button = new Button("Repair tile");
        button.setStyle("-fx-background-color: #222c41;-fx-border-color: #555564; -fx-text-fill: white;-fx-border-width: 3;");
        button.setPrefWidth(150);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                boolean bool = false;
                try {
                    bool = new GameMenuController(new GameModel()).assignNewProject(worker, "repair");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(!bool) {
                    button.setStyle("-fx-border-color: RED");
                }
            }
        });
        if(GameDatabase.getTileByXAndY(worker.getX(), worker.getY()).isRaided()) {
            button.setVisible(true);
        } else {
            button.setVisible(false);
        }
        mainWorkerVBox.getChildren().add(button);
    }

    private void setCreateRailRoadButton() throws IOException {
        Button button = new Button("Build Railroad");
        button.setStyle("-fx-background-color: #222c41;-fx-border-color: #555564; -fx-text-fill: white;-fx-border-width: 3;");
        button.setPrefWidth(150);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                boolean bool = false;
                try {
                    bool = new GameMenuController(new GameModel()).assignNewProject(worker, "Railroad");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(!bool) {
                    button.setStyle("-fx-border-color: RED");
                }
            }
        });
        if(!GameDatabase.getTileByXAndY(worker.getX(), worker.getY()).hasRailroad()) {
            button.setVisible(true);
        } else {
            button.setVisible(false);
        }
        mainWorkerVBox.getChildren().add(button);
    }

    private void setCreateRoadButton() throws IOException {
        Button button = new Button("Build road");
        button.setStyle("-fx-background-color: #222c41;-fx-border-color: #555564; -fx-text-fill: white;-fx-border-width: 3;");
        button.setPrefWidth(150);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                boolean bool = false;
                try {
                    bool = new GameMenuController(new GameModel()).assignNewProject(worker, "Road");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(!bool) {
                    button.setStyle("-fx-border-color: RED");
                }
            }
        });
        if(!GameDatabase.getTileByXAndY(worker.getX(), worker.getY()).hasRoad()) {
            button.setVisible(true);
        } else {
            button.setVisible(false);
        }
        mainWorkerVBox.getChildren().add(button);
    }

    private void setPauseProjectButton() {
        Button button = new Button("Pause Work");
        button.setStyle("-fx-background-color: #222c41;-fx-border-color: #555564; -fx-text-fill: white;-fx-border-width: 3;");
        button.setPrefWidth(150);
        button.setLayoutY(100);
        button.setLayoutX(50);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    new GameMenuController(new GameModel()).pauseProject(worker, worker.getX(), worker.getY());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        if(worker.isAssigned()) {
            button.setVisible(true);
        } else {
            button.setVisible(false);
        }
        mainAnchorPane.getChildren().add(button);
    }

    private void setStopWorkButton() {
        Button button = new Button("Stop Work");
        button.setStyle("-fx-background-color: #222c41;-fx-border-color: #555564; -fx-text-fill: white;-fx-border-width: 3;");
        button.setPrefWidth(150);
        button.setLayoutY(50);
        button.setLayoutX(50);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                worker.stopWork();
            }
        });
        if(worker.isAssigned()) {
            button.setVisible(true);
        } else {
            button.setVisible(false);
        }
        mainAnchorPane.getChildren().add(button);
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

    private void setFirstVariables() throws IOException {
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
