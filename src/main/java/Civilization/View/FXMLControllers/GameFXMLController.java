package Civilization.View.FXMLControllers;

import Civilization.Database.GameDatabase;
import Civilization.Database.GlobalVariables;
import Civilization.Model.*;
import Civilization.View.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.IOException;

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
    public static int turn = 0;
    private Button nextTurn;

    @FXML
    public void initialize() {
        turn = GameDatabase.getTurn();
        setStatusBar();
        setNextTurnButton();
        setBackButton();
        setStopButton();
        setInfoPanel();
        setCheatCodesTerminal();
        setTerminal();
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
                GameDatabase.nextTurn();
                if(isTerminalOn) {
                    endTerminal();
                }
                updateStatusBar();
                updateInfoPanel();
            }
        });
        mainAnchorPane.getChildren().add(nextTurn);
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

        unitSelected = new Rectangle();
        unitSelected.setWidth(150);
        unitSelected.setHeight(unitSelected.getWidth());
        infoPanelVBox.getChildren().add(unitSelected);

        mainAnchorPane.getChildren().add(infoPanel);
        mainAnchorPane.getChildren().add(infoPanelVBox);

        updateInfoPanel();

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
