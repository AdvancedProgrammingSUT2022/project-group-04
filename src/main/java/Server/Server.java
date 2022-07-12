package Server;

import Civilization.Controller.LoginMenuController;
import Civilization.Database.UserDatabase;
import Civilization.Model.LoginMenuModel;
import Civilization.View.Components.Account;
import com.google.gson.JsonObject;
import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket1;
    private ServerSocket serverSocket2;


    public Server() {
        try {
            serverSocket1 = new ServerSocket(8080);
            serverSocket2 = new ServerSocket(9000);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }


    public void startServer() throws IOException {
        UserDatabase.readFromFile("UserDatabase.json");
        LoginMenuController loginMenuController = new LoginMenuController(new LoginMenuModel());
        try {
            while (true) {
                Socket socket = serverSocket1.accept();
                new Thread(() -> {
                    try {
                        DataOutputStream dataOutputStream1 = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                        DataInputStream dataInputStream1 = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                        processSocketRequestLoginMenuRegister(dataInputStream1, dataOutputStream1, loginMenuController);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
                Socket socket1 = serverSocket2.accept();
                new Thread(() -> {
                    try {
                        DataOutputStream dataOutputStream2 = new DataOutputStream(new BufferedOutputStream(socket1.getOutputStream()));
                        DataInputStream dataInputStream2 = new DataInputStream(new BufferedInputStream(socket1.getInputStream()));
                        processSocketRequestLoginMenuLogin(dataInputStream2, dataOutputStream2, loginMenuController);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processSocketRequestLoginMenuRegister(DataInputStream dataInputStream, DataOutputStream dataOutputStream, LoginMenuController loginMenuController) throws IOException {
         while(true) {
             String clientCommand = dataInputStream.readUTF();
             JSONObject clientCommandJ = new JSONObject(clientCommand);
             String username = clientCommandJ.get("username").toString();
             String nickname = clientCommandJ.get("nickname").toString();
             String password = clientCommandJ.get("password").toString();
             System.out.println(loginMenuController.isUsernameUnique("Alireza"));
             if (!loginMenuController.isUsernameUnique(username)) {
                 dataOutputStream.writeUTF("Username is not unique");
                 dataOutputStream.flush();
                 System.out.println("salam salam");
             } else if (!loginMenuController.isNicknameUnique(nickname)) {
                 dataOutputStream.writeUTF("Nickname is not unique");
                 dataOutputStream.flush();
             } else {
                 loginMenuController.userCreate(username, nickname, password);
                 createAccount(username);
                 dataOutputStream.writeUTF("Registered successfully");
                 dataOutputStream.flush();
             }

         }
    }

    private void processSocketRequestLoginMenuLogin(DataInputStream dataInputStream, DataOutputStream dataOutputStream, LoginMenuController loginMenuController) throws IOException {
        while(true) {
            String clientCommand = dataInputStream.readUTF();
            JSONObject clientCommandJ = new JSONObject(clientCommand);
            String username = clientCommandJ.get("username").toString();
            String password = clientCommandJ.get("password").toString();
            if(!loginMenuController.isUserExists(username) || !loginMenuController.isPasswordCorrect(username, password)) {
                dataOutputStream.writeUTF("Username and password didn't match");
                dataOutputStream.flush();
            } else {
                dataOutputStream.writeUTF("success");
                dataOutputStream.flush();
            }
        }
    }




    private void createAccount(String username) throws IOException {
        Account account = new Account(username);
        Account.accounts.add(account);
        Account.writeAccounts("AccountURLs.json");
    }


}
