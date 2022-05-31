package Civilization.View.Transitions;

import Civilization.View.GraphicalBases;
import javafx.animation.Transition;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.awt.*;

public class CursorTransition extends Transition {

    private final Button cursorSet;
    private double buttonX;
    private double buttonY;
    private double x;
    private double y;

    public CursorTransition(Button cursorSet, double buttonX, double buttonY) {
        this.cursorSet = cursorSet;
        this.buttonX = buttonX;
        this.buttonY = buttonY;
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(690));
    }

    @Override
    protected void interpolate(double v) {
        x = MouseInfo.getPointerInfo().getLocation().x;
        y = MouseInfo.getPointerInfo().getLocation().y;
        if(x > buttonX
                && x < buttonX + cursorSet.getWidth() + 10
                && y > buttonY
                && y < buttonY + cursorSet.getHeight() + 10
                && cursorSet.isDisable()) {
            GraphicalBases.scene.setCursor(new ImageCursor(GraphicalBases.CURSOR));
        } else {
            GraphicalBases.scene.setCursor(Cursor.DEFAULT);
        }
    }

}
