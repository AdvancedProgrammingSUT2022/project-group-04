package Civilization.View.Transitions;

import Civilization.View.FXMLControllers.DiscussionFXMLController;
import Civilization.View.FXMLControllers.PanelListFXMLController;
import javafx.animation.Transition;
import javafx.util.Duration;

public class CityPanelChoosingTransition
        extends Transition {

    private PanelListFXMLController panelListFXMLController;

    public CityPanelChoosingTransition(PanelListFXMLController panelListFXMLController) {
        this.panelListFXMLController = panelListFXMLController;
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(1000));
    }

    @Override
    protected void interpolate(double v) {
        panelListFXMLController.handleCityChoiceBox();
    }
}
