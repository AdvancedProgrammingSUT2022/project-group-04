package Controller;

import Database.UserDatabase;
import Model.ProfileMenuModel;
import Model.User;

public class ProfileMenuController {

    private ProfileMenuModel profileMenuModel;

    public ProfileMenuController(ProfileMenuModel profileMenuModel) {
        this.profileMenuModel = profileMenuModel;
    }

    public boolean isNicknameUnique(String nickname) {
        User user = UserDatabase.getUserByNickname(nickname);
        if(user == null) {
            return true;
        }
        return false;
    }

    public boolean isNewPasswordDiffrent(String password, String newPassword) {
        if(password.equals(newPassword)) {
            return false;
        }
        return true;
    }

    public void changeNickname(User loggedinUser, String nickname) {
        this.profileMenuModel.changeNickname(loggedinUser, nickname);
    }

    public void changePassword(User loggedinUser, String password) {
        this.profileMenuModel.changePassword(loggedinUser, password);
    }

    public String commandCorrector(String command) {
        if(command.startsWith("profile change") && isChangeRequestPassword(command)) {
            return correctChangePassword(command);
        }
        return command;
    }

    private boolean isChangeRequestPassword(String command) {
        String[] splitCommand = command.split(" ");
        for(int i=0; i<splitCommand.length; i++) {
            if(splitCommand[i].equals("--password") || splitCommand[i].equals("-p")) {
                return true;
            }
        }
        return false;
    }

    private String correctChangePassword(String command) {
        String[] splitCommand = command.split(" ");
        String correctCommand = "profile change ";

        // sreach for --password or -p
        for(int i=0; i<splitCommand.length; i++) {
            if(splitCommand[i].equals("--password") || splitCommand[i].equals("-p")) {
                correctCommand += splitCommand[i] + " ";
                break;
            }
        }

        // search for --current or -c
        for(int i=0; i<splitCommand.length; i++) {
            if(splitCommand[i].equals("--current") || splitCommand[i].equals("-c")) {
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

        // search for --new or -n
        for(int i=0; i<splitCommand.length; i++) {
            if(splitCommand[i].equals("--new") || splitCommand[i].equals("-n")) {
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
