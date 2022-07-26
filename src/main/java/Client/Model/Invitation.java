package Client.Model;

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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

public class Invitation {
    public static ArrayList<Invitation> invitations = new ArrayList<>();

    private String username1;
    private String username2;
    private boolean isAccepted;
    private boolean isDenied;
    private long minute;

    public Invitation(String username1, String username2) {
        this.username1 = username1;
        this.username2 = username2;
        this.isAccepted = false;
        this.isDenied = false;
        this.minute = minuteCalculator(LocalDateTime.now());
    }

    public Invitation(String username1, String username2, boolean isAccepted, boolean isDenied, long minute) {
        this.username1 = username1;
        this.username2 = username2;
        this.isAccepted = isAccepted;
        this.isDenied = isDenied;
        this.minute = minute;
    }

    public static ArrayList<Invitation> getAllNotExpiredInvitations() {
        ArrayList<Invitation> notExpiredInvitations = new ArrayList<>();
        for (Invitation invitation : Invitation.invitations) {
            if(!invitation.isExpired()) {
                notExpiredInvitations.add(invitation);
            }
        }
        return notExpiredInvitations;
    }

    public static String getAcceptedValidInvitationsString() {
        String result = "";
        for (Invitation invitation : getAcceptedValidInvitations()) {
            result += invitation.getUsername2() + " ";
        }
        return result;
    }

    public static ArrayList<Invitation> getAcceptedValidInvitations() {
        ArrayList<Invitation> validInvitations = new ArrayList<>();
        for (Invitation invitation : Invitation.invitations) {
            if(!invitation.isExpired() && invitation.isAccepted()) {
                validInvitations.add(invitation);
            }
        }
        return validInvitations;
    }

    public static ArrayList<Invitation> getMyInvitations(String username) {
        ArrayList<Invitation> myInvitations = new ArrayList<>();
        for (Invitation invitation : invitations) {
            if(!invitation.isExpired() && !invitation.isAccepted() && !invitation.isDenied()) {
                if(invitation.getUsername2().equals(username)) {
                    myInvitations.add(invitation);
                }
            }
        }
        return myInvitations;
    }

    public static Invitation getInvitationAccepted(String username) {
        for (Invitation invitation : Invitation.invitations) {
            if(invitation.getUsername2().equals(username) && !invitation.isExpired() && invitation.isAccepted()) {
                return invitation;
            }
        }
        return null;
    }

    public static Invitation getInvitationBuUsernames(String username1, String username2) {
        for (Invitation invitation : Invitation.invitations) {
            if(invitation.getUsername1().equals(username1) && invitation.getUsername2().equals(username2)) {
                if(!invitation.isExpired()) {
                    return invitation;
                }
            }
        }
        return null;
    }

    public static String sort(String acceptedValidInvitationsString) {
        String[] users = acceptedValidInvitationsString.split(" ");
        String adminUser = Invitation.getAdminUsername();
        String result = adminUser + " ";
        for (String user : users) {
            if(!user.equals(adminUser)) {
                result += user + " ";
            }
        }
        //System.out.println(result);
        return result;
    }

    private long minuteCalculator(LocalDateTime now) {
        return (now.getDayOfYear()-1)*24*60 + now.getHour()*60 + now.getMinute();
    }

    public static void invite(String username1, String username2) {
        Invitation invitation = new Invitation(username1, username2);
        if(username1.equals(username2)) {
            invitation.accept();
        }
        Invitation.invitations.add(invitation);
    }

    public void accept() {
        if(!isExpired()) {
            this.isAccepted = true;
            this.isDenied = false;
        }
    }

    public static void sendToAll(String[] users, String username1) {
        for (String user : users) {
            System.out.println(user);
            Invitation.invite(username1, user);
        }
    }

    public String getUsername1() {
        return username1;
    }

