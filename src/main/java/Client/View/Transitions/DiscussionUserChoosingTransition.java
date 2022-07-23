package Client.View.Transitions;

import Client.View.FXMLControllers.DiscussionFXMLController;
import javafx.animation.Transition;
import javafx.util.Duration;

import java.io.IOException;

public class DiscussionUserChoosingTransition extends Transition {

    private DiscussionFXMLController discussionFXMLController;

    public DiscussionUserChoosingTransition(DiscussionFXMLController discussionFXMLController) {
        this.discussionFXMLController = discussionFXMLController;
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(1000));
        TransitionDatabase.transitions.add(this);
    }

    @Override
    protected void interpolate(double v) {
        try {
            discussionFXMLController.handleChoiceBox();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
