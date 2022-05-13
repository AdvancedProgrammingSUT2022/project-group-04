package ViewTest;

import Controller.MainMenuController;
import View.MainMenu;
import View.ProfileMenu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testng.annotations.Ignore;

import java.util.Scanner;
import java.util.regex.Matcher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.fail;

@ExtendWith(MockitoExtension.class)
public class MainMenuTest {

    @Mock
    MainMenuController mainMenuController;

    @Mock
    Matcher matcher;

    @Mock
    ProfileMenu profileMenu;

    @BeforeEach
    public void setUp() {

    }

    @Ignore
    public void testNotExecuted() {
        fail("It should not executed");
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
        when(matcher.group("MenuName")).thenReturn("");
        when(mainMenuController.isMenuValid("")).thenReturn(false);
        assertEquals("menu navigation is not possible", mainMenu.menuEnter(matcher));
    }

    @Test
    public void menuEnter_gameMenu() {
        MainMenu mainMenu = new MainMenu(mainMenuController);
        String menuName = "Game Menu";
        when(matcher.group("MenuName")).thenReturn(menuName);
        when(mainMenuController.isMenuValid(menuName)).thenReturn(true);
        assertEquals("you must use play game command.", mainMenu.menuEnter(matcher));
    }

    @Test
    public void menuEnter_profileMenu() {
        MainMenu mainMenu = new MainMenu(mainMenuController);
        String menuName = "Profile Menu";
        when(matcher.group("MenuName")).thenReturn(menuName);
        when(mainMenuController.isMenuValid(menuName)).thenReturn(true);
        assertNull(mainMenu.menuEnter(matcher));
    }

    /**
     * Test of menu exit command
     */
    @Test
    public void menuExit() {
        MainMenu mainMenu = new MainMenu(mainMenuController);
        assertEquals("use user logout command", mainMenu.menuExit(matcher));
    }


}
