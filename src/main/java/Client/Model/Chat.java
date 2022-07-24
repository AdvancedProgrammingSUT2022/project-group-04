package Client.Model;

import java.util.ArrayList;

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

    public Chat(byte[] message, byte[] name, byte[] time, byte[] imageUrl){
        this.message = message;
        this.name = name;
        this.time = time;
        this.imageUrl = imageUrl;
    }


    public static void addChat(byte[] message, byte[] name, byte[] time, byte[] imageUrl){
        Chat temp = new Chat(message, name, time, imageUrl);
        chats.add(temp);
    }






}
