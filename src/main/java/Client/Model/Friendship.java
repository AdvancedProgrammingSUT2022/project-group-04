package Client.Model;

import Client.View.Components.Account;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Friendship {
    public static ArrayList<Friendship> friendships = new ArrayList<>();

    private String firstUsername;
    private String secondUsername;
    private boolean isAccepted;
    private boolean isDenied;

    private Friendship(String firstUsername, String secondUsername) {
        this.firstUsername = firstUsername;
        this.secondUsername = secondUsername;
        this.isAccepted = false;
        this.isDenied = false;
        friendships.add(this);
    }

    public Friendship(String firstUsername, String secondUsername, boolean isAccepted, boolean isDenied) {
        this.firstUsername = firstUsername;
        this.secondUsername = secondUsername;
        this.isAccepted = isAccepted;
        this.isDenied = isDenied;
    }

    public static void writeOneFriendship(Friendship friendship) throws IOException {
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String url = "jdbc:mysql://localhost:3306/project-group-04?user=root";
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            String query = "insert into friendship(firstUsername,secondUsername, isAccepted, isDenied) values('%s','%s', %s, %s)";
            query = String.format(query, friendship.getFirstUsername(), friendship.getSecondUsername(), friendship.isAccepted(), friendship.isDenied());
            statement.execute(query);
            statement.close();
            connection.close();
        } catch (Exception e) {
            Friendship.writeFriendships("friendshipDatabase.json");
            System.err.println("Database ERROR");
        }
    }

    public static void editFriendship(Friendship friendship) throws IOException {
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String url = "jdbc:mysql://localhost:3306/project-group-04?user=root";
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            String query = "update friendship set firstUsername='%s',secondUsername='%s', isAccepted=%s, isDenied=%s where firstUsername='%s'and secondUsername='%s'";
            query = String.format(query, friendship.getFirstUsername(), friendship.getSecondUsername(), friendship.isAccepted(), friendship.isDenied(), friendship.getFirstUsername(), friendship.getSecondUsername());
            statement.execute(query);
            statement.close();
            connection.close();
        } catch (Exception e) {
            //e.printStackTrace();
            Friendship.writeFriendships("friendshipDatabase.json");
            System.err.println("Database ERROR");
        }
    }

    public static void readFromDatabase() throws IOException {
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String url = "jdbc:mysql://localhost:3306/project-group-04?user=root";
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            String query = "select * from friendship";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String firstUsername = resultSet.getString(1);
                String secondUsername = resultSet.getString(2);
                boolean isAccepted = Boolean.parseBoolean(resultSet.getString(3));
                boolean isDenied = Boolean.parseBoolean(resultSet.getString(4));
                Friendship friendship = new Friendship(firstUsername, secondUsername, isAccepted, isDenied);
                Friendship.friendships.add(friendship);
            }
            statement.close();
            connection.close();
        } catch (Exception e) {
            Friendship.readFriendships("friendshipDatabase.json");
            System.err.println("Database ERROR");
        }
    }

    public String getFirstUsername() {
        return firstUsername;
    }

    public String getSecondUsername() {
        return secondUsername;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public boolean isDenied() {
        return isDenied;
    }

    public static void addFriendship(String firstUsername, String secondUsername) {
        Friendship friendship = Friendship.getFriendshipByUsernames(firstUsername, secondUsername);
        if(friendship == null) {
            new Friendship(firstUsername, secondUsername);
        }
    }

    public static ArrayList<String> getMyRequests(String myUsername) {
        ArrayList<String> myRequests = new ArrayList<>();
        for (Friendship friendship : friendships) {
            if(friendship.getSecondUsername().equals(myUsername)) {
                if(!friendship.isDenied() && !friendship.isAccepted()) {
                    myRequests.add(friendship.getFirstUsername());
                }
            }
        }
        return myRequests;

    }

    public void accept() {
        this.isAccepted = true;
        this.isDenied = false;
    }

    public void deny() {
        this.isDenied = true;
        this.isAccepted = false;
    }

    @Override
    public String toString() {
        return firstUsername + " " + secondUsername + " " + isAccepted + " " + isDenied;
    }

    public static ArrayList<String> getMyFriends(String myUsername) {
        ArrayList<String> myFriends = new ArrayList<>();
        for (Friendship friendship : friendships) {
            if(friendship.getFirstUsername().equals(myUsername) || friendship.getSecondUsername().equals(myUsername)) {
                if(friendship.isAccepted() && !friendship.isDenied()) {
                    if(friendship.getFirstUsername().equals(myUsername)) {
                        myFriends.add(friendship.getSecondUsername());
                    } else {
                        myFriends.add(friendship.getFirstUsername());
                    }
                }
            }
        }
        return myFriends;
    }

    public static Friendship getFriendshipByUsernames(String firstUsername, String secondUsername) {
        for (Friendship friendship : friendships) {
            if(friendship.getFirstUsername().equals(firstUsername) && friendship.getSecondUsername().equals(secondUsername)) {
                return friendship;
            } else if(friendship.getFirstUsername().equals(secondUsername) && friendship.getSecondUsername().equals(firstUsername)) {
                return friendship;
            }
        }
        return null;
    }

    public static Friendship getJustFriendshipByUsernames(String firstUsername, String secondUsername) {
        for (Friendship friendship : friendships) {
            if(friendship.getFirstUsername().equals(firstUsername) && friendship.getSecondUsername().equals(secondUsername)) {
                return friendship;
            }
        }
        return null;
    }

    public static void readFriendships(String fileName) throws IOException {
        Gson gson = new Gson();
        Path userPath = Paths.get(fileName);
        if (!userPath.toFile().isFile()) {
            return;
        }
        Reader reader = Files.newBufferedReader(userPath);
        ArrayList<?> jsonStringUserDatabase = gson.fromJson(reader, ArrayList.class);
        reader.close();

        for (int i = 0; i < jsonStringUserDatabase.size(); i++) {
            Friendship friendship = gson.fromJson(jsonStringUserDatabase.get(i).toString(), Friendship.class);
            Friendship.friendships.add(friendship);
        }
    }

    public static void writeFriendships(String fileName) throws IOException {
        Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
        Path userPath = Paths.get(fileName);
        Writer writer = Files.newBufferedWriter(userPath);
        gsonBuilder.toJson(Friendship.friendships, writer);
        writer.close();
    }

    public static boolean isMyFriend(String firstUsername, String secondUsername) {
        Friendship friendship = Friendship.getFriendshipByUsernames(firstUsername, secondUsername);
        if(friendship != null && friendship.isAccepted) {
            return true;
        } else {
            friendship = Friendship.getFriendshipByUsernames(secondUsername, firstUsername);
            if(friendship != null && friendship.isAccepted) {
                return true;
            }
            return false;
        }
    }

    public static boolean isRequestingValid(String firstUsername, String secondUsername) {
        Friendship friendship = Friendship.getFriendshipByUsernames(firstUsername, secondUsername);
        if(friendship != null) {
            return false;
        } else {
            friendship = Friendship.getFriendshipByUsernames(secondUsername, firstUsername);
            if(friendship != null) {
                return false;
            }
            return true;
        }
    }

    public static String getMyFriendsString(String myUsername) {
        ArrayList<String> friends = Friendship.getMyFriends(myUsername);
        String result = "";
        for (String friend : friends) {
            result += friend + "\n";
        }
        return result;
    }

    public static String getMyRequestsOrDeniedFriendshipsString(String myUsername) {
        String result = "";
        ArrayList<String> friends = Friendship.getMyRequestsOrDeniedFriendships(myUsername);
        for (String friend : friends) {
            result += friend + "\n";
        }
        return result;
    }

    public static ArrayList<String> getMyRequestsOrDeniedFriendships(String myUsername) {
        ArrayList<String> myRequestsAndDeniedFriendships = new ArrayList<>();
        for (Friendship friendship : Friendship.friendships) {
            if(friendship.getFirstUsername().equals(myUsername) && !friendship.isAccepted) {
                if(friendship.isDenied()) {
                    myRequestsAndDeniedFriendships.add(friendship.getSecondUsername() + " - Denied");
                } else {
                    myRequestsAndDeniedFriendships.add(friendship.getSecondUsername() + " - Requested");
                }
            }
        }
        return myRequestsAndDeniedFriendships;
    }
}
