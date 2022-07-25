package Server.Controller;

import Client.Model.Chat;
import Client.Model.Friendship;
import Client.View.GraphicalBases;
import Server.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ChatroomController {


    public static void writeChats(String fileName) throws IOException {
        Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
        Path userPath = Paths.get(fileName);
        Writer writer = Files.newBufferedWriter(userPath);
        gsonBuilder.toJson(Chat.chats, writer);
        writer.close();
    }

    public static void readChats(String fileName) throws IOException {
        Gson gson = new Gson();
        Path userPath = Paths.get(fileName);
        if (!userPath.toFile().isFile()) {
            return;
        }
        Reader reader = Files.newBufferedReader(userPath);
        ArrayList<?> jsonStringUserDatabase = gson.fromJson(reader, ArrayList.class);
        reader.close();

        Chat.chats.clear();

        for (int i = 0; i < jsonStringUserDatabase.size(); i++) {

            Chat chat = gson.fromJson(jsonStringUserDatabase.get(i).toString(), Chat.class);
            Chat.chats.add(chat);

        }
    }

    public static void seeChats(String fileName, String username) throws IOException {
        for (Chat chat: Chat.chats){
            if (!new String(chat.getName(), StandardCharsets.UTF_8).equals(username)){
                chat.setSeen(true);
                System.out.println("salam");
            }
        }

        writeChats(fileName);

    }

    public static void seeChatsPrivate(String fileName, String name) throws IOException{
        for (Chat chat: Chat.chats){
            //if (new String(chat, StandardCharsets.UTF_8).equals(name)) {
            //        chat.setSeen(true);
            //        System.out.println("salam");
            //} else {
            //    System.out.println(new String(chat.getReceiver(), StandardCharsets.UTF_8)+ " " + name);
            //}
        }
    }

    public static void addChatsToPanel(String message, String nameUser, String Time, String ImageURL){

        Text name = new Text(nameUser);
        Label label = new Label();
        label.setText(" " + message + " ");
        Text time = new Text();
        time.setText(Time);
        Image avatarImage = new Image(ImageURL);
        ImageView avatar = new ImageView(avatarImage);
        avatar.setFitHeight(37);
        avatar.setFitWidth(37);
        HBox line = new HBox(avatar, name, label, time);
        name.setStyle("-fx-fill: white; -fx-font-size: 10");
        line.setStyle("-fx-alignment: center-left; -fx-spacing: 10; -fx-padding: 10");
        label.setStyle("-fx-font-size: 15;" +
                "    -fx-background-color: black;" +
                "    -fx-border-color: #fffde9;" +
                "    -fx-text-fill: white;" +
                "    -fx-border-width: 1;" +
                "    -fx-fill: white;" +
                "    -fx-text-alignment: center;" +
                "    -fx-tile-alignment: center;" +
                "    -fx-border-radius: 2;" +
                "-fx-start-margin: 20");
        time.setStyle("-fx-font-size: 10; -fx-fill: white; -fx-text-fill: white;");
    }




}
