package ViewTest;

import Controller.MainMenuController;
import View.MainMenu;
import View.ProfileMenu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.regex.Matcher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MainMenuTest {

    @Mock
    MainMenuController mainMenuController;

    @Mock
    Matcher matcher;

    @BeforeEach
    public void setUp() {

    }

    /**
     * Test of menu show-current command
     */
    @Test
    public void menuShow() {
        MainMenu mainMenu = new MainMenu(mainMenuController);
        assertEquals("Main Menu", mainMenu.menuShow());
    }

    /**
     * Test of menu enter command
     */
    @Test
    public void menuEnter_invalidMenu() {
        MainMenu mainMenu = new MainMenu(mainMenuController);
        when( matcher.group("MenuName")).thenReturn("");
        when(mainMenuController.isMenuValid("")).thenReturn(false);
        assertEquals("menu navigation is not possible", mainMenu.menuEnter(matcher));
    }
}
