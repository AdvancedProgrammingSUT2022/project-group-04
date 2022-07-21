package Server;

import java.util.ArrayList;

public class ClientThread {

    public static final ArrayList<ClientThread> clientThreads = new ArrayList<>();

    public static void clientLogout(int id) {
        for (ClientThread clientThread : ClientThread.clientThreads) {
            if(clientThread.getClientID() == id) {
                clientThread.logout();
            }
        }
    }

    public static void disconnectThread(int id) {
        for (ClientThread clientThread : ClientThread.clientThreads) {
            if(clientThread.getClientID() == id) {
                clientThread.disconnect();
            }
        }
    }

    public static ClientThread getThreadID(int id) {
        for (ClientThread clientThread : ClientThread.clientThreads) {
            if(clientThread.getClientID() == id) {
                clientThread.disconnect();
                return clientThread;
            }
        }
        return null;
    }

    private void disconnect() {
        this.disconnected = true;
    }

    public int getClientID() {
        return clientID;
    }

    public static int id = -1;

    private Thread userThread;
    private String username;
    private boolean logout;
    private boolean disconnected;
    private int clientID;

    public ClientThread() {
        this.username = null;
        this.logout = false;
        this.disconnected = false;
        ClientThread.id++;
        this.clientID = id;
        ClientThread.clientThreads.add(this);
    }

    public void setThread(Thread userThread) {
        this.userThread = userThread;
    }

    public static void setUsernames(String username, int id) {
        for (ClientThread clientThread : ClientThread.clientThreads) {
            if(clientThread.getClientID() == id) {
                clientThread.setUsername(username);
            }
        }
    }

    public void setUsername(String username) {
        this.username = username;
        this.logout = false;
    }

    public void logout() {
        this.logout = true;
        this.username = null;
    }

    public String getUsername() {
        return username;
    }

    public boolean isLogout() {
        return logout;
    }

    public static ArrayList<ClientThread> getConnectClientThreads() {
        ArrayList<ClientThread> clientThreads = new ArrayList<>();
        for (ClientThread clientThread : ClientThread.clientThreads) {
            if(!clientThread.disconnected) {
                clientThreads.add(clientThread);
            }
        }
        return clientThreads;
    }

    public static boolean isUserLogin(String username) {
        System.out.println(ClientThread.clientThreads.size());
        for (ClientThread connectClientThread : ClientThread.getConnectClientThreads()) {
            if(connectClientThread.getUsername() != null
                    && connectClientThread.getUsername().equals(username)
                    && !connectClientThread.isLogout()) {
                return true;
            }
        }
        return false;
    }
}
