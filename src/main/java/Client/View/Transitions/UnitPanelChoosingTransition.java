package Client.View.Transitions;

import Client.View.FXMLControllers.PanelListFXMLController;
import javafx.animation.Transition;
import javafx.util.Duration;

import java.io.IOException;

public class UnitPanelChoosingTransition extends Transition {

    private PanelListFXMLController panelListFXMLController;

    public UnitPanelChoosingTransition(PanelListFXMLController panelListFXMLController) {
        this.panelListFXMLController = panelListFXMLController;
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(1000));
        TransitionDatabase.transitions.add(this);
    }

    @Override
    protected void interpolate(double v) {
        try {
            panelListFXMLController.handleUnitChoiceBox();
        } catch (IOException e) {
            //e.printStackTrace();
            System.err.println("Connection lost");
        }
    }
}
