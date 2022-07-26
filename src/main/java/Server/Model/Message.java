package Server.Model;

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

public class Message {
    public static ArrayList<Message> messages = new ArrayList<>();

    private String from;
    private String to;
    private String text;
    private boolean seen;
    private String time;
    private String room;

    public Message(String from, String to, String text, String room) {
        this.from = from;
        this.to = to;
        this.text = text;
        this.seen = false;
        this.time = LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute() + ":" + LocalDateTime.now().getSecond();
        this.room = room;
        Message.messages.add(this);
    }

    public static void readMessages(String fileName) throws IOException {
        Gson gson = new Gson();
        Path userPath = Paths.get(fileName);
        if (!userPath.toFile().isFile()) {
            return;
        }
        Reader reader = Files.newBufferedReader(userPath);
        ArrayList<?> jsonStringUserDatabase = gson.fromJson(reader, ArrayList.class);
        reader.close();

        for (int i = 0; i < jsonStringUserDatabase.size(); i++) {
            Message message = gson.fromJson(jsonStringUserDatabase.get(i).toString(), Message.class);
            Message.messages.add(message);
        }
    }

    public static void writeMessages(String fileName) throws IOException {
        Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
        Path userPath = Paths.get(fileName);
        Writer writer = Files.newBufferedWriter(userPath);
        //System.out.println("Hi");
        //System.out.println(Invitation.invitations.size());
        gsonBuilder.toJson(Message.messages, writer);
        //System.out.println("writing ends");
        writer.close();
    }
}
