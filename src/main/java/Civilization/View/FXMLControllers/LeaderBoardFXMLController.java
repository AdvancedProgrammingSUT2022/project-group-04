package Civilization.View.FXMLControllers;

import Civilization.Database.UserDatabase;
import Civilization.Main;
import Civilization.Model.Civilization;
import Civilization.Model.User;
import Civilization.View.Transitions.CursorTransition;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.awt.*;
import java.util.ArrayList;

public class LeaderBoardFXMLController {
    @FXML
    ImageView background;
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
        setBackground();
    }

    public void setBackground(){
        Image image = new Image(Main.class.getResource("pics/Menus/LeaderboardBackground.png").toString());
        background.setImage(image);
    }


    public void setScores(){
        ArrayList<User> users = UserDatabase.getAllUsers();


        player1Score.setText(users.get(0).getScore() + "");
        player2Score.setText(users.get(1).getScore() + "");
        player3Score.setText(users.get(2).getScore() + "");
        player4Score.setText(users.get(3).getScore() + "");
        player5Score.setText(users.get(4).getScore() + "");
        player6Score.setText(users.get(5).getScore() + "");
        player7Score.setText(users.get(6).getScore() + "");
        player8Score.setText(users.get(7).getScore() + "");
        player9Score.setText(users.get(8).getScore() + "");
        player10Score.setText(users.get(9).getScore() + "");
        player1Name.setText(users.get(0).getNickname());
        player2Name.setText(users.get(1).getNickname());
        player3Name.setText(users.get(2).getNickname());
        player4Name.setText(users.get(3).getNickname());
        player5Name.setText(users.get(4).getNickname());
        player6Name.setText(users.get(5).getNickname());
        player7Name.setText(users.get(6).getNickname());
        player8Name.setText(users.get(7).getNickname());
        player9Name.setText(users.get(8).getNickname());
        player10Name.setText(users.get(9).getNickname());
        Player1LoginTime.setText(users.get(0).getLastLoginTime());
        Player2LoginTime.setText(users.get(1).getLastLoginTime());
        Player3LoginTime.setText(users.get(2).getLastLoginTime());
        Player4LoginTime.setText(users.get(3).getLastLoginTime());
        Player5LoginTime.setText(users.get(4).getLastLoginTime());
        Player6LoginTime.setText(users.get(5).getLastLoginTime());
        Player7LoginTime.setText(users.get(6).getLastLoginTime());
        Player8LoginTime.setText(users.get(7).getLastLoginTime());
        Player9LoginTime.setText(users.get(8).getLastLoginTime());
        Player10LoginTime.setText(users.get(9).getLastLoginTime());
    }


    public ArrayList<User> sortUsers(ArrayList<User> temp){
        for (int i= 0 ; i < temp.size(); i++){
            for (int j = 0; j < temp.size(); j++){
               // if (seyyed == kir)
            }
        }
        return null;
    }


}
