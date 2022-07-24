package Client.View.Transitions;

import Client.View.GraphicalBases;
import javafx.animation.Transition;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.control.Button;
import javafx.util.Duration;

import java.awt.*;
import java.util.ArrayList;

public class CursorTransition extends Transition {

    private ArrayList<Button> cursorSets;
    private double[] buttonXs;
    private double[] buttonYs;
    private double x;
    private double y;

    public CursorTransition(Button cursorSet, double buttonX, double buttonY) {
        this.cursorSets = new ArrayList<>();
        cursorSets.add(cursorSet);
        this.buttonXs = new double[1];
        buttonXs[0] = buttonX;
        this.buttonYs = new double[1];
        buttonYs[0] = buttonY;
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(200));
    }

    public CursorTransition(ArrayList<Button> cursorSets, double[] buttonXs, double[] buttonYs) {
        this.cursorSets = cursorSets;
        setButtons(buttonXs, buttonYs);
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(200));
    }

    private void setButtons(double[] buttonXs, double[] buttonYs) {
        this.buttonXs = buttonXs;
        this.buttonYs = buttonYs;
    }

    @Override
    protected void interpolate(double v) {
        x = MouseInfo.getPointerInfo().getLocation().x;
        y = MouseInfo.getPointerInfo().getLocation().y;
        boolean defaultCursor = true;
        int index = 0;
        for (Button cursorSet : cursorSets) {
            double buttonX = buttonXs[index];
            double buttonY = buttonYs[index];
            if(x > buttonX
                    && x < buttonX + cursorSet.getWidth()
                    && y > buttonY
                    && y < buttonY + cursorSet.getHeight()
                    && cursorSet.isDisable()
                    && cursorSet.isVisible()) {
                GraphicalBases.scene.setCursor(new ImageCursor(GraphicalBases.CURSOR));
                defaultCursor = false;
            }
            index++;
        }
        if(defaultCursor) {
            GraphicalBases.scene.setCursor(Cursor.DEFAULT);
        }
    }

}
