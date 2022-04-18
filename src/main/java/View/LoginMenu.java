package View;

import Controller.LoginMenuController;
import Model.User;

import java.util.Scanner;
import java.util.regex.Matcher;

import Model.User;
import Enums.LoginMenuCommands;
import Database.UserDatabase;

public class LoginMenu extends Menu{

    private LoginMenuController loginMenuController;

    public LoginMenu(LoginMenuController loginMenuController) {
        this.loginMenuController = loginMenuController;
    }

    public User run(Scanner scanner) {
        String command;
        while(true) {
            Matcher matcher;
            command = scanner.nextLine();
            if(command.equals("menu exit")) {
                return null;
            } else if((matcher = LoginMenuCommands.getCommandMatcher(command, LoginMenuCommands.MENU_SHOW)) != null) {
                System.out.println(menuShow(matcher));
            } else if((matcher = LoginMenuCommands.getCommandMatcher(command, LoginMenuCommands.MENU_ENTER)) != null) {
                System.out.println(menuEnter(matcher));
            } else if((matcher = LoginMenuCommands.getCommandMatcher(command, LoginMenuCommands.USER_CREATE)) != null) {
                System.out.println(userCreate(matcher));
            } else if((matcher = LoginMenuCommands.getCommandMatcher(command, LoginMenuCommands.USER_LOGIN)) != null) {
                String result = userLogin(matcher);
                System.out.println(result);
                if(result.endsWith("successfully!")) {
                    return UserDatabase.getUserByUsername(matcher.group("username"));
                }
            } else {
                System.out.println("invalid command");
            }
        }
    }

    @Override
    public String menuShow(Matcher matcher) {
        return "Login Menu";
    }

    private String menuEnter(Matcher matcher) {
        return "please login first";
    }

    private String userCreate(Matcher matcher) {
        String username = matcher.group("username");
        String nickname = matcher.group("nickname");
        String password = matcher.group("password");
        if(!this.loginMenuController.isUsernameUnique(username)) {
            return "user with username " + username + " already exists";
        }
        if(!this.loginMenuController.isNicknameUnique(nickname)) {
            return "user with nickname " + nickname + " already exists";
        }
        this.loginMenuController.userCreate(username, nickname, password);
        return "user created successfully!";
    }

    private String userLogin(Matcher matcher) {
        String username = matcher.group("username");
        String password = matcher.group("password");
        if(!this.loginMenuController.isUserExists(username)
                || !this.loginMenuController.isPasswordCorrect(username, password)) {
            return "Username and password didn't match";
        }
        return "user logged in successfully!";

    }

}
