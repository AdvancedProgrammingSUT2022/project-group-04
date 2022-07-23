package Client.View.FXMLControllers;

import Client.Client;
import Client.View.Components.Account;
import Server.CheckOnlineTransition;
import Server.UserDatabase;
import Civilization.Model.GameModel;
import Server.User;
import Client.View.GraphicalBases;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;


import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class LeaderBoardFXMLController {

    @FXML
    private Button player1Button;
    @FXML
    private Button player2Button;
    @FXML
    private Button player3Button;
    @FXML
    private Button player4Button;
    @FXML
    private Button player5Button;
    @FXML
    private Button player6Button;
    @FXML
    private Button player7Button;
    @FXML
    private Button player8Button;
    @FXML
    private Button player9Button;
    @FXML
    private Button player10Button;

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
            setButtons();
            setOnline();
            checkOnlineTransition = new CheckOnlineTransition(this);
            checkOnlineTransition.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setButtons() {
        if(users.size() > 0) {
            if(users.get(0).getUsername().equals(User.loggedInUser.getUsername())) {
                player1Button.setVisible(false);
            } else {
                player1Button.setVisible(true);
            }
        } else {
            player1Button.setVisible(false);
        }
        if(users.size() > 1) {
            if(users.get(1).getUsername().equals(User.loggedInUser.getUsername())) {
                player2Button.setVisible(false);
            } else {
                player2Button.setVisible(true);
            }
        } else {
            player2Button.setVisible(false);
        }
        if(users.size() > 2) {
            if(users.get(2).getUsername().equals(User.loggedInUser.getUsername())) {
                player3Button.setVisible(false);
            } else {
                player3Button.setVisible(true);
            }        } else {
            player3Button.setVisible(false);
        }
        if(users.size() > 3) {
            if(users.get(3).getUsername().equals(User.loggedInUser.getUsername())) {
                player4Button.setVisible(false);
            } else {
                player4Button.setVisible(true);
            }        } else {
            player4Button.setVisible(false);
        }
        if(users.size() > 4) {
            if(users.get(4).getUsername().equals(User.loggedInUser.getUsername())) {
                player5Button.setVisible(false);
            } else {
                player5Button.setVisible(true);
            }        } else {
            player5Button.setVisible(false);
        }
        if(users.size() > 5) {
            if(users.get(5).getUsername().equals(User.loggedInUser.getUsername())) {
                player6Button.setVisible(false);
            } else {
                player6Button.setVisible(true);
            }
        } else {
            player6Button.setVisible(false);
        }
        if(users.size() > 6) {
            if(users.get(6).getUsername().equals(User.loggedInUser.getUsername())) {
                player7Button.setVisible(false);
            } else {
                player7Button.setVisible(true);
            }
        } else {
            player7Button.setVisible(false);
        }
        if(users.size() > 7) {
            if(users.get(7).getUsername().equals(User.loggedInUser.getUsername())) {
                player8Button.setVisible(false);
            } else {
                player8Button.setVisible(true);
            }        } else {
            player8Button.setVisible(false);
        }
        if(users.size() > 8) {
            if(users.get(8).getUsername().equals(User.loggedInUser.getUsername())) {
                player9Button.setVisible(false);
            } else {
                player9Button.setVisible(true);
            }        } else {
            player9Button.setVisible(false);
        }
        if(users.size() > 9) {
            if(users.get(9).getUsername().equals(User.loggedInUser.getUsername())) {
                player10Button.setVisible(false);
            } else {
                player10Button.setVisible(true);
            }        } else {
            player10Button.setVisible(false);
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


    public void sendFriendship1(MouseEvent mouseEvent) {
        String myUsername = User.loggedInUser.getUsername();
        String secondUsername = users.get(0).getUsername();
        try{
            sendFriendship(myUsername, secondUsername);
        } catch (Exception e) {

        }
    }

    private void sendFriendship(String firstUsername, String secondUsername) throws IOException {
        JSONObject input = new JSONObject();
        input.put("menu type","Leaderboard");
        input.put("action","friendship");
        input.put("firstUsername", firstUsername);
        input.put("secondUsername", secondUsername);
        Client.dataOutputStream1.writeUTF(input.toString());
        Client.dataOutputStream1.flush();
    }

    public void sendFriendship2(MouseEvent mouseEvent) {
        String myUsername = User.loggedInUser.getUsername();
        String secondUsername = users.get(1).getUsername();
        try{
            sendFriendship(myUsername, secondUsername);
        } catch (Exception e) {

        }
    }

    public void sendFriendship3(MouseEvent mouseEvent) {
        String myUsername = User.loggedInUser.getUsername();
        String secondUsername = users.get(2).getUsername();
        try{
            sendFriendship(myUsername, secondUsername);
        } catch (Exception e) {

        }
    }

    public void sendFriendship4(MouseEvent mouseEvent) {
        String myUsername = User.loggedInUser.getUsername();
        String secondUsername = users.get(3).getUsername();
        try{
            sendFriendship(myUsername, secondUsername);
        } catch (Exception e) {

        }
    }

    public void sendFriendship5(MouseEvent mouseEvent) {
        String myUsername = User.loggedInUser.getUsername();
        String secondUsername = users.get(4).getUsername();
        try{
            sendFriendship(myUsername, secondUsername);
        } catch (Exception e) {

        }
    }

    public void sendFriendship6(MouseEvent mouseEvent) {
        String myUsername = User.loggedInUser.getUsername();
        String secondUsername = users.get(5).getUsername();
        try{
            sendFriendship(myUsername, secondUsername);
        } catch (Exception e) {

        }
    }

    public void sendFriendship7(MouseEvent mouseEvent) {
        String myUsername = User.loggedInUser.getUsername();
        String secondUsername = users.get(6).getUsername();
        try{
            sendFriendship(myUsername, secondUsername);
        } catch (Exception e) {

        }
    }

    public void sendFriendship8(MouseEvent mouseEvent) {
        String myUsername = User.loggedInUser.getUsername();
        String secondUsername = users.get(7).getUsername();
        try{
            sendFriendship(myUsername, secondUsername);
        } catch (Exception e) {

        }
    }

    public void sendFriendship9(MouseEvent mouseEvent) {
        String myUsername = User.loggedInUser.getUsername();
        String secondUsername = users.get(8).getUsername();
        try{
            sendFriendship(myUsername, secondUsername);
        } catch (Exception e) {

        }
    }

    public void sendFriendship10(MouseEvent mouseEvent) {
        String myUsername = User.loggedInUser.getUsername();
        String secondUsername = users.get(9).getUsername();
        try{
            sendFriendship(myUsername, secondUsername);
        } catch (Exception e) {

        }
    }
}
