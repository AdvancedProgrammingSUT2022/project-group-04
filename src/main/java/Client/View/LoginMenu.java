package Client.View;

import Server.Controller.LoginMenuController;
import Server.User;

import java.util.Scanner;
import java.util.regex.Matcher;

import Server.UserDatabase;

public class LoginMenu extends Menu {

    private LoginMenuController loginMenuController;
    private static final String USER_CREATE = "user create --username (?<username>\\S+) --nickname (?<nickname>\\S+) --password (?<password>\\S+)";
    private static final String USER_LOGIN = "user login --username (?<username>\\S+) --password (?<password>\\S+)";
    private static final String SHORT_USER_CREATE = "user create -u (?<username>\\S+) -n (?<nickname>\\S+) -p (?<password>\\S+)";
    private static final String SHORT_USER_LOGIN = "user login -u (?<username>\\S+) -p (?<password>\\S+)";

    public LoginMenu(LoginMenuController loginMenuController) {
        this.loginMenuController = loginMenuController;
    }

    public User run(Scanner scanner) {
        String command;
        while (true) {
            Matcher matcher;
            command = this.loginMenuController.commandCorrector(scanner.nextLine());
            if (command.equals("menu exit")) {
                return null;
            } else if ((matcher = getCommandMatcher(command, MENU_SHOW)) != null) {
                System.out.println(menuShow());
            } else if ((matcher = getCommandMatcher(command, MENU_ENTER)) != null) {
                System.out.println(menuEnter(matcher));
            } else if (((matcher = getCommandMatcher(command, USER_CREATE)) != null)
                    || ((matcher = getCommandMatcher(command, SHORT_USER_CREATE)) != null)) {
                System.out.println(userCreate(matcher));
            } else if (((matcher = getCommandMatcher(command, USER_LOGIN)) != null)
                    || ((matcher = getCommandMatcher(command, SHORT_USER_LOGIN)) != null)) {
                String result = userLogin(matcher);
                System.out.println(result);
                if (result.endsWith("successfully!")) {
                    return UserDatabase.getUserByUsername(matcher.group("username"));
                }
            } else {
                System.out.println("invalid command");
            }
        }
    }

    @Override
    public String menuShow() {
        return "Login Menu";
    }

    public String menuEnter(Matcher matcher) {
        return "please login first";
    }

    /**
     * creates a new user
     *
     * @param matcher
     */
    public String userCreate(Matcher matcher) {
        String username = matcher.group("username");
        String nickname = matcher.group("nickname");
        String password = matcher.group("password");
        if (!this.loginMenuController.isUsernameUnique(username)) {
            return "user with username " + username + " already exists";
        }
        if (!this.loginMenuController.isNicknameUnique(nickname)) {
            return "user with nickname " + nickname + " already exists";
        }
        this.loginMenuController.userCreate(username, nickname, password);
        return "user created successfully!";
    }

    public String userLogin(Matcher matcher) {
        String username = matcher.group("username");
        String password = matcher.group("password");
        if (!this.loginMenuController.isUserExists(username)
                || !this.loginMenuController.isPasswordCorrect(username, password)) {
            return "Username and password didn't match";
        }
        return "user logged in successfully!";

    }

}
