package Civilization.Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;

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

    private void accept() {
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

    public static void writeInvitations(String fileName) throws IOException {
        Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
        Path userPath = Paths.get(fileName);
        Writer writer = Files.newBufferedWriter(userPath);
        System.out.println("Hi");
        System.out.println(Invitation.invitations.size());
        gsonBuilder.toJson(Invitation.invitations, writer);
        System.out.println("writing ends");
        writer.close();
    }
}
