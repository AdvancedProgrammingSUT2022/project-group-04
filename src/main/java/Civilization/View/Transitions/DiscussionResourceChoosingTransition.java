package Civilization.View.Transitions;

import Civilization.View.FXMLControllers.DiscussionFXMLController;
import javafx.animation.Transition;
import javafx.util.Duration;

public class DiscussionResourceChoosingTransition extends Transition {

    private DiscussionFXMLController discussionFXMLController;

    public DiscussionResourceChoosingTransition(DiscussionFXMLController discussionFXMLController) {
        this.discussionFXMLController = discussionFXMLController;
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(1000));
    }

    @Override
    protected void interpolate(double v) {
        this.discussionFXMLController.handleResourceSending();
    }
}
