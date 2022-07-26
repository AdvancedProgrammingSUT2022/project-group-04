package Client.View.Transitions;

import Client.View.FXMLControllers.ChatRoomFXMLController;
import Client.View.FXMLControllers.GameMenuFXMLController;
import javafx.animation.Transition;
import javafx.util.Duration;


public class ChatroomChoosingTransition extends Transition {

    private ChatRoomFXMLController chatRoomFXMLController;

    public ChatroomChoosingTransition(ChatRoomFXMLController chatRoomFXMLController) {
        this.chatRoomFXMLController = chatRoomFXMLController;
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(1000));
        TransitionDatabase.transitions.add(this);
    }

    @Override
    protected void interpolate(double v) {
        chatRoomFXMLController.handleChoiceBox();
    }
}

