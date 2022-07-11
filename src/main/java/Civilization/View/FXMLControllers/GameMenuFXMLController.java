package Civilization.View.FXMLControllers;

import Civilization.View.GraphicalBases;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import javax.swing.*;
import java.awt.*;

public class GameMenuFXMLController {

//    @FXML
//    private Button challenge;
//    @FXML
//    private Button save;
//    @FXML
//    private Button challenge;

    @FXML
    private Text saveNumber;
    @FXML
    private Text search;
    @FXML
    private Text gameWidth;
    @FXML
    private Text gameHeight;

    public GameMenuFXMLController() {

    }

    public void enterSearchEngine(MouseEvent mouseEvent) {
        GraphicalBases.changeMenu("SearchEngine");
    }

    public void enterGameSettings(MouseEvent mouseEvent) {
        GraphicalBases.changeMenu("GameSettings");
    }

    public void enterSaveSettings(MouseEvent mouseEvent) {
        GraphicalBases.changeMenu("SaveSettings");
    }


    public void enterGameMenu(MouseEvent mouseEvent) {
        GraphicalBases.changeMenu("Game");
    }

    public void updateSaveNumber(KeyEvent keyEvent) {
        saveNumber.setText(keyEvent.getText());
    }

    public void updateSearchEngineField(KeyEvent keyEvent) {
        search.setText(keyEvent.getText());
    }
}
