package Civilization.View.Transitions;

import Civilization.View.FXMLControllers.DiscussionFXMLController;
import javafx.animation.Transition;
import javafx.util.Duration;

public class DiscussionUserChoosingTransition extends Transition {

    private DiscussionFXMLController discussionFXMLController;

    public DiscussionUserChoosingTransition(DiscussionFXMLController discussionFXMLController) {
        this.discussionFXMLController = discussionFXMLController;
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(1000));
    }

    @Override
    protected void interpolate(double v) {
        discussionFXMLController.handleChoiceBox();
    }
}
