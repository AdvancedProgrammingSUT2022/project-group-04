package View;

import Controller.ProfileMenuController;
import Controller.UserController;
import Model.User;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu extends Menu{

    private ProfileMenuController profileMenuController;
    private UserController userController;
    private static final String PROFILE_CHANGE_NICKNAME = "profile change --nickname (?<nickname>\\S+)";
    private static final String PROFILE_CHANGE_PASSWORD = "profile change --password --current (?<currentPassword>\\S+) --new (?<newPassword>\\S+)";
    private static final String SHORT_CHANGE_NICKNNAME = "profile change -n (?<nickname>\\S+)";
    private static final String SHORT_CHANGE_PASSWORD = "profile change -p -c (?<currentPassword>\\S+) -n (?<newPassword>\\S+)";

    public ProfileMenu(ProfileMenuController profileMenuController) {
        this.profileMenuController = profileMenuController;
        this.userController = new UserController();
    }

    public User run(Scanner scanner, User loggedinUser) {
        String command;
        while(true) {
            Matcher matcher;
            command = this.profileMenuController.commandCorrector(scanner.nextLine());
            if(command.equals("menu exit")) {
                return null;
            } else if((matcher = getCommandMatcher(command, MENU_SHOW)) != null) {
                System.out.println(menuShow(matcher));
            } else if((matcher = getCommandMatcher(command, MENU_ENTER)) != null) {
                System.out.println(menuEnter(matcher));
            } else if(((matcher = getCommandMatcher(command, PROFILE_CHANGE_NICKNAME)) != null)
                    || ((matcher = getCommandMatcher(command, SHORT_CHANGE_NICKNNAME)) != null)) {
                System.out.println(changeNickname(loggedinUser, matcher));
            } else if(((matcher = getCommandMatcher(command, PROFILE_CHANGE_PASSWORD)) != null)
                    || ((matcher = getCommandMatcher(command, SHORT_CHANGE_PASSWORD)) != null)) {
                System.out.println(changePassword(loggedinUser, matcher));
            } else {
                System.out.println("invalid command");
            }
        }
    }

    @Override
    public String menuShow(Matcher matcher) {
        return "Profile Menu";
    }

    private String menuEnter(Matcher matcher) {
        return "menu navigation is not possible";
    }

    private String changeNickname(User loggedinUser, Matcher matcher) {
        String nickname = matcher.group("nickname");
        if(!this.profileMenuController.isNicknameUnique(nickname)) {
            return "user with nickname " + nickname + " already exists";
        }
        this.profileMenuController.changeNickname(loggedinUser, nickname);
        return "nickname changed successfully!";
    }

    private String changePassword(User loggedinUser, Matcher matcher) {
        String password = matcher.group("currentPassword");
        String newPassword = matcher.group("newPassword");
        if(!this.userController.isPasswordCorrect(loggedinUser.getUsername(), password)) {
            return "current password is invalid";
        }
        if(!this.profileMenuController.isNewPasswordDiffrent(password, newPassword)) {
            return "please enter a new password";
        }
        this.profileMenuController.changePassword(loggedinUser, newPassword);
        return "password changed successfully!";
    }

}
