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


}
