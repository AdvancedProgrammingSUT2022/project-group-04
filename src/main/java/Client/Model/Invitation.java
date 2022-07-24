package Client.Model;

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

    public void expire() {
        this.minute = 0;
    }

    public static void expireAll() {
        for (Invitation invitation : getAllNotExpiredInvitations()) {
            invitation.expire();
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
    }
}
