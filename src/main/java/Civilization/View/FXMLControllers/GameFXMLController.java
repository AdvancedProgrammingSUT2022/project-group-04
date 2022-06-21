package Civilization.View.FXMLControllers;

import Civilization.Controller.CombatController;
import Civilization.Controller.GameMenuController;
import Civilization.Controller.MainMenuController;
import Civilization.Database.GameDatabase;
import Civilization.Model.GameModel;
import Civilization.Model.MainMenuModel;
import Civilization.View.*;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GameFXMLController {

    @FXML
    private AnchorPane mainAnchorPane;

    // Status bar
    private Rectangle statusBar;
    private HBox statusBarHBox;
    private Rectangle happy;
    private Rectangle unhappy;

    // Terminal
    private TextArea terminal;
    private String terminalDefaultStart;
    private String terminalDefaultEnd;
    private String terminalDefault;
    private boolean isTerminalOn;

    @FXML
    public void initialize() {

        //// start game for test ////
        ArrayList<String> users = new ArrayList<>();
        users.add("sepehr");
        users.add("alirezaRM");
        GameModel gameModel = new GameModel();
        gameModel.startGame(users);
        /////


        setStatusBar();
        setCheatCodesTerminal();
        setTerminal();
    }

    private void setStatusBar() {
        statusBar = new Rectangle();
        statusBar.setX(0);
        statusBar.setY(0);
        statusBar.setHeight(40);
        statusBar.setWidth(1280);
        mainAnchorPane.getChildren().add(statusBar);

        statusBarHBox = new HBox();
        statusBarHBox.setLayoutX(30);
        statusBarHBox.setLayoutY(5);
        fillStatusBar();
        mainAnchorPane.getChildren().add(statusBarHBox);

        updateStatusBar(0);
    }

    private void fillStatusBar() {
        // coin
        Rectangle coin = new Rectangle(30, 30);
        coin.setFill(new ImagePattern(GraphicalBases.COIN));
        statusBarHBox.getChildren().add(coin);
        Text coinText = new Text("  ");
        coinText.setStyle("-fx-font-size: 30; -fx-fill: white");
        statusBarHBox.getChildren().add(coinText);

        // science
        Rectangle science = new Rectangle(30, 30);
        science.setFill(new ImagePattern(GraphicalBases.SCIENCE));
        statusBarHBox.getChildren().add(science);
        Text scienceText = new Text("  ");
        scienceText.setStyle("-fx-font-size: 30; -fx-fill: white");
        statusBarHBox.getChildren().add(scienceText);

        // happiness
        Rectangle happiness = new Rectangle(30, 30);
        happiness.setFill(new ImagePattern(GraphicalBases.HAPPY));
        happy = new Rectangle(30, 30);
        happy.setFill(new ImagePattern(GraphicalBases.HAPPY));
        unhappy = new Rectangle(30, 30);
        unhappy.setFill(new ImagePattern(GraphicalBases.UNHAPPY));
        statusBarHBox.getChildren().add(happiness);
        Text happinessText = new Text("  ");
        happinessText.setStyle("-fx-font-size: 30; -fx-fill: white");

        statusBarHBox.getChildren().add(happinessText);
    }

    private void updateStatusBar(int turn) {

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
                    startTerminal(GameDatabase.getTurn());
                } else {
                    boolean shallEndTerminal = keyEvent.getText().equals("e") && keyEvent.isShiftDown() && keyEvent.isControlDown();
                    if(shallEndTerminal && isTerminalOn) {
                        endTerminal();
                    } else {
                        boolean shallRestartTerminal = keyEvent.getText().equals("r") && keyEvent.isShiftDown() && keyEvent.isControlDown();
                        if(shallRestartTerminal && isTerminalOn) {
                            restartTerminal(GameDatabase.getTurn());
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

    private void startTerminal(int turn) {
        isTerminalOn = true;
        terminal.setEditable(true);
        terminalDefault = terminalDefaultStart + "Sepehr" + terminalDefaultEnd;
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
                        restartTerminal(turn);
                    } else {
                        isResult = true;
                        String command = commandFounder();
                        cheater = new Cheater(turn);
                        addResult(cheater.run(command));
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

    private void restartTerminal(int turn) {
        endTerminal();
        startTerminal(turn);
    }
}
