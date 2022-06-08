package Civilization.View.Transitions;

import Civilization.View.GraphicalBases;
import javafx.animation.Transition;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.control.Button;
import javafx.util.Duration;

import java.awt.*;

public class CursorTransition extends Transition {

    private final Button cursorSet;
    private final double buttonX;
    private final double buttonY;
    private double x;
    private double y;

    public CursorTransition(Button cursorSet, double buttonX, double buttonY) {
        this.cursorSet = cursorSet;
        this.buttonX = buttonX;
        this.buttonY = buttonY;
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(200));
    }

    @Override
    protected void interpolate(double v) {
        x = MouseInfo.getPointerInfo().getLocation().x;
        y = MouseInfo.getPointerInfo().getLocation().y;
        if(x > buttonX
                && x < buttonX + cursorSet.getWidth()
                && y > buttonY
                && y < buttonY + cursorSet.getHeight()
                && cursorSet.isDisable()) {
            GraphicalBases.scene.setCursor(new ImageCursor(GraphicalBases.CURSOR));
        } else {
            GraphicalBases.scene.setCursor(Cursor.DEFAULT);
        }
    }

}
