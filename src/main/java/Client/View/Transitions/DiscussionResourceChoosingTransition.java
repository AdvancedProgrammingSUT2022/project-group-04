package Client.View.Transitions;

import Client.View.FXMLControllers.DiscussionFXMLController;
import javafx.animation.Transition;
import javafx.util.Duration;

public class DiscussionResourceChoosingTransition extends Transition {

    private DiscussionFXMLController discussionFXMLController;

    public DiscussionResourceChoosingTransition(DiscussionFXMLController discussionFXMLController) {
        this.discussionFXMLController = discussionFXMLController;
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(1000));
        TransitionDatabase.transitions.add(this);
    }

    @Override
    protected void interpolate(double v) {
        this.discussionFXMLController.handleResourceSending();
    }
}
