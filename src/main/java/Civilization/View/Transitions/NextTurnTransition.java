package Civilization.View.Transitions;

import javafx.animation.Transition;

public class NextTurnTransition extends Transition {

    public NextTurnTransition() {
        TransitionDatabase.transitions.add(this);
    }

    @Override
    protected void interpolate(double v) {
        // TODO Rezaei
    }
}
