package ViewTest;

import Civilization.Controller.MainMenuController;
import Civilization.Database.UserDatabase;
import Civilization.Model.User;
import Civilization.View.MainMenu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testng.annotations.Ignore;

import java.util.Scanner;
import java.util.regex.Matcher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.fail;

@ExtendWith(MockitoExtension.class)
public class MainMenuTest {

    static MockedStatic<UserDatabase> database;


    @Mock
    MainMenuController mainMenuController;

    @Mock
    Matcher matcher;

    @Mock
    Scanner scanner;

    @Mock
    User loggedInUser;

    @Mock
    User user;

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

    /**
     * Test of enterProfileMenu function
     */
    @Test
    public void enterProfileMenu() {
        when(scanner.nextLine()).thenReturn("menu exit");
        MainMenu mainMenu = new MainMenu(mainMenuController);
        mainMenu.enterProfileMenu(scanner, loggedInUser);
    }

    /**
     * Test of playGame function
     */
    @Test
    public void playGame_notUsersExists() {
        MainMenu mainMenu = new MainMenu(mainMenuController);
        when(matcher.group("command")).thenReturn(" -p1 sepehr -p2 alirezaRM");
        when(mainMenuController.isUserExists("sepehr")).thenReturn(false);
        assertEquals("at least one username dose not exists.", mainMenu.playGame(matcher));
    }

    @Test
    public void playGame_playWithYourself() {
        MainMenu mainMenu = new MainMenu(mainMenuController);
        when(matcher.group("command")).thenReturn(" -p1 sepehr");
        when(mainMenuController.isUserExists("sepehr")).thenReturn(true);
        assertEquals("you can't play with yourself!", mainMenu.playGame(matcher));
    }

    @Test
    public void playGame_enterGameMenu() {
        MainMenu mainMenu = new MainMenu(mainMenuController);
        when(matcher.group("command")).thenReturn(" -p1 sepehr -p2 alirezaRM");
        when(mainMenuController.isUserExists("sepehr")).thenReturn(true);
        when(mainMenuController.isUserExists("alirezaRM")).thenReturn(true);
        assertEquals("game started. good luck!", mainMenu.playGame(matcher));
    }

    /**
     * Test of enterGameMenu function
     */
    @Test
    public void enterGameMenu() {
        database = mockStatic(UserDatabase.class);
        MainMenu mainMenu = new MainMenu(mainMenuController);
        when(matcher.group("command")).thenReturn(" -p1 sepehr -p2 alirezaRM");
        when(scanner.nextLine()).thenReturn("win");
        database.when(() -> UserDatabase.getUserByUsername("sepehr")).thenReturn(user);
        database.when(() -> UserDatabase.getUserByUsername("alirezaRM")).thenReturn(user);
        when(user.getNickname()).thenReturn("n");
        mainMenu.enterGameMenu(scanner, matcher);
    }




}
