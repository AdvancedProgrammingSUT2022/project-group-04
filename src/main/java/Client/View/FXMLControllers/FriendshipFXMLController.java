package Client.View.FXMLControllers;

import Client.Client;
import Client.View.GraphicalBases;
import Server.User;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;

public class FriendshipFXMLController {

    @FXML
    private AnchorPane mainAnchorPane;

    private Text text1;
    private Text text2;
    private Text text3;
    private Text text4;
    private Text text5;

    private Text profile;
    private Button button;

    private Text request1;
    private Text request2;
    private Button accept1;
    private Button accept2;
    private Button deny1;
    private Button deny2;

    @FXML
    public void initialize() {
        System.out.println("Hi");
        setBackground();
        System.out.println("Hi2");
        setBackButton();
        System.out.println("Hi3");
        setProfile();
        System.out.println("Hi4");
        setSearchEngine();
        System.out.println("Hi5");
        try{
            setRequests();
        } catch (Exception e) {

        }
    }

    private void setRequests() throws IOException {
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setLayoutX(1000);
        vBox.setLayoutY(450);

        request1 = new Text("");
        request1.setStyle("-fx-fill: white; -fx-font-size: 30");
        request1.setVisible(false);
        request2 = new Text("");
        request2.setStyle("-fx-fill: white; -fx-font-size: 30");
        request2.setVisible(false);
        accept1 = new Button("Accept");
        accept1.setStyle("-fx-background-color: #222c41;-fx-border-color: #555564; -fx-text-fill: white;-fx-border-width: 3;");
        accept1.setVisible(false);
        accept1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    acceptFriendship(request1.getText());
                    updateButtons();
                } catch (IOException e) {
                    //e.printStackTrace();
                    System.err.println("Connection lost");
                }
            }
        });
        accept2 = new Button("Accept");
        accept2.setStyle("-fx-background-color: #222c41;-fx-border-color: #555564; -fx-text-fill: white;-fx-border-width: 3;");
        accept2.setVisible(false);
        accept2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    acceptFriendship(request2.getText());
                    updateButtons();
                } catch (IOException e) {
                    //e.printStackTrace();
                    System.err.println("Connection lost");
                }
            }
        });
        deny1 = new Button("Deny");
        deny1.setStyle("-fx-background-color: #222c41;-fx-border-color: #555564; -fx-text-fill: white;-fx-border-width: 3;");
        deny1.setVisible(false);
        deny1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    denyFriendship(request1.getText());
                    updateButtons();
                } catch (IOException e) {
                    //e.printStackTrace();
                    System.err.println("Connection lost");
                }
            }
        });
        deny2 = new Button("Deny");
        deny2.setStyle("-fx-background-color: #222c41;-fx-border-color: #555564; -fx-text-fill: white;-fx-border-width: 3;");
        deny2.setVisible(false);
        deny2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    denyFriendship(request2.getText());
                    updateButtons();
                } catch (IOException e) {
                    //e.printStackTrace();
                    System.err.println("Connection lost");
                }
            }
        });

        vBox.getChildren().add(request1);
        vBox.getChildren().add(accept1);
        vBox.getChildren().add(deny1);
        vBox.getChildren().add(request2);
        vBox.getChildren().add(accept2);
        vBox.getChildren().add(deny2);

        mainAnchorPane.getChildren().add(vBox);

        updateButtons();
    }

    private void denyFriendship(String username) throws IOException {
        JSONObject input = new JSONObject();
        input.put("menu type","Friendship");
        input.put("action","deny");
        input.put("firstUsername", username);
        input.put("secondUsername", User.loggedInUser.getUsername());
        Client.dataOutputStream1.writeUTF(input.toString());
        Client.dataOutputStream1.flush();
    }

    private void acceptFriendship(String username) throws IOException {
        JSONObject input = new JSONObject();
        input.put("menu type","Friendship");
        input.put("action","accept");
        input.put("firstUsername", username);
        input.put("secondUsername", User.loggedInUser.getUsername());
        Client.dataOutputStream1.writeUTF(input.toString());
        Client.dataOutputStream1.flush();
    }

    private void setProfile() {
        profile = new Text("");
        profile.setX(800);
        profile.setY(50);
        profile.setStyle("-fx-fill: white; -fx-font-size: 40");
        mainAnchorPane.getChildren().add(profile);

        button = new Button("Friend");
        button.setLayoutX(1000);
        button.setLayoutY(300);
        button.setPrefWidth(100);
        button.setStyle("-fx-background-color: #222c41;-fx-border-color: #555564; -fx-text-fill: white;-fx-border-width: 3;");
        button.setVisible(false);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String username = findUsername(profile.getText());
                try {
                    sendFriendship(username);
                } catch (IOException e) {
                    //e.printStackTrace();
                    System.err.println("Connection lost");
                }
                button.setVisible(false);
            }
        });
        mainAnchorPane.getChildren().add(button);
    }

    private String findUsername(String text) {
        String[] splitText = text.split("\\s");
        System.out.println(splitText[0]);
        return splitText[0];
    }

    private void sendFriendship(String username) throws IOException {
        JSONObject input = new JSONObject();
        input.put("menu type","Friendship");
        input.put("action","friendship");
        input.put("firstUsername", User.loggedInUser.getUsername());
        input.put("secondUsername", username);
        Client.dataOutputStream1.writeUTF(input.toString());
        Client.dataOutputStream1.flush();
    }

    private void setSearchEngine() {
        TextField textField = new TextField();
        textField.setPromptText("Type a username");
        textField.setLayoutX(50);
        textField.setLayoutY(50);
        textField.setPrefWidth(400);

        VBox vBox = new VBox();
        vBox.setLayoutX(50);
        vBox.setLayoutY(80);
        vBox.setSpacing(10);
        text1 = new Text("");
        text1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!text1.getText().equals("")) {
                    try {
                        profile.setText(getUserInformation(text1.getText()));
                    } catch (IOException e) {
                        //e.printStackTrace();
                        System.err.println("Connection lost");
                    }
                    try {
                        if(isRequestingValid(text1.getText())) {
                            button.setVisible(true);
                        } else {
                            button.setVisible(false);
                        }
                    } catch (IOException e) {
                        //e.printStackTrace();
                        System.err.println("Connection lost");
                    }
                }
            }
        });
        text1.setStyle("-fx-fill: white; -fx-font-size: 40");
        vBox.getChildren().add(text1);
        text2 = new Text("");
        text2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!text2.getText().equals("")) {
                    try {
                        profile.setText(getUserInformation(text2.getText()));
                    } catch (IOException e) {
                        //e.printStackTrace();
                        System.err.println("Connection lost");
                    }
                    try {
                        if(isRequestingValid(text2.getText())) {
                            button.setVisible(true);
                        } else {
                            button.setVisible(false);
                        }
                    } catch (IOException e) {
                        //e.printStackTrace();
                        System.err.println("Connection lost");
                    }
                }
            }
        });
        text2.setStyle("-fx-fill: white; -fx-font-size: 40");
        vBox.getChildren().add(text2);
        text3 = new Text("");
        text3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!text3.getText().equals("")) {
                    try {
                        profile.setText(getUserInformation(text3.getText()));
                    } catch (IOException e) {
                        //e.printStackTrace();
                        System.err.println("Connection lost");
                    }
                    try {
                        if(isRequestingValid(text3.getText())) {
                            button.setVisible(true);
                        } else {
                            button.setVisible(false);
                        }
                    } catch (IOException e) {
                        //e.printStackTrace();
                        System.err.println("Connection lost");
                    }
                }
            }
        });
        text3.setStyle("-fx-fill: white; -fx-font-size: 40");
        vBox.getChildren().add(text3);
        text4 = new Text("");
        text4.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!text4.getText().equals("")) {
                    try {
                        profile.setText(getUserInformation(text4.getText()));
                    } catch (IOException e) {
                        //e.printStackTrace();
                        System.err.println("Connection lost");
                    }
                    try {
                        if(isRequestingValid(text4.getText())) {
                            button.setVisible(true);
                        } else {
                            button.setVisible(false);
                        }
                    } catch (IOException e) {
                        //e.printStackTrace();
                        System.err.println("Connection lost");
                    }
                }
            }
        });
        text4.setStyle("-fx-fill: white; -fx-font-size: 40");
        vBox.getChildren().add(text4);
        text5 = new Text("");
        text5.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!text5.getText().equals("")) {
                    try {
                        profile.setText(getUserInformation(text5.getText()));
                    } catch (IOException e) {
                        //e.printStackTrace();
                        System.err.println("Connection lost");
                    }
                    try {
                        if(isRequestingValid(text5.getText())) {
                            button.setVisible(true);
                        } else {
                            button.setVisible(false);
                        }
                    } catch (IOException e) {
                        //e.printStackTrace();
                        System.err.println("Connection lost");
                    }
                }
            }
        });
        text5.setStyle("-fx-fill: white; -fx-font-size: 40");
        vBox.getChildren().add(text5);


        textField.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String username = textField.getText();
                JSONObject input = new JSONObject();
                input.put("menu type","Friendship");
                input.put("action","getUsers");
                input.put("username", username);
                button.setVisible(false);
                try {
                    Client.dataOutputStream1.writeUTF(input.toString());
                } catch (IOException e) {
                    //e.printStackTrace();
                    System.err.println("Connection lost");
                }
                try {
                    Client.dataOutputStream1.flush();
                } catch (IOException e) {
                    //e.printStackTrace();
                    System.err.println("Connection lost");
                }
                try {
                    String usernames = Client.dataInputStream1.readUTF();
                    setTexts(usernames.split("\n"));
                } catch (IOException e) {
                    //e.printStackTrace();
                    System.err.println("Connection lost");
                }
            }
        });

        mainAnchorPane.getChildren().add(textField);
        mainAnchorPane.getChildren().add(vBox);
    }

    private boolean isRequestingValid(String username) throws IOException {
        JSONObject input = new JSONObject();
        input.put("menu type","Friendship");
        input.put("action","request");
        input.put("firstUsername", User.loggedInUser.getUsername());
        input.put("secondUsername", username);
        Client.dataOutputStream1.writeUTF(input.toString());
        Client.dataOutputStream1.flush();
        boolean result = Boolean.parseBoolean(Client.dataInputStream1.readUTF());
        System.out.println(result);
        return result;
    }

    private void setTexts(String[] split) {
        if(split.length > 0) {
            text1.setText(split[0]);
        } else {
            text1.setText("");
        }
        if(split.length > 1) {
            text2.setText(split[1]);
        } else {
            text2.setText("");
        }
        if(split.length > 2) {
            text3.setText(split[2]);
        } else {
            text3.setText("");
        }
        if(split.length > 3) {
            text4.setText(split[3]);
        } else {
            text4.setText("");
        }
        if(split.length > 4) {
            text5.setText(split[4]);
        } else {
            text5.setText("");
        }
    }

    private String getUserInformation(String text) throws IOException {
        if(text.equals("")) {
            return "";
        }
        JSONObject input = new JSONObject();
        input.put("menu type","Friendship");
        input.put("action","information");
        input.put("username", text);
        Client.dataOutputStream1.writeUTF(input.toString());
        Client.dataOutputStream1.flush();
        String message = Client.dataInputStream1.readUTF();
        System.out.println(message);
        return message;
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
                GraphicalBases.changeMenu("Leaderboard");
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
        rectangle.setFill(new ImagePattern(GraphicalBases.QUEEN_VICTORIA));
        mainAnchorPane.getChildren().add(rectangle);
    }

    private void updateButtons() throws IOException {
        JSONObject input = new JSONObject();
        input.put("menu type","Friendship");
        input.put("action","getRequests");
        input.put("username", User.loggedInUser.getUsername());
        Client.dataOutputStream1.writeUTF(input.toString());
        Client.dataOutputStream1.flush();
        String[] requests = Client.dataInputStream1.readUTF().split(" ");
        if(requests.length == 0 || requests[0].equals("")) {
            request1.setVisible(false);
            request2.setVisible(false);
            accept1.setVisible(false);
            accept2.setVisible(false);
            deny1.setVisible(false);
            deny2.setVisible(false);
        } else if (requests.length == 1f) {
            request1.setVisible(true);
            request1.setText(requests[0]);
            request2.setVisible(false);
            accept1.setVisible(true);
            accept2.setVisible(false);
            deny1.setVisible(true);
            deny2.setVisible(false);
        } else if (requests.length >= 2) {
            request1.setVisible(true);
            request1.setText(requests[0]);
            request2.setVisible(true);
            request2.setText(requests[1]);
            accept1.setVisible(true);
            accept2.setVisible(true);
            deny1.setVisible(true);
            deny2.setVisible(true);
        }
    }
}
