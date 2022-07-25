package Server;

import Client.View.Components.Account;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.sql.*;

public class User {

    public static User loggedInUser;

    public static ArrayList<User> users = new ArrayList<User>();

    private String username;
    private String nickname;
    private String password;
    private int score;
    // times
    private String timeOfScore;
    private String lastLoginTime;

    public User(String username, String nickname, String password) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.score = 0;
        this.timeOfScore = timeToJsonString(LocalDateTime.MAX.format(DateTimeFormatter.ofPattern("E, MMM dd yyyy HH:mm:ss")));
        this.lastLoginTime = timeToJsonString(LocalDateTime.MIN.format(DateTimeFormatter.ofPattern("E, MMM dd yyyy HH:mm:ss")));
    }

    public User(String username, String nickname, String password, int score, String timeOfScore, String lastLoginTime) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.score = score;
        this.timeOfScore = timeOfScore;
        this.lastLoginTime = lastLoginTime;
    }

    private String timeToJsonString(String time) {
        String tempTime = time.replace(":", "^");
        tempTime = tempTime.replace(" ", "_");
        tempTime = tempTime.replace(",", "*");
        return tempTime;
    }

    private String jsonStringToTime(String time) {
        String tempTime = time.replace("^", ":");
        tempTime = tempTime.replace("_", " ");
        tempTime = tempTime.replace("*", ",");
        return tempTime;
    }

    public String getTimeOfScore() {
        return jsonStringToTime(timeOfScore);
    }

    public void setTimeOfScore(LocalDateTime timeOfScore) {
        this.timeOfScore = timeToJsonString(timeOfScore.format(DateTimeFormatter.ofPattern("E,HH:mm:ss")));
    }

    public String getLastLoginTime() {
        return jsonStringToTime(lastLoginTime);
    }

    public void setLastLoginTime(LocalDateTime lastLoginTime) {
        this.lastLoginTime = timeToJsonString(lastLoginTime.format(DateTimeFormatter.ofPattern("E, HH:mm:ss")));
    }

    public String getUsername() {
        return this.username;
    }

    public String getNickname() {
        return this.nickname;
    }

    public String getPassword() {
        return this.password;
    }

    public int getScore() {
        return this.score;
    }

    public void addScore(int amount) {
        this.score += amount;
    }

    public void changeUsername(String username) {
        this.username = username;
    }

    public void changeNickname(String nickname) {
        //System.out.println(this.nickname);
        this.nickname = nickname;
        //System.out.println(this.nickname);
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public String getAvatarURL() {
        for (Account account : Account.accounts) {
            //System.out.println(account.getUsername());
            if(account.getUsername().equals(this.username)) {
                return account.getAvatarURL();
            }
        }
        //System.out.println("I'm null");
        return null;
    }

    public void setAvatarURL(String avatarURL) {
        for (Account account : Account.accounts) {
            if(account.getUsername().equals(this.username)) {
                account.setAvatarURL(avatarURL);
                return;
            }
        }
    }

    public void setScore(int amount) {
        this.score = amount;
    }

    @Override
    public String toString() {
        return this.username + "\n" + "  Nickname = " + this.nickname + "\n  Score = " + this.score;
    }

    public static void writeOneUser(User user) throws IOException {
        try{
           Class.forName("com.mysql.jdbc.Driver").newInstance();
           String url = "jdbc:mysql://localhost:3306/project-group-04?user=root";
           Connection connection = DriverManager.getConnection(url);
           Statement statement = connection.createStatement();
           String query = "insert into user(username,nickname,password,score,timeOfScore,lastLoginTime) values('%s','%s','%s',%s,'%s','%s')";
           query = String.format(query, user.getUsername(), user.getNickname(), user.getPassword(), user.getScore(), user.getTimeOfScore(), user.getLastLoginTime());
           statement.execute(query);
           statement.close();
           connection.close();
        } catch (Exception e) {
            UserDatabase.writeInFile("UserDatabase.json");
            System.err.println("Database ERROR");
        }
    }

    public static void editUser(User user) throws IOException {
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String url = "jdbc:mysql://localhost:3306/project-group-04?user=root";
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            String query = "update user set username='%s',nickname='%s',password='%s',score=%s,timeOfScore='%s',lastLoginTime='%s' where username='%s'";
            query = String.format(query, user.getUsername(), user.getNickname(), user.getPassword(), user.getScore(), user.getTimeOfScore(), user.getLastLoginTime(), user.getUsername());
            statement.execute(query);
            statement.close();
            connection.close();
        } catch (Exception e) {
            UserDatabase.writeInFile("UserDatabase.json");
            System.err.println("Database ERROR");
        }
    }
}
