package Client.View.Transitions;

import javafx.animation.Transition;

import java.util.ArrayList;

public class TransitionDatabase {
    public static ArrayList<Transition> transitions = new ArrayList<>();

    public static void restart() {
        for (Transition transition : transitions) {
            transition.pause();
        }
    }
}
