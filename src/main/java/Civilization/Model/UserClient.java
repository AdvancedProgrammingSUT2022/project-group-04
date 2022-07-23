package Civilization.Model;

import Client.View.Components.Account;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class UserClient {

    public static UserClient loggedInUser;

    public static ArrayList<UserClient> users = new ArrayList<UserClient>();

    private String username;
    private String nickname;
    private String password;
    private int score;
    // times
    private String timeOfScore;
    private String lastLoginTime;

    public UserClient(String username, String nickname, String password) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.score = 0;
        this.timeOfScore = timeToJsonString(LocalDateTime.MAX.format(DateTimeFormatter.ofPattern("E, MMM dd yyyy HH:mm:ss")));
        this.lastLoginTime = timeToJsonString(LocalDateTime.MIN.format(DateTimeFormatter.ofPattern("E, MMM dd yyyy HH:mm:ss")));
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
        this.nickname = nickname;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public String getAvatarURL() {
        for (Account account : Account.accounts) {
            if(account.getUsername().equals(this.username)) {
                return account.getAvatarURL();
            }
        }
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
        this.score += amount;
    }
}
