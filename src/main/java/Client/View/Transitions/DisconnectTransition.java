package Client.View.Transitions;

import Server.GameDatabaseServer;
import Server.UserDatabase;
import javafx.animation.Transition;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDateTime;

public class DisconnectTransition extends Transition {

    private String username;
    private long time;

    public DisconnectTransition(String username) {
        this.username = username;
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(1000));
        this.time = LocalDateTime.now().getMinute();
    }

    @Override
    protected void interpolate(double v) {
        if(UserDatabase.isUserOnline(username)) {
            GameDatabaseServer.userConnected(this);
        } else if (this.time + 2 < LocalDateTime.now().getMinute()) {
            try {
                GameDatabaseServer.removeUser(this, this.username);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
