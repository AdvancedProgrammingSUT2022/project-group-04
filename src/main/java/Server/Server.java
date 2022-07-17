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
    private GameMenuServer game;


    public Server() {
        //game = new GameMenuServer();
        try {
            serverSocket1 = new ServerSocket(8080);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }


    public void startServer() throws IOException {
        UserDatabase.readFromFile("UserDatabase.json");
        Account.readAccounts("AccountURLs.json");
        LoginMenuController loginMenuController = new LoginMenuController(new LoginMenuModel());
        try {
            while (true) {
                Socket socket = serverSocket1.accept();
                new Thread(() -> {
                    try {
                        DataOutputStream dataOutputStream1 = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                        DataInputStream dataInputStream1 = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                        processSocketRequest(dataInputStream1, dataOutputStream1, loginMenuController);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processSocketRequest(DataInputStream dataInputStream, DataOutputStream dataOutputStream, LoginMenuController loginMenuController) throws IOException {
        boolean disconnected = true;
        while (true) {
            try {
                String clientCommand = dataInputStream.readUTF();
                JSONObject clientCommandJ = new JSONObject(clientCommand);
                if (clientCommandJ.get("menu type").equals("Login")) {
                    processLoginMenuReqs(clientCommandJ, loginMenuController, dataOutputStream, disconnected);
                } else if (clientCommandJ.get("menu type").equals("Profile")) {
                    processProfileMenuReqs(clientCommandJ, dataOutputStream);
                } else if (clientCommandJ.get("menu type").equals("Game")) {
                    processGameMenuReqs(clientCommandJ, dataOutputStream);
                } else if (clientCommandJ.get("menu type").equals("Main")) {
                    processMainMenuReqs(clientCommandJ, dataOutputStream);
                } else if (clientCommandJ.get("menu type").equals("")) {

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void processMainMenuReqs(JSONObject clientCommandJ, DataOutputStream dataOutputStream) {

    }

    private void processGameMenuReqs(JSONObject clientCommandJ, DataOutputStream dataOutputStream) throws IOException {
        JSONObject response = game.processGameMenuReqs(clientCommandJ,dataOutputStream);
        dataOutputStream.writeUTF(response.toString());
        dataOutputStream.flush();
    }

    private void processProfileMenuReqs(JSONObject clientCommandJ, DataOutputStream dataOutputStream) {
    }

    private void processLoginMenuReqs(JSONObject clientCommandJ, LoginMenuController loginMenuController, DataOutputStream dataOutputStream, boolean disconnected) throws IOException {
        try {
            if (clientCommandJ.get("action").equals("Register")) {
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
            } else if (clientCommandJ.get("action").equals("Login")) {
                String username = clientCommandJ.get("username").toString();
                String password = clientCommandJ.get("password").toString();
                if (!loginMenuController.isUserExists(username) || !loginMenuController.isPasswordCorrect(username, password)) {
                    dataOutputStream.writeUTF("Username and password didn't match");
                    dataOutputStream.flush();
                } else {
                    dataOutputStream.writeUTF("success");
                    dataOutputStream.flush();
                }
            }
        } catch (Exception e) {
            if (disconnected) {
                System.out.println("Please try again");
                disconnected = false;
            }
        }
    }


    private void createAccount(String username) throws IOException {
        Account account = new Account(username);
        Account.accounts.add(account);
        Account.writeAccounts("AccountURLs.json");
    }


}
