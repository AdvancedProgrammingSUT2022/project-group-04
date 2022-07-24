package Client.View.Transitions;

import Client.View.FXMLControllers.InvitationFXMLController;
import javafx.animation.Transition;
import javafx.util.Duration;

public class InvitationTransition extends Transition {

    private InvitationFXMLController invitationFXMLController;

    public InvitationTransition(InvitationFXMLController invitationFXMLController) {
        this.invitationFXMLController = invitationFXMLController;
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(1000));
    }

    @Override
    protected void interpolate(double v) {
        this.invitationFXMLController.updateInvitations();
    }
}
