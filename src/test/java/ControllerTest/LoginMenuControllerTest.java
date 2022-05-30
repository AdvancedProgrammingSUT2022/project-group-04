package ControllerTest;

import Civilization.Controller.LoginMenuController;
import Civilization.Controller.UserController;
import Civilization.Database.UserDatabase;
import Civilization.Model.LoginMenuModel;
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
public class LoginMenuControllerTest {

    static MockedStatic<UserDatabase> database;

    @Mock
    UserController userController;

    @Mock
    LoginMenuModel loginMenuModel;

    @BeforeAll
    public static void setUp() {
        database = mockStatic(UserDatabase.class);
    }

    @AfterAll
    public static void salam(){
        database.close();
    }
    /**
     * Test of isUsernameUnique method
     */
    @Test
    public void isUsernameUnique_uniqueUsername() {
        LoginMenuController loginMenuController = new LoginMenuController(loginMenuModel);
        database.when(() -> UserDatabase.getUserByUsername("")).thenReturn(null);
        assertTrue(loginMenuController.isUsernameUnique(""));
    }

    @Test
    public void isUsernameUnique_notUniqueUsername() {
        User user = new User("", "", "");
        LoginMenuController loginMenuController = new LoginMenuController(loginMenuModel);
        database.when(() -> UserDatabase.getUserByUsername("")).thenReturn(user);
        assertFalse(loginMenuController.isUsernameUnique(""));
    }

    /**
     * Test of isNicknameUnique method
     */
    @Test
    public void isNicknameUnique_uniqueNickname() {
        LoginMenuController loginMenuController = new LoginMenuController(loginMenuModel);
        database.when(() -> UserDatabase.getUserByNickname("")).thenReturn(null);
        assertTrue(loginMenuController.isNicknameUnique(""));
    }

    @Test
    public void isNicknameUnique_notUniqueNickname() {
        User user = new User("", "", "");
        LoginMenuController loginMenuController = new LoginMenuController(loginMenuModel);
        database.when(() -> UserDatabase.getUserByNickname("")).thenReturn(user);
        assertFalse(loginMenuController.isNicknameUnique(""));
    }

    /**
     * Test of isUserExists method
     */
    @Test
    public void isUserExists_notExists() {
        LoginMenuController loginMenuController = new LoginMenuController(loginMenuModel);
        database.when(() -> UserDatabase.getUserByUsername("")).thenReturn(null);
        assertFalse(loginMenuController.isUserExists(""));
    }

    @Test
    public void isUserExists_exists() {
        User user = new User("", "", "");
        LoginMenuController loginMenuController = new LoginMenuController(loginMenuModel);
        database.when(() -> UserDatabase.getUserByUsername("")).thenReturn(user);
        assertTrue(loginMenuController.isUserExists(""));
    }

    /**
     * Test isPasswordCorrect method
     */
    @Test
    public void isPasswordCorrect() {
        LoginMenuController loginMenuController = new LoginMenuController(loginMenuModel);
        assertFalse(loginMenuController.isPasswordCorrect("", ""));
    }

    /**
     * Test of CommandCorrector method
     */
    @Test
    public void commandCorrector_userCreateShort() {
        LoginMenuController loginMenuController = new LoginMenuController(loginMenuModel);
        String command = "user create -p 123456 -u sepehr -n smiz";
        assertEquals("user create -u sepehr -n smiz -p 123456", loginMenuController.commandCorrector(command));

    }

    @Test
    public void commandCorrector_userCreateLong() {
        LoginMenuController loginMenuController = new LoginMenuController(loginMenuModel);
        String command = "user create --password 123456 --username sepehr --nickname smiz";
        assertEquals("user create --username sepehr --nickname smiz --password 123456", loginMenuController.commandCorrector(command));

    }

    @Test
    public void commandCorrector_userCreateLongNotUsernameType1() {
        LoginMenuController loginMenuController = new LoginMenuController(loginMenuModel);
        String command = "user create --password 123456 --username";
        assertEquals(command, loginMenuController.commandCorrector(command));

    }

    @Test
    public void commandCorrector_userCreateLongNotUsernameType2() {
        LoginMenuController loginMenuController = new LoginMenuController(loginMenuModel);
        String command = "user create --password 123456 --username --nickname smiz";
        assertEquals(command, loginMenuController.commandCorrector(command));

    }

    @Test
    public void commandCorrector_userCreateLongNotPasswordType1() {
        LoginMenuController loginMenuController = new LoginMenuController(loginMenuModel);
        String command = "user create --username sepehr --nickname smiz --password";
        assertEquals(command, loginMenuController.commandCorrector(command));

    }

    @Test
    public void commandCorrector_userCreateLongNotPasswordType2() {
        LoginMenuController loginMenuController = new LoginMenuController(loginMenuModel);
        String command = "user create --password --username sepehr --nickname smiz";
        assertEquals(command, loginMenuController.commandCorrector(command));

    }

    @Test
    public void commandCorrector_userCreateLongNotNicknameType1() {
        LoginMenuController loginMenuController = new LoginMenuController(loginMenuModel);
        String command = "user create --username sepehr --password 123456 --nickname";
        assertEquals(command, loginMenuController.commandCorrector(command));

    }

    @Test
    public void commandCorrector_userCreateLongNotNicknameType2() {
        LoginMenuController loginMenuController = new LoginMenuController(loginMenuModel);
        String command = "user create --password 123456 --nickname --username sepehr";
        assertEquals(command, loginMenuController.commandCorrector(command));

    }

    @Test
    public void commandCorrector_userLoginShort() {
        LoginMenuController loginMenuController = new LoginMenuController(loginMenuModel);
        String command = "user login -p 123456 -u sepehr";
        assertEquals("user login -u sepehr -p 123456", loginMenuController.commandCorrector(command));

    }

    @Test
    public void commandCorrector_userLoginLong() {
        LoginMenuController loginMenuController = new LoginMenuController(loginMenuModel);
        String command = "user login --password 123456 --username sepehr";
        assertEquals("user login --username sepehr --password 123456", loginMenuController.commandCorrector(command));

    }

    @Test
    public void commandCorrector_userLoginLongNotUsernameType1() {
        LoginMenuController loginMenuController = new LoginMenuController(loginMenuModel);
        String command = "user login --password 123456 --username";
        assertEquals(command, loginMenuController.commandCorrector(command));

    }

    @Test
    public void commandCorrector_userLoginLongNotUsernameType2() {
        LoginMenuController loginMenuController = new LoginMenuController(loginMenuModel);
        String command = "user login --username --password 123456";
        assertEquals(command, loginMenuController.commandCorrector(command));

    }

    @Test
    public void commandCorrector_userLoginLongNotPasswordType1() {
        LoginMenuController loginMenuController = new LoginMenuController(loginMenuModel);
        String command = "user login --username sepehr --password";
        assertEquals(command, loginMenuController.commandCorrector(command));

    }

    @Test
    public void commandCorrector_userLoginLongNotPasswordType2() {
        LoginMenuController loginMenuController = new LoginMenuController(loginMenuModel);
        String command = "user login --password --username sepehr";
        assertEquals(command, loginMenuController.commandCorrector(command));

    }

    @Test
    public void commandCorrector_invalidCommand() {
        LoginMenuController loginMenuController = new LoginMenuController(loginMenuModel);
        String command = "boz boz-e ghandi";
        assertEquals(command, loginMenuController.commandCorrector(command));

    }


}
