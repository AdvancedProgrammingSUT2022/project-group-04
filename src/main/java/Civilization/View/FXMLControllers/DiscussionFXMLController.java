package Civilization.View.FXMLControllers;

import Civilization.Database.GameDatabase;
import Civilization.Model.City;
import Civilization.Model.Civilization;
import Civilization.Model.Resources;
import Civilization.Model.User;
import Civilization.View.Cheater;
import Civilization.View.GraphicalBases;
import Civilization.View.Transitions.DiscussionResourceChoosingTransition;
import Civilization.View.Transitions.DiscussionUserChoosingTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DiscussionFXMLController {

    @FXML
    private AnchorPane mainAnchorPane;

    private String you;
    private String defaultText;
    private Civilization selected;
    private String resourceSelected;

    private ChoiceBox<String> usersChoiceBox;
    private VBox mainVBox;
    private HBox mainHBox;
    private HBox sendHBox;
    private TextArea messagePart;
    private TextField message;

    private VBox diplomacyVBox;
    private HBox goldHBox;
    private HBox scienceHBox;
    private HBox foodHBox;
    private HBox resourcesHBox;
    private ChoiceBox<String> resources;
    private Button sendResource;

    @FXML
    public void initialize() {
        you = GameDatabase.getCivilizationByTurn(GameDatabase.getTurn()).getNickname();
        defaultText = you + " Discussions:\n";
        setBackground();
        setListOfUsers();
        setMessagesPart();
        setSendPart();
        setDiplomacy();
    }

    private void setDiplomacy() {

        sendResource = new Button("SEND");
        sendResource.setDisable(true);

        diplomacyVBox = new VBox();
        diplomacyVBox.setSpacing(10);
        resourcesHBox = new HBox();
        resources = new ChoiceBox<>();
        setResourcesChoiceBox();
        setResourcesHBox();

        resourcesHBox.setVisible(false);


        Text text = new Text("Resources: ");
        text.setStyle("-fx-fill: white; -fx-font-size: 20");

        resourcesHBox.getChildren().add(text);

        resourcesHBox.getChildren().add(resources);
        resourcesHBox.getChildren().add(sendResource);
        resourcesHBox.setSpacing(10);

        setDiplomacyVBox();

        resourcesHBox.getChildren().add(diplomacyVBox);
        mainVBox.getChildren().add(resourcesHBox);
    }

    private void setDiplomacyVBox() {
        setGoldHBox();
        setScienceHBox();
        setFoodHBox();
    }

    private void setFoodHBox() {

        String cityWithMinFood;
        String cityWithMaxFood = null;

        long min = Integer.MAX_VALUE;
        long max = 0;

        for (City city : GameDatabase.getCivilizationByNickname(you).getCities()) {
            if(city.getFood() < min) {
                min = city.getFood();
                cityWithMinFood = city.getName();
            }
        }
        for (City city : GameDatabase.getCivilizationByNickname(you).getCities()) {
            if(city.getFood() > max) {
                min = city.getFood();
                cityWithMaxFood = city.getName();
            }
        }

        foodHBox = new HBox();
        foodHBox.setSpacing(10);

        Text text = new Text("Food: ");
        text.setStyle("-fx-fill: white; -fx-font-size: 20");

        TextField textField = new TextField();
        textField.setPromptText("Amount of foods sending to selected civilization");

        Button button = new Button("OK");
        button.setStyle("-fx-background-color: #222c41;-fx-border-color: #555564; -fx-text-fill: white;-fx-border-width: 3;");
        button.setDisable(true);
        long finalMin = min;
        String finalCityWithMaxFood = cityWithMaxFood;
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String text = textField.getText();
                Pattern pattern = Pattern.compile("\\d+");
                Matcher matcher = pattern.matcher(text);
                if(!matcher.matches()) {
                    textField.setText("");
                    textField.setStyle("-fx-border-color: red");
                } else {
                    int number = Integer.parseInt(text);
                    if(number <= 1 || number > finalMin) {
                        textField.setText("");
                        textField.setStyle("-fx-border-color: red");
                    } else {
                        sendMessage(number + " Foods from " + you);
                        GameDatabase.getCityByName(finalCityWithMaxFood).setLeftoverFood(GameDatabase.getCityByName(finalCityWithMaxFood).getFood() - number);
                        for (City city : selected.getCities()) {
                            city.setLeftoverFood(city.getFood() + number);
                            break;
                        }
                        textField.setText("");
                        button.setDisable(true);
                    }
                }
            }
        });

        textField.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String text = textField.getText();
                button.setDisable(text.length() == 0);
                textField.setStyle("-fx-border-color: null");
                Pattern pattern = Pattern.compile("\\d+");
                Matcher matcher = pattern.matcher(text);
                if(!matcher.matches()) {
                    textField.setText("");
                }
            }
        });


        foodHBox.getChildren().add(text);
        foodHBox.getChildren().add(textField);
        foodHBox.getChildren().add(button);
        diplomacyVBox.getChildren().add(foodHBox);
    }

    private void setScienceHBox() {
        scienceHBox = new HBox();
        scienceHBox.setSpacing(10);

        Text text = new Text("Science: ");
        text.setStyle("-fx-fill: white; -fx-font-size: 20");

        TextField textField = new TextField();
        textField.setPromptText("Amount of science sending to selected civilization");

        Button button = new Button("OK");
        button.setStyle("-fx-background-color: #222c41;-fx-border-color: #555564; -fx-text-fill: white;-fx-border-width: 3;");
        button.setDisable(true);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String text = textField.getText();
                Pattern pattern = Pattern.compile("\\d+");
                Matcher matcher = pattern.matcher(text);
                if(!matcher.matches()) {
                    textField.setText("");
                    textField.setStyle("-fx-border-color: red");
                } else {
                    int number = Integer.parseInt(text);
                    if(number <= 1 || number > GameDatabase.getCivilizationByNickname(you).getScience()) {
                        textField.setText("");
                        textField.setStyle("-fx-border-color: red");
                    } else {
                        sendMessage(number + " Science from " + you);
                        Cheater cheater = new Cheater(GameDatabase.getCivilizationIndex(selected.getNickname()));
                        cheater.run("add science " + number);
                        GameDatabase.getCivilizationByNickname(you).setScience(GameDatabase.getCivilizationByNickname(you).getScience() - number);
                        textField.setText("");
                        button.setDisable(true);
                    }
                }
            }
        });

        textField.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String text = textField.getText();
                button.setDisable(text.length() == 0);
                textField.setStyle("-fx-border-color: null");
                Pattern pattern = Pattern.compile("\\d+");
                Matcher matcher = pattern.matcher(text);
                if(!matcher.matches()) {
                    textField.setText("");
                }
            }
        });


        scienceHBox.getChildren().add(text);
        scienceHBox.getChildren().add(textField);
        scienceHBox.getChildren().add(button);
        diplomacyVBox.getChildren().add(scienceHBox);
    }

    private void setGoldHBox() {
        goldHBox = new HBox();
        goldHBox.setSpacing(10);

        Text text = new Text("Gold: ");
        text.setStyle("-fx-fill: white; -fx-font-size: 20");

        TextField textField = new TextField();
        textField.setPromptText("Amount of golds sending to selected civilization");

        Button button = new Button("OK");
        button.setStyle("-fx-background-color: #222c41;-fx-border-color: #555564; -fx-text-fill: white;-fx-border-width: 3;");
        button.setDisable(true);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String text = textField.getText();
                Pattern pattern = Pattern.compile("\\d+");
                Matcher matcher = pattern.matcher(text);
                if(!matcher.matches()) {
                    textField.setText("");
                    textField.setStyle("-fx-border-color: red");
                } else {
                    int number = Integer.parseInt(text);
                    if(number <= 1 || number > GameDatabase.getCivilizationByNickname(you).getGold()) {
                        textField.setText("");
                        textField.setStyle("-fx-border-color: red");
                    } else {
                        sendMessage(number + " Golds from " + you);
                        Cheater cheater = new Cheater(GameDatabase.getCivilizationIndex(selected.getNickname()));
                        cheater.run("gold increase " + number);
                        GameDatabase.getCivilizationByNickname(you).setGold(GameDatabase.getCivilizationByNickname(you).getGold() - number);
                        textField.setText("");
                        button.setDisable(true);
                    }
                }
            }
        });

        textField.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String text = textField.getText();
                button.setDisable(text.length() == 0);
                textField.setStyle("-fx-border-color: null");
                Pattern pattern = Pattern.compile("\\d+");
                Matcher matcher = pattern.matcher(text);
                if(!matcher.matches()) {
                    textField.setText("");
                }
            }
        });


        goldHBox.getChildren().add(text);
        goldHBox.getChildren().add(textField);
        goldHBox.getChildren().add(button);
        diplomacyVBox.getChildren().add(goldHBox);
    }

    private void setResourcesHBox() {
        sendResource.setStyle("-fx-background-color: #222c41;-fx-border-color: #555564; -fx-text-fill: white;-fx-border-width: 3;");
        sendResource.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                sendMessage(you + " sent resource " + resourceSelected);
            }
        });
    }

    private void setResourcesChoiceBox() {

        DiscussionResourceChoosingTransition discussionResourceChoosingTransition = new DiscussionResourceChoosingTransition(this);
        discussionResourceChoosingTransition.play();

        ArrayList<String> validResources = new ArrayList<>();
        for (City city : GameDatabase.getCivilizationByNickname(you).getCities()) {
            for (Resources discoveredResource : city.getDiscoveredResources()) {
                if(!discoveredResource.getType().equals("bonus") && !validResources.contains(discoveredResource.getName())) {
                    validResources.add(discoveredResource.getName());
                }
            }
        }
        ObservableList<String> usersInput = FXCollections.observableArrayList(validResources);
        resources.setItems(usersInput);
    }

    public HBox getSendHBox() {
        return sendHBox;
    }

    private void setSendPart() {
        sendHBox = new HBox();
        message = new TextField();
        message.setPromptText("Write your Message: ");
        message.setPrefWidth(650);

        sendHBox.setSpacing(10);
        sendHBox.getChildren().add(message);

        Button button = new Button("SEND");
        button.setStyle("-fx-background-color: #222c41;-fx-border-color: #555564; -fx-text-fill: white;-fx-border-width: 3;");
        button.setDisable(true);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                sendMessage(message.getText());
                message.setText("");
            }
        });

        sendHBox.getChildren().add(button);

        message.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                button.setDisable(message.getText().isEmpty());
            }
        });

        mainVBox.getChildren().add(sendHBox);
    }

    private void sendMessage(String text) {
        String messageToYou = "Message to " + selected.getNickname() + ":\n\t" + text;
        GameDatabase.getCivilizationByNickname(you).getMessages().add(messageToYou);

        String messageToCivilization = "Message from " + you + ":\n\t" + text;
        selected.getMessages().add(messageToCivilization);

        writeMessages();
    }

    private void setMessagesPart() {
        messagePart = new TextArea();
        messagePart.setPrefWidth(300);
        messagePart.setPrefHeight(300);
        messagePart.setText(defaultText);
        messagePart.setEditable(false);
        writeMessages();
        mainVBox.getChildren().add(messagePart);
    }

    private void writeMessages() {
        if(GameDatabase.getCivilizationByNickname(you).getMessages().size() == 1) {
            messagePart.setText(defaultText + GameDatabase.getCivilizationByNickname(you).getMessages().get(0) + "\nNo Messages Yet.");
        } else {
            String text = defaultText;
            for (String message : GameDatabase.getCivilizationByNickname(you).getMessages()) {
                text += message + "\n";
            }
            messagePart.setText(text);
        }
    }

    private void setListOfUsers() {
        mainVBox = new VBox();
        mainHBox = new HBox();

        mainHBox.setSpacing(10);
        mainVBox.setSpacing(10);

        mainVBox.setLayoutX(50);
        mainVBox.setLayoutY(50);

        Text text = new Text("Discussion And Diplomacy Panel");
        text.setStyle("-fx-fill: white; -fx-font-size: 30");

        mainHBox.getChildren().add(text);

        setBackButton();

        usersChoiceBox = new ChoiceBox<>();
        usersChoiceBox.setPrefWidth(100);
        setUsersChoiceBox();

        mainHBox.getChildren().add(usersChoiceBox);

        mainVBox.getChildren().add(mainHBox);
        mainAnchorPane.getChildren().add(mainVBox);

    }

    private void setUsersChoiceBox() {

        DiscussionUserChoosingTransition discussionUserChoosingTransition = new DiscussionUserChoosingTransition(this);
        discussionUserChoosingTransition.play();

        ArrayList<String> users = new ArrayList<>();
        for (Civilization civilization : GameDatabase.players) {
            if(civilization.getNickname().equals(you)) {
                continue;
            }
            users.add(civilization.getNickname());
        }
        ObservableList<String> usersInput = FXCollections.observableArrayList(users);
        usersChoiceBox.setItems(usersInput);

    }

    private void setBackground() {
        Rectangle black = new Rectangle();
        black.setHeight(720);
        black.setWidth(1280);
        black.setX(0);
        black.setY(0);
        black.setFill(new ImagePattern(GraphicalBases.BLACK));
        mainAnchorPane.getChildren().add(black);

        Rectangle ezekiel = new Rectangle();
        ezekiel.setWidth(500);
        ezekiel.setHeight(720);
        ezekiel.setX(1280 - ezekiel.getWidth());
        ezekiel.setY(0);
        ezekiel.setFill(new ImagePattern(GraphicalBases.EZEKIEL));
        mainAnchorPane.getChildren().add(ezekiel);
    }

    private void setBackButton() {
        Button backButton = new Button("BACK");
        backButton.setPrefWidth(100);
        backButton.setStyle("-fx-background-color: #222c41;-fx-border-color: #555564; -fx-text-fill: white;-fx-border-width: 3;");
        backButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GraphicalBases.enterGame("Game");
            }
        });
        mainHBox.getChildren().add(backButton);
    }

    public void handleChoiceBox() {
        if(usersChoiceBox.getValue() != null) {
            String nickname = usersChoiceBox.getValue();
            selected = GameDatabase.getCivilizationByNickname(nickname);

            foodHBox.setVisible(GameDatabase.getCivilizationByNickname(you).getCities().size() != 0
                    && selected.getCities().size() != 0);

            sendHBox.setVisible(true);
            resourcesHBox.setVisible(true);
        } else {
            sendHBox.setVisible(false);
            resourcesHBox.setVisible(false);
        }
    }

    public void handleResourceSending() {
        if(resources.getValue() != null) {
            resourceSelected = resources.getValue();
            sendResource.setDisable(false);
        } else {
            sendResource.setDisable(true);
        }
    }
}
