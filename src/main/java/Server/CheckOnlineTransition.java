package Server;

import Civilization.View.FXMLControllers.LeaderBoardFXMLController;
import javafx.animation.Transition;
import javafx.util.Duration;

import java.io.IOException;

public class CheckOnlineTransition extends Transition {
    private LeaderBoardFXMLController leaderBoardFXMLController;

    public CheckOnlineTransition(LeaderBoardFXMLController leaderBoardFXMLController) {
        this.leaderBoardFXMLController = leaderBoardFXMLController;
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(1000));
    }

    @Override
    protected void interpolate(double v) {
        try {
            this.leaderBoardFXMLController.setOnline();
            this.leaderBoardFXMLController.sortUsers(UserDatabase.getAllUsers());
            this.leaderBoardFXMLController.setScores();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
