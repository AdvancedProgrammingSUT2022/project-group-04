package Civilization.View.Transitions;

import Civilization.View.Components.SwitchButton;
import Civilization.View.FXMLControllers.GameMenuFXMLController;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.util.Duration;

import java.util.ArrayList;

public class SwitchButtonTransition extends Transition {

    private SwitchButton switchButton;
    private GameMenuFXMLController gameMenuFXMLController;

    public SwitchButtonTransition(SwitchButton switchButton, GameMenuFXMLController gameMenuFXMLController) {
        this.switchButton = switchButton;
        this.gameMenuFXMLController = gameMenuFXMLController;
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(1000));
        TransitionDatabase.transitions.add(this);
    }


    @Override
    protected void interpolate(double v) {
        gameMenuFXMLController.handleSwitchButton();
    }
}
