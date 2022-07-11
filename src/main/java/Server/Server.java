package Server;

import Civilization.Controller.LoginMenuController;
import Civilization.Model.LoginMenuModel;
import Civilization.View.Components.Account;
import com.google.gson.JsonObject;
import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;

    public Server() {
        try {
            serverSocket = new ServerSocket(8080);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }


    public void startServer() {
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> {
                    try {
                        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                        processSocketRequestLoginMenuUsername(dataInputStream, dataOutputStream);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processSocketRequestLoginMenuUsername(DataInputStream dataInputStream, DataOutputStream dataOutputStream) throws IOException {
        while(true) {
            String clientCommand = dataInputStream.readUTF();
            JSONObject clientCommandJ = new JSONObject(clientCommand);
            String username = clientCommandJ.get("username").toString();
            String nickname = clientCommandJ.get("nickname").toString();
            String password = clientCommandJ.getString("password").toString();
            LoginMenuController loginMenuController = new LoginMenuController(new LoginMenuModel());
            if(!loginMenuController.isUsernameUnique(username)) {
                dataOutputStream.writeUTF("Username is not unique");
            } else if(!loginMenuController.isNicknameUnique(nickname)) {
                dataOutputStream.writeUTF("Nickname is not unique");
            } else {
                loginMenuController.userCreate(username, nickname, password);
                createAccount(username);
                dataOutputStream.writeUTF("Registered successfully");
                return;
            }
        }
    }


    private void createAccount(String username) throws IOException {
        Account account = new Account(username);
        Account.accounts.add(account);
        Account.writeAccounts("AccountURLs.json");
    }


}
