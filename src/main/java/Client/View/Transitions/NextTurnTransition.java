package Client.View.Transitions;

import Civilization.Database.GameDatabase;
import Client.View.FXMLControllers.GameFXMLController;
import Server.GameDatabaseServer;
import javafx.animation.Transition;
import javafx.util.Duration;
import Client.*;

import java.io.IOException;

public class NextTurnTransition extends Transition {

    GameFXMLController gameFXMLController;

    public NextTurnTransition(GameFXMLController gameFXMLController) {
        TransitionDatabase.transitions.add(this);
        this.gameFXMLController = gameFXMLController;
        this.setCycleDuration(Duration.millis(1000));
        this.setCycleCount(-1);
    }

    @Override
    protected void interpolate(double v) {
        try {
            System.out.println("are  are areare1");
            System.out.println(GameDatabase.mapTransferStarted);
            if (GameDatabase.mapTransferStarted) GameDatabase.getMapFromServer();
            System.out.println("are  are areare2");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
