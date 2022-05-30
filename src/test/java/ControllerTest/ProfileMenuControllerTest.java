package ControllerTest;

import Civilization.Controller.ProfileMenuController;
import Civilization.Controller.UserController;
import Civilization.Database.UserDatabase;
import Civilization.Model.ProfileMenuModel;
import Civilization.Model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

@ExtendWith(MockitoExtension.class)
public class ProfileMenuControllerTest {

    static MockedStatic<UserDatabase> database;

    @Mock
    ProfileMenuModel profileMenuModel;

    @Mock
    UserController userController;

    @Mock
    User loggedInUser;

    @BeforeAll
    public static void setUp() {
        database = mockStatic(UserDatabase.class);
    }

    @AfterAll
    public static void salam(){
        database.close();
    }
    /**
     * Test of isNicknameUnique method
     */
    @Test
    public void isNicknameUnique_userNotExists() {
        ProfileMenuController profileMenuController = new ProfileMenuController(profileMenuModel);
        database.when(() -> UserDatabase.getUserByNickname("")).thenReturn(null);
        assertTrue(profileMenuController.isNicknameUnique(""));

    }

    @Test
    public void isNicknameUnique_userExists() {
        ProfileMenuController profileMenuController = new ProfileMenuController(profileMenuModel);
        User user = new User("", "", "");
        database.when(() -> UserDatabase.getUserByNickname("")).thenReturn(user);
        assertFalse(profileMenuController.isNicknameUnique(""));

    }

    /**
     * Test of isUsernameUnique method
     */
    @Test
    public void isUsernameUnique_userNotExists() {
        ProfileMenuController profileMenuController = new ProfileMenuController(profileMenuModel);
        database.when(() -> UserDatabase.getUserByUsername("")).thenReturn(null);
        assertTrue(profileMenuController.isUsernameUnique(""));

    }

    @Test
    public void isUsernameUnique_userExists() {
        ProfileMenuController profileMenuController = new ProfileMenuController(profileMenuModel);
        User user = new User("", "", "");
        database.when(() -> UserDatabase.getUserByUsername("")).thenReturn(user);
        assertFalse(profileMenuController.isUsernameUnique(""));

    }

    /**
     * Test of isNewPasswordDifferent method
     */
    @Test
    public void isNewPasswordDifferent_notDifferentPassword() {
        ProfileMenuController profileMenuController = new ProfileMenuController(profileMenuModel);
        String password = "";
        String newPassword = "";
        assertFalse(profileMenuController.isNewPasswordDifferent(password, newPassword));
    }

    @Test
    public void isNewPasswordDifferent_DifferentPassword() {
        ProfileMenuController profileMenuController = new ProfileMenuController(profileMenuModel);
        String password = "";
        String newPassword = "p";
        assertTrue(profileMenuController.isNewPasswordDifferent(password, newPassword));
    }

    /**
     * Test of changeUsername method
     */
    @Test
    public void changeUsername() {
        ProfileMenuController profileMenuController = new ProfileMenuController(profileMenuModel);
        profileMenuController.changeUsername(loggedInUser, "");
        verify(profileMenuModel).changeUsername(loggedInUser, "");
    }

    /**
     * Test of changeNickname method
     */
    @Test
    public void changeNickname() {
        ProfileMenuController profileMenuController = new ProfileMenuController(profileMenuModel);
        profileMenuController.changeNickname(loggedInUser, "");
        verify(profileMenuModel).changeNickname(loggedInUser, "");
    }

    /**
     * Test of changePassword method
     */
    @Test
    public void changePassword() {
        ProfileMenuController profileMenuController = new ProfileMenuController(profileMenuModel);
        profileMenuController.changePassword(loggedInUser, "");
        verify(profileMenuModel).changePassword(loggedInUser, "");
    }

    /**
     * Test of commandCorrector method
     */
    @Test
    public void commandCorrector_changePasswordShort() {
        ProfileMenuController profileMenuController = new ProfileMenuController(profileMenuModel);
        String command = "profile change -p -n 123456 -c 123456";
        assertEquals("profile change -p -c 123456 -n 123456", profileMenuController.commandCorrector(command));
    }

    @Test
    public void commandCorrector_changeNickname() {
        ProfileMenuController profileMenuController = new ProfileMenuController(profileMenuModel);
        String command = "profile change -n 123456";
        assertEquals("profile change -n 123456", profileMenuController.commandCorrector(command));
    }

    @Test
    public void commandCorrector_invalidCurrentPasswordType1() {
        ProfileMenuController profileMenuController = new ProfileMenuController(profileMenuModel);
        String command = "profile change -p -n 123456 -c";
        assertEquals("profile change -p -n 123456 -c", profileMenuController.commandCorrector(command));
    }

    @Test
    public void commandCorrector_invalidCurrentPasswordType2() {
        ProfileMenuController profileMenuController = new ProfileMenuController(profileMenuModel);
        String command = "profile change -p -c -n 123456";
        assertEquals("profile change -p -c -n 123456", profileMenuController.commandCorrector(command));
    }

    @Test
    public void commandCorrector_invalidNewPasswordType1() {
        ProfileMenuController profileMenuController = new ProfileMenuController(profileMenuModel);
        String command = "profile change -p -c 123456 -n";
        assertEquals("profile change -p -c 123456 -n", profileMenuController.commandCorrector(command));
    }

    @Test
    public void commandCorrector_invalidNewPasswordType2() {
        ProfileMenuController profileMenuController = new ProfileMenuController(profileMenuModel);
        String command = "profile change -p -n -c 123456";
        assertEquals("profile change -p -n -c 123456", profileMenuController.commandCorrector(command));
    }

}
