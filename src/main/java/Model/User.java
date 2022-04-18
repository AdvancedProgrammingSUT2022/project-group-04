package Model;

public class User {

    private String username;
    private String nickname;
    private String password;
    private int score;

    public User(String username, String nickname, String password) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.score = 0;
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
}