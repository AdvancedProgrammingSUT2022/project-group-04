package ControllerTest;

import Civilization.Controller.MainMenuController;
import Civilization.Database.UserDatabase;
import Civilization.Model.MainMenuModel;
import Civilization.Model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mockStatic;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@ExtendWith(MockitoExtension.class)
public class MainMenuControllerTest {

    static MockedStatic<UserDatabase> database;

    @Mock
    MainMenuModel mainMenuModel;

    @BeforeAll
    public static void setUp() {
        database = mockStatic(UserDatabase.class);
    }

    @AfterAll
    public static void salam(){
        database.close();
    }

    /**
     * Test of isMenuValid method
     */
    @Test
    public void isMenuValid_GameMenu() {
        String menuName = "Game Menu";
        MainMenuController mainMenuController = new MainMenuController(mainMenuModel);
        assertTrue(mainMenuController.isMenuValid(menuName));
    }

    @Test
    public void isMenuValid_ProfileMenu() {
        String menuName = "Profile Menu";
        MainMenuController mainMenuController = new MainMenuController(mainMenuModel);
        assertTrue(mainMenuController.isMenuValid(menuName));
    }

    @Test
    public void isMenuValid_invalidMenu() {
        String menuName = "boz";
        MainMenuController mainMenuController = new MainMenuController(mainMenuModel);
        assertFalse(mainMenuController.isMenuValid(menuName));
    }

    /**
     * Test of isUserExists method
     */
    @Test
    public void isUserExists_notExists() {
        MainMenuController mainMenuControllerr = new MainMenuController(mainMenuModel);
        database.when(() -> UserDatabase.getUserByUsername("")).thenReturn(null);
        assertFalse(mainMenuControllerr.isUserExists(""));
    }

    @Test
    public void isUserExists_exists() {
        User user = new User("", "", "");
        MainMenuController mainMenuControllerr = new MainMenuController(mainMenuModel);
        database.when(() -> UserDatabase.getUserByUsername("")).thenReturn(user);
        assertTrue(mainMenuControllerr.isUserExists(""));
    }
}
