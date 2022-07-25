package Client.View.FXMLControllers;

import Civilization.Database.GameDatabase;
import Client.Client;
import Client.View.GraphicalBases;
import Client.View.Transitions.InvitationTransition;
import Server.User;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.json.JSONObject;

import java.io.IOException;

public class InvitationFXMLController {

    @FXML
    private AnchorPane mainAnchorPane;

    private Text noInvitations;
    private Text invitationFrom;
    private Text username;
    private Button accept;
    private Button deny;

    private InvitationTransition invitationTransition;

    @FXML
    public void initialize() {
        setBackground();
        setBackButton();
        setTexts();
        setButtons();
        updateInvitations();

        invitationTransition = new InvitationTransition(this);
        invitationTransition.play();
    }

    private void setButtons() {
        VBox vBox = new VBox();
        vBox.setLayoutX(100);
        vBox.setLayoutY(200);

        accept = new Button("Accept");
        accept.setStyle("-fx-background-color: #222c41;-fx-border-color: #555564; -fx-text-fill: white;-fx-border-width: 3;");
        accept.setPrefWidth(100);
        accept.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!username.getText().equals("")) {
                    try {
                        GameDatabase.setYou();
                        acceptInvitation(username.getText());
                        invitationTransition.pause();
                        GraphicalBases.changeMenu("Loading");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        vBox.getChildren().add(accept);

        deny = new Button("Deny");
        deny.setStyle("-fx-background-color: #222c41;-fx-border-color: #555564; -fx-text-fill: white;-fx-border-width: 3;");
        deny.setPrefWidth(100);
        deny.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!username.getText().equals("")) {
                    try {
                        denyInvitation(username.getText());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        vBox.getChildren().add(deny);

        mainAnchorPane.getChildren().add(vBox);
    }

    private void setTexts() {
        noInvitations = new Text("No Invitations Yet.\nPlease Wait.");
        noInvitations.setStyle("-fx-font-size: 60");
        noInvitations.setX(100);
        noInvitations.setY(100);
        mainAnchorPane.getChildren().add(noInvitations);

        invitationFrom = new Text("You Have an Invitation From");
        invitationFrom.setStyle("-fx-font-size: 30");
        invitationFrom.setX(100);
        invitationFrom.setY(100);
        mainAnchorPane.getChildren().add(invitationFrom);

        username = new Text("");
        username.setStyle("-fx-font-size: 60");
        username.setX(50);
        username.setY(150);
        mainAnchorPane.getChildren().add(username);
    }

    private void setBackButton() {
        Button button = new Button("BACK");
        button.setPrefWidth(200);
        button.setLayoutX(540);
        button.setLayoutY(650);
        button.setStyle("-fx-background-color: #222c41;-fx-border-color: #555564; -fx-text-fill: white;-fx-border-width: 3;");
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                invitationTransition.pause();
                GraphicalBases.userLoggedIn();
            }
        });
        mainAnchorPane.getChildren().add(button);
    }

    private void setBackground() {
        Rectangle rectangle = new Rectangle();
        rectangle.setX(0);
        rectangle.setY(0);
        rectangle.setWidth(1280);
        rectangle.setHeight(720);
        rectangle.setFill(new ImagePattern(GraphicalBases.INVITATION));
        mainAnchorPane.getChildren().add(rectangle);
    }

    public void updateInvitations() {
        try{
            if(haveInvitation()) {
                noInvitations.setVisible(false);
                invitationFrom.setVisible(true);
                username.setVisible(true);
                accept.setVisible(true);
                deny.setVisible(true);
                username.setText(getUser());
            } else {
                noInvitations.setVisible(true);
                invitationFrom.setVisible(false);
                username.setVisible(false);
                accept.setVisible(false);
                deny.setVisible(false);
                username.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getUser() throws IOException {
        JSONObject input = new JSONObject();
        input.put("menu type","invitation");
        input.put("action","getUserInvitation");
        input.put("username", User.loggedInUser.getUsername());
        Client.dataOutputStream1.writeUTF(input.toString());
        Client.dataOutputStream1.flush();
        String message = Client.dataInputStream1.readUTF();
        System.out.println(message);
        return message;
    }

    private boolean haveInvitation() throws IOException {
        JSONObject input = new JSONObject();
        input.put("menu type","invitation");
        input.put("action","haveNotAcceptedInvitation");
        input.put("username", User.loggedInUser.getUsername());
        Client.dataOutputStream1.writeUTF(input.toString());
        Client.dataOutputStream1.flush();
        Boolean message = Boolean.parseBoolean(Client.dataInputStream1.readUTF());
        System.out.println(message);
        return message;
    }

    private void acceptInvitation(String text) throws IOException {
        JSONObject input = new JSONObject();
        input.put("menu type","invitation");
        input.put("action","accept");
        input.put("username1", username.getText());
        input.put("username2", User.loggedInUser.getUsername());
        Client.dataOutputStream1.writeUTF(input.toString());
        Client.dataOutputStream1.flush();
    }

    private void denyInvitation(String text) throws IOException {
        JSONObject input = new JSONObject();
        input.put("menu type","invitation");
        input.put("action","deny");
        input.put("username1", username.getText());
        input.put("username2", User.loggedInUser.getUsername());
        Client.dataOutputStream1.writeUTF(input.toString());
        Client.dataOutputStream1.flush();
    }
}
