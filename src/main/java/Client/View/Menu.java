package Client.View;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Menu {

    // Commands in all menus
    protected static final String MENU_SHOW = "menu show-current";
    protected static final String MENU_ENTER = "menu enter (?<MenuName>.+)";
    protected static final String MENU_EXIT = "menu exit";

    public String menuShow() {
        return "Menu";
    }

    protected Matcher getCommandMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            return matcher;
        }
        return null;
    }
}
