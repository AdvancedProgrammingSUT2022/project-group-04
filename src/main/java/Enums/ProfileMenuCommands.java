package Enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProfileMenuCommands {
    MENU_SHOW("menu show-current"),
    MENU_ENTER("menu enter (?<MenuName>.+)"),
    PROFILE_CHANGE_NICKNAME("profile change --nickname (?<nickname>\\S+)"),
    PROFILE_CHANGE_PASSWORD("profile change --password --current (?<currentPassword>\\S+) --new (?<newPassword>\\S+)");

    private String regex;

    ProfileMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getCommandMatcher(String input, ProfileMenuCommands command) {
        Matcher matcher = Pattern.compile(command.regex).matcher(input);

        if (matcher.matches()) {
            return matcher;
        }
        return null;
    }
}
