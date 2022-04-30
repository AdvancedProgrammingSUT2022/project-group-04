package Model;

import java.util.ArrayList;

public class Notification {

    private String source;
    private String destination;
    private String text;
    private boolean unread;
    public static ArrayList<Notification> notifications = new ArrayList<Notification>();

    public Notification(String source, String destination, String text) {
        this.source = source;
        this.destination = destination;
        this.text = text;
        this.unread = true;

    }

    public static void addNotification(Notification notification) {
        Notification.notifications.add(notification);
        System.out.println(Notification.notifications.size());
    }

    public static ArrayList<Notification> get(Civilization civilization) {
        ArrayList<Notification> notificationsForCivilization = new ArrayList<Notification>();
        for (Notification notification : Notification.notifications) {
            if(notification.destination.equals(civilization.getNickname())) {
                notificationsForCivilization.add(notification);
            }
        }
        return notificationsForCivilization;
    }

    public static int getUnreadMessagesNumber(Civilization civilization) {
        int counter = 0;
        for (Notification notification : Notification.get(civilization)) {
            if(notification.unread) {
                counter++;
            }
        }
        return counter;
    }

    @Override
    public String toString() {
        String result = "";
        if(this.unread) {
            result += "New ";
        }
        result = "Message From " + this.source + ":\n";
        result += "\t " + text;
        return result;
    }

    public void read() {
        this.unread = false;
    }
}