    public String getUsername2() {
        return username2;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public boolean isDenied() {
        return isDenied;
    }

    public boolean isExpired() {
        long nowMin = minuteCalculator(LocalDateTime.now());
        if(nowMin - this.minute > 2) {
            return true;
        }
        return false;
    }

    public static void readInvitations(String fileName) throws IOException {
        Gson gson = new Gson();
        Path userPath = Paths.get(fileName);
        if (!userPath.toFile().isFile()) {
            return;
        }
        Reader reader = Files.newBufferedReader(userPath);
        ArrayList<?> jsonStringUserDatabase = gson.fromJson(reader, ArrayList.class);
        reader.close();

        for (int i = 0; i < jsonStringUserDatabase.size(); i++) {
            Invitation invitation = gson.fromJson(jsonStringUserDatabase.get(i).toString(), Invitation.class);
            Invitation.invitations.add(invitation);
        }
    }

    public static void readFromDatabase() throws IOException {
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String url = "jdbc:mysql://localhost:3306/project-group-04?user=root";
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            String query = "select * from invitation";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String username1 = resultSet.getString(1);
                String username2 = resultSet.getString(2);
                boolean isAccepted = Boolean.parseBoolean(resultSet.getString(3));
                boolean isDenied = Boolean.parseBoolean(resultSet.getString(4));
                long minute = Long.parseLong(resultSet.getString(5));
                Invitation invitation = new Invitation(username1, username2, isAccepted, isDenied, minute);
                Invitation.invitations.add(invitation);
            }
            statement.close();
            connection.close();
        } catch (Exception e) {
            Invitation.readInvitations("invitationDatabase.json");
            System.err.println("Database ERROR");
        }
    }

    public static void writeInvitations(String fileName) throws IOException {
        Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
        Path userPath = Paths.get(fileName);
        Writer writer = Files.newBufferedWriter(userPath);
        //System.out.println("Hi");
        System.out.println(Invitation.invitations.size());
        gsonBuilder.toJson(Invitation.invitations, writer);
        //System.out.println("writing ends");
        writer.close();
    }

    public static void editInvitation(Invitation invitation) throws IOException {
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String url = "jdbc:mysql://localhost:3306/project-group-04?user=root";
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            String query = "update invitation set username1='%s',username2='%s', isAccepted=%s, isDenied=%s, minute=%s where username1='%s'and username2='%s'";
            query = String.format(query, invitation.getUsername1(), invitation.getUsername2(), invitation.isAccepted(), invitation.isDenied(), invitation.getMinute(), invitation.getUsername1(), invitation.getUsername2());
            statement.execute(query);
            statement.close();
            connection.close();
        } catch (Exception e) {
            //e.printStackTrace();
            Invitation.writeInvitations("invitationDatabase.json");
            System.err.println("Database ERROR");
        }
    }

    public long getMinute() {
        return minute;
    }

    public static void writeOneInvitation(Invitation invitation) throws IOException {
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String url = "jdbc:mysql://localhost:3306/project-group-04?user=root";
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            String query = "insert into invitation(username1,username2, isAccepted, isDenied, minute) values('%s','%s', %s, %s, %s)";
            query = String.format(query, invitation.getUsername1(), invitation.getUsername2(), invitation.isAccepted(), invitation.isDenied(), invitation.getMinute());
            statement.execute(query);
            statement.close();
            connection.close();
        } catch (Exception e) {
            Invitation.writeInvitations("invitationDatabase.json");
            System.err.println("Database ERROR");
        }
    }

    public void expire() {
        this.minute = 0;
    }

    public static void expireAll() throws IOException {
        for (Invitation invitation : getAllNotExpiredInvitations()) {
            invitation.expire();
            Invitation.editInvitation(invitation);
        }
    }

    public static String getAdminUsername() {
        if(getAllNotExpiredInvitations().size() == 0) {
            return "";
        } else {
            return getAllNotExpiredInvitations().get(0).getUsername1();
        }
    }

    public void deny() {
        this.isAccepted = false;
        this.isDenied = true;
        expire();
    }
}
