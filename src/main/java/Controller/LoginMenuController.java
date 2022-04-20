package Controller;

import Model.LoginMenuModel;
import Model.User;
import Database.UserDatabase;

public class LoginMenuController {

    private LoginMenuModel loginMenuModel;
    private UserController userController;

    public LoginMenuController(LoginMenuModel loginMenuModel) {
        this.loginMenuModel = loginMenuModel;
        this.userController = new UserController();
    }

    public boolean isUsernameUnique(String username) {
        User user = UserDatabase.getUserByUsername(username);
        if(user == null) {
            return true;
        }
        return false;
    }

    public boolean isNicknameUnique(String nickname) {
        User user = UserDatabase.getUserByNickname(nickname);
        if(user == null) {
            return true;
        }
        return false;
    }

    public void userCreate(String username, String nickname, String password) {
        User newUser = new User(username, nickname, password);
        this.loginMenuModel.userCreate(newUser);
    }

    public boolean isUserExists(String username) {
        User user = UserDatabase.getUserByUsername(username);
        if(user == null) {
            return false;
        }
        return true;
    }

    public boolean isPasswordCorrect(String username, String password) {
        return this.userController.isPasswordCorrect(username, password);
    }

    public String commandCorrector(String command) {
        if(command.startsWith("user create")) {
            return correctUserCreate(command);
        }
        if(command.startsWith("user login")) {
            return correctUserLogin(command);
        }
        return command;
    }

    private String correctUserCreate(String command) {
        String correctCommand = "user create ";
        String[] splitCommand = command.split(" ");

        // search for --username or -u
        for(int i=0; i<splitCommand.length; i++) {
            if(splitCommand[i].equals("--username") || splitCommand[i].equals("-u")) {
                if(i == splitCommand.length - 1) {
                    return command;
                }
                if(splitCommand[i+1].startsWith("-")) {
                    return command;
                }
                correctCommand += splitCommand[i] + " " + splitCommand[i+1] + " ";
                break;
            }
        }

        // search for --nickname or -n
        for(int i=0; i<splitCommand.length; i++) {
            if(splitCommand[i].equals("--nickname") || splitCommand[i].equals("-n")) {
                if(i == splitCommand.length - 1) {
                    return command;
                }
                if(splitCommand[i+1].startsWith("-")) {
                    return command;
                }
                correctCommand += splitCommand[i] + " " + splitCommand[i+1] + " ";
                break;
            }
        }

        // search for --password or -p
        for(int i=0; i<splitCommand.length; i++) {
            if(splitCommand[i].equals("--password") || splitCommand[i].equals("-p")) {
                if(i == splitCommand.length - 1) {
                    return command;
                }
                if(splitCommand[i+1].startsWith("-")) {
                    return command;
                }
                correctCommand += splitCommand[i] + " " + splitCommand[i+1];
                break;
            }
        }

        return correctCommand;
    }

    private String correctUserLogin(String command) {
        String correctCommand = "user login ";
        String[] splitCommand = command.split(" ");

        // search for --username or -u
        for(int i=0; i<splitCommand.length; i++) {
            if(splitCommand[i].equals("--username") || splitCommand[i].equals("-u")) {
                if(i == splitCommand.length - 1) {
                    return command;
                }
                if(splitCommand[i+1].startsWith("-")) {
                    return command;
                }
                correctCommand += splitCommand[i] + " " + splitCommand[i+1] + " ";
                break;
            }
        }

        // search for --password or -p
        for(int i=0; i<splitCommand.length; i++) {
            if(splitCommand[i].equals("--password") || splitCommand[i].equals("-p")) {
                if(i == splitCommand.length - 1) {
                    return command;
                }
                if(splitCommand[i+1].startsWith("-")) {
                    return command;
                }
                correctCommand += splitCommand[i] + " " + splitCommand[i+1];
                break;
            }
        }

        return correctCommand;
    }
}
