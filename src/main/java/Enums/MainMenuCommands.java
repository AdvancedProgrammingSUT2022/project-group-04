package Enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MainMenuCommands {
    MENU_SHOW("menu show-current"),
    MENU_ENTER("menu enter (?<MenuName>.+)"),
    PLAY_GAME("play game");

    private String regex;

    MainMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getCommandMatcher(String input, MainMenuCommands command) {
        Matcher matcher = Pattern.compile(command.regex).matcher(input);

        if (matcher.matches()) {
            return matcher;
        }
        return null;
    }
}
