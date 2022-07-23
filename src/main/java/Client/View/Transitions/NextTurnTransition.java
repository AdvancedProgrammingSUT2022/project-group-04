package Client.View.Transitions;

import Civilization.Database.GameDatabase;
import Server.GameDatabaseServer;
import javafx.animation.Transition;
import javafx.util.Duration;
import Client.*;

import java.io.IOException;

public class NextTurnTransition extends Transition {

    public NextTurnTransition() {
        TransitionDatabase.transitions.add(this);
        this.setCycleDuration(Duration.millis(1000));
        this.setCycleCount(-1);
    }

    @Override
    protected void interpolate(double v) {
        try {
            GameDatabase.getMapFromServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
