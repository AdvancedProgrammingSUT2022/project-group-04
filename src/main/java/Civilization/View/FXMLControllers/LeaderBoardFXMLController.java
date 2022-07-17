package Civilization.View.FXMLControllers;

import Civilization.Database.UserDatabase;
import Civilization.Main;
import Civilization.Model.Civilization;
import Civilization.Model.GameModel;
import Civilization.Model.User;
import Civilization.View.GraphicalBases;
import Civilization.View.Transitions.CursorTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class LeaderBoardFXMLController {

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






    @FXML
    public void initialize() {
        GameModel.isGame = false;
        setScores();
    }



    public void setScores(){
        ArrayList<User> users = sortUsers(UserDatabase.getAllUsers());
        if (users.size() > 0) {
            player1Score.setText(users.get(0).getScore() + "");
            player1Name.setText(users.get(0).getNickname());
            Player1LoginTime.setText(users.get(0).getLastLoginTime());
            if (User.loggedInUser.getUsername().equals(users.get(0).getUsername())){
                player1Name.setStyle("-fx-fill: yellow; -fx-text-fill: yellow");
            }
        }
        else {
            player1Score.setText("");
            player1Name.setText("");
            Player1LoginTime.setText("");
        }
        if (users.size() > 1) {
            player2Score.setText(users.get(1).getScore() + "");
            player2Name.setText(users.get(1).getNickname());
            Player2LoginTime.setText(users.get(1).getLastLoginTime());
            if (User.loggedInUser.getUsername().equals(users.get(1).getUsername())){
                player2Name.setStyle("-fx-fill: yellow; -fx-text-fill: yellow");
            }
        }
        else {
            player2Score.setText("");
            player2Name.setText("");
            Player2LoginTime.setText("");
        }
        if (users.size() > 2) {
            player3Score.setText(users.get(2).getScore() + "");
            player3Name.setText(users.get(2).getNickname());
            Player3LoginTime.setText(users.get(2).getLastLoginTime());
            if (User.loggedInUser.getUsername().equals(users.get(2).getUsername())){
                player3Name.setStyle("-fx-fill: yellow; -fx-text-fill: yellow");
            }

        }
        else {
            player3Score.setText("");
            player3Name.setText("");
            Player3LoginTime.setText("");
        }
        if (users.size() > 3) {
            player4Score.setText(users.get(3).getScore() + "");
            player4Name.setText(users.get(3).getNickname());
            Player4LoginTime.setText(users.get(3).getLastLoginTime());
            if (User.loggedInUser.getUsername().equals(users.get(3).getUsername())){
                player4Name.setStyle("-fx-fill: yellow; -fx-text-fill: yellow");
            }

        }
        else {
            player4Score.setText("");
            player4Name.setText("");
            Player4LoginTime.setText("");
        }
        if (users.size() > 4) {
            player5Score.setText(users.get(4).getScore() + "");
            player5Name.setText(users.get(4).getNickname());
            Player5LoginTime.setText(users.get(4).getLastLoginTime());
            if (User.loggedInUser.getUsername().equals(users.get(4).getUsername())){
                player5Name.setStyle("-fx-fill: yellow; -fx-text-fill: yellow");
            }

        }
        else {
            player5Score.setText("");
            player5Name.setText("");
            Player5LoginTime.setText("");
        }
        if (users.size() > 5) {
            player6Score.setText(users.get(5).getScore() + "");
            player6Name.setText(users.get(5).getNickname());
            Player6LoginTime.setText(users.get(5).getLastLoginTime());
            if (User.loggedInUser.getUsername().equals(users.get(5).getUsername())){
                player6Name.setStyle("-fx-fill: yellow; -fx-text-fill: yellow");
            }

        }
        else {
            player6Score.setText("");
            player6Name.setText("");
            Player6LoginTime.setText("");
        }
        if (users.size() > 6) {
            player7Score.setText(users.get(6).getScore() + "");
            player7Name.setText(users.get(6).getNickname());
            Player7LoginTime.setText(users.get(6).getLastLoginTime());
            if (User.loggedInUser.getUsername().equals(users.get(6).getUsername())){
                player7Name.setStyle("-fx-fill: yellow; -fx-text-fill: yellow");
            }

        }
        else {
            player7Score.setText("");
            player7Name.setText("");
            Player7LoginTime.setText("");
        }
        if (users.size() > 7) {
            player8Name.setText(users.get(7).getNickname());
            player8Score.setText(users.get(7).getScore() + "");
            Player8LoginTime.setText(users.get(7).getLastLoginTime());
            if (User.loggedInUser.getUsername().equals(users.get(7).getUsername())){
                player8Name.setStyle("-fx-fill: yellow; -fx-text-fill: yellow");
            }

        }
        else {
            player8Score.setText("");
            player8Name.setText("");
            Player8LoginTime.setText("");
        }
        if (users.size() > 8) {
            player9Score.setText(users.get(8).getScore() + "");
            player9Name.setText(users.get(8).getNickname());
            Player9LoginTime.setText(users.get(8).getLastLoginTime());
            if (User.loggedInUser.getUsername().equals(users.get(8).getUsername())){
                player9Name.setStyle("-fx-fill: yellow; -fx-text-fill: yellow");
            }

        }
        else {
            player9Score.setText("");
            player9Name.setText("");
            Player9LoginTime.setText("");
        }
        if (users.size() > 9) {
            player10Score.setText(users.get(9).getScore() + "");
            player10Name.setText(users.get(9).getNickname());
            Player10LoginTime.setText(users.get(9).getLastLoginTime());
            if (User.loggedInUser.getUsername().equals(users.get(9).getUsername())){
                player10Name.setStyle("-fx-fill: yellow; -fx-text-fill: yellow");
            }
        }
        else {
            player10Score.setText("");
            player10Name.setText("");
            Player10LoginTime.setText("");
        }




    }


    public ArrayList<User> sortUsers(ArrayList<User> temp){
        ArrayList<User> users = new ArrayList<>();
        users.addAll(temp);
        for (int i= 0 ; i < users.size(); i++){
            for (int j = 0; j < users.size(); j++){
                if (users.get(i).getScore() < users.get(j).getScore()){
                    Collections.swap(users, i, j);
                } else if (users.get(i).getScore() == users.get(j).getScore()){
                    //Todo how does this shit work
//                    if (Integer.parseInt(users.get(i).getTimeOfScore()) > Integer.parseInt(users.get(j).getTimeOfScore())){
//                        Collections.swap(users, i, j);
//                    } else if (Integer.parseInt(users.get(i).getTimeOfScore()) > Integer.parseInt(users.get(j).getTimeOfScore())){
                        if (users.get(i).getUsername().compareTo(users.get(j).getUsername()) < 0){
                            Collections.swap(users, i, j);
                        }
//                    }
                }
            }
        }
        return users;
    }

    public void backToMain(){
        GraphicalBases.changeMenu("MainMenu");
    }


}
