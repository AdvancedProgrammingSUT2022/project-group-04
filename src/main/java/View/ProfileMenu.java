package View;

import Controller.ProfileMenuController;
import Controller.UserController;
import Model.User;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu extends Menu {

    private ProfileMenuController profileMenuController;
    private UserController userController;
    private static final String PROFILE_CHANGE_USERNAME = "profile change --username (?<username>\\S+)";
    private static final String PROFILE_CHANGE_NICKNAME = "profile change --nickname (?<nickname>\\S+)";
    private static final String PROFILE_CHANGE_PASSWORD = "profile change --password --current (?<currentPassword>\\S+) --new (?<newPassword>\\S+)";
    private static final String SHORT_CHANGE_USERNAME = "profile change -u (?<username>\\S+)";
    private static final String SHORT_CHANGE_NICKNAME = "profile change -n (?<nickname>\\S+)";
    private static final String SHORT_CHANGE_PASSWORD = "profile change -p -c (?<currentPassword>\\S+) -n (?<newPassword>\\S+)";

    public ProfileMenu(ProfileMenuController profileMenuController) {
        this.profileMenuController = profileMenuController;
        this.userController = new UserController();
    }

    public User run(Scanner scanner, User loggedInUser) {
        System.out.println("Profile Menu");
        String command;
        while (true) {
            Matcher matcher;
            command = this.profileMenuController.commandCorrector(scanner.nextLine());
            if (command.equals("menu exit")) {
                return null;
            } else if ((matcher = getCommandMatcher(command, MENU_SHOW)) != null) {
                System.out.println(menuShow());
            } else if ((matcher = getCommandMatcher(command, MENU_ENTER)) != null) {
                System.out.println(menuEnter(matcher));
            } else if (((matcher = getCommandMatcher(command, PROFILE_CHANGE_USERNAME)) != null)
                    || ((matcher = getCommandMatcher(command, SHORT_CHANGE_USERNAME)) != null)) {
                System.out.println(changeUsername(loggedInUser, matcher));
            } else if (((matcher = getCommandMatcher(command, PROFILE_CHANGE_NICKNAME)) != null)
                    || ((matcher = getCommandMatcher(command, SHORT_CHANGE_NICKNAME)) != null)) {
                System.out.println(changeNickname(loggedInUser, matcher));
            } else if (((matcher = getCommandMatcher(command, PROFILE_CHANGE_PASSWORD)) != null)
                    || ((matcher = getCommandMatcher(command, SHORT_CHANGE_PASSWORD)) != null)) {
                System.out.println(changePassword(loggedInUser, matcher));
            } else {
                System.out.println("invalid command");
            }
        }
    }

    @Override
    public String menuShow() {
        return "Profile Menu";
    }

    private String menuEnter(Matcher matcher) {
        return "menu navigation is not possible";
    }

    /**
     * changes username
     *
     * @param loggedInUser
     * @param matcher
     * @return
     */
    private String changeUsername(User loggedInUser, Matcher matcher) {
        String username = matcher.group("username");
        if (!this.profileMenuController.isUsernameUnique(username)) {
            return "user with username " + username + " already exists";
        }
        this.profileMenuController.changeUsername(loggedInUser, username);
        return "username changed successfully!";
    }

    /**
     * changes nickname
     *
     * @param loggedInUser
     * @param matcher
     */
    private String changeNickname(User loggedInUser, Matcher matcher) {
        String nickname = matcher.group("nickname");
        if (!this.profileMenuController.isNicknameUnique(nickname)) {
            return "user with nickname " + nickname + " already exists";
        }
        this.profileMenuController.changeNickname(loggedInUser, nickname);
        return "nickname changed successfully!";
    }

    /**
     * changes password
     *
     * @param loggedInUser
     * @param matcher
     */
    private String changePassword(User loggedInUser, Matcher matcher) {
        String password = matcher.group("currentPassword");
        String newPassword = matcher.group("newPassword");
        if (!this.userController.isPasswordCorrect(loggedInUser.getUsername(), password)) {
            return "current password is invalid";
        }
        if (!this.profileMenuController.isNewPasswordDifferent(password, newPassword)) {
            return "please enter a new password";
        }
        this.profileMenuController.changePassword(loggedInUser, newPassword);
        return "password changed successfully!";
    }

}
