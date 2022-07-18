package Server;

import Civilization.Controller.LoginMenuController;
import Civilization.Controller.ProfileMenuController;
import Civilization.Database.UserDatabase;
import Civilization.Model.LoginMenuModel;
import Civilization.Model.ProfileMenuModel;
import Civilization.Model.User;
import Civilization.View.Components.Account;
import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket1;
    private ServerSocket serverSocket2;
//    private GameMenuServer game;


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
        ProfileMenuController profileMenuController = new ProfileMenuController(new ProfileMenuModel());
        try {
            while (true) {
                Socket socket = serverSocket1.accept();
                new Thread(() -> {
                    try {
                        DataOutputStream dataOutputStream1 = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                        DataInputStream dataInputStream1 = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                        processSocketRequest(dataInputStream1, dataOutputStream1, loginMenuController, profileMenuController);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processSocketRequest(DataInputStream dataInputStream, DataOutputStream dataOutputStream, LoginMenuController loginMenuController, ProfileMenuController profileMenuController) throws IOException {
        boolean disconnected = true;
        while (true) {
            try {
                String clientCommand = dataInputStream.readUTF();
                JSONObject clientCommandJ;
                if (!clientCommand.startsWith("!!!")) {
                    clientCommandJ = new JSONObject(clientCommand);
                    if (clientCommandJ.get("menu type").equals("Login")) {
                        processLoginMenuReqs(clientCommandJ, loginMenuController, dataOutputStream, disconnected);
                    } else if (clientCommandJ.get("menu type").equals("Profile")) {
                        processProfileMenuReqs(clientCommandJ, dataOutputStream, profileMenuController, disconnected);
                    } else if (clientCommandJ.get("menu type").equals("Game Database")) {
                        processGameMenuReqs(clientCommandJ, dataOutputStream);
                    } else if (clientCommandJ.get("menu type").equals("Main")) {
                        processMainMenuReqs(clientCommandJ, dataOutputStream);
                    } else if (clientCommandJ.get("menu type").equals("")) {

                    }
                } else {
                    clientCommand = clientCommand.substring(3);
                    processGameUsingXML(clientCommand, dataOutputStream);
                }
            } catch (Exception ex) {
//                System.out.println("Client disconnected");
//                break;

            }
        }
    }

    private void processGameUsingXML(String s, DataOutputStream dataOutputStream) throws IOException {
        String response = GameDatabaseServer.processReq(s);
        dataOutputStream.writeUTF(response);
        dataOutputStream.flush();
    }

    private void processMainMenuReqs(JSONObject clientCommandJ, DataOutputStream dataOutputStream) {

    }

    private void processGameMenuReqs(JSONObject clientCommandJ, DataOutputStream dataOutputStream) throws IOException {
//        JSONObject response = game.processGameMenuReqs(clientCommandJ,dataOutputStream);
//        dataOutputStream.writeUTF(response.toString());
//        dataOutputStream.flush();
        JSONObject response = GameDatabaseServer.processReq(clientCommandJ);
        dataOutputStream.writeUTF(response.toString());
        dataOutputStream.flush();
    }

    private void processProfileMenuReqs(JSONObject clientCommandJ, DataOutputStream dataOutputStream, ProfileMenuController profileMenuController, boolean disconnected) {
        try {
            if (clientCommandJ.get("action").equals("change password")) {
                String password = clientCommandJ.get("password").toString();
                String newPassword = clientCommandJ.get("new password").toString();
                if (!profileMenuController.isPasswordCorrect(User.loggedInUser.getUsername(), password)) {
                    dataOutputStream.writeUTF("current password is invalid");
                    dataOutputStream.flush();
                    return;
                }
                if (!profileMenuController.isNewPasswordDifferent(password, newPassword)
                        || !profileMenuController.isPasswordValid(newPassword)) {
                    dataOutputStream.writeUTF("Please enter a new password");
                    dataOutputStream.flush();
                    return;
                }
                profileMenuController.changePassword(User.loggedInUser, newPassword);
                UserDatabase.writeInFile("UserDatabase.json");
            } else if (clientCommandJ.get("action").equals("change nickname")) {
                String nickname = clientCommandJ.get("nickname").toString();
                if (!profileMenuController.isNicknameUnique(nickname)) {
                    dataOutputStream.writeUTF("Nickname is not unique");
                    dataOutputStream.flush();
                    return;
                }
                if (!profileMenuController.isNicknameValid(nickname)) {
                    dataOutputStream.writeUTF("Please enter a new nickname");
                    dataOutputStream.flush();
                    return;
                }
                profileMenuController.changeNickname(User.loggedInUser, nickname);
                UserDatabase.writeInFile("UserDatabase.json");
                dataOutputStream.writeUTF("nickname changed successfully");
                dataOutputStream.flush();
            }
        } catch (Exception e) {
            if (disconnected) {
                System.out.println("Please try again");
                disconnected = false;
            }
        }
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
