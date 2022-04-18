package Enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginMenuCommands {
    MENU_SHOW("menu show-current"),
    MENU_ENTER("menu enter (?<MenuName>.+)"),
    USER_CREATE("user create --username (?<username>\\S+) --nickname (?<nickname>\\S+) --password (?<password>\\S+)"),
    USER_LOGIN("user login --username (?<username>\\S+) --password (?<password>\\S+)");

    private String regex;

    LoginMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getCommandMatcher(String input, LoginMenuCommands command) {
        Matcher matcher = Pattern.compile(command.regex).matcher(input);

        if (matcher.matches()) {
            return matcher;
        }
        return null;
    }
}
