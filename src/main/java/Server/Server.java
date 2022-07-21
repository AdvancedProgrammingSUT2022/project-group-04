package Server;

import Civilization.Controller.LoginMenuController;
import Civilization.Controller.ProfileMenuController;
import Civilization.Model.LoginMenuModel;
import Civilization.Model.ProfileMenuModel;
import Civilization.View.Components.Account;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Server {
    private ServerSocket serverSocket1;


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
                ClientThread clientThread = new ClientThread();
                Thread thread = new Thread(() -> {
                    try {
                        DataOutputStream dataOutputStream1 = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                        DataInputStream dataInputStream1 = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                        processSocketRequest(dataInputStream1, dataOutputStream1, loginMenuController, profileMenuController, ClientThread.id);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                thread.start();
                clientThread.setThread(thread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processSocketRequest(DataInputStream dataInputStream, DataOutputStream dataOutputStream, LoginMenuController loginMenuController, ProfileMenuController profileMenuController, int id) throws IOException {
        boolean disconnected = false;
        while (true) {
            try {
                String clientCommand = dataInputStream.readUTF();
                JSONObject clientCommandJ;
                if (!clientCommand.startsWith("!!!")) {
                    clientCommandJ = new JSONObject(clientCommand);
                    if (clientCommandJ.get("menu type").equals("Login")) {
                        processLoginMenuReqs(clientCommandJ, loginMenuController, dataOutputStream, disconnected, id);
                    } else if (clientCommandJ.get("menu type").equals("Profile")) {
                        processProfileMenuReqs(clientCommandJ, dataOutputStream, profileMenuController, disconnected);
                    } else if (clientCommandJ.get("menu type").equals("Game Database")) {
                        processGameMenuReqs(clientCommandJ, dataOutputStream);
                    } else if (clientCommandJ.get("menu type").equals("Main")) {
                        processMainMenuReqs(clientCommandJ, dataOutputStream, id);
                    } else if (clientCommandJ.get("menu type").equals("Leaderboard")) {
                        processLeaderBoardMenuReqs(clientCommandJ, dataOutputStream);
                    }
                } else {
                    clientCommand = clientCommand.substring(3);
                    processGameUsingXML(clientCommand, dataOutputStream);
                }
            } catch (Exception ex) {
                if(!disconnected) {
                    System.out.println("Client " + id + " disconnected");
                    ClientThread clientThread = ClientThread.getThreadID(id);
                    if(clientThread != null && clientThread.getUsername() != null) {
                        UserDatabase.disconnectUser(clientThread.getUsername());
                    }
                    disconnected = true;
                }
//                break;

            }
        }
    }

    private void processLeaderBoardMenuReqs(JSONObject clientCommandJ, DataOutputStream dataOutputStream) {
        try{
            if(clientCommandJ.get("action").equals("isOnline")) {
                String username = clientCommandJ.get("username").toString();
                String result = Boolean.toString(UserDatabase.isUserOnline(username));
                dataOutputStream.writeUTF(result);
                dataOutputStream.flush();
            } else if (clientCommandJ.get("action").equals("getScore")) {
                String username = clientCommandJ.get("username").toString();
                String result = Integer.toString(UserDatabase.getUserByUsername(username).getScore());
                dataOutputStream.writeUTF(result);
                dataOutputStream.flush();
            } else if (clientCommandJ.get("action").equals("getLastLoginTime")) {
                String username = clientCommandJ.get("username").toString();
                String result = UserDatabase.getUserByUsername(username).getLastLoginTime();
                dataOutputStream.writeUTF(result);
                dataOutputStream.flush();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void processGameUsingXML(String s, DataOutputStream dataOutputStream) throws IOException {
        String response = GameDatabaseServer.processReq(s);
        System.out.println(response);

        byte[] requestToBytes = response.getBytes(StandardCharsets.UTF_8);
        System.out.println(Arrays.toString(requestToBytes));
        dataOutputStream.writeInt(requestToBytes.length);
        dataOutputStream.flush();
        dataOutputStream.write(requestToBytes);
        dataOutputStream.flush();
    }

    private void processMainMenuReqs(JSONObject clientCommandJ, DataOutputStream dataOutputStream, int id) {
        if(clientCommandJ.get("action").equals("logout")) {
            ClientThread clientThread = ClientThread.getThreadID(id);
            if(clientThread != null && clientThread.getUsername() != null) {
                UserDatabase.disconnectUser(clientThread.getUsername());
            }
        }
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
            //    e.printStackTrace();
                System.out.println("Please try again");
                disconnected = false;
            }
        }
    }

    private void processLoginMenuReqs(JSONObject clientCommandJ, LoginMenuController loginMenuController, DataOutputStream dataOutputStream, boolean disconnected, int id) throws IOException {
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
                    System.out.println(User.users.size());
                    loginMenuController.userCreate(username, nickname, password);
                    System.out.println(User.users.size());
                    createAccount(username);
                    UserDatabase.writeInFile("UserDatabase.json");
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
                    ClientThread.setUsernames(username, id);
                    UserDatabase.onlineUsers.add(username);
                    dataOutputStream.writeUTF("success");
                    dataOutputStream.flush();
                }
            } else if (clientCommandJ.get("action").equals("Exit")) {
                UserDatabase.writeInFile("UserDatabase.json");
            } else if (clientCommandJ.get("action").equals("user")) {
                String username = clientCommandJ.get("username").toString();
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String toJson = gson.toJson(UserDatabase.getUserByUsername(username));
                dataOutputStream.writeUTF(toJson);
                dataOutputStream.flush();
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
