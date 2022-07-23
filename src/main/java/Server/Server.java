package Server;

import Civilization.Controller.LoginMenuController;
import Civilization.Controller.ProfileMenuController;
import Civilization.Model.Friendship;
import Civilization.Model.LoginMenuModel;
import Civilization.Model.ProfileMenuModel;
import Client.View.Components.Account;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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
        Friendship.readFriendships("friendshipDatabase.json");
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
                    } else if (clientCommandJ.get("menu type").equals("Friendship")) {
                        processFriendshipMenuReqs(clientCommandJ, dataOutputStream);
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

    private void processFriendshipMenuReqs(JSONObject clientCommandJ, DataOutputStream dataOutputStream) throws IOException {
        if(clientCommandJ.get("action").equals("getUsers")) {
            String username = clientCommandJ.get("username").toString();
            String result = UserDatabase.searchFor(username, 5);
            dataOutputStream.writeUTF(result);
            dataOutputStream.flush();
        } else if (clientCommandJ.get("action").equals("information")) {
            String username = clientCommandJ.get("username").toString();
            User result = UserDatabase.getUserByUsername(username);
            if(result == null) {
                dataOutputStream.writeUTF("");
                dataOutputStream.flush();
            } else {
                dataOutputStream.writeUTF(result.toString());
                dataOutputStream.flush();
            }
        } else if (clientCommandJ.get("action").equals("friendship")) {
            String firstUsername = clientCommandJ.get("firstUsername").toString();
            String secondUsername = clientCommandJ.get("secondUsername").toString();
            Friendship.addFriendship(firstUsername, secondUsername);
            Friendship.writeFriendships("friendshipDatabase.json");
        } else if (clientCommandJ.get("action").equals("request")) {
            String firstUsername = clientCommandJ.get("firstUsername").toString();
            String secondUsername = clientCommandJ.get("secondUsername").toString();
            dataOutputStream.writeUTF(Boolean.toString(Friendship.isRequestingValid(firstUsername, secondUsername)));
            dataOutputStream.flush();
        } else if (clientCommandJ.get("action").equals("accept")) {
            String firstUsername = clientCommandJ.get("firstUsername").toString();
            String secondUsername = clientCommandJ.get("secondUsername").toString();
            Friendship friendship = Friendship.getFriendshipByUsernames(firstUsername, secondUsername);
            if(friendship != null) {
                System.out.println("Not null");
                friendship.accept();
            }
            Friendship.writeFriendships("friendshipDatabase.json");
        } else if (clientCommandJ.get("action").equals("deny")) {
            String firstUsername = clientCommandJ.get("firstUsername").toString();
            String secondUsername = clientCommandJ.get("secondUsername").toString();
            Friendship friendship = Friendship.getFriendshipByUsernames(firstUsername, secondUsername);
            if (friendship != null) {
                System.out.println("Not null");
                friendship.deny();
            }
            Friendship.writeFriendships("friendshipDatabase.json");
        } else if (clientCommandJ.get("action").equals("getRequests")) {
            String username = clientCommandJ.get("username").toString();
            ArrayList<String> users = Friendship.getMyRequests(username);
            String result = "";
            for (String user : users) {
                result += user + " ";
            }
            dataOutputStream.writeUTF(result);
            dataOutputStream.flush();
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
            } else if(clientCommandJ.get("action").equals("friendship")) {
                System.out.println("Friendship");
                String firstUsername = clientCommandJ.get("firstUsername").toString();
                String secondUsername = clientCommandJ.get("secondUsername").toString();
                Friendship.addFriendship(firstUsername, secondUsername);
                Friendship.writeFriendships("friendshipDatabase.json");
                System.out.println("Hiiiiii");
            } else if (clientCommandJ.get("action").equals("request")) {
                String firstUsername = clientCommandJ.get("firstUsername").toString();
                String secondUsername = clientCommandJ.get("secondUsername").toString();
                dataOutputStream.writeUTF(Boolean.toString(Friendship.isRequestingValid(firstUsername, secondUsername)));
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

    private void processMainMenuReqs(JSONObject clientCommandJ, DataOutputStream dataOutputStream, int id) throws IOException {
        if(clientCommandJ.get("action").equals("logout")) {
            ClientThread clientThread = ClientThread.getThreadID(id);
            if(clientThread != null && clientThread.getUsername() != null) {
                UserDatabase.disconnectUser(clientThread.getUsername());
            }
            UserDatabase.writeInFile("UserDatabase.json");
        } else if (clientCommandJ.get("action").equals("profile")) {
            UserDatabase.readFromFile("UserDatabase.json");
            Account.readAccounts("AccountURLs.json");
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
                String username = clientCommandJ.get("username").toString();
                //System.out.println(username);
                if (!profileMenuController.isPasswordCorrect(UserDatabase.getUserByUsername(username).getUsername(), password)) {
                    //System.out.println("invalid");
                    dataOutputStream.writeUTF("current password is invalid");
                    dataOutputStream.flush();
                    return;
                }
                if (!profileMenuController.isNewPasswordDifferent(password, newPassword)
                        || !profileMenuController.isPasswordValid(newPassword)) {
                    //System.out.println("need new");
                    dataOutputStream.writeUTF("Please enter a new password");
                    dataOutputStream.flush();
                    return;
                }
                //System.out.println("username");
                profileMenuController.changePassword(UserDatabase.getUserByUsername(username), newPassword);
                UserDatabase.writeInFile("UserDatabase.json");
                dataOutputStream.writeUTF("password changed successfully");
                dataOutputStream.flush();
            } else if (clientCommandJ.get("action").equals("change nickname")) {
                //System.out.println("changing nickname");
                String nickname = clientCommandJ.get("nickname").toString();
                //System.out.println(nickname);
                if (!profileMenuController.isNicknameUnique(nickname)) {
                    //System.out.println("not unique");
                    dataOutputStream.writeUTF("Nickname is not unique");
                    dataOutputStream.flush();
                    return;
                }
                if (!profileMenuController.isNicknameValid(nickname)) {
                    //System.out.println("invalid");
                    dataOutputStream.writeUTF("Please enter a new nickname");
                    dataOutputStream.flush();
                    return;
                }
                //System.out.println("start");
                String username = clientCommandJ.get("username").toString();
                //System.out.println(username);
                profileMenuController.changeNickname(username, nickname);
                //System.out.println("changed");
                UserDatabase.writeInFile("UserDatabase.json");
                //System.out.println("writing");
                dataOutputStream.writeUTF("nickname changed successfully");
                dataOutputStream.flush();
            } else if (clientCommandJ.get("action").equals("avatarURL")) {
                User user = UserDatabase.getUserByUsername(clientCommandJ.get("username").toString());
                //System.out.printf("User %s \n", user);
                Account account = Account.getUserAccount(user);
                //System.out.printf("Account %s \n", account);
                //System.out.printf("AvatarURL %s \n", account.getAvatarURL());
                dataOutputStream.writeUTF(account.getAvatarURL());
                dataOutputStream.flush();
            } else if (clientCommandJ.get("action").equals("getRequests")) {
                String username = clientCommandJ.get("username").toString();
                dataOutputStream.writeUTF(Friendship.getMyRequestsOrDeniedFriendshipsString(username));
                dataOutputStream.flush();
            } else if (clientCommandJ.get("action").equals("getFriends")) {
                String username = clientCommandJ.get("username").toString();
                dataOutputStream.writeUTF(Friendship.getMyFriendsString(username));
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
                    //System.out.println(User.users.size());
                    loginMenuController.userCreate(username, nickname, password);
                    //System.out.println(User.users.size());
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
