package Enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MapAndGeneralCommandsOfGameMenu {

    MENU_SHOW("menu show-current"),
    MENU_ENTER("menu enter (?<MenuName>.+)"),
    MENU_EXIT("menu exit");

    private String regex;

    MapAndGeneralCommandsOfGameMenu(String regex) {
        this.regex = regex;
    }

    public static Matcher getCommandMatcher(String input, MapAndGeneralCommandsOfGameMenu command) {
        Matcher matcher = Pattern.compile(command.regex).matcher(input);

        if (matcher.matches()) {
            return matcher;
        }
        return null;
    }
}
