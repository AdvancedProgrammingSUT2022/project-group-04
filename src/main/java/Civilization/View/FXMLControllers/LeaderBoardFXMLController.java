package Civilization.View.FXMLControllers;

import Client.Client;
import Server.CheckOnlineTransition;
import Server.UserDatabase;
import Civilization.Model.GameModel;
import Server.User;
import Civilization.View.GraphicalBases;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;


import Civilization.View.Components.Account;
import Civilization.View.GraphicalBases;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class LeaderBoardFXMLController {

    @FXML
    Circle player1Avatar;
    @FXML
    Circle player2Avatar;
    @FXML
    Circle player3Avatar;
    @FXML
    Circle player4Avatar;
    @FXML
    Circle player5Avatar;
    @FXML
    Circle player6Avatar;
    @FXML
    Circle player7Avatar;
    @FXML
    Circle player8Avatar;
    @FXML
    Circle player9Avatar;
    @FXML
    Circle player10Avatar;


    @FXML
    Label player1Score;
    @FXML
    Label player2Score;
    @FXML
    Label player3Score;
    @FXML
    Label player4Score;
    @FXML
    Label player5Score;
    @FXML
    Label player6Score;
    @FXML
    Label player7Score;
    @FXML
    Label player8Score;
    @FXML
    Label player9Score;
    @FXML
    Label player10Score;

    @FXML
    Text player1Name;
    @FXML
    Text player2Name;
    @FXML
    Text player3Name;
    @FXML
    Text player4Name;
    @FXML
    Text player5Name;
    @FXML
    Text player6Name;
    @FXML
    Text player7Name;
    @FXML
    Text player8Name;
    @FXML
    Text player9Name;
    @FXML
    Text player10Name;


    @FXML
    Text Player1LoginTime;
    @FXML
    Text Player2LoginTime;
    @FXML
    Text Player3LoginTime;
    @FXML
    Text Player4LoginTime;
    @FXML
    Text Player5LoginTime;
    @FXML
    Text Player6LoginTime;
    @FXML
    Text Player7LoginTime;
    @FXML
    Text Player8LoginTime;
    @FXML
    Text Player9LoginTime;
    @FXML
    Text Player10LoginTime;



    private ArrayList<User> users;
    private CheckOnlineTransition checkOnlineTransition;


    @FXML
    public void initialize() {
        GameModel.isGame = false;
        users = sortUsers((ArrayList<User>) UserDatabase.getAllUsers());
        try{
            setScores();
            setOnline();
            checkOnlineTransition = new CheckOnlineTransition(this);
            checkOnlineTransition.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setOnline() throws IOException {
        if(users.size() > 0) {
            if(isUserOnline(users.get(0))) {
                player1Avatar.setStroke(Color.GREEN);
            } else {
                player1Avatar.setStroke(Color.RED);
            }
        }
        if(users.size() > 1) {
            if(isUserOnline(users.get(1))) {
                player2Avatar.setStroke(Color.GREEN);
            } else {
                player2Avatar.setStroke(Color.RED);
            }
        }
        if(users.size() > 2) {
            if(isUserOnline(users.get(2))) {
                player3Avatar.setStroke(Color.GREEN);
            } else {
                player3Avatar.setStroke(Color.RED);
            }
        }
        if(users.size() > 3) {
            if(isUserOnline(users.get(3))) {
                player4Avatar.setStroke(Color.GREEN);
            } else {
                player4Avatar.setStroke(Color.RED);
            }
        }
        if(users.size() > 4) {
            if(isUserOnline(users.get(4))) {
                player5Avatar.setStroke(Color.GREEN);
            } else {
                player5Avatar.setStroke(Color.RED);
            }
        }
        if(users.size() > 5) {
            if(isUserOnline(users.get(5))) {
                player6Avatar.setStroke(Color.GREEN);
            } else {
                player6Avatar.setStroke(Color.RED);
            }
        }
        if(users.size() > 6) {
            if(isUserOnline(users.get(6))) {
                player7Avatar.setStroke(Color.GREEN);
            } else {
                player7Avatar.setStroke(Color.RED);
            }
        }
        if(users.size() > 7) {
            if(isUserOnline(users.get(7))) {
                player8Avatar.setStroke(Color.GREEN);
            } else {
                player8Avatar.setStroke(Color.RED);
            }
        }
        if(users.size() > 8) {
            if(isUserOnline(users.get(8))) {
                player9Avatar.setStroke(Color.GREEN);
            } else {
                player9Avatar.setStroke(Color.RED);
            }
        }
        if(users.size() > 9) {
            if(isUserOnline(users.get(9))) {
                player10Avatar.setStroke(Color.GREEN);
            } else {
                player10Avatar.setStroke(Color.RED);
            }
        }
    }


    public void setScores() throws IOException {

        if (users.size() > 0) {
            player1Avatar.setFill(new ImagePattern(Account.getAvatarImage(users.get(0))));
            player1Score.setText(getUserScore(users.get(0)) + "");
            player1Name.setText(users.get(0).getNickname());
            Player1LoginTime.setText(getUserLastLoginTime(users.get(0)));
            if (User.loggedInUser.getUsername().equals(users.get(0).getUsername())){
                player1Name.setStyle("-fx-fill: yellow; -fx-text-fill: yellow");
            }
        }
        else {
            player1Avatar.setVisible(false);
            player1Score.setText("");
            player1Name.setText("");
            Player1LoginTime.setText("");
        }
        if (users.size() > 1) {
            player2Avatar.setFill(new ImagePattern(Account.getAvatarImage(users.get(1))));
            player2Score.setText(getUserScore(users.get(1)) + "");
            player2Name.setText(users.get(1).getNickname());
            Player2LoginTime.setText(getUserLastLoginTime(users.get(1)));
            if (User.loggedInUser.getUsername().equals(users.get(1).getUsername())){
                player2Name.setStyle("-fx-fill: yellow; -fx-text-fill: yellow");
            }
        }
        else {
            player2Avatar.setVisible(false);
            player2Score.setText("");
            player2Name.setText("");
            Player2LoginTime.setText("");
        }
        if (users.size() > 2) {
            player3Avatar.setFill(new ImagePattern(Account.getAvatarImage(users.get(2))));
            player3Score.setText(getUserScore(users.get(2)) + "");
            player3Name.setText(users.get(2).getNickname());
            Player3LoginTime.setText(getUserLastLoginTime(users.get(2)));
            if (User.loggedInUser.getUsername().equals(users.get(2).getUsername())){
                player3Name.setStyle("-fx-fill: yellow; -fx-text-fill: yellow");
            }

        }
        else {
            player3Avatar.setVisible(false);
            player3Score.setText("");
            player3Name.setText("");
            Player3LoginTime.setText("");
        }
        if (users.size() > 3) {
            player4Avatar.setFill(new ImagePattern(Account.getAvatarImage(users.get(3))));
            player4Score.setText(getUserScore(users.get(3)) + "");
            player4Name.setText(users.get(3).getNickname());
            Player4LoginTime.setText(getUserLastLoginTime(users.get(3)));
            if (User.loggedInUser.getUsername().equals(users.get(3).getUsername())){
                player4Name.setStyle("-fx-fill: yellow; -fx-text-fill: yellow");
            }

        }
        else {
            player4Avatar.setVisible(false);
            player4Score.setText("");
            player4Name.setText("");
            Player4LoginTime.setText("");
        }
        if (users.size() > 4) {
            player5Avatar.setFill(new ImagePattern(Account.getAvatarImage(users.get(4))));
            player5Score.setText(getUserScore(users.get(4)) + "");
            player5Name.setText(users.get(4).getNickname());
            Player5LoginTime.setText(getUserLastLoginTime(users.get(4)));
            if (User.loggedInUser.getUsername().equals(users.get(4).getUsername())){
                player5Name.setStyle("-fx-fill: yellow; -fx-text-fill: yellow");
            }

        }
        else {
            player5Avatar.setVisible(false);
            player5Score.setText("");
            player5Name.setText("");
            Player5LoginTime.setText("");
        }
        if (users.size() > 5) {
            player6Avatar.setFill(new ImagePattern(Account.getAvatarImage(users.get(5))));
            player6Score.setText(getUserScore(users.get(5)) + "");
            player6Name.setText(users.get(5).getNickname());
            Player6LoginTime.setText(getUserLastLoginTime(users.get(5)));
            if (User.loggedInUser.getUsername().equals(users.get(5).getUsername())){
                player6Name.setStyle("-fx-fill: yellow; -fx-text-fill: yellow");
            }

        }
        else {
            player6Avatar.setVisible(false);
            player6Score.setText("");
            player6Name.setText("");
            Player6LoginTime.setText("");
        }
        if (users.size() > 6) {
            player7Avatar.setFill(new ImagePattern(Account.getAvatarImage(users.get(6))));
            player7Score.setText(getUserScore(users.get(6)) + "");
            player7Name.setText(users.get(6).getNickname());
            Player7LoginTime.setText(getUserLastLoginTime(users.get(6)));
            if (User.loggedInUser.getUsername().equals(users.get(6).getUsername())){
                player7Name.setStyle("-fx-fill: yellow; -fx-text-fill: yellow");
            }

        }
        else {
            player7Avatar.setVisible(false);
            player7Score.setText("");
            player7Name.setText("");
            Player7LoginTime.setText("");
        }
        if (users.size() > 7) {
            player8Avatar.setFill(new ImagePattern(Account.getAvatarImage(users.get(7))));
            player8Name.setText(users.get(7).getNickname());
            player8Score.setText(getUserScore(users.get(7)) + "");
            Player8LoginTime.setText(getUserLastLoginTime(users.get(7)));
            if (User.loggedInUser.getUsername().equals(users.get(7).getUsername())){
                player8Name.setStyle("-fx-fill: yellow; -fx-text-fill: yellow");
            }

        }
        else {
            player8Avatar.setVisible(false);
            player8Score.setText("");
            player8Name.setText("");
            Player8LoginTime.setText("");
        }
        if (users.size() > 8) {
            player9Avatar.setFill(new ImagePattern(Account.getAvatarImage(users.get(8))));
            player9Score.setText(getUserScore(users.get(8)) + "");
            player9Name.setText(users.get(8).getNickname());
            Player9LoginTime.setText(getUserLastLoginTime(users.get(8)));
            if (User.loggedInUser.getUsername().equals(users.get(8).getUsername())){
                player9Name.setStyle("-fx-fill: yellow; -fx-text-fill: yellow");
            }

        }
        else {
            player9Avatar.setVisible(false);
            player9Score.setText("");
            player9Name.setText("");
            Player9LoginTime.setText("");
        }
        if (users.size() > 9) {
            player10Avatar.setFill(new ImagePattern(Account.getAvatarImage(users.get(9))));
            player10Score.setText(getUserScore(users.get(9)) + "");
            player10Name.setText(users.get(9).getNickname());
            Player10LoginTime.setText(getUserLastLoginTime(users.get(9)));
            if (User.loggedInUser.getUsername().equals(users.get(9).getUsername())){
                player10Name.setStyle("-fx-fill: yellow; -fx-text-fill: yellow");
            }
        }
        else {
            player10Avatar.setVisible(false);
            player10Score.setText("");
            player10Name.setText("");
            Player10LoginTime.setText("");
        }




    }


    public ArrayList<User> sortUsers(ArrayList<User> temp){
        ArrayList<User> users = new ArrayList<>(temp);
        ArrayList<User> sortedUser = new ArrayList<>();
        while (users.size() != 0) {
            User user = getMaxUser(users);
            sortedUser.add(user);
            removeMaxUser(user, users);
            //System.out.println(users.size());
        }
        return sortedUser;
    }

    private void removeMaxUser(User maxUser, ArrayList<User> users) {
        if(maxUser == null) {
            return;
        }
        int index = 0;
        for (User user : users) {
            if(user.getUsername().equals(maxUser.getUsername())) {
                users.remove(index);
                break;
            }
            index++;
        }
    }

    private User getMaxUser(ArrayList<User> users) {
        int maxScore = 0;
        String lastLoginTime = LocalDateTime.MAX.format(DateTimeFormatter.ofPattern("E, MMM dd yyyy HH:mm:ss"));
        if(users.size() == 0) {
            return null;
        }
        User maxUser = users.get(0);
        for (User user : users) {
            if(user.getScore() > maxScore) {
                maxScore = user.getScore();
                maxUser = user;
            }
            if(user.getScore() == maxScore) {
                if(lastLoginTime.compareTo(user.getLastLoginTime()) > 0
                        && !user.getLastLoginTime().equals(LocalDateTime.MAX.format(DateTimeFormatter.ofPattern("E, MMM dd yyyy HH:mm:ss")))) {
                    lastLoginTime = user.getLastLoginTime();
                    maxUser = user;
                }
            }
        }
        return maxUser;
    }

    public void backToMain(){
        checkOnlineTransition.pause();
        GraphicalBases.changeMenu("MainMenu");
    }

    public boolean isUserOnline(User user) throws IOException {
        JSONObject input = new JSONObject();
        input.put("menu type","Leaderboard");
        input.put("action","isOnline");
        input.put("username", user.getUsername());
        Client.dataOutputStream1.writeUTF(input.toString());
        Client.dataOutputStream1.flush();
        String message = Client.dataInputStream1.readUTF();
        return Boolean.parseBoolean(message);
    }

    private int getUserScore(User user) throws IOException {
        JSONObject input = new JSONObject();
        input.put("menu type","Leaderboard");
        input.put("action","getScore");
        input.put("username", user.getUsername());
        Client.dataOutputStream1.writeUTF(input.toString());
        Client.dataOutputStream1.flush();
        String message = Client.dataInputStream1.readUTF();
        return Integer.parseInt(message);
    }


    private String getUserLastLoginTime(User user) throws IOException {
        JSONObject input = new JSONObject();
        input.put("menu type","Leaderboard");
        input.put("action","getLastLoginTime");
        input.put("username", user.getUsername());
        Client.dataOutputStream1.writeUTF(input.toString());
        Client.dataOutputStream1.flush();
        String message = Client.dataInputStream1.readUTF();
        return message;
    }



}
