package Server.Model;

import java.util.ArrayList;
import java.util.Arrays;

public class Chat {
    public static ArrayList<Chat> chats = new ArrayList<>();

    public byte[] getMessage() {
        return message;
    }

    public void setMessage(byte[] message) {
        this.message = message;
    }

    public byte[] getName() {
        return name;
    }

    public void setName(byte[] name) {
        this.name = name;
    }

    public byte[] getTime() {
        return time;
    }

    public void setTime(byte[] time) {
        this.time = time;
    }

    public byte[] getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(byte[] imageUrl) {
        this.imageUrl = imageUrl;
    }

    private byte[] message;
    private byte[] name;
    private byte[] time;
    private byte[] imageUrl;

    public byte[] getReceiver() {
        return receiver;
    }

    public void setReceiver(byte[] receiver) {
        this.receiver = receiver;
    }


    private byte[] receiver;
    private ArrayList<byte[]> roomReceivers;

    public ArrayList<byte[]> getRoomReceivers() {
        return roomReceivers;
    }

    public void setRoomReceivers(ArrayList<byte[]> roomReceivers) {
        this.roomReceivers = roomReceivers;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }

    private boolean seen = false;
    private boolean edited = false;

    public Chat(byte[] message, byte[] name, byte[] time, byte[] imageUrl, boolean seen, boolean edited){
        this.message = message;
        this.name = name;
        this.time = time;
        this.imageUrl = imageUrl;
        this.seen = seen;
        this.edited = edited;
    }


    public static void addChat(byte[] message, byte[] name, byte[] time, byte[] imageUrl, boolean seen , boolean edited){
        Chat temp = new Chat(message, name, time, imageUrl, seen, edited);
        chats.add(temp);
    }

    public static void addChat2(Chat chat){
        chats.add(chat);
    }


    public static void removeChat(byte[] message, byte[] name, byte[] time, byte[] imageUrl, boolean seen , boolean edited){
        chats.remove(getChat(message,name,time,imageUrl,seen,edited));
    }

    public static Chat getChat(byte[] message, byte[] name, byte[] time, byte[] imageUrl,  boolean seen , boolean edited){
        for (Chat chat : Chat.chats){
            if (Arrays.equals(chat.message, message) && Arrays.equals(chat.name, name) && Arrays.equals(chat.time, time) && Arrays.equals(chat.imageUrl, imageUrl) && chat.seen == seen && chat.edited == edited){
                return chat;
            }
        }
        return null;
    }

    public static void editChat(byte[] message, byte[] name, byte[] time, byte[] imageUrl,  boolean seen , boolean edited, byte[] newMessage){
        Chat chat = getChat(message,name,time,imageUrl,seen,edited);
        chat.setMessage(newMessage);
    }





}
