package Client.View.Transitions;

import Client.View.FXMLControllers.LoadingFXMLController;
import javafx.animation.Transition;
import javafx.util.Duration;

public class CheckStartGameTransition extends Transition {

    private LoadingFXMLController loadingFXMLController;

    public CheckStartGameTransition(LoadingFXMLController loadingFXMLController) {
        this.loadingFXMLController = loadingFXMLController;
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(1000));
    }

    @Override
    protected void interpolate(double v) {
        try{
            System.out.println("1");
            if(this.loadingFXMLController.isGettingUsersValid()) {
                System.out.println("2");
                this.loadingFXMLController.updateUsersInGame();
                System.out.println("3");
            }
            System.out.println("4");
            this.loadingFXMLController.shallGoIn();
            System.out.println("5");
        } catch (Exception e) {
            //e.printStackTrace();
            System.err.println("Connection lost");
        }
    }
}
